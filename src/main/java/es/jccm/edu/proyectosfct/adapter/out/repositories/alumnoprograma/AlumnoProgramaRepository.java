package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosActividadesProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosCabeceraEvaluacionProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosEvaluacionesProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosHojaSemanalProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.QAlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.CentroAlumnosProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AlumnoProgramaRepository extends AbstractRepository<AlumnoPrograma, Long, QAlumnoPrograma> {
 

 @Query(value = "SELECT alu.X_ALUMNO AS ID,  "
   + "       mat.X_MATRICULA AS IDMATRICULA,  "
   + "       mat.X_UNIDAD AS IDUNIDAD,   "
   + "       TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto,  "
   + "       uni.T_NOMBRE AS nombreUnidad,  "
   + "       alu.T_NUSS as tnuss, "   
   + "       NVL((select LG_COTIZA from fct_convprog_alu where x_matricula = mat.x_matricula and id_conv_prog = :idConvProg), NVL((select decode(emp.l_sscen,'S',1,0) " 
   + "                                                                                                                             from fct_conv_prog prog, fct_convenios con, tlempresas emp " 
   + "                                                                                                                             where prog.id_conv_prog = :idConvProg  "
   + "                                                                                                                             and con.id_convenio = prog.id_convenio "
   + "                                                                                                                             and emp.x_empresa = con.x_empresa),0))  as lgCotiza,  "   
   + "       NVL((select LG_NUSS from fct_convprog_alu where x_matricula = mat.x_matricula and id_conv_prog = :idConvProg),0)  as lgNuss,  "
   + "       decode(alu.t_nuss,null,1,0)  as lgEditable,  "
   + "       CASE "
   + "       WHEN calcula_edad(alu.f_nacimiento) < 16 THEN 1 "
   + "       ELSE 0 END AS lgMenor, "
   + "       NVL((select LG_ERASMUS from fct_convprog_alu where x_matricula = mat.x_matricula and id_conv_prog = :idConvProg),0)  as lgErasmus, "
   + "    NVL(( "
   + "        SELECT CASE "
   + "            WHEN NOT EXISTS ( "
   + "                SELECT 1 FROM fct_convprog_alu ca WHERE ca.x_matricula = mat.x_matricula "
   + "            ) THEN 0  "
   + "            WHEN EXISTS ( "
   + "                SELECT 1 FROM fct_convprog_alu ca "
   + "                WHERE ca.x_matricula = mat.x_matricula "
   + "                  AND ca.id_conv_prog = :idConvProg "
   + "            ) THEN 0  "
   + "            WHEN EXISTS ( "
   + "                SELECT 1 FROM fct_convprog_alu ca "
   + "                JOIN fct_conv_prog prog ON ca.id_conv_prog = prog.id_conv_prog "
   + "                WHERE ca.x_matricula = mat.x_matricula "
   + "                  AND ca.id_conv_prog <> :idConvProg "
   + "                  AND prog.id_convenio = (SELECT id_convenio FROM fct_conv_prog WHERE id_conv_prog = :idConvProg) "
   + "                  AND prog.id_programa = (SELECT id_programa FROM fct_conv_prog WHERE id_conv_prog = :idConvProg) "
   + "            ) THEN 1  "
//   + "            WHEN EXISTS ( "
//   + "                SELECT 1 FROM fct_convprog_alu ca "
//   + "                JOIN fct_conv_prog prog ON ca.id_conv_prog = prog.id_conv_prog "
//   + "                WHERE ca.x_matricula = mat.x_matricula "
//   + "                  AND NOT EXISTS ( "
//   + "                    SELECT 1 FROM fct_conv_prog p "
//   + "                    WHERE p.id_conv_prog = :idConvProg "
//   + "                      AND p.id_programa = prog.id_programa "
//   + "                  ) "
//   + "            ) THEN 2  "
   + "            ELSE 0 "
   + "        END "
   + "        FROM dual "
   + "    ), 0) AS lgAlumnoEnEmpresa "
   + "      FROM TLMATALU mat,  "
   + "           TLALUMNOS alu,  "
   + "           TLUNIDADESCEN uni "
   + "      WHERE mat.x_centro = :idCentro  "
   + "      AND mat.X_OFERTAMATRIG = :idOfertamatrig  "
   + "      AND mat.C_ANNO = :cAnno  "
   + "      AND nvl(mat.C_RESULTADO,99) > 1  "
   + "      AND mat.X_ALUMNO = alu.X_ALUMNO  "
   + "      AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 List<AlumnoProjection> getAlumnosConvenio(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProg);
 
 @Query(value = "SELECT alu.X_ALUMNO AS ID, "
   + "             mat.X_MATRICULA AS IDMATRICULA, "
   + "             mat.X_UNIDAD AS IDUNIDAD, "
   + "             TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto, "
   + "             alu.T_NOMBRE || ' ' || alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 AS nombre,   " 
   + "             uni.T_NOMBRE AS nombreUnidad, "
   + "             alu.T_NUSS as tnuss, "
   + "             NVL(alp.LG_COTIZA,0) as lgCotiza, "
   + "             NVL(alp.LG_NUSS,0) as lgNuss, "
   + "             alp.id_convprog_alu as idConvAlu, "
   + "             CASE "
   + "             WHEN (alu.t_nuss is null OR alp.lg_nuss = 1) THEN 1 "
   + "             ELSE 0 END AS lgEditable, "
   + "             NVL(alp.LG_ERASMUS,0) as lgErasmus, "
   + "             CASE "
   + "    WHEN (select count(*) from fct_altass_prog where id_convprog_alu = alp.id_convprog_alu and f_envioss is not null and NVL(lg_anulado,0) !=1) >0 "
   + "          AND (SELECT COUNT(*) FROM fct_convprog_alu WHERE x_matricula = alp.x_matricula AND id_conv_prog <> alp.id_conv_prog) = 0 THEN 'Enviada alta SS' "
   + "    WHEN (select count(*) from fct_historicoaltas_prog where id_altass_prog = (select id_altass_prog from fct_altass_prog where id_convprog_alu = alp.id_convprog_alu and NVL(lg_anulado,0) !=1 and rownum = 1)) >0 "
   + "        AND (SELECT COUNT(*) FROM fct_convprog_alu WHERE x_matricula = alp.x_matricula AND id_conv_prog <> alp.id_conv_prog) = 0 THEN 'Enviada alta SS' "
   + "    WHEN (select count(*) from fct_parsem_aluprog where id_convprog_alu = alp.id_convprog_alu) >0 THEN 'Contiene partes semanales' "
   + "    ELSE '' END AS dsMotivo"
   + "      FROM TLMATALU mat, "
   + "           TLALUMNOS alu, "
   + "           FCT_CONVPROG_ALU alp, "
   + "           TLUNIDADESCEN uni "
   + "      WHERE mat.x_centro = ?1 "
   + "      AND mat.X_OFERTAMATRIG = ?2 "
   + "      AND mat.C_ANNO = ?3 "
   + "      AND nvl(mat.C_RESULTADO,99) > 1  "
   + "      AND mat.X_ALUMNO = alu.X_ALUMNO "
   + "      AND alp.X_MATRICULA = mat.X_MATRICULA "
   + "      AND alp.ID_CONV_PROG = ?4 "
   + "      AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 List<AlumnoProjection> getAlumnosConvenioSeleccionados(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProg);

 List<AlumnoPrograma> findAllByConvenioProgramaId(Long id);

 @Query(value = "SELECT UNC.X_UNIDAD AS ID, OFU.X_OFERTAMATRIC AS IDOFERTAMATRIC, "
     + "TLF_NOMBREUNIDADPERIODO(UNC.X_UNIDAD) AS NOMBRE , DECODE (UNC.C_TIPO, 'P', 'PURA', 'MIXTA') AS TIPO, "
     + "UNC.N_CAPACIDAD as CAPACIDAD, TLF_ALUUNI(UNC.X_UNIDAD, OFU.X_OFERTAMATRIC) AS ALUMNOSASIGNADOS, "
     + "DECODE(UNC.X_EMPLEADO, NULL, '' , TLF_NOMBRE(EMP.NOMBRE, EMP.APELLIDO1, EMP.APELLIDO2)) as NOMBRETUTOR, "
     + "PTO.F_CESE as FechaCese, NULL AS ACTUACION , X_MATRICULA_DELEGADO AS IdMatriculaDelegado, "
     + "X_MATRICULA_SUBDELEGADO AS IdMatriculaSubDelegado, TUR.D_TURNO AS TURNO "
     + "FROM TLOFERTASUNIDAD OFU, TLOFEMATRCEN OMC, "
     + "TLUNIDADESCEN UNC, TLEMPLEADOS EMP, "
     + "TLPTOTRAEMP PTO, TLTURNOS TUR "
     + "WHERE OMC.X_CENTRO = ?1 AND OMC.X_OFERTAMATRIG = ?2  "
     + "AND OFU.X_OFERTAMATRIC = OMC.X_OFERTAMATRIC  "
     + "AND UNC.C_ANNO = ?3 AND UNC.N_PERIODO = 1 "
     + "AND UNC.X_TURNO = TUR.X_TURNO(+) "
     + "AND UNC.X_UNIDAD = OFU.X_UNIDAD "
     + "AND UNC.X_EMPLEADO = PTO.X_EMPLEADO(+) "
     + "AND UNC.F_TOMAPOS = PTO.F_TOMAPOS(+) "
     + "AND UNC.X_EMPLEADO = EMP.X_EMPLEADO(+) "
     + "ORDER BY UNC.N_ORDEN, UNC.T_NOMBRE ", nativeQuery = true)
 List<UnidadCursoProjection> getUnidades(Long idCentro, Long idOfertamatrig, int cAnno);
 
 @Query(value = "SELECT cen.x_centro AS ID, cen.c_codigo || ' - '  ||  den.s_denominacion || ' ' || dat.D_ESPECIFICA AS NOMBRECOMPLETO, "
   + "den.s_denominacion || ' ' || dat.D_ESPECIFICA || ', ' || mun.D_MUNICIPIO || ' (' ||pro.D_PROVINCIA||')' AS CENTROPROVINCIA "
   + "FROM TLCENTROS cen, TLDENGEN den, TLDATOSCEN dat, TLPROVINCIAS pro, TLMUNICIPIOS mun  "
   + "WHERE cen.x_centro  = ?1 "
   + "AND dat.x_centro = cen.x_centro  "
   + "AND den.X_DENGEN = dat.X_DENGEN "
   + "AND dat.C_PROVINCIA = pro.C_PROVINCIA "
   + "AND dat.C_MUNICIPIO = mun.C_MUNICIPIO "
   + "AND dat.C_PROVINCIA = mun.C_PROVINCIA ", nativeQuery = true)
 CentroAlumnosProjection findNombreCentro(Long idCentro);

 Integer countByconvenioProgramaId(Long idConvProg);

 Optional<AlumnoPrograma> getAlumnoProgramaById(Long idAluConvProg);

 @Query(value = "select conv.id_convprog_alu AS id, "
   + "den.s_denominacion || ' ' || dat.D_ESPECIFICA || ', ' || mun.D_MUNICIPIO || ' (' ||pro.D_PROVINCIA||')' AS centro, "
   + "cen.c_codigo AS codigo_centro, "
   + "alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 AS nombre_alumno, "
   + "fam.d_familia AS familia, "
   + "omg.d_ofertamatrig AS curso, "
   + "emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS nombre_tutor, "
   + "eps.d_empresa AS centro_trabajo, "
   + "eemp.tx_nombre || ' ' || eemp.tx_apellido1 || ' ' || eemp.tx_apellido2 AS responsable, "
   + "temp.ds_departamento AS area, "
   + "NVL(TO_CHAR((SELECT MIN(per.fh_inicio) FROM fct_conv_progaluhoraper per WHERE per.ID_CONV_PROG = conp.ID_CONV_PROG), 'DD/MM/YYYY'), TO_CHAR(conp.fh_inicio, 'DD/MM/YYYY')) || ' - ' || "
   + "NVL(TO_CHAR((SELECT MAX(per.fh_fin) FROM fct_conv_progaluhoraper per WHERE per.ID_CONV_PROG = conp.ID_CONV_PROG), 'DD/MM/YYYY'), TO_CHAR(conp.fh_fin, 'DD/MM/YYYY')) AS periodo, "
   + "val.d_valliscat AS evaluacion, "
   + "conv.tx_orientaciones AS orientaciones, "
   + "con.ID_CONVENIO AS IDCONVENIO, "
   + "loc.d_localidad AS LOCALIDAD, "
   + "DECODE(NVL(conv.NU_HORAS_EVA, 0), 0, conp.NU_HORAS_TOTALES, conv.NU_HORAS_EVA) AS horas "
   + "from fct_convprog_alu conv,  "
   + "tlmatalu mua,  "
   + "tlalumnos alu, "
   + "tlcentros cen, "
   + "tldatoscen dat, "
   + "tldengen den, "
   + "tlprovincias pro,  "
   + "tlmunicipios mun, "
   + "tlofematrgen omg, "
   + "tlmodalidades mod, "
   + "tlfamilias fam, "
   + "fct_conv_prog conp, "
   + "fct_programas prog, "
   + "fct_tutorfctdual tut, "
   + "tlempleados emp, "
   + "tlempresas eps, "
   + "fct_convenios con, "
   + "EMP_TRAEMP temp, "
   + "EMP_EMPLEADOS eemp, "
   + "EMP_TRAEMP temp2, "
   + "EMP_EMPLEADOS eemp2, "
   + "TLVALLISCAT val, "
   + "TLLOCALIDADES loc " 
   + "where conv.ID_CONVPROG_ALU = ?1  "
   + "and mua.x_matricula = conv.x_matricula  "
   + "and alu.x_alumno = mua.x_alumno  "
   + "and cen.x_centro = mua.x_centro  "
   + "and dat.x_centro = mua.x_centro  "
   + "and den.x_dengen = dat.x_dengen "
   + "and pro.c_provincia = dat.c_provincia  "
   + "and mun.c_provincia = pro.c_provincia  "
   + "and mun.c_municipio = dat.c_municipio  "
   + "and omg.x_ofertamatrig =  mua.x_ofertamatrig "
   + "and mod.x_modalidad = omg.x_modalidad   "
   + "and mod.x_familia = fam.x_familia   "
   + "and conp.id_conv_prog = conv.id_conv_prog  "
   + "and prog.id_programa = conp.id_programa  "
   + "and tut.id_tutorfctdual = prog.id_tutorfctdual  "
   + "and emp.x_empleado = tut.x_empleado  "
   + "and con.x_empresa = eps.x_empresa  "
   + "and conp.id_convenio = con.id_convenio "
   + "and conp.id_traemp = temp.id_traemp "
   + "and temp.id_empleado = eemp.id_empleado "
   + "and conv.id_evaluacion = val.x_valliscat(+) "
   + "and con.x_representante = temp2.id_traemp "
   + "and temp2.id_empleado = eemp2.id_empleado "
   + "AND loc.x_localidad = dat.x_localidad ", nativeQuery = true)
 DatosCabeceraEvaluacionProjection getDatosCabeceraFCT(Long id);
 
 
 @Query(value = "select conv.id_convproy_alu AS id, "
   + "den.s_denominacion || ' ' || dat.D_ESPECIFICA || ', ' || mun.D_MUNICIPIO || ' (' ||pro.D_PROVINCIA||')' AS centro, "
   + "cen.c_codigo AS codigo_centro, "
   + "alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 AS nombre_alumno, "
   + "fam.d_familia AS familia, "
   + "omg.d_ofertamatrig AS curso, "
   + "emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS nombre_tutor, "
   + "eps.d_empresa AS centro_trabajo, "
   + "eemp.tx_nombre || ' ' || eemp.tx_apellido1 || ' ' || eemp.tx_apellido2 AS responsable, "
   + "temp.ds_departamento AS area, "
   + "NVL(TO_CHAR((SELECT MIN(per.fh_inicio) FROM fct_conv_proyaluhoraper per WHERE per.ID_CONV_PROY = conp.ID_CONV_PROY), 'DD/MM/YYYY'), TO_CHAR(conp.fh_inicio, 'DD/MM/YYYY')) || ' - ' || "
   + "NVL(TO_CHAR((SELECT MAX(per.fh_fin) FROM fct_conv_proyaluhoraper per WHERE per.ID_CONV_PROY = conp.ID_CONV_PROY), 'DD/MM/YYYY'), TO_CHAR(conp.fh_fin, 'DD/MM/YYYY')) AS periodo, "
   + "val.d_valliscat AS evaluacion, "
   + "conv.tx_orientaciones AS orientaciones, "
   + "conp.ID_CONVENIO AS IDCONVENIO, "
   + "loc.d_localidad AS LOCALIDAD, "
   + "DECODE(NVL(conv.NU_HORAS_EVA, 0), 0, conp.NU_HORAS_TOTALES, conv.NU_HORAS_EVA) AS horas "
   + "from fct_convproy_alu conv,  "
   + "tlmatalu mua,  "
   + "tlalumnos alu, "
   + "tlcentros cen, "
   + "tldatoscen dat, "
   + "tldengen den, "
   + "tlprovincias pro, "
   + "tlmunicipios mun, "
   + "tlofematrgen omg, "
   + "tlmodalidades mod, "
   + "tlfamilias fam, "
   + "fct_conv_proy conp, "
   + "fct_proyectos proy, "
   + "fct_tutorfctdual tut, "
   + "tlempleados emp, "
   + "tlempresas eps, "
   + "fct_convenios con, "
   + "EMP_TRAEMP temp, "
   + "EMP_EMPLEADOS eemp, "
   + "EMP_TRAEMP temp2, "
   + "EMP_EMPLEADOS eemp2, "
   + "TLVALLISCAT val, "
   + "TLLOCALIDADES loc " 
   + "where conv.ID_CONVPROY_ALU = ?1 "
   + "and mua.x_matricula = conv.x_matricula "
   + "and alu.x_alumno = mua.x_alumno "
   + "and cen.x_centro = mua.x_centro "
   + "and dat.x_centro = mua.x_centro "
   + "and den.x_dengen = dat.x_dengen "
   + "and pro.c_provincia = dat.c_provincia "
   + "and mun.c_provincia = pro.c_provincia "
   + "and mun.c_municipio = dat.c_municipio "
   + "and omg.x_ofertamatrig =  mua.x_ofertamatrig "
   + "and mod.x_modalidad = omg.x_modalidad  "
   + "and mod.x_familia = fam.x_familia  "
   + "and conp.id_conv_proy = conv.id_conv_proy "
   + "and proy.id_proyecto = conp.id_proyecto "
   + "and tut.id_tutorfctdual = proy.id_tutorfctdual "
   + "and emp.x_empleado = tut.x_empleado "
   + "and con.x_empresa = eps.x_empresa "
   + "and conp.id_convenio = con.id_convenio "
   + "and conp.id_traemp = temp.id_traemp "
   + "and temp.id_empleado = eemp.id_empleado "
   + "and conv.id_evaluacion = val.x_valliscat (+) "
   + "and con.x_representante = temp2.id_traemp "
   + "and temp2.id_empleado = eemp2.id_empleado "
   + "AND loc.x_localidad = dat.x_localidad ", nativeQuery = true)
 DatosCabeceraEvaluacionProjection getDatosCabeceraDUAL(Long id);

 
 @Query(value = "select act.id_dato_programa AS id, "
   + "act.TX_ACT_EVALUACION AS actividades, "
   + "decode(eva.lg_realizada,1,'X') AS realizada, "
   + "decode(eva.lg_realizada,0,'X') AS  norealizada, "
   + "act.TX_RESULTADO AS capacidades, "
   + "eva.TX_CRITERIOS AS criterios, "
   + "decode(eva.lg_adquirida,1,'X') AS adquirida, "
   + "decode(eva.lg_adquirida,0,'X') AS noadquirida, "
   + "eva.tx_observaciones AS observaciones "
   + "from   FCT_EVAALU_ACTPROG eva, FCT_DATOS_PROGRAMAS act "
   + "where  eva.id_dato_programa = act.id_dato_programa "
   + "and    eva.id_convprog_alu = ?1 "
   + "order  by act.nu_orden ", nativeQuery = true)
 List<DatosEvaluacionesProjection> getActividadesFCT(Long id);
 
 @Query(value = "select act.id_dato_proyecto AS id, "
   + "act.TX_ACT_EVALUACION AS actividades, "
   + "decode(eva.lg_realizada,1,'X') AS realizada, "
   + "decode(eva.lg_realizada,0,'X') AS norealizada, "
   + "act.TX_RESULTADO AS capacidades, "
   + "eva.TX_CRITERIOS AS criterios, "
   + "decode(eva.lg_adquirida,1,'X') AS adquirida, "
   + "decode(eva.lg_adquirida,0,'X') AS noadquirida, "
   + "eva.tx_observaciones AS observaciones "
   + "from   FCT_EVAALU_ACTPROY eva, FCT_DATOS_PROYECTOS act "
   + "where  eva.id_dato_proyecto = act.id_dato_proyecto "
   + "and    eva.id_convproy_alu = ?1 "
   + "order  by act.nu_orden ", nativeQuery = true)
 List<DatosEvaluacionesProjection> getActividadesDUAL(Long id);

 @Query(value = "select id_parsem_alu AS id, "
   + " TO_DATE (fh_inisem,'dd/MM/yyyy') || '-' || TO_DATE (fh_inisem+6,'dd/MM/yyyy')  AS jornadas, "
   + " '' actividades, "
   + " '' tiempos, "
   + " tx_observaciones AS observaciones, "
   + " nu_dias as nuDias "
   + " from   FCT_PARSEM_ALUPROG "
   + " where  id_parsem_alu = ?1 "
   + " order  by fh_inisem ", nativeQuery = true)
 List<DatosHojaSemanalProjection> getHojaSemanalFCT(Long idParsem);
 
 @Query(value = "select id_parsem_alu AS id, "
   + " TO_DATE (fh_inisem,'dd/MM/yyyy') || '-' || TO_DATE (fh_inisem+6,'dd/MM/yyyy')  AS jornadas, "
   + " '' actividades, "
   + " '' tiempos, "
   + " tx_observaciones AS observaciones, "
   + " nu_dias AS nuDias "
   + " from   FCT_PARSEM_ALUPROY  "
   + " where  id_parsem_alu = ?1 "
   + " order  by fh_inisem ", nativeQuery = true)
  List<DatosHojaSemanalProjection> getHojaSemanalDUAL(Long idParsem);

 @Query(value = "select dat.id_dato_proyecto AS id, "
   + "          dat.tx_act_formativo AS txActividad "
   + "   from FCT_DATOS_PROYECTOS dat , FCT_PARSEM_ACTPROY par "
   + "   where par.id_parsem_alu = ?1 "
   + "   and par.id_dato_proyecto = dat.id_dato_proyecto ", nativeQuery = true)
List<DatosActividadesProjection> getActividadesSemanaDUAL(Long id);

 @Query(value = "select dat.id_dato_programa AS id, "
   + "          dat.tx_act_formativo AS txActividad "
   + "   from FCT_DATOS_PROGRAMAS dat , FCT_PARSEM_ACTPROG par "
   + "   where par.id_parsem_alu = ?1 "
   + "   and par.id_dato_programa = dat.id_dato_programa ", nativeQuery = true)
List<DatosActividadesProjection> getActividadesSemanaFCT(Long id);

Optional<AlumnoPrograma> findByIdEvaRodal(String idEvaRodal);

@Query(value = "select SUM(NU_HORAS)  "
  + "from fct_conv_progaluhoraper  "
  + "where id_conv_prog = :idConvProg "
  + "and x_matricula = :idMatricula ", nativeQuery = true)
Double getHoras(Long idConvProg, Long idMatricula);

@Query(value = "select NVL(SUM(NU_HORAS),0)  "
  + "from fct_conv_proyaluhoraper  "
  + "where id_conv_proy = :idConvProy "
  + "and x_matricula = :idMatricula ", nativeQuery = true)
Double getHorasProyecto(Long idConvProy, Long idMatricula);

@Query(value= "select (n_horfin - n_horini) / 60 horas   "
  + " from fct_conv_prog conv, "
  + "     fct_convprog_alu cva, "
  + "     fct_conv_progaluhoraper hop, "
  + "     fct_conv_progaluhoratra hot "
  + " where cva.id_convprog_alu = :idConv "
  + " and conv.id_conv_prog = cva.id_conv_prog "
  + " and hop.id_conv_prog = conv.id_conv_prog "
  + " and hop.x_matricula = cva.x_matricula "
  + " and :fechaInicio BETWEEN TRUNC(hop.fh_inicio) and TRUNC(hop.fh_fin) "
  + " and :idDia = hot.n_diasemana "
  + " and hot.id_conv_progaluhoraper = hop.id_conv_progaluhoraper ", nativeQuery = true)
List<Double> getHorasSemanales(Date fechaInicio, Long idDia, Long idConv);

@Query(value= "select (n_horfin - n_horini) / 60 horas   "
  + " from fct_conv_proy conv, "
  + "     fct_convproy_alu cva, "
  + "     fct_conv_proyaluhoraper hop, "
  + "     fct_conv_proyaluhoratra hot "
  + " where cva.id_convproy_alu = :idConv "
  + " and conv.id_conv_proy = cva.id_conv_proy "
  + " and hop.id_conv_proy = conv.id_conv_proy "
  + " and hop.x_matricula = cva.x_matricula "
  + " and :fechaInicio BETWEEN TRUNC(hop.fh_inicio) and TRUNC(hop.fh_fin) "
  + " and :idDia = hot.n_diasemana "
  + " and hot.id_conv_proyaluhoraper = hop.id_conv_proyaluhoraper ", nativeQuery = true)
List<Double> getHorasSemanalesDUAL(Date fechaInicio, Long idDia, Long idConv);


@Query(value="select CASE  "
		+ "   WHEN (NU_HORAS_TOTALES is not null AND NU_HORAS_TOTALES != 0) THEN  NU_HORAS_TOTALES "
		+ "   ELSE (SELECT SUM(hop.NU_HORAS) from fct_conv_progaluhoraper hop "
		+ "         where hop.id_conv_prog = conv.id_conv_prog AND hop.x_matricula = conva.x_matricula  ) "
		+ "   END NU_HORAS  "
		+ "from fct_convprog_alu conva, fct_conv_prog conv   "
		+ "where conva.id_convprog_alu = :idConv "
		+ "and conv.id_conv_prog = conva.id_conv_prog ", nativeQuery=true)
Double getHorasTotales(Long idConv);

@Query(value="select CASE  "
		+ "   WHEN (NU_HORAS_TOTALES is not null AND NU_HORAS_TOTALES != 0) THEN  NU_HORAS_TOTALES "
		+ "   ELSE (SELECT SUM(hop.NU_HORAS) from fct_conv_proyaluhoraper hop "
		+ "         where hop.id_conv_proy = conv.id_conv_proy AND hop.x_matricula = conva.x_matricula  ) "
		+ "   END NU_HORAS  "
		+ "from fct_convproy_alu conva, fct_conv_proy conv   "
		+ "where conva.id_convproy_alu = :idConv "
		+ "and conv.id_conv_proy = conva.id_conv_proy ", nativeQuery=true)
Double getHorasTotalesDUAL(Long idConv);

@Query(value = " SELECT DISTINCT ID , NOMBRE FROM ( SELECT UNC.X_UNIDAD AS ID, OFU.X_OFERTAMATRIC AS IDOFERTAMATRIC,                "
		+ "      TLF_NOMBREUNIDADPERIODO(UNC.X_UNIDAD) AS NOMBRE , DECODE (UNC.C_TIPO, 'P', 'PURA', 'MIXTA') AS TIPO,                "
		+ "          UNC.N_CAPACIDAD as CAPACIDAD, TLF_ALUUNI(UNC.X_UNIDAD, OFU.X_OFERTAMATRIC) AS ALUMNOSASIGNADOS,                "
		+ "          DECODE(UNC.X_EMPLEADO, NULL, '' , TLF_NOMBRE(EMP.NOMBRE, EMP.APELLIDO1, EMP.APELLIDO2)) as NOMBRETUTOR,                "
		+ "          PTO.F_CESE as FechaCese, NULL AS ACTUACION , X_MATRICULA_DELEGADO AS IdMatriculaDelegado,                "
		+ "          X_MATRICULA_SUBDELEGADO AS IdMatriculaSubDelegado, TUR.D_TURNO AS TURNO                "
		+ "         FROM TLOFERTASUNIDAD OFU, TLOFEMATRCEN OMC,                "
		+ "              TLUNIDADESCEN UNC, TLEMPLEADOS EMP,                "
		+ "              TLPTOTRAEMP PTO, TLTURNOS TUR, TLOFEMATRGEN OMG, "
		+ "                       FCT_TUTORFCTDUAL tut, tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni "
		+ "          WHERE OMC.X_CENTRO = :idCentro             "
		+ "          AND OMC.X_OFERTAMATRIG = OMG.X_OFERTAMATRIG             "
		+ "          AND OMG.D_OFERTAMATRIG LIKE '%LOFP%'            "
		+ "          AND OMG.X_MODALIDAD = :idModalidad            "
		+ "          AND OFU.X_OFERTAMATRIC = OMC.X_OFERTAMATRIC                 "
		+ "          AND UNC.C_ANNO = :cAnno             "
		+ "          AND UNC.N_PERIODO = 1                "
		+ "          AND UNC.X_TURNO = TUR.X_TURNO(+)                      "
		+ "          AND UNC.X_UNIDAD = OFU.X_UNIDAD                "
		+ "          AND UNC.X_EMPLEADO = PTO.X_EMPLEADO(+)                "
		+ "          AND UNC.F_TOMAPOS = PTO.F_TOMAPOS(+)                "
		+ "          AND UNC.X_EMPLEADO = EMP.X_EMPLEADO(+)                "
		+ "          AND (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor)   "
		+ "          and HOR.X_EMPLEADO = tut.X_EMPLEADO                  "
		+ "          AND HOR.F_TOMAPOS IN (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca            "
		+ "          where aca.c_anno = :cAnno            "
		+ "          and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1            "
		+ "          and pto1.x_centro  = :idCentro          "
		+ "          and pto1.x_empleado =  hor.x_empleado) "
		+ "          and HOR.C_ANNO = :cAnno                  "
		+ "          and UAT.X_HORARIORE = HOR.X_HORARIORE  "
		+ "          and UAT.x_ofertamatric = omc.x_ofertamatric          "
		+ "          and UNC.x_unidad = UAT.x_unidad  "
		+ "          and uni.x_unidad = UAT.x_unidad                  "
		+ "          and omc.x_ofertamatric =  uni.x_ofertamatric "
		+ "          ORDER BY UNC.N_ORDEN, UNC.T_NOMBRE) ", nativeQuery = true)
List<UnidadCursoProjection> getUnidadesModalidad(Long idCentro, Long idModalidad, int cAnno, Long idTutor);

@Query(value = "SELECT * FROM FCT_CONVPROG_ALU WHERE X_MATRICULA = :matricula", nativeQuery = true)
List<AlumnoPrograma> findByMatricula(Long matricula);

	@Query(value = "SELECT prog.* "
			+ "FROM FCT_CONVPROG_ALU prog "
			+ "JOIN FCT_CONVPROG_ALU ref ON prog.x_matricula = ref.x_matricula "
			+ "WHERE ref.id_convprog_alu = :idConvprogAlu", nativeQuery = true)
	List<AlumnoPrograma> findByIdMatricula(Long idConvprogAlu);
    
	@Query(value = "SELECT NVL(TO_CHAR((SELECT MIN(per.fh_inicio) FROM fct_conv_progaluhoraper per WHERE per.ID_CONV_PROG = prog.ID_CONV_PROG), 'DD/MM/YYYY'), TO_CHAR(prog.fh_inicio, 'DD/MM/YYYY')) || ' - ' || " +
			"              NVL(TO_CHAR((SELECT MAX(per.fh_fin) FROM fct_conv_progaluhoraper per WHERE per.ID_CONV_PROG = prog.ID_CONV_PROG), 'DD/MM/YYYY'), TO_CHAR(prog.fh_fin, 'DD/MM/YYYY')) AS periodo " +
			"       FROM fct_conv_prog prog " +
			"       WHERE prog.ID_CONV_PROG = :idConvProg",  nativeQuery = true)
	String getPeriodoEval(Long idConvProg);
 
}