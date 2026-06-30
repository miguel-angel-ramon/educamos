package es.jccm.edu.proyectosfct.adapter.out.repositories.segsocialcotizames;

import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoSegSocialCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.CotizaMesProgramas;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.QCotizaMesProgramas;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SegSocialCotizaMesRepository extends AbstractRepository<CotizaMesProgramas, Long, QCotizaMesProgramas> {

    @Query(value= "SELECT * "
      + "FROM ( "
      + "    SELECT  "
      + "        id, "
      + "        idAluCon, "
      + "        centro, "
      + "        tutor, "
      + "        dni, "
      + "        nuss, "
      + "        nombreAlumno, "
      + "        nombreEmpresa, "
      + "        tipoEmpresa, "
      + "        curso, "
      + "        unidad,         "
      + "        nuDiasReal, "
      + "        nuDiasInte, "
      + "        nuDiasNacu, "
      + "        valTut, "
      + "        valCen, "
      + "        valDel, "
      + "        enviado, "
      + "        puederechazar, "      
      + "        ROW_NUMBER() OVER ( "
      + "                PARTITION BY nuss,  "
      + "                CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  "
      + "                ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon "
      + "        ) AS row_num, "
      + "        lgErasBec, "
      + "        diasInteEra, "
      + "        tipoErasmus, "
      + "     dsWarnings, "
      + "     fechaEnvio, "
      + "     esCorrecto, "
      + "     esCorrectoAlta, "
      + "     TO_CHAR(inicio, 'DD/MM/YYYY') inicio, "
      + "     TO_CHAR(fin, 'DD/MM/YYYY') fin, "
      + "    CASE    "
      + "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " 
      + "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) "   
      + "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    "
      + "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN "   
      + "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    "
      + "    ELSE 0  "  
      + "    END AS diasRestantes, "
      + "    0 AS avisoMes, "
      + "    1 AS bloqueoMes "
      + "  FROM    "
      + "  (select CASE "                  
      + "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "                                             
      + "          ELSE TO_CHAR(:cAnno)  END anno                  "
      + "        FROM DUAL) annonat, "
      + "   ( "
      + " SELECT distinct id, idAluCon, null centro, null tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,               "
      + "                CASE               "
      + "                WHEN nuDiasReal>maxdias THEN maxdias               "
      + "                ELSE nuDiasReal END nuDiasReal,               "
      + "                nuDiasInte, nuDiasNacu,  valTut, valCen, valDel, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, esCorrectoAlta, inicio, fin FROM (                              "
      + "                SELECT NVL(mes.id_cotizames_prog,-1) as id,                  "
      + "                       cpa.id_convprog_alu as idAluCon,                 "
      + "                       alu.c_numide as dni,                       "
      + "                       alu.t_nuss as nuss,                        "
      + "                       alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                         "
      + "                       emp.d_empresa as nombreEmpresa,                         "
      + "                       'FCT' as tipoEmpresa,                         "
      + "                       omg.s_ofertamatrig curso,                         "
      + "                       uni.t_nombre unidad,                             "
      + "                       NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par                    "
      + "                       where par.id_convprog_alu = alp.id_convprog_alu               "
      + "                       and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,                   "
      + "                       mes.nu_dias_inte as nuDiasInte,               "
      + "                       mes.nu_dias_nacu as nuDiasNacu,               "
      + "                       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                      "
      + "                       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                      "
      + "                       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                      "
      + "                       CASE            "
      + "                       WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "                       ELSE 'No' END as enviado, " 
      + "                       0 as puederechazar,               "
      + "                       mdias.max as maxdias,                "
      + "                       alp.lg_erasmus_cb as lgErasBec, "
      + "                       mes.nu_dias_inte_era as diasInteEra, "
      + "                       CASE "
      + "                          when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "                          when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "                          else 'No Erasmus' "
      + "                       END as tipoErasmus, "
      + "                    mes.ds_warnings as dsWarnings, "
      + "                     TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "               CASE  "
      + "               WHEN (select count(*) from fct_datos_gestora_mes dgm  "
      + "                     where dgm.x_matricula = mat.x_matricula  "
      + "                     and mes.x_matricula = dgm.x_matricula  "
      + "                     and dgm.nu_mes = :nMes                                        "
      + "                     and dgm.ds_estado in ('P', 'F')) > 0 THEN 1  "
      + "               WHEN (select count(*) from fct_datos_gestora_mes dgm  "
      + "                     where dgm.x_matricula = mat.x_matricula  "
      + "                     and mes.x_matricula = dgm.x_matricula  "
      + "                     and dgm.nu_mes = :nMes) = 0 THEN 1  "      
      + "               ELSE 0 END esCorrecto,  "
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "
      /*+ "               NVL(NVL(alp.f_inicio, NVL((select MIN(aper.fh_inicio)         "
      + "                             from fct_conv_progaluhoraper aper        "
      + "                             where aper.x_matricula = mat.x_matricula),  "
      + "                             (select MIN(cper.fh_inicio)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                             where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) inicio,          "
      + "                NVL(NVL(alp.f_fin,   NVL((select MAX(aper.fh_fin)         "
      + "                             from fct_conv_progaluhoraper aper          "
      + "                             where aper.x_matricula = mat.x_matricula), "
      + "                            (select MAX(cper.fh_fin)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                            where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) AS fin " */
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convprog_alu = mes.id_convprog_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_prog alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "         WHEN  alp.f_inicio is not null THEN alp.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_progaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_progaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proghoraper cper            "
            + "                        where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proghoraper cper           " 
            + "                                                                                    where cper.id_conv_prog = cp.id_conv_prog) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convprog_alu = mes.id_convprog_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_prog alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "      WHEN  alp.f_fin is not null THEN alp.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_progaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_progaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proghoraper cper           " 
            + "                     where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proghoraper cper    "       
            + "                                                                                 where cper.id_conv_prog = cp.id_conv_prog)   "
            + "      ELSE  TRUNC(sysdate) END fin    " 
      
      + "                 from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                        "
      + "                      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                        "
      + "                      TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, FCT_COTIZAMES_PROG mes,                       "
      + "                      (select CASE                "
      + "                              WHEN :nMes in (1,3,5,7,8,10,12) THEN 31               "
      + "                              WHEN :nMes in (4,6,9,11) THEN 30               "
      + "                              ELSE 28 END max               "
      + "                       FROM DUAL) mdias               "
      + "                 where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%'))               "
      + "                 and tut.id_tutorfctdual = :idTutorfctdual                       "
      + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
      + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
      + "        WHERE  USU.X_USUARIO = :idUsuario                          "
      + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
      + "       AND PTO.X_CENTRO = :idCentro "
      + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
      + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
      + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
      + "                 and pro.id_programa = cp.id_programa                        "
      + "                 and cp.id_conv_prog = cpa.id_conv_prog                        "
      + "                 and cpa.x_matricula = mat.x_matricula                        "
      + "                 and mat.x_alumno = alu.x_alumno                        "
      + "                 and alp.id_convprog_alu =  cpa.id_convprog_alu                      "
      + "                 and alp.x_matricula = mat.x_matricula                 "            
      + "                 and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                       "
      + "                 and alp.f_envioss is not null               "
      + "                 and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) "
      + "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   "
      + "                                          where mes1.x_matricula = mes.x_matricula   "
      + "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  "
      + "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      "                                    
      + "                 and mes.x_matricula(+) = mat.x_matricula                  "
      + "                 and cp.id_convenio = con.id_convenio                        "
      + "                 and con.x_empresa = emp.x_empresa                        "
      + "                 and mat.x_ofertamatrig = omg.x_ofertamatrig                        "
      + "                 and mat.x_unidad = uni.x_unidad                        "
      + "                 and pro.x_centro = :idCentro                        "
      + "                 and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                        "
      + "                 and mes.nu_mes(+) = :nMes                     "
      //+ "                 and :nMes in (SELECT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1)) "
      //+ "                               FROM DUAL "
      //+ "                               CONNECT BY TRUNC(alp.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1) <= TRUNC(alp.f_fin)) "     
      + "                 and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes "
      + "                                FROM DUAL "
      + "                                CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) "
      + "                 and mat.c_anno = :cAnno                        "
      + "                 and :tipoEmpresa in (-1,1)                        "
      + "                 and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                        "
      + "                 and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                        "
      + "                 and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                        "      
      + "                 and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) "      
     // + "                 and not exists (select 1  FROM FCT_COTIZAMES_PROG cot1 "
     // + "                                 where  cot1.id_convprog_alu != cpa.id_convprog_alu " 
     // + "                                 and cot1.x_matricula = cpa.x_matricula "       
     // + "                                 and cot1.nu_mes = :nMes) "      
      + "                 and NVL(cpa.lg_cotiza,0) = 1                      "
      + "                 and NVL(cpa.lg_excluir,0) = 0 "
      + "                 union                        "
      + "                 select NVL(mes.id_cotizames_proy,-1) as id,                  "
      + "                        cpa.id_convproy_alu as idAluCon,                     "
      + "                        alu.c_numide as dni,                      "
      + "                        alu.t_nuss as nuss,                       "
      + "                        alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                        "
      + "                        emp.d_empresa as nombreEmpresa,                        "
      + "                        'FP Dual' as tipoEmpresa,                        "
      + "                        omg.s_ofertamatrig curso,                        "
      + "                        uni.t_nombre unidad,                                  "
      + "                        NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par                    "
      + "                                              where par.id_convproy_alu = aly.id_convproy_alu               "
      + "                                              and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,                  "
      + "                        mes.nu_dias_inte as nuDiasInte,               "
      + "                        mes.nu_dias_nacu as nuDiasNacu,                "
      + "                        DECODE(mes.lg_valtut,1,'Sí','No') AS valTut,                      "
      + "                        DECODE(mes.lg_valcen,1,'Sí','No') AS valCen,                      "
      + "                        DECODE(mes.lg_valdel,1,'Sí','No') AS valDel,                      "
      + "                        CASE            "
      + "                        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "                        ELSE 'No' END as enviado, " 
      + "                        0 as puederechazar,               "
      + "                        mdias.max as maxdias,                "
      + "                        aly.lg_erasmus_cb as lgErasBec, "
      + "                        mes.nu_dias_inte_era as diasInteEra, "
      + "                        CASE "
      + "                           when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "                           when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "                           else 'No Erasmus' "
      + "                        END as tipoErasmus, "
      + "                     mes.ds_warnings as dsWarnings, "
      + "                      TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "                      CASE   "
      + "                      WHEN (select count(*) from fct_datos_gestora_mes dgm   "
      + "                            where dgm.x_matricula = mat.x_matricula   "
      + "                            and mes.x_matricula = dgm.x_matricula   "
      + "                            and dgm.nu_mes = :nMes                                         "
      + "                            and dgm.ds_estado in ('P', 'F')) > 0 THEN 1   "
      + "               WHEN (select count(*) from fct_datos_gestora_mes dgm  "
      + "                     where dgm.x_matricula = mat.x_matricula  "
      + "                     and mes.x_matricula = dgm.x_matricula  "
      + "                     and dgm.nu_mes = :nMes) = 0 THEN 1  "  
      + "                      ELSE 0 END esCorrecto,   "
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "
      /*+ "                NVL(NVL(aly.f_inicio, NVL((select MIN(aper.fh_inicio)         "
      + "                             from fct_conv_progaluhoraper aper        "
      + "                             where aper.x_matricula = mat.x_matricula),  "
      + "                             (select MIN(cper.fh_inicio)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                             where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) inicio,          "
      + "                NVL(NVL(aly.f_fin,   NVL((select MAX(aper.fh_fin)         "
      + "                             from fct_conv_progaluhoraper aper          "
      + "                             where aper.x_matricula = mat.x_matricula), "
      + "                            (select MAX(cper.fh_fin)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                            where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) AS fin " */
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convproy_alu = mes.id_convproy_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_proy alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "         WHEN  aly.f_inicio is not null THEN aly.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_proyaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proyhoraper cper            "
            + "                        where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyhoraper cper           " 
            + "                                                                                    where cper.id_conv_proy = cp.id_conv_proy) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convproy_alu = mes.id_convproy_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_proy alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "      WHEN  aly.f_fin is not null THEN aly.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_proyaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_proyaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proyhoraper cper           " 
            + "                     where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proyhoraper cper    "       
            + "                                                                                 where cper.id_conv_proy = cp.id_conv_proy)   "
            + "      ELSE  TRUNC(sysdate) END fin    " 
      + "                   from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                        "
      + "                        TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                        "
      + "                        TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, FCT_COTIZAMES_PROY mes,               "
      + "                        (select CASE                "
      + "                          WHEN :nMes in (1,3,5,7,8,10,12) THEN 31               "
      + "                          WHEN :nMes in (4,6,9,11) THEN 30               "
      + "                          ELSE 28 END max               "
      + "                          FROM DUAL) mdias               "
      + "                    where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%'))        "
      + "                    and tut.id_tutorfctdual = :idTutorfctdual                 "
      + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
      + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
      + "        WHERE  USU.X_USUARIO = :idUsuario                          "
      + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
      + "       AND PTO.X_CENTRO = :idCentro "
      + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
      + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
      + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
      + "                    and pro.id_proyecto = cp.id_proyecto                        "
      + "                    and cp.id_conv_proy = cpa.id_conv_proy                        "
      + "                    and cpa.x_matricula = mat.x_matricula                        "
      + "                    and mat.x_alumno = alu.x_alumno                        "
      + "                    and cp.id_convenio = con.id_convenio                        "
      + "                    and aly.f_envioss is not null               "
      + "                    and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) "
      + "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  "
      + "                                           where mes1.x_matricula = mes.x_matricula  "
      + "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null "
      + "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    "
      + "                    and con.x_empresa = emp.x_empresa                        "
      + "                    and aly.id_convproy_alu =  cpa.id_convproy_alu                       "            
      + "                    and aly.x_matricula = mat.x_matricula                  "
      + "                    and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                       "
      + "                    and mes.x_matricula(+) = mat.x_matricula                  "
      + "                    and mat.x_ofertamatrig = omg.x_ofertamatrig                        "
      + "                    and mat.x_unidad = uni.x_unidad                        "
      + "                    and pro.x_centro = :idCentro                        "
      + "                    and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                        "
      + "                    and mes.nu_mes(+) = :nMes               "
      //+ "                    and :nMes in (SELECT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1)) "
      //+ "                                  FROM DUAL "
      //+ "                                  CONNECT BY TRUNC(aly.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1) <= TRUNC(aly.f_fin)) "
      + "                    and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes "
      + "                                   FROM DUAL "
      + "                                   CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) "
      + "                    and mat.c_anno = :cAnno                        "
      + "                    and :tipoEmpresa in (-1,2)                        "
      + "                    and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                        "
      + "                    and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                        "
      + "                    and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                       "
      + "                    and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb) "
      //+ "                    and not exists (select 1  FROM FCT_COTIZAMES_PROY cot1 "
      //+ "                                    where  cot1.id_convproy_alu != cpa.id_convproy_alu " 
      //+ "                                    and cot1.x_matricula = cpa.x_matricula "       
      //+ "                                    and cot1.nu_mes = :nMes) "
      + "                    and NVL(cpa.lg_cotiza,0) = 1 "
      + "                    and NVL(cpa.lg_excluir,0) = 0) , FCT_ESTADOS_COTIZACION est                 "
      + "           where ((-1 = :idEstado) OR                  "
      + "                  (1 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valTut= 'No') OR                   "
      + "                                                 (4 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valTut= 'Sí'))  "
      + "    ) original_query "
      + ") ranked_data "
      + "WHERE row_num = 1 "
      + "order by nombreAlumno, curso, unidad, nombreEmpresa ", nativeQuery = true)
    List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMes(Long idTutorfctdual,
                                                                      Long idCentro,
                                                                      Integer cAnno,
                                                                      Integer tipoEmpresa,
                                                                      Long idEmpresa,
                                                                      Long idOfertamatrig,
                                                                      Long idUnidad,
                                                                      Integer idEstado,
                                                                      Integer nMes,
                                                                      Integer idTipo,
                                                                      Long idUsuario);

    @Query(value= "SELECT * " +
            "      FROM ( " +
            "          SELECT  " +
            "              id, " +
            "              idAluCon, " +
            "              centro, " +
            "              tutor, " +
            "              dni, " +
            "              nuss, " +
            "              nombreAlumno, " +
            "              nombreEmpresa, " +
            "              tipoEmpresa, " +
            "              curso, " +
            "              unidad,         " +
            "              nuDiasReal, " +
            "              nuDiasInte, " +
            "              nuDiasNacu, " +
            "              valTut, " +
            "              valCen, " +
            "              valDel, " +
            "              enviado, " +
            "              puederechazar, " +
            "              ROW_NUMBER() OVER ( " +
            "                      PARTITION BY nuss,  " +
            "                      CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  " +
            "                      ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon " +
            "              ) AS row_num, " +
            "              lgErasBec, " +
            "              diasInteEra, " +
            "              tipoErasmus, " +
            "           dsWarnings, " +
            "           fechaEnvio, " +
            "           esCorrecto," +
            "           esCorrectoAlta, "+
            "            TO_CHAR(inicio, 'DD/MM/YYYY') inicio, " +
            "           TO_CHAR(fin, 'DD/MM/YYYY') fin, " +
             
            "    CASE    " +
            "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " + 
            "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) " +   
            "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    " +
            "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN " +   
            "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    " +
            "    ELSE 0  " +  
            "    END AS diasRestantes, " +
            "    0 AS avisoMes, " +
            "    1 AS bloqueoMes " +
            "  FROM    " +
            "  (select CASE "   +                
            "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "  +                                            
            "          ELSE TO_CHAR(:cAnno)  END anno                  " +
            "        FROM DUAL) annonat, " +
            "   ( " +
            "            SELECT distinct id, idAluCon, null centro, tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,             " +
            "               CASE             " +
            "               WHEN nuDiasReal>maxdias THEN maxdias             " +
            "               ELSE nuDiasReal END nuDiasReal,              " +
            "                nuDiasInte, nuDiasNacu,  valTut, valCen, valDel, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, esCorrectoAlta, inicio, fin FROM (                 " +
            "                     select NVL(mes.id_cotizames_prog,-1) as id,                 " +
            "                         cpa.id_convprog_alu as idAluCon,                " +
            "                         empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                " +
            "                         alu.c_numide as dni,                    " +
            "                         alu.t_nuss as nuss,                     " +
            "                         alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                      " +
            "                         emp.d_empresa as nombreEmpresa,                      " +
            "                         'FCT' as tipoEmpresa,                      " +
            "                         omg.s_ofertamatrig curso,                      " +
            "                         uni.t_nombre unidad,                    " +
            "                         NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par                  " +
            "                      where par.id_convprog_alu = alp.id_convprog_alu             " +
            "                      and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,                " +
            "                      mes.nu_dias_inte as nuDiasInte,             " +
            "                      mes.nu_dias_nacu as nuDiasNacu,             " +
            "                      DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                    " +
            "                      DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                    " +
            "                      DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                        " +
            "                      CASE            " +
            "                      WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            " +
            "                      ELSE 'No' END as enviado,  " +
            "                      CASE              " +
            "                      WHEN mes.lg_valdel = 0 AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1) THEN 1             " +
            "                      ELSE 0 END as puederechazar,               " +
            "                      mdias.max as maxdias,             " +
            "                      alp.lg_erasmus_cb as lgErasBec, " +
            "                      mes.nu_dias_inte_era as diasInteEra, " +
            "                      CASE " +
            "                         when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' " +
            "                         when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' " +
            "                         else 'No Erasmus' " +
            "                      END as tipoErasmus, " +
            "                    mes.ds_warnings as dsWarnings, " +
            "                     TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, " +
            "                              CASE    " +
            "                             WHEN (select count(*) from fct_datos_gestora_mes dgm    " +
            "                                   where dgm.x_matricula = mat.x_matricula    " +
            "                                   and mes.x_matricula = dgm.x_matricula    " +
            "                                   and dgm.nu_mes = :nMes                   "    +                    
            "                                   and dgm.ds_estado in ('P', 'F')) > 0 THEN 1    " +
            "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " +
            "                                   where dgm.x_matricula = mat.x_matricula  " +
            "                                   and mes.x_matricula = dgm.x_matricula  " +
            "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "   +
            "                             ELSE 0 END esCorrecto, " +
            "            (SELECT error FROM (SELECT " +
            "             CASE " +
            "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' " +
            "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR " +
            "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA " +
            "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO " +
            "                 ELSE '0' " +
            "             END AS error, " +
            "             CASE " +
            "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 " +
            "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 " +
            "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 " +
            "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 " +
            "                 ELSE 5 " +
            "             END AS prioridad " +
            "         FROM FCT_DATOS_GESTORA dg " +
            "         WHERE dg.X_MATRICULA = mat.x_matricula " +
            "           AND dg.X_CENTRO = pro.x_centro " +
            "           AND dg.NU_ANNO = :cAnno " +
            "           AND ( " +
            "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  " +
            "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "  +
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END)) " +
            "               OR " +
            "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  " +
            "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " + 
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END)) " +
            "               OR " +
            "                 (TO_DATE(:nMes || '/01/' || CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " +
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) " +
            "              ) "  +
            "     ORDER BY prioridad " +
            "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, " +
            /* "               NVL(NVL(alp.f_inicio, NVL((select MIN(aper.fh_inicio)         " +
            "                             from fct_conv_progaluhoraper aper        " +
            "                             where aper.x_matricula = mat.x_matricula),  " +
            "                             (select MIN(cper.fh_inicio)         " +
            "                             from fct_conv_proghoraper cper         " +
            "                             where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) inicio,          " +
            "                NVL(NVL(alp.f_fin,   NVL((select MAX(aper.fh_fin)         " +
            "                             from fct_conv_progaluhoraper aper          " +
            "                             where aper.x_matricula = mat.x_matricula), " +
            "                            (select MAX(cper.fh_fin)         " +
            "                             from fct_conv_proghoraper cper         " +
            "                            where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) AS fin" + */
            "         CASE " +
            "         WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  " +
            "              where alp.x_matricula = mes.x_matricula  " +
            "              and alp.id_convprog_alu = mes.id_convprog_alu " +
            "              and dat.x_matricula = alp.x_matricula " +
            "              and dat.ds_tipo = 'Alta' "  +
            "              and dat.ds_estado = 'S' " +
            "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) " +
            "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  " +
            "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    " +
            "                             FROM DUAL    " +
            "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_prog alp " +
            "                                                                                                        where alp.x_matricula = mes.x_matricula  " +
            "                                                                                                        and alp.id_convprog_alu = mes.id_convprog_alu) " +
            "         WHEN  alp.f_inicio is not null THEN alp.f_inicio " +
            "         WHEN  (select MIN(aper.fh_inicio)            " + 
            "                        from fct_conv_progaluhoraper aper           " +
            "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            " +
            "                                                                                    from fct_conv_progaluhoraper aper       " +    
            "                                                                                    where aper.x_matricula = mat.x_matricula) " + 
            "         WHEN  (select MIN(cper.fh_inicio)    "         +
            "                        from fct_conv_proghoraper cper            " +
            "                        where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MIN(cper.fh_inicio)            " +
            "                                                                                    from fct_conv_proghoraper cper           " + 
            "                                                                                    where cper.id_conv_prog = cp.id_conv_prog) " +                                                                            
            "         ELSE  TRUNC(sysdate) END inicio,  " +
            "       CASE " +
            "      WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  " +
            "           where alp.x_matricula = mes.x_matricula  " +
            "            and alp.id_convprog_alu = mes.id_convprog_alu " +
            "           and dat.x_matricula = alp.x_matricula " +
            "           and dat.ds_tipo = 'Alta' " +
            "           and dat.ds_estado = 'S' " +
            "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) " +
            "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  " +
            "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    " +
            "                          FROM DUAL   " +
            "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_prog alp " +
            "                                                                                                     where alp.x_matricula = mes.x_matricula  " +
            "                                                                                                     and alp.id_convprog_alu = mes.id_convprog_alu) " +
            "      WHEN  alp.f_fin is not null THEN alp.f_fin " +
            "      WHEN  (select MAX(aper.fh_fin)            " +
            "                     from fct_conv_progaluhoraper aper           " +
            "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            " +
            "                                                                                 from fct_conv_progaluhoraper aper           " + 
            "                                                                                 where aper.x_matricula = mat.x_matricula)  " +
            "      WHEN  (select MAX(cper.fh_fin)            " +
            "                     from fct_conv_proghoraper cper           " + 
            "                     where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MAX(cper.fh_fin)           " +
            "                                                                                 from fct_conv_proghoraper cper    "    +    
            "                                                                                 where cper.id_conv_prog = cp.id_conv_prog)   " +
            "      ELSE  TRUNC(sysdate) END fin    "  +
            
            
            "             from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                        " +
            "                  TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                        " +
            "                  TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, TLEMPLEADOS empl, FCT_COTIZAMES_PROG mes,                        " +
            "                  (select CASE              " +
            "                   WHEN :nMes in (1,3,5,7,8,10,12) THEN 31             " +
            "                   WHEN :nMes in (4,6,9,11) THEN 30             " +
            "                   ELSE 28 END max             " +
            "                   FROM DUAL) mdias             " +
            "              where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%'))               "+
            "              and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                        " +
            "              and tut.id_tutorfctdual = pro.id_tutorfctdual                        " +
            "              and pro.id_programa = cp.id_programa                        " +
            "              and cp.id_conv_prog = cpa.id_conv_prog                        " +
            "              and cpa.x_matricula = mat.x_matricula                        " +
            "              and mat.x_alumno = alu.x_alumno                        " +
            "              and alp.id_convprog_alu =  cpa.id_convprog_alu                      " +                
            "              and alp.x_matricula = mat.x_matricula             " +
            "              and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                     " +
            "              and mes.x_matricula(+) = mat.x_matricula              " +
            "              and cp.id_convenio = con.id_convenio                        " +
            "              and alp.f_envioss is not null             " +
            "              and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) " +
            "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   " +
            "                                          where mes1.x_matricula = mes.x_matricula   " +
            "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  " +
            "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      " +                                    
            "              and con.x_empresa = emp.x_empresa                        " +
            "              and mat.x_ofertamatrig = omg.x_ofertamatrig                        " +
            "              and mat.x_unidad = uni.x_unidad                        " +
            "              and pro.x_centro = :idCentro                        " +
            "              and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                        " +
            "              and mat.c_anno = :cAnno                        " +
            "              and empl.x_empleado = tut.x_empleado                " +
            "              and mes.nu_mes(+) = :nMes              " +
            "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes " +
            "                             FROM DUAL " +
            "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) " +
            "              and :tipoEmpresa in (-1,1)                        " +
            "              and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                        " +
            "              and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                        " +
            "              and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                        " +
            "              and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) " +
            "              and NVL(cpa.lg_cotiza,0) = 1      " +
            //"              and not exists (select 1  FROM FCT_COTIZAMES_PROG cot1 " +
            //"                              where  cot1.id_convprog_alu != cpa.id_convprog_alu  " +
            //"                              and cot1.x_matricula = cpa.x_matricula        " +
            //"                              and cot1.nu_mes = :nMes)       " +
            "              and NVL(cpa.lg_excluir,0) = 0 " +
            "              union                        " +
            "              select NVL(mes.id_cotizames_proy,-1) as id,                  " +
            "                     cpa.id_convproy_alu as idAluCon,                " +
            "                     empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                " +
            "                     alu.c_numide as dni,                      " +
            "                     alu.t_nuss as nuss,                       " +
            "                     alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                        " +
            "                     emp.d_empresa as nombreEmpresa,                        " +
            "                     'FP Dual' as tipoEmpresa,                        " +
            "                     omg.s_ofertamatrig curso,                        " +
            "                     uni.t_nombre unidad,                                  " +
            "                     NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par                  " +
            "                                           where par.id_convproy_alu = aly.id_convproy_alu             " +
            "                                           and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,                " +
            "                     mes.nu_dias_inte as nuDiasInte,             " +
            "                     mes.nu_dias_nacu as nuDiasNacu,              " +
            "                     DECODE(mes.lg_valtut,1,'Sí','No') AS valTut,                    " +
            "                     DECODE(mes.lg_valcen,1,'Sí','No') AS valCen,                    " +
            "                     DECODE(mes.lg_valdel,1,'Sí','No') AS valDel,                        " +
            "                     CASE            " +
            "                     WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            " +
            "                     ELSE 'No' END as enviado,  " +
            "                     CASE              " +
            "                     WHEN mes.lg_valdel = 0 AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1) THEN 1             " +
            "                     ELSE 0 END as puederechazar,               " +
            "                      mdias.max as maxdias,             " +
            "                      aly.lg_erasmus_cb as lgErasBec, " +
            "                      mes.nu_dias_inte_era as diasInteEra, " +
            "                      CASE " +
            "                          when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' " +
            "                          when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' " +
            "                          else 'No Erasmus' " +
            "                      END as tipoErasmus, " +
            "                    mes.ds_warnings as dsWarnings, " +
            "                    TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, " +
            "                    CASE       " +
            "                  WHEN (select count(*) from fct_datos_gestora_mes dgm       " +
            "                        where dgm.x_matricula = mat.x_matricula       " +
            "                        and mes.x_matricula = dgm.x_matricula       " +
            "                        and dgm.nu_mes = :nMes                        " +
            "                        and dgm.ds_estado in ('P', 'F')) > 0 THEN 1       " +
            "                  WHEN (select count(*) from fct_datos_gestora_mes dgm  " +
            "                        where dgm.x_matricula = mat.x_matricula  " +
            "                        and mes.x_matricula = dgm.x_matricula  " +
            "                        and dgm.nu_mes = :nMes) = 0 THEN 1  "   +
            "                  ELSE 0 END esCorrecto, " +
            "            (SELECT error FROM (SELECT " +
            "             CASE " +
            "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' " +
            "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR " +
            "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA " +
            "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO " +
            "                 ELSE '0' " +
            "             END AS error, " +
            "             CASE " +
            "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 " +
            "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 " +
            "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 " +
            "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 " +
            "                 ELSE 5 " +
            "             END AS prioridad " +
            "         FROM FCT_DATOS_GESTORA dg " +
            "         WHERE dg.X_MATRICULA = mat.x_matricula " +
            "           AND dg.X_CENTRO = pro.x_centro " +
            "           AND dg.NU_ANNO = :cAnno " +
            "           AND ( " +
            "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  " +
            "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "  +
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END)) " +
            "               OR " +
            "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  " +
            "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " + 
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END)) " +
            "               OR " +
            "                 (TO_DATE(:nMes || '/01/' || CASE  " +
            "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " +
            "                     ELSE TO_CHAR(:cAnno) " +
            "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) " +
            "              ) "  +
            "     ORDER BY prioridad " +
            "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, " +
            /*"                NVL(NVL(aly.f_inicio, NVL((select MIN(aper.fh_inicio)         " +
            "                             from fct_conv_progaluhoraper aper        " +
            "                             where aper.x_matricula = mat.x_matricula),  " +
            "                             (select MIN(cper.fh_inicio)         " +
            "                             from fct_conv_proghoraper cper         " +
            "                             where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) inicio,          " +
            "                NVL(NVL(aly.f_fin,   NVL((select MAX(aper.fh_fin)         " +
            "                             from fct_conv_progaluhoraper aper          " +
            "                             where aper.x_matricula = mat.x_matricula), " +
            "                            (select MAX(cper.fh_fin)         " +
            "                             from fct_conv_proghoraper cper         " +
            "                            where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) AS fin" + */
            
   "         CASE " +
   "         WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  " +
   "              where alp.x_matricula = mes.x_matricula  " +
   "              and alp.id_convproy_alu = mes.id_convproy_alu " +
   "              and dat.x_matricula = alp.x_matricula " +
   "              and dat.ds_tipo = 'Alta' "  +
   "              and dat.ds_estado = 'S' " +
   "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) " +
   "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  " +
   "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    " +
   "                             FROM DUAL    " +
   "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_proy alp " +
   "                                                                                                        where alp.x_matricula = mes.x_matricula  " +
   "                                                                                                        and alp.id_convproy_alu = mes.id_convproy_alu) " +
   "         WHEN  aly.f_inicio is not null THEN aly.f_inicio " +
   "         WHEN  (select MIN(aper.fh_inicio)            " +
   "                        from fct_conv_proyaluhoraper aper           " +
   "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            " +
   "                                                                                    from fct_conv_proyaluhoraper aper       " +    
   "                                                                                    where aper.x_matricula = mat.x_matricula) " + 
   "         WHEN  (select MIN(cper.fh_inicio)    "         +
   "                        from fct_conv_proyhoraper cper            " +
   "                        where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MIN(cper.fh_inicio)            " +
   "                                                                                    from fct_conv_proyhoraper cper           " + 
   "                                                                                    where cper.id_conv_proy = cp.id_conv_proy) " +                                                                            
   "         ELSE  TRUNC(sysdate) END inicio,  " +
   "       CASE " +
   "      WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  " +
   "           where alp.x_matricula = mes.x_matricula  " +
   "            and alp.id_convproy_alu = mes.id_convproy_alu " +
   "           and dat.x_matricula = alp.x_matricula " +
   "           and dat.ds_tipo = 'Alta' " +
   "           and dat.ds_estado = 'S' " +
   "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) " +
   "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  " +
   "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    " +
   "                          FROM DUAL   " +
   "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_proy alp " +
   "                                                                                                     where alp.x_matricula = mes.x_matricula  " +
   "                                                                                                     and alp.id_convproy_alu = mes.id_convproy_alu) " +
   "      WHEN  aly.f_fin is not null THEN aly.f_fin " +
   "      WHEN  (select MAX(aper.fh_fin)            " +
   "                     from fct_conv_proyaluhoraper aper           " + 
   "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            " +
   "                                                                                 from fct_conv_proyaluhoraper aper           " +
   "                                                                                 where aper.x_matricula = mat.x_matricula)  " +
   "      WHEN  (select MAX(cper.fh_fin)            " +
   "                     from fct_conv_proyhoraper cper           " + 
   "                     where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MAX(cper.fh_fin)           " +
   "                                                                                 from fct_conv_proyhoraper cper    "    +    
   "                                                                                 where cper.id_conv_proy = cp.id_conv_proy)   " +
   "      ELSE  TRUNC(sysdate) END fin    "  +
            
            
            
            "                from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                        " +
            "                     TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                        " +
            "                     TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, TLEMPLEADOS empl, FCT_COTIZAMES_PROY mes,                           " +
            "                     (select CASE              " +
            "                      WHEN :nMes in (1,3,5,7,8,10,12) THEN 31             " +
            "                      WHEN :nMes in (4,6,9,11) THEN 30             " +
            "                      ELSE 28 END max             " +
            "                      FROM DUAL) mdias             " +
            "                where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%'))       "+
            "                and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                        " +
            "                and tut.id_tutorfctdual = pro.id_tutorfctdual                        " +
            "                and pro.id_proyecto = cp.id_proyecto                        " +
            "                and cp.id_conv_proy = cpa.id_conv_proy                        " +
            "                and cpa.x_matricula = mat.x_matricula                        " +
            "                and mat.x_alumno = alu.x_alumno                        " +
            "                and cp.id_convenio = con.id_convenio                        " +
            "                and aly.f_envioss is not null             " +
            "                and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) " +
            "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  " +
            "                                           where mes1.x_matricula = mes.x_matricula  " +
            "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null " +
            "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    " +
            "                and con.x_empresa = emp.x_empresa                        " +
            "                and aly.id_convproy_alu =  cpa.id_convproy_alu                       " +                
            "                and aly.x_matricula = mat.x_matricula           " +
            "                and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                     " +
            "                and mes.x_matricula(+) = mat.x_matricula                " +
            "                and mat.x_ofertamatrig = omg.x_ofertamatrig                        " +
            "                and mat.x_unidad = uni.x_unidad                        " +
            "                and pro.x_centro = :idCentro                        " +
            "                and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                        " +
            "                and mat.c_anno = :cAnno                        " +
            "                and empl.x_empleado = tut.x_empleado                " +
            "                and mes.nu_mes(+) = :nMes              " +
            "                and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes " +
            "                               FROM DUAL " +
            "                               CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) " +
            "                and :tipoEmpresa in (-1,2)                        " +
            "                and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                        " +
            "                and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                        " +
            "                and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                       " +
            "                and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb)       " +
            //"                and not exists (select 1  FROM FCT_COTIZAMES_PROY cot1 " +
            //"                                where  cot1.id_convproy_alu != cpa.id_convproy_alu  " +
            //"                                and cot1.x_matricula = cpa.x_matricula        " +
            //"                                and cot1.nu_mes = :nMes)  " +
            "                and NVL(cpa.lg_cotiza,0) = 1 " +
            "                and NVL(cpa.lg_excluir,0) = 0) , FCT_ESTADOS_COTIZACION est                 " +
            "             where ((-1 = :idEstado) OR                 " +
            "                    (1 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valCen= 'No') OR                  " +
            "                    (4 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valCen= 'Sí') OR                " +
            "                    (5 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valTut= 'No'))                " +
            "          ) original_query " +
            "      ) ranked_data " +
            "      WHERE row_num = 1 " +
            "      order by nombreAlumno, curso, unidad, nombreEmpresa  ", nativeQuery = true)
    List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMesCen(Long idTutorfctdual,
                                                                     Long idCentro,
                                                                     Integer cAnno,
                                                                     Integer tipoEmpresa,
                                                                     Long idEmpresa,
                                                                     Long idOfertamatrig,
                                                                     Long idUnidad,
                                                                     Integer idEstado,
                                                                     Integer nMes,
                                                                     Integer idTipo);


    @Query(value = " SELECT * "
      + "FROM ( "
      + "    SELECT  "
      + "        id, "
      + "        idAluCon, "
      + "        centro, "
      + "        tutor, "
      + "        dni, "
      + "        nuss, "
      + "        nombreAlumno, "
      + "        nombreEmpresa, "
      + "        tipoEmpresa, "
      + "        curso, "
      + "        unidad,         "
      + "        nuDiasReal, "
      + "        nuDiasInte, "
      + "        nuDiasNacu, "
      + "        valTut, "
      + "        valCen, "
      + "        valDel, "
      + "        enviado, "
      + "        puederechazar, "
      + "        ROW_NUMBER() OVER ( "
      + "                PARTITION BY nuss,  "
      + "                CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  "
      + "                ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon "
      + "        ) AS row_num, "
      + "        lgErasBec, "
      + "        diasInteEra, "
      + "        tipoErasmus, "
      + "     dsWarnings, "
      + "     fechaEnvio, "
      + "     esCorrecto, "
      + "     esCorrectoAlta, "
      + "     TO_CHAR(inicio, 'DD/MM/YYYY') inicio, "
      + "     TO_CHAR(fin, 'DD/MM/YYYY') fin, "    
      + "    CASE    "
      + "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " 
      + "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) "   
      + "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    "
      + "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN "   
      + "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    "
      + "    ELSE 0  "  
      + "    END AS diasRestantes, "
      + "    0 AS avisoMes, "
      + "    0 AS bloqueoMes "
      + "  FROM    "
      + "  (select CASE "                  
      + "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "                                             
      + "          ELSE TO_CHAR(:cAnno)  END anno                  "
      + "        FROM DUAL) annonat, "
      + "   ( "      
      + "    SELECT distinct  id, idAluCon, centro, tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,           "
      + "           CASE           "
      + "           WHEN nuDiasReal>maxdias THEN maxdias           "
      + "           ELSE nuDiasReal END nuDiasReal,            "
      + "           nuDiasInte, nuDiasNacu, valTut, valCen, valDel, cif, ccodigo, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, esCorrectoAlta, inicio, fin FROM (                   "
      + "                      select NVL(mes.id_cotizames_prog,-1) as id,                     "
      + "                             cpa.id_convprog_alu as idAluCon,                  "
      + "                             cen.c_codigo || '-' || dat.d_especifica centro,                  "
      + "                             empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
      + "                             alu.c_numide as dni,                                                "
      + "                             alu.t_nuss as nuss,                                                 "
      + "                             alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                  "
      + "                             emp.d_empresa as nombreEmpresa,                                                  "
      + "                             'FCT' as tipoEmpresa,                                                  "
      + "                             omg.s_ofertamatrig curso,                                                  "
      + "                             uni.t_nombre unidad,                                                "
      + "                             NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par                "
      + "                                                   where par.id_convprog_alu = alp.id_convprog_alu           "
      + "                                                   and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
      + "                       mes.nu_dias_inte as nuDiasInte,           "
      + "                       mes.nu_dias_nacu as nuDiasNacu,           "
      + "                       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
      + "                       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
      + "                       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
      + "                       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
      + "                        cen.c_codigo as ccodigo,                                                              "
      + "                        CASE            "
      + "                        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "                        ELSE 'No' END as enviado,                                       "
      + "                        CASE            "
      + "                        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
      + "                        ELSE 0 END as puederechazar,             "
      + "                        mdias.max as maxdias,            "
      + "                        alp.lg_erasmus_cb as lgErasBec, "
      + "                        mes.nu_dias_inte_era as diasInteEra, "
      + "                        CASE "
      + "                           when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "                           when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "                           else 'No Erasmus' "
      + "                        END as tipoErasmus, "
      + "              mes.ds_warnings as dsWarnings, "
      + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "                          CASE        "
      + "                               WHEN (select count(*) from fct_datos_gestora_mes dgm        "
      + "                                     where dgm.x_matricula = mat.x_matricula        "
      + "                                     and mes.x_matricula = dgm.x_matricula        " 
      + "                                     and dgm.nu_mes = :nMes                             "
      + "                                     and dgm.ds_estado in ('P', 'F')) > 0 THEN 1       "
      + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
      + "                                   where dgm.x_matricula = mat.x_matricula  " 
      + "                                   and mes.x_matricula = dgm.x_matricula  " 
      + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "   
      + "                               ELSE 0 END esCorrecto,   "    
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "            
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convprog_alu = mes.id_convprog_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_prog alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "         WHEN  alp.f_inicio is not null THEN alp.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_progaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_progaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proghoraper cper            "
            + "                        where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proghoraper cper           " 
            + "                                                                                    where cper.id_conv_prog = cp.id_conv_prog) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convprog_alu = mes.id_convprog_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_prog alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "      WHEN  alp.f_fin is not null THEN alp.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_progaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_progaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proghoraper cper           " 
            + "                     where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proghoraper cper    "       
            + "                                                                                 where cper.id_conv_prog = cp.id_conv_prog)   "
            + "      ELSE  TRUNC(sysdate) END fin    "   
            + "              from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                                                  "
            + "      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                  "
            + "      TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROG mes,                                                    "
            + "       (select CASE            "
            + "        WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
            + "        WHEN :nMes in (4,6,9,11) THEN 30           "
            + "        ELSE 28 END max           "
            + "        FROM DUAL) mdias,           "      
            + " (SELECT distinct dcen1.x_centro                              "
         + "                          from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
         + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
         + "                          WHERE u.x_usuario = :idUsuario                             "
         + "                          AND pop.x_perfil = :idPerfil                             "
         + "                          AND pto.x_centro = :idCentro                             "
         + "                          AND pto.x_empleado=u.x_empleado                             "
         + "                          AND pop.x_empleado=pto.x_empleado                             "
         + "                          AND pop.f_tomapos = pto.f_tomapos                             "
         + "                          AND pto.x_centro = dcen.x_centro                             "
         + "                          AND dcen.c_provincia = prv.c_provincia                              "
         + "                          AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
         + "                          AND dcen1.c_provincia = prv.c_provincia                             "
         + "                           AND dcen1.l_vigente = 'S'                             "
         + "                          AND cen.x_centro = dcen1.x_centro                             "
         + "                          AND cen.l_delegacion = 'N'                             "
         + "                          AND cen.l_extranjero = 'N')  centros  "          
            + "where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%')) " 
            + "     and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                  "
      + "     and tut.id_tutorfctdual = pro.id_tutorfctdual                                                  "
      + "     and pro.id_programa = cp.id_programa                                                 "
      + "     and cp.id_conv_prog = cpa.id_conv_prog                                                 "
      + "     and cpa.x_matricula = mat.x_matricula                                                 "
      + "     and mat.x_alumno = alu.x_alumno                                                 "
      + "     and alp.id_convprog_alu =  cpa.id_convprog_alu                                               "          
      + "     and alp.x_matricula = mat.x_matricula           "
      + "     and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                   "
      + "     and mes.x_matricula(+) = mat.x_matricula           "
      + "     and cp.id_convenio = con.id_convenio                                                 "
      + "     and alp.f_envioss is not null           "
      + "     and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) "
      + "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   "
      + "                                          where mes1.x_matricula = mes.x_matricula   "
      + "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  "
      + "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      "                                    
      + "     and con.x_empresa = emp.x_empresa                                                 "
      + "     and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
      + "     and mat.x_unidad = uni.x_unidad                      "
      + "     and dat.x_centro = pro.x_centro                  "
      + "     and cen.x_centro = dat.x_centro                                                                           "
      + "     and empl.x_empleado = tut.x_empleado                  "
      + "     and mes.nu_mes(+) = :nMes           "      
      + "     and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes "
      + "                    FROM DUAL "
      + "                    CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) "
      + "     and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
      + "     and mat.c_anno = :cAnno                            "
      + "     and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
      + "     and pro.x_centro = centros.x_centro                          "
      + "     and :tipoEmpresa in (-1,1)                                                 "
      + "     and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
      + "     and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
      + "     and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                 "
      + "     and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) "
      + "     and NVL(cpa.lg_cotiza,0) = 1                                               "      
      + "     and NVL(cpa.lg_excluir,0) = 0 "
      + "     union                                                 "
      + "     select NVL(mes.id_cotizames_proy,-1) as id,                       "
      + "       cpa.id_convproy_alu as idAluCon,                  "
      + "       cen.c_codigo || '-' || dat.d_especifica centro,                  "
      + "       empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
      + "       alu.c_numide as dni,                                               "
      + "       alu.t_nuss as nuss,                                                "
      + "       alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                 "
      + "       emp.d_empresa as nombreEmpresa,                                                 "
      + "       'FP Dual' as tipoEmpresa,                                                 "
      + "       omg.s_ofertamatrig curso,                                                 "
      + "       uni.t_nombre unidad,                                                           "
      + "       NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par                "
      + "                             where par.id_convproy_alu = aly.id_convproy_alu           "
      + "                            and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
      + "       mes.nu_dias_inte as nuDiasInte,           "
      + "       mes.nu_dias_nacu as nuDiasNacu,           "
      + "       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
      + "       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
      + "       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
      + "       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
      + "        cen.c_codigo as ccodigo,    "
      + "        CASE            "
      + "        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "        ELSE 'No' END as enviado,                                       "
      + "        CASE            "
      + "        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
      + "        ELSE 0 END as puederechazar, "
      + "        mdias.max as maxdias,            "
      + "       aly.lg_erasmus_cb as lgErasBec, "
      + "       mes.nu_dias_inte_era as diasInteEra, "
      + "       CASE "
      + "           when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "           when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "           else 'No Erasmus' "
      + "       END as tipoErasmus, "
      + "              mes.ds_warnings as dsWarnings, "
      + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "                          CASE         "
      + "                                      WHEN (select count(*) from fct_datos_gestora_mes dgm         "
      + "                                            where dgm.x_matricula = mat.x_matricula         "
      + "                                            and mes.x_matricula = dgm.x_matricula        "
      + "                                            and dgm.nu_mes = :nMes                              "
      + "                                            and dgm.ds_estado in ('P', 'F')) > 0 THEN 1        "
      + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
      + "                                   where dgm.x_matricula = mat.x_matricula  " 
      + "                                   and mes.x_matricula = dgm.x_matricula  " 
      + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
      + "                                      ELSE 0 END esCorrecto,  "
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "            
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convproy_alu = mes.id_convproy_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_proy alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "         WHEN  aly.f_inicio is not null THEN aly.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_proyaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proyhoraper cper            "
            + "                        where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyhoraper cper           " 
            + "                                                                                    where cper.id_conv_proy = cp.id_conv_proy) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convproy_alu = mes.id_convproy_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_proy alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "      WHEN  aly.f_fin is not null THEN aly.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_proyaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_proyaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proyhoraper cper           " 
            + "                     where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proyhoraper cper    "       
            + "                                                                                 where cper.id_conv_proy = cp.id_conv_proy)   "
            + "      ELSE  TRUNC(sysdate) END fin    " 
            + "       from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                                                 "
            + "            TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                 "
            + "            TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROY mes,                                                   "
            + "           (select CASE            "
            + "            WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
            + "            WHEN :nMes in (4,6,9,11) THEN 30           "
            + "            ELSE 28 END max           "
            + "            FROM DUAL) mdias,           "      
            + "           (SELECT distinct dcen1.x_centro                              "
           + "                            from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
           + "                                 TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
           + "                             WHERE u.x_usuario = :idUsuario                             "
           + "                             AND pop.x_perfil = :idPerfil                             "
           + "                             AND pto.x_centro = :idCentro                             "
           + "                             AND pto.x_empleado=u.x_empleado                             "
           + "                             AND pop.x_empleado=pto.x_empleado                             "
           + "                             AND pop.f_tomapos = pto.f_tomapos                             "
           + "                             AND pto.x_centro = dcen.x_centro                             "
           + "                             AND dcen.c_provincia = prv.c_provincia                              "
           + "                             AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
           + "                             AND dcen1.c_provincia = prv.c_provincia                             "
           + "                             AND dcen1.l_vigente = 'S'                             "
           + "                             AND cen.x_centro = dcen1.x_centro                             "
           + "                             AND cen.l_delegacion = 'N'                             "
           + "                             AND cen.l_extranjero = 'N') centros  "
            + "where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%')) " 
            + "       and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                 "
      + "       and tut.id_tutorfctdual = pro.id_tutorfctdual                                                 "
      + "       and pro.id_proyecto = cp.id_proyecto                                                 "
      + "       and cp.id_conv_proy = cpa.id_conv_proy                                                 "
      + "       and cpa.x_matricula = mat.x_matricula                                                 "
      + "       and mat.x_alumno = alu.x_alumno                                                 "
      + "       and cp.id_convenio = con.id_convenio                                                 "
      + "       and con.x_empresa = emp.x_empresa                                                 "
      + "       and aly.id_convproy_alu =  cpa.id_convproy_alu                                                "           
      + "       and aly.f_envioss is not null           "
      + "       and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) "
      + "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  "
      + "                                           where mes1.x_matricula = mes.x_matricula  "
      + "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null "
      + "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    "
      + "       and aly.x_matricula = mat.x_matricula           "
      + "       and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                   "
      + "       and mes.x_matricula(+) = mat.x_matricula            "
      + "       and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
      + "       and mat.x_unidad = uni.x_unidad                        "
      + "       and dat.x_centro = pro.x_centro                  "
      + "       and cen.x_centro = dat.x_centro                                                                   "
      + "       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
      + "       and empl.x_empleado = tut.x_empleado                  "
      + "       and mes.nu_mes(+) = :nMes           "
      + "       and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes "
      + "                      FROM DUAL "
      + "                      CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) "
      + "       and mat.c_anno = :cAnno                         "
      + "       and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
      + "       and pro.x_centro = centros.x_centro                           "
      + "        and :tipoEmpresa in (-1,2)                                                 "
      + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
      + "        and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
      + "        and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                "
      + "        and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb) "
      + "        and NVL(cpa.lg_cotiza,0) = 1 "
      + "        and NVL(cpa.lg_excluir,0) = 0), FCT_ESTADOS_COTIZACION est                  "
      + "      where ((-1 = :idEstado) OR                   "
      + "             (1 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'No') OR                    "
      + "             (4 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí') OR                  "
      + "             (6 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valCen= 'No') OR "
      + "             (7 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí' AND fechaenvio is null) OR "
      + "             (8 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí' AND fechaenvio is not null) "
      + "              ) "
      + "    ) original_query "
      + ") ranked_data "
      + "WHERE row_num = 1 "
      + "order by nombreAlumno, curso, unidad, nombreEmpresa   ", nativeQuery = true)
    List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMesDelegacion(Long idTutorfctdual,
                                                                                Long idCentro,
                                                                                Integer cAnno,
                                                                                Integer tipoEmpresa,
                                                                                Long idEmpresa,
                                                                                Long idOfertamatrig,
                                                                                Long idUnidad,
                                                                                Long idPerfil,
                                                                                Long idCentroCombo,
                                                                                Long idUsuario,
                                                                                Integer idEstado,
                                                                                Integer nMes,
                                                                                Integer idTipo);
    
    
    @Query(value = " SELECT * "
           + "FROM ( "
           + "    SELECT  "
           + "        id, "
           + "        idAluCon, "
           + "        centro, "
           + "        tutor, "
           + "        dni, "
           + "        nuss, "
           + "        nombreAlumno, "
           + "        nombreEmpresa, "
           + "        tipoEmpresa, "
           + "        curso, "
           + "        unidad,         "
           + "        nuDiasReal, "
           + "        nuDiasInte, "
           + "        nuDiasNacu, "
           + "        valTut, "
           + "        valCen, "
           + "        valDel, "
           + "        enviado, "
           + "        puederechazar, "
           + "        ROW_NUMBER() OVER ( "
           + "                PARTITION BY nuss,  "
           + "                CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  "
           + "                ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon "
           + "        ) AS row_num, "
           + "        lgErasBec, "
           + "        diasInteEra, "
           + "        tipoErasmus, "
           + "     dsWarnings, "
           + "     fechaEnvio, "
           + "     esCorrecto, "
           + "     TO_CHAR(inicio, 'DD/MM/YYYY') inicio, "
           + "     TO_CHAR(fin, 'DD/MM/YYYY') fin, "    
           + "    CASE    "
           + "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " 
           + "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) "   
           + "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    "
           + "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN "   
           + "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    "
           + "    ELSE 0  "  
           + "    END AS diasRestantes, "
           + "    0 AS avisoMes, "
           + "    0 AS bloqueoMes "
           + "  FROM    "
           + "  (select CASE "                  
           + "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "                                             
           + "          ELSE TO_CHAR(:cAnno)  END anno                  "
           + "        FROM DUAL) annonat, "
           + "   ( "      
           + "    SELECT distinct  id, idAluCon, centro, tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,           "
           + "           CASE           "
           + "           WHEN nuDiasReal>maxdias THEN maxdias           "
           + "           ELSE nuDiasReal END nuDiasReal,            "
           + "           nuDiasInte, nuDiasNacu, valTut, valCen, valDel, cif, ccodigo, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, inicio, fin FROM (                   "
           + "                      select NVL(mes.id_cotizames_prog,-1) as id,                     "
           + "                             cpa.id_convprog_alu as idAluCon,                  "
           + "                             cen.c_codigo || '-' || dat.d_especifica centro,                  "
           + "                             empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
           + "                             alu.c_numide as dni,                                                "
           + "                             alu.t_nuss as nuss,                                                 "
           + "                             alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                  "
           + "                             emp.d_empresa as nombreEmpresa,                                                  "
           + "                             'FCT' as tipoEmpresa,                                                  "
           + "                             omg.s_ofertamatrig curso,                                                  "
           + "                             uni.t_nombre unidad,                                                "
           + "                             NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par                "
           + "                                                   where par.id_convprog_alu = alp.id_convprog_alu           "
           + "                                                   and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
           + "                       mes.nu_dias_inte as nuDiasInte,           "
           + "                       mes.nu_dias_nacu as nuDiasNacu,           "
           + "                       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
           + "                       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
           + "                       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
           + "                       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
           + "                        cen.c_codigo as ccodigo,                                                              "
           + "                        CASE            "
           + "                        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
           + "                        ELSE 'No' END as enviado,                                       "
           + "                        CASE            "
           + "                        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
           + "                        ELSE 0 END as puederechazar,             "
           + "                        mdias.max as maxdias,            "
           + "                        alp.lg_erasmus_cb as lgErasBec, "
           + "                        mes.nu_dias_inte_era as diasInteEra, "
           + "                        CASE "
           + "                           when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
           + "                           when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
           + "                           else 'No Erasmus' "
           + "                        END as tipoErasmus, "
           + "              mes.ds_warnings as dsWarnings, "
           + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
           + "                          CASE        "
           + "                               WHEN (select count(*) from fct_datos_gestora_mes dgm        "
           + "                                     where dgm.x_matricula = mat.x_matricula        "
           + "                                     and mes.x_matricula = dgm.x_matricula        " 
           + "                                     and dgm.nu_mes = :nMes                             "
           + "                                     and dgm.ds_estado in ('P', 'F')) > 0 THEN 1        "
           + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
           + "                                   where dgm.x_matricula = mat.x_matricula  " 
           + "                                   and mes.x_matricula = dgm.x_matricula  " 
           + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
           + "                               ELSE 0 END esCorrecto,   "
           + "               NVL(NVL(alp.f_inicio, NVL((select MIN(aper.fh_inicio)         "
           + "                             from fct_conv_progaluhoraper aper        "
           + "                             where aper.x_matricula = mat.x_matricula),  "
           + "                             (select MIN(cper.fh_inicio)         "
           + "                             from fct_conv_proghoraper cper         "
           + "                             where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) inicio,          "
           + "                NVL(NVL(alp.f_fin,   NVL((select MAX(aper.fh_fin)         "
           + "                             from fct_conv_progaluhoraper aper          "
           + "                             where aper.x_matricula = mat.x_matricula), "
           + "                            (select MAX(cper.fh_fin)         "
           + "                             from fct_conv_proghoraper cper         "
           + "                            where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) AS fin "
           + "              from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                                                  "
           + "      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                  "
           + "      TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROG mes,                                                    "
           + "       (select CASE            "
           + "        WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
           + "        WHEN :nMes in (4,6,9,11) THEN 30           "
           + "        ELSE 28 END max           "
           + "        FROM DUAL) mdias           "
                 + "where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%')) " // Excluir registros con valor en DS_ERRORS en FCT_ALTASS_PROG y con los errores contemplados
                 + "     and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                  "
           + "     and tut.id_tutorfctdual = pro.id_tutorfctdual                                                  "
           + "     and pro.id_programa = cp.id_programa                                                 "
           + "     and cp.id_conv_prog = cpa.id_conv_prog                                                 "
           + "     and cpa.x_matricula = mat.x_matricula                                                 "
           + "     and mat.x_alumno = alu.x_alumno                                                 "
           + "     and alp.id_convprog_alu =  cpa.id_convprog_alu                                               "
           + "     and alp.x_matricula = mat.x_matricula           "
           + "     and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                   "
           + "     and mes.x_matricula(+) = mat.x_matricula           "
           + "     and cp.id_convenio = con.id_convenio                                                 "
           + "     and alp.f_envioss is not null           "
           + "     and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) "
           + "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   "
           + "                                          where mes1.x_matricula = mes.x_matricula   "
           + "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  "
           + "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      "                                    
           + "     and NVL(mes.LG_VALDEL,0) = 0  "
           + "     and con.x_empresa = emp.x_empresa                                                 "
           + "     and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
           + "     and mat.x_unidad = uni.x_unidad                      "
           + "     and dat.x_centro = pro.x_centro                  "
           + "     and cen.x_centro = dat.x_centro                                                                           "
           + "     and empl.x_empleado = tut.x_empleado                  "
           + "     and mes.nu_mes(+) = :nMes           "           
           + "     and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes "
           + "                    FROM DUAL "
           + "                    CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) "
           + "     and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
           + "     and mat.c_anno = :cAnno                            "
           + "     and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
           + "     and pro.x_centro IN (SELECT distinct dcen1.x_centro id                             "
           + "                          from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
           + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
           + "                          WHERE u.x_usuario = :idUsuario                             "
           + "                          AND pop.x_perfil = :idPerfil                             "
           + "                          AND pto.x_centro = :idCentro                             "
           + "                          AND pto.x_empleado=u.x_empleado                             "
           + "                          AND pop.x_empleado=pto.x_empleado                             "
           + "                          AND pop.f_tomapos = pto.f_tomapos                             "
           + "                          AND pto.x_centro = dcen.x_centro                             "
           + "                          AND dcen.c_provincia = prv.c_provincia                              "
           + "                          AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
           + "                          AND dcen1.c_provincia = prv.c_provincia                             "
           + "                           AND dcen1.l_vigente = 'S'                             "
           + "                          AND cen.x_centro = dcen1.x_centro                             "
           + "                          AND cen.l_delegacion = 'N'                             "
           + "                          AND cen.l_extranjero = 'N')                         "
           + "     and :tipoEmpresa in (-1,1)                                                 "
           + "     and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
           + "     and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
           + "     and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                 "
           + "     and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) "
           + "     and NVL(cpa.lg_cotiza,0) = 1                                               "
           //+ "     and not exists (select 1  FROM FCT_COTIZAMES_PROG cot1 "
           //+ "                     where  cot1.id_convprog_alu != cpa.id_convprog_alu " 
           //+ "                     and cot1.x_matricula = cpa.x_matricula        "
           //+ "                     and cot1.nu_mes = :nMes) "
              + "     and NVL(cpa.lg_excluir,0) = 0 "
           + "     union                                                 "
           + "     select NVL(mes.id_cotizames_proy,-1) as id,                       "
           + "       cpa.id_convproy_alu as idAluCon,                  "
           + "       cen.c_codigo || '-' || dat.d_especifica centro,                  "
           + "       empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
           + "       alu.c_numide as dni,                                               "
           + "       alu.t_nuss as nuss,                                                "
           + "       alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                 "
           + "       emp.d_empresa as nombreEmpresa,                                                 "
           + "       'FP Dual' as tipoEmpresa,                                                 "
           + "       omg.s_ofertamatrig curso,                                                 "
           + "       uni.t_nombre unidad,                                                           "
           + "       NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par                "
           + "                             where par.id_convproy_alu = aly.id_convproy_alu           "
           + "                            and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
           + "       mes.nu_dias_inte as nuDiasInte,           "
           + "       mes.nu_dias_nacu as nuDiasNacu,           "
           + "       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
           + "       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
           + "       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
           + "       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
           + "        cen.c_codigo as ccodigo,    "
           + "        CASE            "
           + "        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
           + "        ELSE 'No' END as enviado,                                       "
           + "        CASE            "
           + "        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
           + "        ELSE 0 END as puederechazar, "
           + "        mdias.max as maxdias,            "
           + "       aly.lg_erasmus_cb as lgErasBec, "
           + "       mes.nu_dias_inte_era as diasInteEra, "
           + "       CASE "
           + "           when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
           + "           when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
           + "           else 'No Erasmus' "
           + "       END as tipoErasmus, "
           + "              mes.ds_warnings as dsWarnings, "
           + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
           + "                          CASE         "
           + "                                      WHEN (select count(*) from fct_datos_gestora_mes dgm         "
           + "                                            where dgm.x_matricula = mat.x_matricula         "
           + "                                            and mes.x_matricula = dgm.x_matricula        "
           + "                                            and dgm.nu_mes = :nMes                              "
           + "                                            and dgm.ds_estado in ('P', 'F')) > 0 THEN 1         "
           + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
           + "                                   where dgm.x_matricula = mat.x_matricula  " 
           + "                                   and mes.x_matricula = dgm.x_matricula  " 
           + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
           + "                                      ELSE 0 END esCorrecto,  "
           + "                NVL(NVL(aly.f_inicio, NVL((select MIN(aper.fh_inicio)         "
           + "                             from fct_conv_progaluhoraper aper        "
           + "                             where aper.x_matricula = mat.x_matricula),  "
           + "                             (select MIN(cper.fh_inicio)         "
           + "                             from fct_conv_proghoraper cper         "
           + "                             where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) inicio,          "
           + "                NVL(NVL(aly.f_fin,   NVL((select MAX(aper.fh_fin)         "
           + "                             from fct_conv_progaluhoraper aper          "
           + "                             where aper.x_matricula = mat.x_matricula), "
           + "                            (select MAX(cper.fh_fin)         "
           + "                             from fct_conv_proghoraper cper         "
           + "                            where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) AS fin "
           + "       from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                                                 "
           + "            TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                 "
           + "            TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROY mes,                                                   "
           + "           (select CASE            "
           + "            WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
           + "            WHEN :nMes in (4,6,9,11) THEN 30           "
           + "            ELSE 28 END max           "
           + "            FROM DUAL) mdias           "
                 + "where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%')) " // Excluir registros con valor en DS_ERRORS en FCT_ALTASS_PROY y con los errores contemplados
                 + "       and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                 "
           + "       and tut.id_tutorfctdual = pro.id_tutorfctdual                                                 "
           + "       and pro.id_proyecto = cp.id_proyecto                                                 "
           + "       and cp.id_conv_proy = cpa.id_conv_proy                                                 "
           + "       and cpa.x_matricula = mat.x_matricula                                                 "
           + "       and mat.x_alumno = alu.x_alumno                                                 "
           + "       and cp.id_convenio = con.id_convenio                                                 "
           + "       and con.x_empresa = emp.x_empresa                                                 "
           + "       and aly.id_convproy_alu =  cpa.id_convproy_alu                                                "
           + "       and aly.f_envioss is not null           "
           + "       and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) "
           + "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  "
           + "                                           where mes1.x_matricula = mes.x_matricula  "
           + "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null "
           + "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    "
           + "       and NVL(mes.LG_VALDEL,0) = 0  "
           + "       and aly.x_matricula = mat.x_matricula           "
           + "       and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                   "
           + "       and mes.x_matricula(+) = mat.x_matricula            "
           + "       and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
           + "       and mat.x_unidad = uni.x_unidad                        "
           + "       and dat.x_centro = pro.x_centro                  "
           + "       and cen.x_centro = dat.x_centro                                                                   "
           + "       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
           + "       and empl.x_empleado = tut.x_empleado                  "
           + "       and mes.nu_mes(+) = :nMes           "           
           + "       and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes "
           + "                      FROM DUAL "
           + "                      CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) "
           + "       and mat.c_anno = :cAnno                         "
           + "       and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
           + "       and pro.x_centro IN (SELECT distinct dcen1.x_centro id                             "
           + "                            from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
           + "                                 TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
           + "                             WHERE u.x_usuario = :idUsuario                             "
           + "                             AND pop.x_perfil = :idPerfil                             "
           + "                             AND pto.x_centro = :idCentro                             "
           + "                             AND pto.x_empleado=u.x_empleado                             "
           + "                             AND pop.x_empleado=pto.x_empleado                             "
           + "                             AND pop.f_tomapos = pto.f_tomapos                             "
           + "                             AND pto.x_centro = dcen.x_centro                             "
           + "                             AND dcen.c_provincia = prv.c_provincia                              "
           + "                             AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
           + "                             AND dcen1.c_provincia = prv.c_provincia                             "
           + "                             AND dcen1.l_vigente = 'S'                             "
           + "                             AND cen.x_centro = dcen1.x_centro                             "
           + "                             AND cen.l_delegacion = 'N'                             "
           + "                             AND cen.l_extranjero = 'N')                           "
           + "        and :tipoEmpresa in (-1,2)                                                 "
           + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
           + "        and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
           + "        and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                "
           + "        and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb) "
           //+ "        and not exists (select 1  FROM FCT_COTIZAMES_PROY cot1 "
           //+ "                        where  cot1.id_convproy_alu != cpa.id_convproy_alu " 
           //+ "                        and cot1.x_matricula = cpa.x_matricula        "
           //+ "                        and cot1.nu_mes = :nMes) " 
           + "        and NVL(cpa.lg_cotiza,0) = 1                 "
              + "        and NVL(cpa.lg_excluir,0) = 0) "
           + "    ) original_query "
           + ") ranked_data "
           + "WHERE row_num = 1 "
           + "order by nombreAlumno, curso, unidad, nombreEmpresa   ", nativeQuery = true)
         List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMesDelegacionSinValidar(Long idTutorfctdual,
                                                                                     Long idCentro,
                                                                                     Integer cAnno,
                                                                                     Integer tipoEmpresa,
                                                                                     Long idEmpresa,
                                                                                     Long idOfertamatrig,
                                                                                     Long idUnidad,
                                                                                     Long idPerfil,
                                                                                     Long idCentroCombo,
                                                                                     Long idUsuario,                                                                               
                                                                                     Integer nMes,
                                                                                     Integer idTipo);
    
    @Query(value = " SELECT * "
         + "FROM ( "
         + "    SELECT  "
         + "        id, "
         + "        idAluCon, "
         + "        centro, "
         + "        tutor, "
         + "        dni, "
         + "        nuss, "
         + "        nombreAlumno, "
         + "        nombreEmpresa, "
         + "        tipoEmpresa, "
         + "        curso, "
         + "        unidad,         "
         + "        nuDiasReal, "
         + "        nuDiasInte, "
         + "        nuDiasNacu, "
         + "        valTut, "
         + "        valCen, "
         + "        valDel, "
         + "        enviado, "
         + "        puederechazar, "
         + "        ROW_NUMBER() OVER ( "
         + "                PARTITION BY nuss,  "
         + "                CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  "
         + "                ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon "
         + "        ) AS row_num, "
         + "        lgErasBec, "
         + "        diasInteEra, "
         + "        tipoErasmus, "
         + "     dsWarnings, "
         + "     fechaEnvio, "
         + "     esCorrecto, "
         + "     TO_CHAR(inicio, 'DD/MM/YYYY') inicio, "
         + "     TO_CHAR(fin, 'DD/MM/YYYY') fin, "    
         + "    CASE    "
         + "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " 
         + "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) "   
         + "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    "
         + "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN "   
         + "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    "
         + "    ELSE 0  "  
         + "    END AS diasRestantes, "
         + "    0 AS avisoMes, "
         + "    0 AS bloqueoMes "
         + "  FROM    "
         + "  (select CASE "                  
         + "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "                                             
         + "          ELSE TO_CHAR(:cAnno)  END anno                  "
         + "        FROM DUAL) annonat, "
         + "   ( "      
         + "    SELECT distinct  id, idAluCon, centro, tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,           "
         + "           CASE           "
         + "           WHEN nuDiasReal>maxdias THEN maxdias           "
         + "           ELSE nuDiasReal END nuDiasReal,            "
         + "           nuDiasInte, nuDiasNacu, valTut, valCen, valDel, cif, ccodigo, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, inicio, fin FROM (                   "
         + "                      select NVL(mes.id_cotizames_prog,-1) as id,                     "
         + "                             cpa.id_convprog_alu as idAluCon,                  "
         + "                             cen.c_codigo || '-' || dat.d_especifica centro,                  "
         + "                             empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
         + "                             alu.c_numide as dni,                                                "
         + "                             alu.t_nuss as nuss,                                                 "
         + "                             alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                  "
         + "                             emp.d_empresa as nombreEmpresa,                                                  "
         + "                             'FCT' as tipoEmpresa,                                                  "
         + "                             omg.s_ofertamatrig curso,                                                  "
         + "                             uni.t_nombre unidad,                                                "
         + "                             NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par                "
         + "                                                   where par.id_convprog_alu = alp.id_convprog_alu           "
         + "                                                   and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
         + "                       mes.nu_dias_inte as nuDiasInte,           "
         + "                       mes.nu_dias_nacu as nuDiasNacu,           "
         + "                       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
         + "                       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
         + "                       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
         + "                       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
         + "                        cen.c_codigo as ccodigo,                                                              "
         + "                        CASE            "
         + "                        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
         + "                        ELSE 'No' END as enviado,                                       "
         + "                        CASE            "
         + "                        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
         + "                        ELSE 0 END as puederechazar,             "
         + "                        mdias.max as maxdias,            "
         + "                        alp.lg_erasmus_cb as lgErasBec, "
         + "                        mes.nu_dias_inte_era as diasInteEra, "
         + "                        CASE "
         + "                           when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
         + "                           when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
         + "                           else 'No Erasmus' "
         + "                        END as tipoErasmus, "
         + "              mes.ds_warnings as dsWarnings, "
         + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
         + "                          CASE        "
         + "                               WHEN (select count(*) from fct_datos_gestora_mes dgm        "
         + "                                     where dgm.x_matricula = mat.x_matricula        "
         + "                                     and mes.x_matricula = dgm.x_matricula        " 
         + "                                     and dgm.nu_mes = :nMes                             "
         + "                                     and dgm.ds_estado in ('P', 'F')) > 0 THEN 1        "
         + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
         + "                                   where dgm.x_matricula = mat.x_matricula  " 
         + "                                   and mes.x_matricula = dgm.x_matricula  " 
         + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
         + "                               ELSE 0 END esCorrecto,   "
         + "               NVL(NVL(alp.f_inicio, NVL((select MIN(aper.fh_inicio)         "
         + "                             from fct_conv_progaluhoraper aper        "
         + "                             where aper.x_matricula = mat.x_matricula),  "
         + "                             (select MIN(cper.fh_inicio)         "
         + "                             from fct_conv_proghoraper cper         "
         + "                             where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) inicio,          "
         + "                NVL(NVL(alp.f_fin,   NVL((select MAX(aper.fh_fin)         "
         + "                             from fct_conv_progaluhoraper aper          "
         + "                             where aper.x_matricula = mat.x_matricula), "
         + "                            (select MAX(cper.fh_fin)         "
         + "                             from fct_conv_proghoraper cper         "
         + "                            where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) AS fin "
         + "              from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                                                  "
         + "      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                  "
         + "      TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROG mes,                                                    "
         + "       (select CASE            "
         + "        WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
         + "        WHEN :nMes in (4,6,9,11) THEN 30           "
         + "        ELSE 28 END max           "
         + "        FROM DUAL) mdias           "
               + "where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%')) " // Excluir registros con valor en DS_ERRORS en FCT_ALTASS_PROG y con los errores contemplados
               + "     and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                  "
         + "     and tut.id_tutorfctdual = pro.id_tutorfctdual                                                  "
         + "     and pro.id_programa = cp.id_programa                                                 "
         + "     and cp.id_conv_prog = cpa.id_conv_prog                                                 "
         + "     and cpa.x_matricula = mat.x_matricula                                                 "
         + "     and mat.x_alumno = alu.x_alumno                                                 "
         + "     and alp.id_convprog_alu =  cpa.id_convprog_alu                                               "
         + "     and alp.x_matricula = mat.x_matricula           "
         + "     and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                   "
         + "     and mes.x_matricula(+) = mat.x_matricula           "
         + "     and cp.id_convenio = con.id_convenio                                                 "
         + "     and alp.f_envioss is not null           "
         + "     and mes.f_envioss is null "
         + "     and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) "
         + "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   "
         + "                                          where mes1.x_matricula = mes.x_matricula   "
         + "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  "
         + "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      "                                    
         + "     and mes.LG_VALDEL = 1  "
         + "     and con.x_empresa = emp.x_empresa                                                 "
         + "     and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
         + "     and mat.x_unidad = uni.x_unidad                      "
         + "     and dat.x_centro = pro.x_centro                  "
         + "     and cen.x_centro = dat.x_centro                                                                           "
         + "     and empl.x_empleado = tut.x_empleado                  "
         + "     and mes.nu_mes(+) = :nMes           "           
         + "     and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes "
         + "                    FROM DUAL "
         + "                    CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) "
         + "     and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
         + "     and mat.c_anno = :cAnno                            "
         + "     and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
         + "     and pro.x_centro IN (SELECT distinct dcen1.x_centro id                             "
         + "                          from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
         + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
         + "                          WHERE u.x_usuario = :idUsuario                             "
         + "                          AND pop.x_perfil = :idPerfil                             "
         + "                          AND pto.x_centro = :idCentro                             "
         + "                          AND pto.x_empleado=u.x_empleado                             "
         + "                          AND pop.x_empleado=pto.x_empleado                             "
         + "                          AND pop.f_tomapos = pto.f_tomapos                             "
         + "                          AND pto.x_centro = dcen.x_centro                             "
         + "                          AND dcen.c_provincia = prv.c_provincia                              "
         + "                          AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
         + "                          AND dcen1.c_provincia = prv.c_provincia                             "
         + "                           AND dcen1.l_vigente = 'S'                             "
         + "                          AND cen.x_centro = dcen1.x_centro                             "
         + "                          AND cen.l_delegacion = 'N'                             "
         + "                          AND cen.l_extranjero = 'N')                         "
         + "     and :tipoEmpresa in (-1,1)                                                 "
         + "     and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
         + "     and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
         + "     and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                 "
         + "     and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) "
         + "     and NVL(cpa.lg_cotiza,0) = 1                                               "
         //+ "     and not exists (select 1  FROM FCT_COTIZAMES_PROG cot1 "
         //+ "                     where  cot1.id_convprog_alu != cpa.id_convprog_alu " 
         //+ "                     and cot1.x_matricula = cpa.x_matricula        "
         //+ "                     and cot1.nu_mes = :nMes) "
          + "     and NVL(cpa.lg_excluir,0) = 0 "
         + "     union                                                 "
         + "     select NVL(mes.id_cotizames_proy,-1) as id,                       "
         + "       cpa.id_convproy_alu as idAluCon,                  "
         + "       cen.c_codigo || '-' || dat.d_especifica centro,                  "
         + "       empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,                  "
         + "       alu.c_numide as dni,                                               "
         + "       alu.t_nuss as nuss,                                                "
         + "       alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                                                 "
         + "       emp.d_empresa as nombreEmpresa,                                                 "
         + "       'FP Dual' as tipoEmpresa,                                                 "
         + "       omg.s_ofertamatrig curso,                                                 "
         + "       uni.t_nombre unidad,                                                           "
         + "       NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par                "
         + "                             where par.id_convproy_alu = aly.id_convproy_alu           "
         + "                            and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,              "
         + "       mes.nu_dias_inte as nuDiasInte,           "
         + "       mes.nu_dias_nacu as nuDiasNacu,           "
         + "       DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                  "
         + "       DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                  "
         + "       DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                                                "
         + "       (select cif from xx_centrosfct where cod_centro = cen.c_codigo)  as cif,             "
         + "        cen.c_codigo as ccodigo,    "
         + "        CASE            "
         + "        WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
         + "        ELSE 'No' END as enviado,                                       "
         + "        CASE            "
         + "        WHEN mes.f_envioss IS NULL AND (mes.lg_valtut = 1 OR mes.lg_valcen = 1 OR mes.lg_valdel = 1 ) THEN 1            "
         + "        ELSE 0 END as puederechazar, "
         + "        mdias.max as maxdias,            "
         + "       aly.lg_erasmus_cb as lgErasBec, "
         + "       mes.nu_dias_inte_era as diasInteEra, "
         + "       CASE "
         + "           when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
         + "           when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
         + "           else 'No Erasmus' "
         + "       END as tipoErasmus, "
         + "              mes.ds_warnings as dsWarnings, "
         + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
         + "                          CASE         "
         + "                                      WHEN (select count(*) from fct_datos_gestora_mes dgm         "
         + "                                            where dgm.x_matricula = mat.x_matricula         "
         + "                                            and mes.x_matricula = dgm.x_matricula        "
         + "                                            and dgm.nu_mes = :nMes                              "
         + "                                            and dgm.ds_estado in ('P', 'F')) > 0 THEN 1         "
         + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
         + "                                   where dgm.x_matricula = mat.x_matricula  " 
         + "                                   and mes.x_matricula = dgm.x_matricula  " 
         + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
         + "                                      ELSE 0 END esCorrecto,  "
         + "                NVL(NVL(aly.f_inicio, NVL((select MIN(aper.fh_inicio)         "
         + "                             from fct_conv_progaluhoraper aper        "
         + "                             where aper.x_matricula = mat.x_matricula),  "
         + "                             (select MIN(cper.fh_inicio)         "
         + "                             from fct_conv_proghoraper cper         "
         + "                             where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) inicio,          "
         + "                NVL(NVL(aly.f_fin,   NVL((select MAX(aper.fh_fin)         "
         + "                             from fct_conv_progaluhoraper aper          "
         + "                             where aper.x_matricula = mat.x_matricula), "
         + "                            (select MAX(cper.fh_fin)         "
         + "                             from fct_conv_proghoraper cper         "
         + "                            where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) AS fin "
         + "       from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                                                 "
         + "            TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                                                 "
         + "            TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROY mes,                                                   "
         + "           (select CASE            "
         + "            WHEN :nMes in (1,3,5,7,8,10,12) THEN 31           "
         + "            WHEN :nMes in (4,6,9,11) THEN 30           "
         + "            ELSE 28 END max           "
         + "            FROM DUAL) mdias           "
               + "where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%')) " // Excluir registros con valor en DS_ERRORS en FCT_ALTASS_PROY y con los errores contemplados
               + "       and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                                                 "
         + "       and tut.id_tutorfctdual = pro.id_tutorfctdual                                                 "
         + "       and pro.id_proyecto = cp.id_proyecto                                                 "
         + "       and cp.id_conv_proy = cpa.id_conv_proy                                                 "
         + "       and cpa.x_matricula = mat.x_matricula                                                 "
         + "       and mat.x_alumno = alu.x_alumno                                                 "
         + "       and cp.id_convenio = con.id_convenio                                                 "
         + "       and con.x_empresa = emp.x_empresa                                                 "
         + "       and aly.id_convproy_alu =  cpa.id_convproy_alu                                                "
         + "       and aly.f_envioss is not null           "
         + "       and mes.f_envioss is null "
         + " and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) "
         + "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  "
         + "                                           where mes1.x_matricula = mes.x_matricula  "
         + "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null "
         + "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    "
         + "       and mes.LG_VALDEL = 1  "
         + "       and aly.x_matricula = mat.x_matricula           "
         + "       and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                   "
         + "       and mes.x_matricula(+) = mat.x_matricula            "
         + "       and mat.x_ofertamatrig = omg.x_ofertamatrig                                                 "
         + "       and mat.x_unidad = uni.x_unidad                        "
         + "       and dat.x_centro = pro.x_centro                  "
         + "       and cen.x_centro = dat.x_centro                                                                   "
         + "       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                                                 "
         + "       and empl.x_empleado = tut.x_empleado                  "
         + "       and mes.nu_mes(+) = :nMes           "           
         + "       and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes "
         + "                      FROM DUAL "
         + "                      CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) "
         + "       and mat.c_anno = :cAnno                         "
         + "       and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                        "
         + "       and pro.x_centro IN (SELECT distinct dcen1.x_centro id                             "
         + "                            from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,                             "
         + "                                 TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv                              "
         + "                             WHERE u.x_usuario = :idUsuario                             "
         + "                             AND pop.x_perfil = :idPerfil                             "
         + "                             AND pto.x_centro = :idCentro                             "
         + "                             AND pto.x_empleado=u.x_empleado                             "
         + "                             AND pop.x_empleado=pto.x_empleado                             "
         + "                             AND pop.f_tomapos = pto.f_tomapos                             "
         + "                             AND pto.x_centro = dcen.x_centro                             "
         + "                             AND dcen.c_provincia = prv.c_provincia                              "
         + "                             AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)                             "
         + "                             AND dcen1.c_provincia = prv.c_provincia                             "
         + "                             AND dcen1.l_vigente = 'S'                             "
         + "                             AND cen.x_centro = dcen1.x_centro                             "
         + "                             AND cen.l_delegacion = 'N'                             "
         + "                             AND cen.l_extranjero = 'N')                           "
         + "        and :tipoEmpresa in (-1,2)                                                 "
         + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                                                 "
         + "        and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                                                 "
         + "        and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                                                "
         + "        and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb) "
         //+ "        and not exists (select 1  FROM FCT_COTIZAMES_PROY cot1 "
         //+ "                        where  cot1.id_convproy_alu != cpa.id_convproy_alu " 
         //+ "                        and cot1.x_matricula = cpa.x_matricula        "
         //+ "                        and cot1.nu_mes = :nMes) " 
         + "        and NVL(cpa.lg_cotiza,0) = 1                 "
          + "        and NVL(cpa.lg_excluir,0) = 0) "
          + "    ) original_query "
         + ") ranked_data "
         + "WHERE row_num = 1 "
         + "order by nombreAlumno, curso, unidad, nombreEmpresa   ", nativeQuery = true)
       List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMesDelegacionValidadasSinEnviar(Long idTutorfctdual,
                                                                                   Long idCentro,
                                                                                   Integer cAnno,
                                                                                   Integer tipoEmpresa,
                                                                                   Long idEmpresa,
                                                                                   Long idOfertamatrig,
                                                                                   Long idUnidad,
                                                                                   Long idPerfil,
                                                                                   Long idCentroCombo,
                                                                                   Long idUsuario,                                                                               
                                                                                   Integer nMes,
                                                                                   Integer idTipo);

    @Query(value = " SELECT * "
      + "FROM ( "
      + "    SELECT  "
      + "        id, "
      + "        idAluCon, "
      + "        centro, "
      + "        tutor, "
      + "        dni, "
      + "        nuss, "
      + "        nombreAlumno, "
      + "        nombreEmpresa, "
      + "        tipoEmpresa, "
      + "        curso, "
      + "        unidad,         "
      + "        nuDiasReal, "
      + "        nuDiasInte, "
      + "        nuDiasNacu, "
      + "        valTut, "
      + "        valCen, "
      + "        valDel, "
      + "        enviado, "
      + "        puederechazar, "
      + "        ROW_NUMBER() OVER ( "
      + "                PARTITION BY nuss,  "
      + "                CASE WHEN tipoErasmus = 'Erasmus con Beca' THEN tipoErasmus END  "
      + "                ORDER BY  CASE WHEN id != -1 THEN 0 ELSE 1 END, idAluCon "
      + "        ) AS row_num, "
      + "        lgErasBec, "
      + "        diasInteEra, "
      + "        tipoErasmus, "
      + "     dsWarnings, "
      + "     fechaEnvio, "
      + "     esCorrecto, "
      + "     esCorrectoAlta, "
      + "     TO_CHAR(inicio, 'DD/MM/YYYY') inicio, "
      + "     TO_CHAR(fin, 'DD/MM/YYYY') fin, "    
      + "    CASE    "
      + "    WHEN :nMes = EXTRACT(MONTH FROM inicio) THEN   " 
      + "    EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno)))) - (EXTRACT(DAY FROM inicio) - 1) "   
      + "    WHEN :nMes = EXTRACT(MONTH FROM fin) THEN EXTRACT(DAY FROM (fin))    "
      + "    WHEN (TO_DATE('01-' || :nMes || '-' || annonat.anno) BETWEEN TRUNC(inicio, 'MONTH') AND TRUNC(fin, 'MONTH')) THEN "   
      + "      EXTRACT(DAY FROM(LAST_DAY(TO_DATE('01-' || :nMes || '-' || annonat.anno))))    "
      + "    ELSE 0  "  
      + "    END AS diasRestantes, "
      + "    0 AS avisoMes, "
      + "    0 AS bloqueoMes "
      + "  FROM    "
      + "  (select CASE "                  
      + "          WHEN :nMes in (1,2,3,4,5,6,7,8) THEN TO_CHAR((:cAnno +1)) "                                             
      + "          ELSE TO_CHAR(:cAnno)  END anno                  "
      + "        FROM DUAL) annonat, "
      + "   ( "     
      + "        SELECT distinct id, idAluCon, centro, tutor, dni, nuss, nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad,         "
      + "            CASE         "
      + "            WHEN nuDiasReal>maxdias THEN maxdias         "
      + "            ELSE nuDiasReal END nuDiasReal,          "
      + "            nuDiasInte, nuDiasNacu, valTut, valCen, valDel, enviado, puederechazar, lgErasBec, diasInteEra, tipoErasmus, dsWarnings, fechaEnvio, esCorrecto, esCorrectoAlta, inicio, fin FROM (                 "
      + "               select NVL(mes.id_cotizames_prog,-1) as id,                "
      + "                      cpa.id_convprog_alu as idAluCon,              "
      + "                      cen.c_codigo || '-' || dat.d_especifica centro,               "
      + "                      empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,              "
      + "                      alu.c_numide as dni,                            "
      + "                      alu.t_nuss as nuss,                             "
      + "                      alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                              "
      + "                      emp.d_empresa as nombreEmpresa,                              "
      + "                     'FCT' as tipoEmpresa,                              "
      + "                      omg.s_ofertamatrig curso,                              "
      + "                      uni.t_nombre unidad,                            "
      + "                      NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluprog par              "
      + "                                            where par.id_convprog_alu = alp.id_convprog_alu         "
      + "                                            and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,            "
      + "                      mes.nu_dias_inte as nuDiasInte,         "
      + "                      mes.nu_dias_nacu as nuDiasNacu,         "
      + "                      DECODE(mes.lg_valtut,1,'Sí','No') as valTut,                "
      + "                      DECODE(mes.lg_valcen,1,'Sí','No') as valCen,                "
      + "                      DECODE(mes.lg_valdel,1,'Sí','No') as valDel,                            "
      + "                      CASE            "
      + "                      WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "                      ELSE 'No' END as enviado,                                       "
      + "                      0 as puederechazar,         "
      + "                      mdias.max as maxdias,          "
      + "                      alp.lg_erasmus_cb as lgErasBec, "
      + "                      mes.nu_dias_inte_era as diasInteEra, "
      + "                      CASE "
      + "                            when alp.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "                            when alp.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "                            else 'No Erasmus' "
      + "                      END as tipoErasmus, "
      + "                   mes.ds_warnings as dsWarnings, "
      + "                    TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "                          CASE          "
      + "                          WHEN (select count(*) from fct_datos_gestora_mes dgm          "
      + "                                where dgm.x_matricula = mat.x_matricula          "
      + "                                and mes.x_matricula = dgm.x_matricula         "
      + "                                and dgm.nu_mes = :nMes                               "
      + "                                and dgm.ds_estado in ('P', 'F')) > 0 THEN 1          "
      + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
      + "                                   where dgm.x_matricula = mat.x_matricula  " 
      + "                                   and mes.x_matricula = dgm.x_matricula  " 
      + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
      + "                          ELSE 0 END esCorrecto,  "
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "
      /*+ "               NVL(NVL(alp.f_inicio, NVL((select MIN(aper.fh_inicio)         "
      + "                             from fct_conv_progaluhoraper aper        "
      + "                             where aper.x_matricula = mat.x_matricula),  "
      + "                             (select MIN(cper.fh_inicio)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                             where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) inicio,          "
      + "                NVL(NVL(alp.f_fin,   NVL((select MAX(aper.fh_fin)         "
      + "                             from fct_conv_progaluhoraper aper          "
      + "                             where aper.x_matricula = mat.x_matricula), "
      + "                            (select MAX(cper.fh_fin)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                            where cper.id_conv_prog = cp.id_conv_prog))),TRUNC(sysdate)) AS fin " */
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convprog_alu = mes.id_convprog_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_prog alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "         WHEN  alp.f_inicio is not null THEN alp.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_progaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_progaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proghoraper cper            "
            + "                        where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proghoraper cper           " 
            + "                                                                                    where cper.id_conv_prog = cp.id_conv_prog) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_prog alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convprog_alu = mes.id_convprog_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_prog alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convprog_alu = mes.id_convprog_alu) "
            + "      WHEN  alp.f_fin is not null THEN alp.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_progaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_progaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proghoraper cper           " 
            + "                     where cper.id_conv_prog = cp.id_conv_prog) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proghoraper cper    "       
            + "                                                                                 where cper.id_conv_prog = cp.id_conv_prog)   "
            + "      ELSE  TRUNC(sysdate) END fin    " 
      + "            from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,                              "
      + "                 TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                              "
      + "                 TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROG alp, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROG mes,                                "
      + "                (select CASE          "
      + "                 WHEN :nMes in (1,3,5,7,8,10,12) THEN 31         "
      + "                 WHEN :nMes in (4,6,9,11) THEN 30         "
      + "                 ELSE 28 END max         "
      + "                 FROM DUAL) mdias         "
            + "           where (alp.ds_errors IS NULL OR (alp.ds_errors NOT LIKE '%3688%' AND alp.ds_errors NOT LIKE '%7934%')) "  // Excluir registros con ds_errors que contienen '3688' o '7934'
      + "           and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                              "
      + "           and tut.id_tutorfctdual = pro.id_tutorfctdual                              "
      + "           and pro.id_programa = cp.id_programa                              "
      + "           and cp.id_conv_prog = cpa.id_conv_prog                              "
      + "           and cpa.x_matricula = mat.x_matricula                              "
      + "           and mat.x_alumno = alu.x_alumno                              "
      + "           and alp.id_convprog_alu =  cpa.id_convprog_alu                            "            
      + "           and alp.x_matricula = mat.x_matricula         "
      + "           and mes.id_convprog_alu(+) =  cpa.id_convprog_alu                 "
      + "           and mes.x_matricula(+) = mat.x_matricula          "
      + "           and cp.id_convenio = con.id_convenio                              "
      + "           and alp.f_envioss is not null         "
      + "           and (NVL(alp.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes  and mes1.f_envioss is not null ) "
      + "                               OR EXISTS (select 1 from FCT_COTIZAMES_PROG mes1, fct_historicomes_prog his   "
      + "                                          where mes1.x_matricula = mes.x_matricula   "
      + "                                          and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null  "
      + "                                          and his.id_cotizames_prog = mes1.id_cotizames_prog ))      "                                    
      + "           and con.x_empresa = emp.x_empresa                              "
      + "           and mat.x_ofertamatrig = omg.x_ofertamatrig                              "
      + "           and mat.x_unidad = uni.x_unidad                                   "
      + "           and dat.x_centro = pro.x_centro              "
      + "           and cen.x_centro = dat.x_centro              "
      + "           and empl.x_empleado = tut.x_empleado             "
      + "           and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                              "
      + "           and mat.c_anno = :cAnno                        "
      + "           and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                              "
      + "           and pro.x_centro IN (SELECT distinct dcen.x_centro id                                "
      + "                                from  TLDATOSCEN dcen, TLPROVINCIAS prv ,                                "
      + "                                      TLCENTROS cen, FCT_CONVENIOS conv                                 "
      + "                                WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)                                      "
      + "                                AND dcen.c_provincia = prv.c_provincia                                "
      + "                                AND dcen.l_vigente = 'S'                                "
      + "                                AND cen.x_centro = dcen.x_centro                                "
      + "                                AND cen.l_delegacion = 'N'                                "
      + "                                AND cen.l_extranjero = 'N')                       "
      + "           and :tipoEmpresa in (-1,1)                              "
      + "           and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                              "
      + "           and mes.nu_mes(+) = :nMes         "
      //+ "           and :nMes in (SELECT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1)) "
      //+ "                         FROM DUAL "
      //+ "                         CONNECT BY TRUNC(alp.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1) <= TRUNC(alp.f_fin)) "
      + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes "
      + "                          FROM DUAL "
      + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin)) "
      + "           and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                              "
      + "           and ( :idTipo = -1 or :idTipo = alp.lg_erasmus_cb) "
      + "           and NVL(cpa.lg_cotiza,0) = 1                            "      
      //+ "           and not exists (select 1  FROM FCT_COTIZAMES_PROG cot1 "
      //+ "                           where  cot1.id_convprog_alu != cpa.id_convprog_alu " 
      //+ "                           and cot1.x_matricula = cpa.x_matricula "       
      //+ "                           and cot1.nu_mes = :nMes) "
      + "           and NVL(cpa.lg_excluir,0) = 0 "
      + "           union                              "
      + "           select NVL(mes.id_cotizames_proy,-1) as id,                 "
      + "                  cpa.id_convproy_alu as idAluCon,              "
      + "                  cen.c_codigo || '-' || dat.d_especifica centro,               "
      + "                  empl.nombre || ' ' || empl.apellido1 || ' ' || empl.apellido2 as tutor,              "
      + "                  alu.c_numide as dni,                            "
      + "                  alu.t_nuss as nuss,                             "
      + "                  alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,                              "
      + "                  emp.d_empresa as nombreEmpresa,                              "
      + "                  'FP Dual' as tipoEmpresa,                              "
      + "                  omg.s_ofertamatrig curso,                              "
      + "                  uni.t_nombre unidad,                                        "
      + "                  NVL(mes.nu_dias_real,(select NVL(SUM(nu_dias),0) from fct_parsem_aluproy par              "
      + "                                        where par.id_convproy_alu = aly.id_convproy_alu         "
      + "                                        and TO_CHAR(par.fh_inisem,'MM') = :nMes)) as nuDiasReal,            "
      + "                 mes.nu_dias_inte as nuDiasInte,         "
      + "                 mes.nu_dias_nacu as nuDiasNacu,          "
      + "                 DECODE(mes.lg_valtut,1,'Sí','No') AS valTut,                "
      + "                 DECODE(mes.lg_valcen,1,'Sí','No') AS valCen,                "
      + "                 DECODE(mes.lg_valdel,1,'Sí','No') AS valDel,                          "
      + "                 CASE            "
      + "                 WHEN mes.f_envioss IS NOT NULL THEN 'Sí'            "
      + "                 ELSE 'No' END as enviado,                                       "
      + "                 0 as puederechazar,         "
      + "                 mdias.max as maxdias,          "
      + "                 aly.lg_erasmus_cb as lgErasBec, "
      + "                 mes.nu_dias_inte_era as diasInteEra, "
      + "                 CASE "
      + "                    when aly.lg_erasmus_cb = 1 then 'Erasmus con Beca' "
      + "                    when aly.lg_erasmus_sb = 1 then 'Erasmus sin Beca' "
      + "                    else 'No Erasmus' "
      + "                 END as tipoErasmus, "
      + "              mes.ds_warnings as dsWarnings, "
      + "               TO_CHAR(mes.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
      + "                          CASE           "
      + "                           WHEN (select count(*) from fct_datos_gestora_mes dgm           "
      + "                                 where dgm.x_matricula = mat.x_matricula           "
      + "                                 and mes.x_matricula = dgm.x_matricula          "
      + "                                 and dgm.nu_mes = :nMes                                "
      + "                                 and dgm.ds_estado in ('P', 'F')) > 0 THEN 1           "
      + "                             WHEN (select count(*) from fct_datos_gestora_mes dgm  " 
      + "                                   where dgm.x_matricula = mat.x_matricula  " 
      + "                                   and mes.x_matricula = dgm.x_matricula  " 
      + "                                   and dgm.nu_mes = :nMes) = 0 THEN 1  "  
      + "                           ELSE 0 END esCorrecto,  "
      + "            (SELECT error FROM (SELECT "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN '1' "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN dg.CD_ERROR "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN dg.DS_ERROR_ALTA "
      + "                 WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN dg.DS_MOTIVO_ERROR_ARCHIVADO "
      + "                 ELSE '0' "
      + "             END AS error, "
      + "             CASE "
      + "                 WHEN UPPER(dg.DS_TIPO) = 'ALTA' AND UPPER(dg.DS_ESTADO) = 'S' THEN 1 "
      + "                 WHEN dg.CD_ERROR IS NOT NULL THEN 2 "
      + "                 WHEN dg.DS_ERROR_ALTA IS NOT NULL THEN 3 "
      + "                WHEN dg.DS_MOTIVO_ERROR_ARCHIVADO IS NOT NULL THEN 4 "
      + "                 ELSE 5 "
      + "             END AS prioridad "
      + "         FROM FCT_DATOS_GESTORA dg "
      + "         WHERE dg.X_MATRICULA = mat.x_matricula "
      + "           AND dg.X_CENTRO = pro.x_centro "
      + "           AND dg.NU_ANNO = :cAnno "
      + "           AND ( "
      + "                 (EXTRACT(MONTH FROM dg.F_ALTA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_ALTA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (EXTRACT(MONTH FROM dg.F_BAJA) = :nMes AND  "
      + "                  EXTRACT(YEAR FROM dg.F_BAJA) = TO_NUMBER(CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) " 
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END)) "
      + "               OR "
      + "                 (TO_DATE(:nMes || '/01/' || CASE  "
      + "                     WHEN :nMes IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(:cAnno + 1) "
      + "                     ELSE TO_CHAR(:cAnno) "
      + "                  END, 'MM/DD/YYYY') BETWEEN TRUNC(dg.F_ALTA) AND TRUNC(dg.F_BAJA)) "
      + "              ) " 
      + "     ORDER BY prioridad "
      + "     FETCH FIRST 1 ROW ONLY)) AS  esCorrectoAlta, "
      /*+ "                NVL(NVL(aly.f_inicio, NVL((select MIN(aper.fh_inicio)         "
      + "                             from fct_conv_progaluhoraper aper        "
      + "                             where aper.x_matricula = mat.x_matricula),  "
      + "                             (select MIN(cper.fh_inicio)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                             where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) inicio,          "
      + "                NVL(NVL(aly.f_fin,   NVL((select MAX(aper.fh_fin)         "
      + "                             from fct_conv_progaluhoraper aper          "
      + "                             where aper.x_matricula = mat.x_matricula), "
      + "                            (select MAX(cper.fh_fin)         "
      + "                             from fct_conv_proghoraper cper         "
      + "                            where cper.id_conv_prog = cp.id_conv_proy))),TRUNC(sysdate)) AS fin " */
            + "         CASE "
            + "         WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "              where alp.x_matricula = mes.x_matricula  "
            + "              and alp.id_convproy_alu = mes.id_convproy_alu "
            + "              and dat.x_matricula = alp.x_matricula "
            + "              and dat.ds_tipo = 'Alta' " 
            + "              and dat.ds_estado = 'S' "
            + "              and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "              and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "              and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                             FROM DUAL    "
            + "                             CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select min(alp.f_inicio) from fct_altass_proy alp "
            + "                                                                                                        where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                        and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "         WHEN  aly.f_inicio is not null THEN aly.f_inicio "
            + "         WHEN  (select MIN(aper.fh_inicio)            "
            + "                        from fct_conv_proyaluhoraper aper           "
            + "                        where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MIN(aper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyaluhoraper aper       "    
            + "                                                                                    where aper.x_matricula = mat.x_matricula) " 
            + "         WHEN  (select MIN(cper.fh_inicio)    "        
            + "                        from fct_conv_proyhoraper cper            "
            + "                        where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MIN(cper.fh_inicio)            "
            + "                                                                                    from fct_conv_proyhoraper cper           " 
            + "                                                                                    where cper.id_conv_proy = cp.id_conv_proy) "                                                                            
            + "         ELSE  TRUNC(sysdate) END inicio,  "
            + "       CASE "
            + "      WHEN (select count(*) from fct_altass_proy alp, fct_datos_gestora dat  "
            + "           where alp.x_matricula = mes.x_matricula  "
            + "            and alp.id_convproy_alu = mes.id_convproy_alu "
            + "           and dat.x_matricula = alp.x_matricula "
            + "           and dat.ds_tipo = 'Alta' "
            + "           and dat.ds_estado = 'S' "
            + "           and TRUNC(alp.f_inicio) = TRUNC(dat.f_alta) "
            + "           and TRUNC(alp.f_fin) = TRUNC(dat.f_baja)  "
            + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(alp.f_inicio) + (LEVEL - 1)) AS mes    "
            + "                          FROM DUAL   "
            + "                          CONNECT BY TRUNC(alp.f_inicio) + (LEVEL - 1) <= TRUNC(alp.f_fin))) >0 THEN  (select max(alp.f_fin) from fct_altass_proy alp "
            + "                                                                                                     where alp.x_matricula = mes.x_matricula  "
            + "                                                                                                     and alp.id_convproy_alu = mes.id_convproy_alu) "
            + "      WHEN  aly.f_fin is not null THEN aly.f_fin "
            + "      WHEN  (select MAX(aper.fh_fin)            "
            + "                     from fct_conv_proyaluhoraper aper           "
            + "                     where aper.x_matricula = mat.x_matricula) IS NOT NULL THEN (select MAX(aper.fh_fin)            "
            + "                                                                                 from fct_conv_proyaluhoraper aper           "
            + "                                                                                 where aper.x_matricula = mat.x_matricula)  "
            + "      WHEN  (select MAX(cper.fh_fin)            "
            + "                     from fct_conv_proyhoraper cper           " 
            + "                     where cper.id_conv_proy = cp.id_conv_proy) IS NOT NULL THEN (select MAX(cper.fh_fin)           "
            + "                                                                                 from fct_conv_proyhoraper cper    "       
            + "                                                                                 where cper.id_conv_proy = cp.id_conv_proy)   "
            + "      ELSE  TRUNC(sysdate) END fin    " 
      + "           from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,                              "
      + "                TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,                              "
      + "                TLOFEMATRGEN omg, TLUNIDADESCEN uni, FCT_ALTASS_PROY aly, TLEMPLEADOS empl, TLDATOSCEN dat, TLCENTROS cen, FCT_COTIZAMES_PROY mes,        "
      + "                (select CASE          "
      + "                 WHEN :nMes in (1,3,5,7,8,10,12) THEN 31         "
      + "                 WHEN :nMes in (4,6,9,11) THEN 30         "
      + "                 ELSE 28 END max         "
      + "                 FROM DUAL) mdias                                  "
            + "           where (aly.ds_errors IS NULL OR (aly.ds_errors NOT LIKE '%3688%' AND aly.ds_errors NOT LIKE '%7934%')) "  // Excluir registros con ds_errors que contienen '3688' o '7934'
      + "           and (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                              "
      + "           and tut.id_tutorfctdual = pro.id_tutorfctdual                              "
      + "           and pro.id_proyecto = cp.id_proyecto                              "
      + "           and cp.id_conv_proy = cpa.id_conv_proy                              "
      + "           and cpa.x_matricula = mat.x_matricula                              "
      + "           and mat.x_alumno = alu.x_alumno                              "
      + "           and cp.id_convenio = con.id_convenio                              "
      + "           and con.x_empresa = emp.x_empresa                              "
      + "           and aly.id_convproy_alu =  cpa.id_convproy_alu                             "            
      + "           and aly.f_envioss is not null         "
      + "           and (NVL(aly.lg_anulado,0) = 0 OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1 where mes1.x_matricula = mes.x_matricula AND mes1.nu_mes = mes.nu_mes and mes1.f_envioss is not null ) "
      + "                                OR EXISTS (select 1 from FCT_COTIZAMES_PROY mes1, fct_historicomes_proy his  "
      + "                                           where mes1.x_matricula = mes.x_matricula  "
      + "                                           and mes1.nu_mes = mes.nu_mes and mes1.f_envioss is null "
      + "                                           and his.id_cotizames_proy = mes1.id_cotizames_proy ))    "
      + "           and aly.x_matricula = mat.x_matricula         "
      + "           and mes.id_convproy_alu(+) =  cpa.id_convproy_alu                 "
      + "           and mes.x_matricula(+) = mat.x_matricula           "
      + "           and mat.x_ofertamatrig = omg.x_ofertamatrig                              "
      + "           and mat.x_unidad = uni.x_unidad                 "
      + "           and dat.x_centro = pro.x_centro              "
      + "           and cen.x_centro = dat.x_centro              "
      + "           and empl.x_empleado = tut.x_empleado                              "
      + "           and :cAnno between pro.c_anno_desde and pro.c_anno_hasta                              "
      + "           and mes.nu_mes(+) = :nMes         "
      //+ "            and :nMes in (SELECT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1)) "
      //+ "                          FROM DUAL "
      //+ "                          CONNECT BY TRUNC(aly.f_inicio) + INTERVAL '1' MONTH * (LEVEL - 1) <= TRUNC(aly.f_fin)) "
      + "           and :nMes in ( SELECT DISTINCT EXTRACT(MONTH FROM TRUNC(aly.f_inicio) + (LEVEL - 1)) AS mes "
      + "                          FROM DUAL "
      + "                          CONNECT BY TRUNC(aly.f_inicio) + (LEVEL - 1) <= TRUNC(aly.f_fin)) "
      + "           and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                              "
      + "           and pro.x_centro IN (SELECT distinct dcen.x_centro id                                "
      + "                                from  TLDATOSCEN dcen, TLPROVINCIAS prv ,                                "
      + "                                      TLCENTROS cen, FCT_CONVENIOS conv                                 "
      + "                                WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)                                      "
      + "                                AND dcen.c_provincia = prv.c_provincia                                "
      + "                                AND dcen.l_vigente = 'S'                                "
      + "                                AND cen.x_centro = dcen.x_centro                                "
      + "                                AND cen.l_delegacion = 'N'                                "
      + "                                AND cen.l_extranjero = 'N')                              "
      + "           and :tipoEmpresa in (-1,2)                              "
      + "           and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)                              "
      + "           and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)                              "
      + "           and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)                             "
      + "           and ( :idTipo = -1 or :idTipo = aly.lg_erasmus_cb) "
      //+ "           and not exists (select 1  FROM FCT_COTIZAMES_PROY cot1 "
      //+ "                           where  cot1.id_convproy_alu != cpa.id_convproy_alu " 
      //+ "                           and cot1.x_matricula = cpa.x_matricula "       
      //+ "                           and cot1.nu_mes = :nMes) "
      + "           and NVL(cpa.lg_cotiza,0) = 1 "
      + "           and NVL(cpa.lg_excluir,0) = 0) , FCT_ESTADOS_COTIZACION est                            "
      + "      where ((-1 = :idEstado) OR                   "
      + "             (1 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'No') OR                    "
      + "             (4 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí') OR                  "
      + "             (6 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valCen= 'No') OR "
      + "             (7 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí' AND fechaenvio is null) OR "
      + "             (8 = :idEstado AND est.id_estados_cotizacion = :idEstado  AND valDel= 'Sí' AND fechaenvio is not null) "
      + "              ) "
      + "    ) original_query "
      + ") ranked_data "
      + "WHERE row_num = 1 "
      + "order by nombreAlumno, curso, unidad, nombreEmpresa  ", nativeQuery = true)
    List<ListadoSegSocialCotizaMesProjection> findListadoCotizaMesDelegacionProvincias(Long idTutorfctdual,
                                                                                              Long idCentro,
                                                                                              Integer cAnno,
                                                                                              Integer tipoEmpresa,
                                                                                              Long idEmpresa,
                                                                                              Long idOfertamatrig,
                                                                                              Long idUnidad,
                                                                                              Long idCentroCombo,
                                                                                              Long idProvincia,
                                                                                              Integer idEstado,
                                                                                              Integer nMes,
                                                                                              Integer idTipo);

    @Query(value = " select ID_ESTADOS_COTIZACION AS id, "
            + "       DECODE(DS_ABREV,'SVALSC','Sin validar y a ' || nu_dias || ' días de inicio prácticas',DS_ESTADO) AS descripcion "
            + "from FCT_ESTADOS_COTIZACION "
            + "where lg_activo = 1 "
            + "and (cd_tipo = 'T' AND (cd_afecta = 'T' OR cd_afecta = 'M') OR (cd_tipo = 'C' and :idPerfil = 161) OR (cd_tipo = 'D' and :idPerfil = 11207)) "
            + "order by nu_orden asc ", nativeQuery = true)
    List<ElementoSelectProjection> getEstadosSS(Long idPerfil);


}
