package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.DatosFormacionDto;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.Alumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QAlumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjectionAux;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AlumnadoRepository extends AbstractRepository<Alumnado, Long, QAlumnado> {

 @Query(value= "SELECT distinct "
   + "TRUNC(per.fh_inicio-1 + LEVEL,'IW') primerDia, "
   + "TO_CHAR(TRUNC(per.fh_inicio-1 + LEVEL,'IW'),'DD/MM/YYYY') || ' - ' || TO_CHAR(TRUNC(per.fh_inicio-1 + LEVEL,'IW')+6,'DD/MM/YYYY') semana, "
   + "CASE "
   + "when sysdate between TRUNC(per.fh_inicio-1 + LEVEL,'IW') and TRUNC(per.fh_inicio-1 + LEVEL,'IW')+6 "
   + "then 'S' "
   + "else 'N' "
   + "END actual "
   + "from ( "
   + "select cp.fh_inicio, cp.fh_fin, cp.fh_fin - cp.fh_inicio dias  "
   + "from   FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa "
   + "where  cp.id_conv_prog = cpa.id_conv_prog "
   + "and    cpa.id_convprog_alu = ?1 ) per "
   + "CONNECT BY TRUNC(per.fh_inicio-1 + LEVEL, 'IW') <= per.fh_fin "
   + "order  by 1 ",nativeQuery = true)
 List<FechaSemanaProjection> findFechasSemanal(Long idConvProgAlu);
 
 @Query(value= "SELECT distinct "
   + "TRUNC(per.fh_inicio-1 + LEVEL,'IW') primerDia, "
   + "TO_CHAR(TRUNC(per.fh_inicio-1 + LEVEL,'IW'),'DD/MM/YYYY') || ' - ' || TO_CHAR(TRUNC(per.fh_inicio-1 + LEVEL,'IW')+6,'DD/MM/YYYY') semana, "
   + "CASE "
   + "when sysdate between TRUNC(per.fh_inicio-1 + LEVEL,'IW') and TRUNC(per.fh_inicio-1 + LEVEL,'IW')+6 "
   + "then 'S' "
   + "else 'N' "
   + "END actual "
   + "from ( "
   + "select cp.fh_inicio, cp.fh_fin, cp.fh_fin - cp.fh_inicio dias  "
   + "from FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa "
   + "where cp.id_conv_proy = cpa.id_conv_proy "
   + "and    cpa.id_convproy_alu = ?1 ) per "
   + "CONNECT BY TRUNC(per.fh_inicio-1 + LEVEL, 'IW') <= per.fh_fin "
   + "order  by 1 ",nativeQuery = true)
 List<FechaSemanaProjection> findFechasSemanalProy(Long idConvProgAlu);
 
 @Query(value= "SELECT alu.X_ALUMNO AS ID, "
   + "mat.X_MATRICULA AS IDMATRICULA, "
   + "mat.X_UNIDAD AS IDUNIDAD, "
   + "TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto, "
   + "alu.T_NOMBRE || ' ' || alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 AS nombre, "
   + "uni.T_NOMBRE AS nombreUnidad, "
   + "alu.T_NUSS AS tnuss "
   + "FROM TLMATALU mat, "
   + "TLALUMNOS alu, "
   + "TLUNIDADESCEN uni "
   + "WHERE mat.X_MATRICULA  = ?1 "
   + "AND nvl(mat.C_RESULTADO, 99) > 1 "
   + "AND mat.X_ALUMNO = alu.X_ALUMNO "
   + "AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 AlumnoProjection findAlumnoByIdMatricula(Long idMatricula);
 
 @Query(value= "SELECT alu.X_ALUMNO AS ID, "
   + "mat.X_MATRICULA AS IDMATRICULA, "
   + "mat.X_UNIDAD AS IDUNIDAD, "
   + "alu.T_NOMBRE || ' ' || alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2) AS nombreCompleto, "
   + "uni.T_NOMBRE AS nombreUnidad "
   + "FROM TLMATALU mat, "
   + "TLALUMNOS alu, "
   + "TLUNIDADESCEN uni "
   + "WHERE mat.X_MATRICULA  = ?1 "
   + "AND nvl(mat.C_RESULTADO, 99) > 1 "
   + "AND mat.X_ALUMNO = alu.X_ALUMNO "
   + "AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 AlumnoProjection findAlumnoByIdMatricula1(Long idMatricula);
 
  @Query(value=" select fini, ffin, horario FROM ( "
      + " select fini, ffin, (select LISTAGG(horario,'\\\\n') horario FROM ( "
      + "             select 'Periodo' || ' ' || rownum || ' '|| (SELECT LISTAGG(tramos,'') FROM ( "
      + "                                                 SELECT 'Tramo:' || rownum  || ' ' || tramos AS tramos   "
      + "                                                 FROM ( "
      + "                                                 select LISTAGG(decode(n_diasemana,1,'L',2,'M','3','X',4,'J',5,'V',6,'S','D'), '-')    "
      + "                                                 WITHIN GROUP(ORDER BY n_diasemana,n_tramo, n_horini,n_horfin) || ' (' ||convierte_hora(n_horini) || ' '|| convierte_hora(n_horfin) || ') ' AS tramos   "
      + "                                                 from fct_conv_progaluhoratra  "
      + "                                                 where id_conv_progaluhoraper in (per.id_conv_progaluhoraper) "
      + "                                                 group by n_tramo, n_horini,n_horfin))) AS horario "
      + "             from fct_conv_progaluhoraper per "
      + "             where x_matricula = :idMatricula"
      + "                               )) horario "
      + "              from ( "
      + "              select TO_CHAR(MIN(FH_INICIO),'dd/MM/yyyy') fini,  "
      + "                  TO_CHAR(MAX(FH_FIN),'dd/MM/yyyy') ffin "
      + "              from fct_conv_progaluhoraper per "
      + "              where x_matricula = :idMatricula) "
      + " UNION "
      + "select fini, ffin, (select LISTAGG(horario,'\\\\n') horario FROM ( "
      + "             select 'Periodo' || ' ' || rownum || ' '|| (SELECT LISTAGG(tramos,'') FROM ( "
      + "                                                 SELECT 'Tramo:' || rownum  || ' ' || tramos AS tramos   "
      + "                                                 FROM ( "
      + "                                                 select LISTAGG(decode(n_diasemana,1,'L',2,'M','3','X',4,'J',5,'V',6,'S','D'), '-')    "
      + "                                                 WITHIN GROUP(ORDER BY n_diasemana,n_tramo, n_horini,n_horfin) || ' (' ||convierte_hora(n_horini) || ' '|| convierte_hora(n_horfin) || ') ' AS tramos   "
      + "                                                 from fct_conv_proyaluhoratra  "
      + "                                                 where id_conv_proyaluhoraper in (per.id_conv_proyaluhoraper) "
      + "                                                 group by n_tramo, n_horini,n_horfin))) AS horario "
      + "             from fct_conv_proyaluhoraper per "
      + "             where x_matricula = :idMatricula"
      + "                               )) horario "
      + "              from ( "
      + "              select TO_CHAR(MIN(FH_INICIO),'dd/MM/yyyy') fini,  "
      + "                  TO_CHAR(MAX(FH_FIN),'dd/MM/yyyy') ffin "
      + "              from fct_conv_proyaluhoraper per "
      + "              where x_matricula = :idMatricula) "
      + " ) where fini is not null and ffin is not null and horario is not null ", nativeQuery = true)
 AlumnoProjectionAux getDateAndSchedule(Long idMatricula);

 @Query(value= "select alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
   + "emp.d_empresa as nombreEmpresa, "
   + "'FCT' as tipoEmpresa, "
   + "omg.d_ofertamatrig curso, "
   + "uni.t_nombre unidad, "
   + "pro.ds_programa descripcion, "
   + "(select count(*) from FCT_PARSEM_ALUPROG "
   + "where id_convprog_alu = cpa.id_convprog_alu) partes, "
   + "cpa.ID_CONVPROG_ALU AS id, "
   + "cpa.id_evafir_rodal AS idevarodal,  "
   + "cpa.tx_evafir_fichero AS txevarodal,   "
   + "cpa.f_firma AS ffirma, "
   + "alu.t_nuss as tnuss, "
         + "        NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
   + "cpa.lg_cotiza as cotiza, "
   + "DECODE((SELECT SUM(id)   "  
   + "       FROM (SELECT COUNT(*) AS id     " 
   + "             FROM fct_parsem_anexosprog    " 
   + "             WHERE id_convprog_alu = cpa.id_convprog_alu "    
   + "             UNION ALL    " 
   + "             SELECT COUNT(*) AS id     "
   + "              FROM fct_parsem_aluprog    "
   + "              WHERE id_convprog_alu = cpa.id_convprog_alu   "  
   + "              )),0,0,1) AS puedeBorrar,   "
   + "    0 as avisoMes, "
   + "CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
   + "ELSE 0 END AS nussProvisional, "
   + " '' AS advertenciaPartes, "
   + "NVL(cpa.lg_excluir,0) as lgExcluir "
   + " from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
   + "TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
   + "TLOFEMATRGEN omg, TLUNIDADESCEN uni "
   + "where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
   + "and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL  "             
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT  "                                                                                
   + "        WHERE  USU.X_USUARIO = :idUsuario  "                         
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO  "  
   + "       AND PTO.X_CENTRO = :idCentro "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_programa = cp.id_programa "
   + "and cp.id_conv_prog = cpa.id_conv_prog "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and mat.x_alumno = alu.x_alumno "
   + "and cp.id_convenio = con.id_convenio "
   +" and con.lg_lofp = 0 "
   +" and omg.d_ofertamatrig not like '%LOFP%' "
   + "and con.x_empresa = emp.x_empresa "
   + "and mat.x_ofertamatrig = omg.x_ofertamatrig "
   + "and mat.x_unidad = uni.x_unidad "
   + "and mat.c_anno = :cAnno "
   + "and pro.x_centro = :idCentro "
   + "and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
   + "and mat.c_anno = :cAnno "
   + "and :tipoEmpresa in (-1,1) "
   + "and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
   + "and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
   + "and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
   + "union "
   + "select alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
   + "emp.d_empresa as nombreEmpresa, "
   + "'FP Dual' as tipoEmpresa, "
   + "omg.d_ofertamatrig curso, "
   + "uni.t_nombre unidad, "
   + "pro.ds_proyecto descripcion, "
   + "(select count(*) from FCT_PARSEM_ALUPROY "
   + "where id_convproy_alu = cpa.id_convproy_alu) partes, "
   + "cpa.ID_CONVPROY_ALU AS id, "   
   + "cpa.id_evafir_rodal AS idevarodal,  "
   + "cpa.tx_evafir_fichero AS txevarodal,   "
   + "cpa.f_firma AS ffirma, "
   + "alu.t_nuss as tnuss, "
         + "        NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
   + "cpa.lg_cotiza as cotiza, "
   + "DECODE((SELECT SUM(id)     "
   + "        FROM (SELECT COUNT(*) AS id "     
   + "              FROM fct_parsem_anexosproy "   
   + "              WHERE id_convproy_alu = cpa.id_convproy_alu  "   
   + "              UNION ALL     "
   + "              SELECT COUNT(*) AS id "    
   + "              FROM fct_parsem_aluproy     "
   + "              WHERE id_convproy_alu = cpa.id_convproy_alu "    
   + "              )),0,0,1) AS puedeBorrar,  "
   + "0 as avisoMes, "
   + "CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
   + "ELSE 0 END AS nussProvisional, "
   + " '' AS advertenciaPartes, "
   + "NVL(cpa.lg_excluir,0) as lgExcluir "
   + "from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
   + "TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
   + "TLOFEMATRGEN omg, TLUNIDADESCEN uni "
   + "where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
   + "and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL  "             
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT  "                                                                                
   + "        WHERE  USU.X_USUARIO = :idUsuario  "                         
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO  "  
   + "       AND PTO.X_CENTRO = :idCentro "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_proyecto = cp.id_proyecto "
   + "and cp.id_conv_proy = cpa.id_conv_proy "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and mat.x_alumno = alu.x_alumno "
   + "and cp.id_convenio = con.id_convenio "
   + "and con.x_empresa = emp.x_empresa "
   +" and con.lg_lofp = 0 "
   + "and omg.d_ofertamatrig not like '%LOFP%' "
   + "and mat.x_ofertamatrig = omg.x_ofertamatrig "
   + "and mat.x_unidad = uni.x_unidad "
   + "and mat.c_anno = :cAnno "
   + "and pro.x_centro = :idCentro "
   + "and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
   + "and mat.c_anno = :cAnno "
   + "and :tipoEmpresa in (-1,2) "
   + "and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
   + "and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
   + "and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
   + "order by nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad ", nativeQuery = true)
 List<ListadoAlumnadoTutorProjection> findListadoAlumnosTutor(Long idTutorfctdual, Long idCentro, Integer cAnno, Integer tipoEmpresa, Long idEmpresa, Long idOfertamatrig, Long idUnidad, Long idUsuario);

 @Query(value= " select distinct alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
      + "   emp.d_empresa as nombreEmpresa, "
      + "   'FCT' as tipoEmpresa, "
      + "   omg.d_ofertamatrig curso, "
      + "   uni.t_nombre unidad, "
      + "   pro.ds_programa descripcion, "
      + "   (select count(*) from FCT_PARSEM_ALUPROG "
      + "   where id_convprog_alu = cpa.id_convprog_alu) partes, "
      + "   cpa.ID_CONVPROG_ALU AS id, "
      + "   cpa.id_evafir_rodal AS idevarodal,  "
      + "   cpa.tx_evafir_fichero AS txevarodal,   "
      + "   cpa.f_firma AS ffirma, "
      + "   alu.t_nuss as tnuss, "
         + "   NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
      + "   cpa.lg_cotiza as cotiza, "
      + "   0 as avisoMes, "
      + "   CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
      + "   ELSE 0 END AS nussProvisional, "
      + " '' AS advertenciaPartes, "
      + "   NVL(cpa.lg_excluir,0) as lgExcluir "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
      + "   TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
      + "   TLOFEMATRGEN omg, TLUNIDADESCEN uni "
      + "   where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual "
      + "   and pro.id_programa = cp.id_programa "
      + "   and cp.id_conv_prog = cpa.id_conv_prog "
      + "   and cpa.x_matricula = mat.x_matricula "
      + "   and mat.x_alumno = alu.x_alumno "
      + "   and mat.c_anno = :cAnno "
      + "   and cp.id_convenio = con.id_convenio "
      +"    and con.lg_lofp = 0 "
      + "   and omg.d_ofertamatrig not like '%LOFP%' "
      + "   and con.x_empresa = emp.x_empresa "
      + "   and mat.x_ofertamatrig = omg.x_ofertamatrig "
      + "   and mat.x_unidad = uni.x_unidad "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id   "
      + "                                         from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
      + "                                        TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv    "
      + "                                  WHERE u.x_usuario = :idUsuario   "
      + "                                  AND pop.x_perfil = :idPerfil   "
      + "                                  AND pto.x_centro = :idCentro   "
      + "                                        AND pto.x_empleado=u.x_empleado   "
      + "                                  AND pop.x_empleado=pto.x_empleado   "
      + "                                  AND pop.f_tomapos = pto.f_tomapos   "
      + "                                        AND pto.x_centro = dcen.x_centro   "
      + "                                  AND dcen.c_provincia = prv.c_provincia    "
      + "                                  AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)   "
      + "                                  AND dcen1.c_provincia = prv.c_provincia   "
      + "                                        AND dcen1.l_vigente = 'S'   "
      + "                                  AND cen.x_centro = dcen1.x_centro   "
      + "                                  AND cen.l_delegacion = 'N'   "
      + "                                  AND cen.l_extranjero = 'N') "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
      + "   and :tipoEmpresa in (-1,1) "
      + "   and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
      + "   and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
      + "   and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
      + "   union "
      + "   select distinct alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
      + "   emp.d_empresa as nombreEmpresa, "
      + "   'FP Dual' as tipoEmpresa, "
      + "   omg.d_ofertamatrig curso, "
      + "   uni.t_nombre unidad, "
      + "   pro.ds_proyecto descripcion, "
      + "   (select count(*) from FCT_PARSEM_ALUPROY "
      + "   where id_convproy_alu = cpa.id_convproy_alu) partes, "
      + "   cpa.ID_CONVPROY_ALU AS id,    "
      + "   cpa.id_evafir_rodal AS idevarodal,  "
      + "   cpa.tx_evafir_fichero AS txevarodal,   "
      + "   cpa.f_firma AS ffirma, "
      + "   alu.t_nuss as tnuss, "
         + "   NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
      + "   cpa.lg_cotiza as cotiza, "
      + "   0 as avisoMes, "
      + "   CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
      + "   ELSE 0 END AS nussProvisional, "
      + " '' AS advertenciaPartes, "
      + "   NVL(cpa.lg_excluir,0) as lgExcluir "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
      + "   TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
      + "   TLOFEMATRGEN omg, TLUNIDADESCEN uni "
      + "   where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual "
      + "   and pro.id_proyecto = cp.id_proyecto "
      + "   and cp.id_conv_proy = cpa.id_conv_proy "
      + "   and cpa.x_matricula = mat.x_matricula "
      + "   and mat.x_alumno = alu.x_alumno "
      + "   and mat.c_anno = :cAnno "
      + "   and cp.id_convenio = con.id_convenio "
      +"    and con.lg_lofp = 0 "
      + "   and omg.d_ofertamatrig not like '%LOFP%' "
      + "   and con.x_empresa = emp.x_empresa "
      + "   and mat.x_ofertamatrig = omg.x_ofertamatrig "
      + "   and mat.x_unidad = uni.x_unidad "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id   "
      + "                                         from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
      + "                                        TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv    "
      + "                                  WHERE u.x_usuario = :idUsuario   "
      + "                                  AND pop.x_perfil = :idPerfil   "
      + "                                  AND pto.x_centro = :idCentro   "
      + "                                        AND pto.x_empleado=u.x_empleado   "
      + "                                  AND pop.x_empleado=pto.x_empleado   "
      + "                                  AND pop.f_tomapos = pto.f_tomapos   "
      + "                                        AND pto.x_centro = dcen.x_centro   "
      + "                                  AND dcen.c_provincia = prv.c_provincia    "
      + "                                  AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)   "
      + "                                  AND dcen1.c_provincia = prv.c_provincia   "
      + "                                        AND dcen1.l_vigente = 'S'   "
      + "                                  AND cen.x_centro = dcen1.x_centro   "
      + "                                  AND cen.l_delegacion = 'N'   "
      + "                                  AND cen.l_extranjero = 'N') "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
      + "   and :tipoEmpresa in (-1,2) "
      + "   and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
      + "   and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
      + "   and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
      + "   order by nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad ", nativeQuery = true)
 List<ListadoAlumnadoTutorProjection> findListadoAlumnosTutorDelegacion(Long idTutorfctdual, 
                        Long idCentro, 
                        Integer cAnno, 
                        Integer tipoEmpresa, 
                        Long idEmpresa, 
                        Long idOfertamatrig, 
                        Long idUnidad,
                        Long idPerfil, 
                     Long idCentroCombo,
                     Long idUsuario);

   @Query(value= " select distinct alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
     + "        emp.d_empresa as nombreEmpresa, "
     + "        'FCT' as tipoEmpresa, "
     + "        omg.d_ofertamatrig curso, "
     + "        uni.t_nombre unidad, "
     + "        pro.ds_programa descripcion, "
     + "        (select count(*) from FCT_PARSEM_ALUPROG "
     + "        where id_convprog_alu = cpa.id_convprog_alu) partes, "
     + "        cpa.ID_CONVPROG_ALU AS id, "
     + "        cpa.id_evafir_rodal AS idevarodal,  "
     + "        cpa.tx_evafir_fichero AS txevarodal,   "
     + "        cpa.f_firma AS ffirma, "
     + "        alu.t_nuss as tnuss, "
           + "        NVL((SELECT CD_NUSS_OLD FROM ( "
           + "            SELECT CD_NUSS_OLD "
           + "            FROM FCT_CORRECCIONES_NUSS cn "
           + "            WHERE cn.X_MATRICULA = mat.x_matricula "
           + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
           + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
     + "        cpa.lg_cotiza as cotiza, "
     + "        0 as avisoMes, "
     + "        CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
     + "        ELSE 0 END AS nussProvisional, "
     + " '' AS advertenciaPartes, "
     + "        cpa.lg_excluir as lgExcluir "
     + "        from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
     + "        TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
     + "        TLOFEMATRGEN omg, TLUNIDADESCEN uni "
     + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
     + "        and tut.id_tutorfctdual = pro.id_tutorfctdual "
     + "        and pro.id_programa = cp.id_programa "
     + "        and cp.id_conv_prog = cpa.id_conv_prog "
     + "        and cpa.x_matricula = mat.x_matricula "
     + "        and mat.x_alumno = alu.x_alumno "
     + "        and mat.c_anno = :cAnno "
     + "        and cp.id_convenio = con.id_convenio "
     + "        and con.x_empresa = emp.x_empresa "
     +"         and con.lg_lofp = 0 "
     + "        and omg.d_ofertamatrig not like '%LOFP%' "
     + "        and mat.x_ofertamatrig = omg.x_ofertamatrig "
     + "        and mat.x_unidad = uni.x_unidad "
     + "                 and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
     + "        and pro.x_centro IN (SELECT distinct dcen.x_centro id   "
     + "                                              from TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
     + "                                                   TLCENTROS cen, FCT_CONVENIOS conv    "
     + "                                       WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)    "
     + "                                       AND dcen.c_provincia = prv.c_provincia   "
     + "                                                 AND dcen.l_vigente = 'S'   "
     + "                                       AND cen.x_centro = dcen.x_centro   "
     + "                                       AND cen.l_delegacion = 'N'   "
     + "                                       AND cen.l_extranjero = 'N') "
     + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
     + "        and :tipoEmpresa in (-1,1) "
     + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
     + "        and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
     + "        and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
     + "        union "
     + "        select distinct alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno, "
     + "        emp.d_empresa as nombreEmpresa, "
     + "        'FP Dual' as tipoEmpresa, "
     + "        omg.d_ofertamatrig curso, "
     + "        uni.t_nombre unidad, "
     + "        pro.ds_proyecto descripcion, "
     + "        (select count(*) from FCT_PARSEM_ALUPROY "
     + "        where id_convproy_alu = cpa.id_convproy_alu) partes, "
     + "        cpa.ID_CONVPROY_ALU AS id,    "
     + "        cpa.id_evafir_rodal AS idevarodal,  "
     + "        cpa.tx_evafir_fichero AS txevarodal,   "
     + "        cpa.f_firma AS ffirma, "
     + "        alu.t_nuss as tnuss, "
           + "        NVL((SELECT CD_NUSS_OLD FROM ( "
           + "            SELECT CD_NUSS_OLD "
           + "            FROM FCT_CORRECCIONES_NUSS cn "
           + "            WHERE cn.X_MATRICULA = mat.x_matricula "
           + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
           + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
     + "        cpa.lg_cotiza as cotiza, "
     + "        0 as avisoMes, "
     + "        CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
     + "        ELSE 0 END AS nussProvisional, "
           + " '' AS advertenciaPartes, "
     + "        NVL(cpa.lg_excluir,0) as lgExcluir "
     + "        from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
     + "        TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, "
     + "        TLOFEMATRGEN omg, TLUNIDADESCEN uni "
     + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
     + "        and tut.id_tutorfctdual = pro.id_tutorfctdual "
     + "        and pro.id_proyecto = cp.id_proyecto "
     + "        and cp.id_conv_proy = cpa.id_conv_proy "
     + "        and cpa.x_matricula = mat.x_matricula "
     + "        and mat.x_alumno = alu.x_alumno "
     + "        and mat.c_anno = :cAnno "
     + "        and cp.id_convenio = con.id_convenio "
     +"         and con.lg_lofp = 0 "
     + "        and omg.d_ofertamatrig not like '%LOFP%' "
     + "        and con.x_empresa = emp.x_empresa "
     + "        and mat.x_ofertamatrig = omg.x_ofertamatrig "
     + "        and mat.x_unidad = uni.x_unidad "
     + "                 and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
     + "        and pro.x_centro IN (SELECT distinct dcen.x_centro id   "
     + "                                              from  TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
     + "                                                   TLCENTROS cen, FCT_CONVENIOS conv    "
     + "                                       WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)         "
     + "                                       AND dcen.c_provincia = prv.c_provincia   "
     + "                                       AND dcen.l_vigente = 'S'   "
     + "                                       AND cen.x_centro = dcen.x_centro   "
     + "                                       AND cen.l_delegacion = 'N'   "
     + "                                       AND cen.l_extranjero = 'N') "
     + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
     + "        and :tipoEmpresa in (-1,2) "
     + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
     + "        and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig) "
     + "        and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad) "
     + "        order by nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad ", nativeQuery = true)
 List<ListadoAlumnadoTutorProjection> findListadoAlumnosTutorDelegacionProvincias(Long idTutorfctdual, 
                            Long idCentro, 
                            Integer cAnno, 
                            Integer tipoEmpresa, 
                            Long idEmpresa, 
                            Long idOfertamatrig, 
                            Long idUnidad,
                         Long idCentroCombo,
                         Long idProvincia);
 
 @Query(value= "select omg.x_ofertamatrig as idOfertamatrig, "
   + "omg.d_ofertamatrig as curso "
   + "from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
   + "TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg "
   + "where (-1 = ?1 OR  tut.id_tutorfctdual = ?1) "
   + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?6                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_programa = cp.id_programa "
   + "and cp.id_conv_prog = cpa.id_conv_prog "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and cp.id_convenio = con.id_convenio "
   + "and (?7 = 1 OR con.lg_lofp = 0) "
   + "and (?7 = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
   + "and mat.x_ofertamatrig = omg.x_ofertamatrig "
   + "and pro.x_centro = ?2 "
   + "and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   + "and ?4 in (-1,1) "
   + "and ( ?5 = -1 or ?5 = con.x_empresa) "
   + "union "
   + "select omg.x_ofertamatrig as idOfertamatrig, "
   + "omg.d_ofertamatrig as curso "
   + "from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
   + "TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg "
   + "where (-1 = ?1 OR  tut.id_tutorfctdual = ?1) "
   + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?6                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_proyecto = cp.id_proyecto "
   + "and cp.id_conv_proy = cpa.id_conv_proy "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and cp.id_convenio = con.id_convenio "
   + "and mat.x_ofertamatrig = omg.x_ofertamatrig "
   + "and (?7 = 1 OR con.lg_lofp = 0) "
   + "and (?7 = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
   + "and pro.x_centro = ?2 "
   + "and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   + "and ?4 in (-1,2) "
   + "and ( ?5 = -1 or ?5 = con.x_empresa) "
   + "order by curso ", nativeQuery = true)
 List<CursoModalidadProjection> findCursosEmpleadoCentro(Long idTutorfctdual, Long idCentro,
   Integer cAnno, Integer tipoEmpresa, Long idEmpresa, Long idUsuario, Integer sTodos);
 
 @Query(value= "select  tut.id_tutorfctdual as id,           "
   + "  EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion           "
   + "           from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,           "
   + "           TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP           "
   + "           where tut.id_tutorfctdual = pro.id_tutorfctdual           "
   + "           and pro.id_programa = cp.id_programa           "
   + "           and cp.id_conv_prog = cpa.id_conv_prog           "
   + "           and cpa.x_matricula = mat.x_matricula    "
   + "                    and emp.x_empleado = tut.x_empleado  "
   + "           and cp.id_convenio = con.id_convenio                 "
   + "           and (:sTodos = 1 or con.lg_lofp = 0)  "
   + "           and pro.x_centro = :idCentro           "
   + "           and :cAnno between pro.c_anno_desde and pro.c_anno_hasta           "
   + "           and :tipoEmpresa in (-1,1)           "
   + "           and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)           "
   + "           union           "
   + "select tut.id_tutorfctdual as id,           "
   + "       EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2 as descripcion        "
   + "           from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,           "
   + "           TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP            "
   + "           where tut.id_tutorfctdual = pro.id_tutorfctdual           "
   + "           and pro.id_proyecto = cp.id_proyecto           "
   + "           and cp.id_conv_proy = cpa.id_conv_proy           "
   + "           and cpa.x_matricula = mat.x_matricula     "
   + "                    and emp.x_empleado = tut.x_empleado "
   + "           and cp.id_convenio = con.id_convenio                  "
   + "           and (:sTodos = 1 or con.lg_lofp = 0) "
   + "           and pro.x_centro = :idCentro            "
   + "           and :cAnno between pro.c_anno_desde and pro.c_anno_hasta           "
   + "           and :tipoEmpresa in (-1,2)           "
   + "           and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
   + "        order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> findTutoresCentro(Long idCentro,
                                            Integer cAnno, 
                                            Integer tipoEmpresa, 
                                            Long idEmpresa,
                                            Integer sTodos);
 
 
 @Query(value= "select distinct  omg.x_ofertamatrig as idOfertamatrig,  "
        + "      omg.d_ofertamatrig as curso  "
        + "      from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,  "
        + "      TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg  "
        + "      where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
        + "      and tut.id_tutorfctdual = pro.id_tutorfctdual  "
        + "      and pro.id_programa = cp.id_programa  "
        + "      and cp.id_conv_prog = cpa.id_conv_prog  "
        + "      and cpa.x_matricula = mat.x_matricula  "
        + "      and cp.id_convenio = con.id_convenio  "
        + "      and (:sTodos = 1 OR con.lg_lofp = 0) "
        + "      and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
        + "      and mat.x_ofertamatrig = omg.x_ofertamatrig  "
        + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
        + "      and pro.x_centro IN (SELECT distinct dcen1.x_centro id  "
        + "                                 from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
        + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv   "
        + "                          WHERE u.x_usuario = :idUsuario  "
        + "                          AND pop.x_perfil = :idPerfil  "
        + "                          AND pto.x_centro = :idCentro  "
        + "                                AND pto.x_empleado=u.x_empleado  "
        + "                          AND pop.x_empleado=pto.x_empleado  "
        + "                          AND pop.f_tomapos = pto.f_tomapos  "
        + "                                AND pto.x_centro = dcen.x_centro  "
        + "                          AND dcen.c_provincia = prv.c_provincia   "
        + "                          AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)  "
        + "                          AND dcen1.c_provincia = prv.c_provincia  "
        + "                                AND dcen1.l_vigente = 'S'  "
        + "                          AND cen.x_centro = dcen1.x_centro  "
        + "                          AND cen.l_delegacion = 'N'  "
        + "                          AND cen.l_extranjero = 'N')  "
        + "      and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
        + "      and :tipoEmpresa in (-1,1)  "
        + "      and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
        + "      union  "
        + "      select distinct omg.x_ofertamatrig as idOfertamatrig,  "
        + "      omg.d_ofertamatrig as curso  "
        + "      from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,  "
        + "      TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg  "
        + "      where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
        + "      and tut.id_tutorfctdual = pro.id_tutorfctdual  "
        + "      and pro.id_proyecto = cp.id_proyecto  "
        + "      and cp.id_conv_proy = cpa.id_conv_proy  "
        + "      and cpa.x_matricula = mat.x_matricula  "
        + "      and cp.id_convenio = con.id_convenio  "
        + "      and (:sTodos = 1 OR con.lg_lofp = 0) "
        + "      and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
        + "      and mat.x_ofertamatrig = omg.x_ofertamatrig  "
        + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
        + "      and pro.x_centro IN (SELECT distinct dcen1.x_centro id  "
        + "                                 from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
        + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv   "
        + "                          WHERE u.x_usuario = :idUsuario  "
        + "                          AND pop.x_perfil = :idPerfil  "
        + "                          AND pto.x_centro = :idCentro  "
        + "                                AND pto.x_empleado=u.x_empleado  "
        + "                          AND pop.x_empleado=pto.x_empleado  "
        + "                          AND pop.f_tomapos = pto.f_tomapos  "
        + "                                AND pto.x_centro = dcen.x_centro  "
        + "                          AND dcen.c_provincia = prv.c_provincia   "
        + "                          AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)  "
        + "                          AND dcen1.c_provincia = prv.c_provincia  "
        + "                                AND dcen1.l_vigente = 'S'  "
        + "                          AND cen.x_centro = dcen1.x_centro  "
        + "                          AND cen.l_delegacion = 'N'  "
        + "                          AND cen.l_extranjero = 'N')  "
        + "      and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
        + "      and :tipoEmpresa in (-1,2)  "
        + "      and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
        + "      order by curso ", nativeQuery = true)
 List<CursoModalidadProjection> findCursosEmpleadoDelegacion(Long idTutorfctdual, 
                   Long idCentro, 
                   Integer cAnno,
                   Integer tipoEmpresa, 
                   Long idEmpresa, 
                   Long idPerfil, 
                   Long idCentroCombo,
                   Long idUsuario,
                   Integer sTodos);
 
 @Query(value= "select distinct tut.id_tutorfctdual as id,           "
   + "                              EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion                "
   + "                       from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,              "
   + "                       TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP                "
   + "                       where tut.id_tutorfctdual = pro.id_tutorfctdual              "
   + "                       and pro.id_programa = cp.id_programa              "
   + "                       and cp.id_conv_prog = cpa.id_conv_prog  "
   + "                             and emp.x_empleado = tut.x_empleado  "
   + "                       and cpa.x_matricula = mat.x_matricula              "
   + "                       and cp.id_convenio = con.id_convenio                                         "
   + "                       and (:sTodos = 1 or con.lg_lofp = 0)  "
   + "                             and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)             "
   + "                       and pro.x_centro IN (SELECT distinct dcen1.x_centro id              "
   + "                                                  from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,              "
   + "                                                 TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv               "
   + "                                           WHERE u.x_usuario = :idUsuario              "
   + "                                           AND pop.x_perfil = :idPerfil              "
   + "                                           AND pto.x_centro = :idCentro              "
   + "                                                 AND pto.x_empleado=u.x_empleado              "
   + "                                           AND pop.x_empleado=pto.x_empleado              "
   + "                                           AND pop.f_tomapos = pto.f_tomapos              "
   + "                                                 AND pto.x_centro = dcen.x_centro              "
   + "                                           AND dcen.c_provincia = prv.c_provincia               "
   + "                                           AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)              "
   + "                                           AND dcen1.c_provincia = prv.c_provincia              "
   + "                                                 AND dcen1.l_vigente = 'S'              "
   + "                                           AND cen.x_centro = dcen1.x_centro              "
   + "                                           AND cen.l_delegacion = 'N'              "
   + "                                           AND cen.l_extranjero = 'N')              "
   + "                       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta              "
   + "                       and :tipoEmpresa in (-1,1)              "
   + "                       and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)              "
   + "                       union              "
   + "                       select distinct tut.id_tutorfctdual as id,           "
   + "                              EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion              "
   + "                       from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,              "
   + "                       TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP               "
   + "                       where tut.id_tutorfctdual = pro.id_tutorfctdual              "
   + "                       and pro.id_proyecto = cp.id_proyecto              "
   + "                       and cp.id_conv_proy = cpa.id_conv_proy              "
   + "                       and cpa.x_matricula = mat.x_matricula   "
   + "                             and emp.x_empleado = tut.x_empleado  "
   + "                       and cp.id_convenio = con.id_convenio   "
   + "                      and (:sTodos = 1 or con.lg_lofp = 0)  "
   + "                             and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)             "
   + "                       and pro.x_centro IN (SELECT distinct dcen1.x_centro id              "
   + "                                                  from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,              "
   + "                                                 TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv               "
   + "                                           WHERE u.x_usuario = :idUsuario              "
   + "                                           AND pop.x_perfil = :idPerfil              "
   + "                                           AND pto.x_centro = :idCentro              "
   + "                                                 AND pto.x_empleado=u.x_empleado              "
   + "                                           AND pop.x_empleado=pto.x_empleado              "
   + "                                           AND pop.f_tomapos = pto.f_tomapos              "
   + "                                                 AND pto.x_centro = dcen.x_centro              "
   + "                                           AND dcen.c_provincia = prv.c_provincia               "
   + "                                           AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)              "
   + "                                           AND dcen1.c_provincia = prv.c_provincia              "
   + "                                                 AND dcen1.l_vigente = 'S'              "
   + "                                           AND cen.x_centro = dcen1.x_centro              "
   + "                                           AND cen.l_delegacion = 'N'              "
   + "                                           AND cen.l_extranjero = 'N')              "
   + "                       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta              "
   + "                       and :tipoEmpresa in (-1,2)              "
   + "                       and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)              "
   + "                       order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> findTutoresDelegacion(Long idCentro, 
                        Integer cAnno,
                        Integer tipoEmpresa, 
                        Long idEmpresa, 
                        Long idPerfil, 
                        Long idCentroCombo,
                        Long idUsuario,
                        Integer sTodos);
 
  @Query(value= "select distinct  omg.x_ofertamatrig as idOfertamatrig,  "
      + "              omg.d_ofertamatrig as curso  "
      + "              from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,  "
      + "              TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg  "
      + "              where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
      + "              and tut.id_tutorfctdual = pro.id_tutorfctdual  "
      + "              and pro.id_programa = cp.id_programa  "
      + "              and cp.id_conv_prog = cpa.id_conv_prog  "
      + "              and cpa.x_matricula = mat.x_matricula  "
      + "              and cp.id_convenio = con.id_convenio  "
      + "              and (:sTodos = 1 OR con.lg_lofp = 0) "
      + "              and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
      + "              and mat.x_ofertamatrig = omg.x_ofertamatrig  "
      + "                    and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
      + "              and pro.x_centro IN (SELECT distinct dcen.x_centro id  "
      + "                                         from TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
      + "                                              TLCENTROS cen, FCT_CONVENIOS conv   "
      + "                                  WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia) "
      + "                                  AND dcen.c_provincia = prv.c_provincia   "
      + "                                  AND dcen.l_vigente = 'S'  "
      + "                                  AND cen.x_centro = dcen.x_centro  "
      + "                                  AND cen.l_delegacion = 'N'  "
      + "                                  AND cen.l_extranjero = 'N')  "
      + "              and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
      + "              and :tipoEmpresa in (-1,1)  "
      + "              and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
      + "              union  "
      + "              select distinct omg.x_ofertamatrig as idOfertamatrig,  "
      + "              omg.d_ofertamatrig as curso  "
      + "              from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,  "
      + "              TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg  "
      + "              where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
      + "              and tut.id_tutorfctdual = pro.id_tutorfctdual  "
      + "              and pro.id_proyecto = cp.id_proyecto  "
      + "              and cp.id_conv_proy = cpa.id_conv_proy  "
      + "              and cpa.x_matricula = mat.x_matricula  "
      + "              and cp.id_convenio = con.id_convenio  "
      + "              and (:sTodos = 1 OR con.lg_lofp = 0) "
      + "              and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
      + "              and mat.x_ofertamatrig = omg.x_ofertamatrig  "
      + "                    and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
      + "              and pro.x_centro IN (SELECT distinct dcen.x_centro id  "
      + "                                   from  TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
      + "                                         TLCENTROS cen, FCT_CONVENIOS conv   "
      + "                                  WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia) "
      + "                                  AND dcen.c_provincia = prv.c_provincia   "
      + "                                  AND dcen.l_vigente = 'S'  "
      + "                                  AND cen.x_centro = dcen.x_centro  "
      + "                                  AND cen.l_delegacion = 'N'  "
      + "                                  AND cen.l_extranjero = 'N')  "
      + "              and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
      + "              and :tipoEmpresa in (-1,2)  "
      + "              and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
      + "              order by curso ", nativeQuery = true)
 List<CursoModalidadProjection> findCursosEmpleadoDelegacionProvincias(Long idTutorfctdual, 
                       Long idCentro, 
                       Integer cAnno,
                       Integer tipoEmpresa, 
                       Long idEmpresa, 
                       Long idCentroCombo,
                       Long idProvincia,
                       Integer sTodos);
 
  
  @Query(value= "select distinct tut.id_tutorfctdual as id,           "
    + "                                                   EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion              "
    + "                             from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,             "
    + "                             TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP             "
    + "                             where tut.id_tutorfctdual = pro.id_tutorfctdual             "
    + "                             and pro.id_programa = cp.id_programa             "
    + "                             and cp.id_conv_prog = cpa.id_conv_prog  "
    + "                                      and emp.x_empleado = tut.x_empleado "
    + "                             and cpa.x_matricula = mat.x_matricula             "
    + "                             and cp.id_convenio = con.id_convenio                         "
    + "                             and (:sTodos = 1 or con.lg_lofp = 0)  "
    + "                                   and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)            "
    + "                             and pro.x_centro IN (SELECT distinct dcen.x_centro id             "
    + "                                                        from TLDATOSCEN dcen, TLPROVINCIAS prv ,             "
    + "                                                             TLCENTROS cen, FCT_CONVENIOS conv              "
    + "                                                 WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia)            "
    + "                                                 AND dcen.c_provincia = prv.c_provincia              "
    + "                                                 AND dcen.l_vigente = 'S'             "
    + "                                                 AND cen.x_centro = dcen.x_centro             "
    + "                                                 AND cen.l_delegacion = 'N'             "
    + "                                                    AND cen.l_extranjero = 'N')             "
    + "                                and :cAnno between pro.c_anno_desde and pro.c_anno_hasta             "
    + "                                and :tipoEmpresa in (-1,1)             "
    + "                                and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)             "
    + "                                union             "
    + "                                select distinct tut.id_tutorfctdual as id,           "
    + "                                                   EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion              "
    + "                                from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,             "
    + "                                TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP             "
    + "                                where tut.id_tutorfctdual = pro.id_tutorfctdual             "
    + "                                and pro.id_proyecto = cp.id_proyecto  "
    + "                                         and emp.x_empleado = tut.x_empleado "
    + "                                and cp.id_conv_proy = cpa.id_conv_proy             "
    + "                                and cpa.x_matricula = mat.x_matricula             "
    + "                                and cp.id_convenio = con.id_convenio                           "
    + "                                and (:sTodos = 1 or con.lg_lofp = 0)  "
    + "                                      and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)            "
    + "                                and pro.x_centro IN (SELECT distinct dcen.x_centro id             "
    + "                                                     from  TLDATOSCEN dcen, TLPROVINCIAS prv ,             "
    + "                                                           TLCENTROS cen, FCT_CONVENIOS conv              "
    + "                                                    WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia)            "
    + "                                                    AND dcen.c_provincia = prv.c_provincia              "
    + "                                                    AND dcen.l_vigente = 'S'             "
    + "                                                    AND cen.x_centro = dcen.x_centro             "
    + "                                                    AND cen.l_delegacion = 'N'             "
    + "                                                    AND cen.l_extranjero = 'N')             "
    + "                                and :cAnno between pro.c_anno_desde and pro.c_anno_hasta             "
    + "                                and :tipoEmpresa in (-1,2)             "
    + "                                and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)             "
    + "                                order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> findTutoresDelegacionProvincias(Long idCentro, 
                              Integer cAnno,
                              Integer tipoEmpresa, 
                              Long idEmpresa, 
                              Long idCentroCombo,
                              Long idProvincia,
                              Integer sTodos);
 
 @Query(value= "select uni.x_unidad as id, "
   + "uni.t_nombre as nombre "
   + "from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
   + "TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni "
   + "where (-1 = ?1 OR tut.id_tutorfctdual = ?1) "
   + "and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?7                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_programa = cp.id_programa "
   + "and cp.id_conv_prog = cpa.id_conv_prog "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and cp.id_convenio = con.id_convenio "
   + "and mat.x_unidad = uni.x_unidad "
   + "and pro.x_centro = ?2 "
   + "and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   + "and ?4 in (-1,1) "
   + "and ( ?5 = -1 or ?5 = con.x_empresa) "
   + "and ( ?6 = -1 or ?6 = mat.x_ofertamatrig) "
   + "union "
   + "select uni.x_unidad as idUnidad, "
   + "uni.t_nombre as nombre "
   + "from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
   + "TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni "
   + "where (-1 = ?1 OR tut.id_tutorfctdual = ?1) "
   + "and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?7                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   + "and pro.id_proyecto = cp.id_proyecto "
   + "and cp.id_conv_proy = cpa.id_conv_proy "
   + "and cpa.x_matricula = mat.x_matricula "
   + "and cp.id_convenio = con.id_convenio "
   + "and mat.x_unidad = uni.x_unidad "
   + "and pro.x_centro = ?2 "
   + "and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   + "and ?4 in (-1,2) "
   + "and ( ?5 = -1 or ?5 = con.x_empresa) "
   + "and ( ?6 = -1 or ?6 = mat.x_ofertamatrig) "
   + "order by nombre ", nativeQuery = true)
 List<UnidadCursoProjection> findUnidadesEmpleadoCentro(Long idTutorfctdual, Long idCentro,
   Integer cAnno, Integer tipoEmpresa, Long idEmpresa, Long idOfertamatrig, Long idUsuario);
 
    @Query(value= "select distinct uni.x_unidad as id,  "
      + "   uni.t_nombre as nombre  "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,  "
      + "   TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni  "
      + "   where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)   "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual  "
      + "   and pro.id_programa = cp.id_programa  "
      + "   and cp.id_conv_prog = cpa.id_conv_prog  "
      + "   and cpa.x_matricula = mat.x_matricula  "
      + "   and cp.id_convenio = con.id_convenio  "
      + "   and mat.x_unidad = uni.x_unidad  "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)  "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id   "
      + "                        from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
      + "                             TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv    "
      + "                        WHERE u.x_usuario = :idUsuario   "
      + "                        AND pop.x_perfil = :idPerfil   "
      + "                        AND pto.x_centro = :idCentro   "
      + "                        AND pto.x_empleado=u.x_empleado   "
      + "                        AND pop.x_empleado=pto.x_empleado   "
      + "                        AND pop.f_tomapos = pto.f_tomapos   "
      + "                        AND pto.x_centro = dcen.x_centro   "
      + "                        AND dcen.c_provincia = prv.c_provincia    "
      + "                        AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)   "
      + "                        AND dcen1.c_provincia = prv.c_provincia   "
      + "                        AND dcen1.l_vigente = 'S'   "
      + "                        AND cen.x_centro = dcen1.x_centro   "
      + "                        AND cen.l_delegacion = 'N'   "
      + "                        AND cen.l_extranjero = 'N')   "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
      + "   and :tipoEmpresa in (-1,1)  "
      + "   and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
      + "   and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig)  "
      + "   union  "
      + "   select distinct uni.x_unidad as idUnidad,  "
      + "   uni.t_nombre as nombre  "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,  "
      + "   TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni  "
      + "   where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)   "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual  "
      + "   and pro.id_proyecto = cp.id_proyecto  "
      + "   and cp.id_conv_proy = cpa.id_conv_proy  "
      + "   and cpa.x_matricula = mat.x_matricula  "
      + "   and cp.id_convenio = con.id_convenio  "
      + "   and mat.x_unidad = uni.x_unidad  "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)  "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id   "
      + "                        from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,   "
      + "                             TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv    "
      + "                        WHERE u.x_usuario = :idUsuario   "
      + "                        AND pop.x_perfil = :idPerfil   "
      + "                        AND pto.x_centro = :idCentro   "
      + "                        AND pto.x_empleado=u.x_empleado   "
      + "                        AND pop.x_empleado=pto.x_empleado   "
      + "                        AND pop.f_tomapos = pto.f_tomapos   "
      + "                        AND pto.x_centro = dcen.x_centro   "
      + "                        AND dcen.c_provincia = prv.c_provincia    "
      + "                        AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)   "
      + "                        AND dcen1.c_provincia = prv.c_provincia   "
      + "                        AND dcen1.l_vigente = 'S'   "
      + "                        AND cen.x_centro = dcen1.x_centro   "
      + "                        AND cen.l_delegacion = 'N'   "
      + "                        AND cen.l_extranjero = 'N')   "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
      + "   and :tipoEmpresa in (-1,2)  "
      + "   and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)  "
      + "   and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig)  "
      + "   order by nombre ", nativeQuery = true)
 List<UnidadCursoProjection> findUnidadesEmpleadoDelegacion(Long idTutorfctdual, 
                  Long idCentro,
                  Integer cAnno, 
                  Integer tipoEmpresa, 
                  Long idEmpresa, 
                  Long idOfertamatrig,
                  Long idPerfil,
                  Long idCentroCombo,
                  Long idUsuario);
    
     @Query(value= "select distinct uni.x_unidad as id, "
           + "        uni.t_nombre as nombre "
           + "        from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
           + "        TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni "
           + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
           + "        and tut.id_tutorfctdual = pro.id_tutorfctdual "
           + "        and pro.id_programa = cp.id_programa "
           + "        and cp.id_conv_prog = cpa.id_conv_prog "
           + "        and cpa.x_matricula = mat.x_matricula "
           + "        and cp.id_convenio = con.id_convenio "
           + "        and mat.x_unidad = uni.x_unidad "
           + "                 and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
           + "        and pro.x_centro IN (SELECT distinct dcen.x_centro id  "
           + "                             from TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
           + "                                  TLCENTROS cen, FCT_CONVENIOS conv   "
           + "                             WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)   "
           + "                             AND dcen.c_provincia = prv.c_provincia  "
           + "                             AND dcen.l_vigente = 'S'  "
           + "                             AND cen.x_centro = dcen.x_centro  "
           + "                             AND cen.l_delegacion = 'N'  "
           + "                             AND cen.l_extranjero = 'N')  "
           + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
           + "        and :tipoEmpresa in (-1,1) "
           + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
           + "        and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig) "
           + "        union "
           + "        select distinct uni.x_unidad as idUnidad, "
           + "        uni.t_nombre as nombre "
           + "        from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
           + "        TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni "
           + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)  "
           + "        and tut.id_tutorfctdual = pro.id_tutorfctdual "
           + "        and pro.id_proyecto = cp.id_proyecto "
           + "        and cp.id_conv_proy = cpa.id_conv_proy "
           + "        and cpa.x_matricula = mat.x_matricula "
           + "        and cp.id_convenio = con.id_convenio "
           + "        and mat.x_unidad = uni.x_unidad "
           + "                 and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
           + "        and pro.x_centro IN (SELECT distinct dcen.x_centro id  "
           + "                             from TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
           + "                                  TLCENTROS cen, FCT_CONVENIOS conv   "
           + "                             WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)   "
           + "                             AND dcen.c_provincia = prv.c_provincia  "
           + "                             AND dcen.l_vigente = 'S'  "
           + "                             AND cen.x_centro = dcen.x_centro  "
           + "                             AND cen.l_delegacion = 'N'  "
           + "                             AND cen.l_extranjero = 'N')  "
           + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
           + "        and :tipoEmpresa in (-1,2) "
           + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa) "
           + "        and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig) "
           + "        order by nombre ", nativeQuery = true)
 List<UnidadCursoProjection> findUnidadesEmpleadoDelegacionProvincias(Long idTutorfctdual, 
                   Long idCentro,
                   Integer cAnno, 
                   Integer tipoEmpresa, 
                   Long idEmpresa, 
                   Long idOfertamatrig,   
                   Long idCentroCombo,
                   Long idProvincia);

 @Query(value= "select val.x_valliscat AS ID, c_valliscat AS DESCRIPCIONCORTA, d_valliscat AS DESCRIPCIONLARGA "
    + "from tlvalliscat val, tlcatlisval cat  "
    + "where val.x_catlisval = cat.x_catlisval "
    + "and c_catlisval = 'FCT_EVAALU' "
    + "ORDER by val.n_ordpre ", nativeQuery = true)
 List<AptoEvaluacionAlumnoProjection> findAptoEvaluacionAlumno();

 
 @Query(value= "select CASE " 
   + "           WHEN substr(alu.c_numide,1,1) in ('X','Y','Z') THEN '6' || LPAD(alu.c_numide, 10, '0') "
   + "           WHEN alu.c_tipide = 'D' THEN '1' || LPAD(alu.c_numide, 10, '0')  "   
   + "           WHEN alu.c_tipide = 'P' THEN '2' || LPAD(alu.c_numide, 10, '0')    "
   + "           ELSE LPAD('0', 10, '0') END ipf,                 "
   + "           (select NVL(LPAD(sed.c_provincia, 2, '0'),'00')   "
   + "            from FCT_CONV_PROG cvp,  "
   + "                 FCT_CONVENIOS conv,  "
   + "                 emp_sedemp sed  "
   + "            where cvp.id_conv_prog = cpa.id_conv_prog " 
   + "            and conv.id_convenio = cvp.id_convenio  "
   + "            and sed.id_sedemp = conv.id_sedemp  "
   + "            and ROWNUM = 1) AS prov,              "   
   + "            NVL(RPAD(alu.t_apellido1, 33, ' '), RPAD('---', 33, ' '))  AS ap1, " 
   + "            NVL(RPAD(alu.t_apellido2, 33, ' '), RPAD('---', 33, ' '))  AS ap2,  "
   + "            RPAD(alu.t_nombre, 33, ' ')  AS nom,  "
   + "            TO_CHAR(alu.f_nacimiento, 'YYYYMMDD') AS fnac, " 
   + "            DECODE(alu.c_pais_nacer,'034','724',alu.c_pais_nacer) AS nacionalidad, " 
   + "            NVL((SELECT RPAD(tut.t_nombre,15,' ') from tlfamiliasalu fam , tltutores tut " 
   + "                  WHERE fam.x_familia = alu.x_familia  "
   + "                  AND (fam.x_tutor1 = tut.x_tutor or fam.x_tutor2 = tut.x_tutor) " 
   + "                  AND tut.l_sexo = 'H'"
   + "                  AND ROWNUM = 1),RPAD('NO CONSTA',15,' ')) AS nombrepadre,  "
   + "            NVL((SELECT RPAD(tut.t_nombre,15,' ') from tlfamiliasalu fam , tltutores tut " 
   + "                 WHERE fam.x_familia = alu.x_familia  "
   + "                 AND (fam.x_tutor1 = tut.x_tutor or fam.x_tutor2 = tut.x_tutor) " 
   + "                 AND tut.l_sexo = 'M'"
   + "                 AND ROWNUM = 1),RPAD('NO CONSTA',15,' ')) AS nombremadre,  "
   + "            '34 ' AS movilpre,  "
   + "            alu.T_TELEFONO AS movil " 
   + "    from  FCT_CONVPROG_ALU cpa, "     
   + "          TLMATALU mat,   "
   + "                   delphos_segedu.TLALUMNOS alu "       
   + "    where mat.c_anno = :cAnno      "
   + "    and cpa.x_matricula = mat.x_matricula "     
   + "    and mat.x_alumno = alu.x_alumno  "
   + "    union     "
   + "          select CASE " 
   + "                 WHEN substr(alu.c_numide,1,1) in ('X','Y','Z') THEN '6' || LPAD(alu.c_numide, 10, '0') "
   + "                 WHEN c_tipide = 'D' THEN '1' || LPAD(alu.c_numide, 10, '0') "        
   + "                 WHEN c_tipide = 'P' THEN '2' || LPAD(alu.c_numide, 10, '0')    "
   + "                 ELSE LPAD('0', 10, '0') END ipf,                      "
   + "                (select NVL(LPAD(sed.c_provincia, 2, '0'),'00')   "
   + "                 from FCT_CONV_PROY cvy,  "
   + "                      FCT_CONVENIOS conv,  "
   + "                      emp_sedemp sed  "
   + "                 where cvy.id_conv_proy = cpa.id_conv_proy " 
   + "                 and conv.id_convenio = cvy.id_convenio  "
   + "                 and sed.id_sedemp = conv.id_sedemp  "
   + "                 and ROWNUM = 1) AS prov,              "        
   + "                 NVL(RPAD(alu.t_apellido1, 33, ' '), RPAD('---', 33, ' '))  AS ap1, " 
   + "                 NVL(RPAD(alu.t_apellido2, 33, ' '), RPAD('---', 33, ' '))  AS ap2,  "
   + "                 RPAD(alu.t_nombre, 33, ' ')  AS nom,  "
   + "                 TO_CHAR(alu.f_nacimiento, 'YYYYMMDD') fnac, "
   + "                 DECODE(alu.c_pais_nacer,'034','724',alu.c_pais_nacer) AS nacionalidad, " 
   + "                 NVL((SELECT RPAD(tut.t_nombre,15,' ') from tlfamiliasalu fam , tltutores tut " 
   + "                      WHERE fam.x_familia = alu.x_familia  "
   + "                      AND (fam.x_tutor1 = tut.x_tutor or fam.x_tutor2 = tut.x_tutor) " 
   + "                      AND tut.l_sexo = 'H'"
   + "                      AND ROWNUM = 1),RPAD('NO CONSTA',15,' ')) AS nombrepadre,  "
   + "                 NVL((SELECT RPAD(tut.t_nombre,15,' ') from tlfamiliasalu fam , tltutores tut " 
   + "                      WHERE fam.x_familia = alu.x_familia  "
   + "                      AND (fam.x_tutor1 = tut.x_tutor or fam.x_tutor2 = tut.x_tutor) " 
   + "                      AND tut.l_sexo = 'M'"
   + "                      AND ROWNUM = 1),RPAD('NO CONSTA',15,' ')) AS nombremadre,  "
   + "                '34 ' AS movilpre,  "
   + "                alu.T_TELEFONO AS movil " 
   + "    from FCT_CONVPROY_ALU cpa,      "
   + "         TLMATALU mat,   "
   + "                  delphos_segedu.TLALUMNOS alu " 
   + "    where mat.c_anno = :cAnno   "
   + "    and cpa.x_matricula = mat.x_matricula "     
   + "    and mat.x_alumno = alu.x_alumno ", nativeQuery = true)
 List<AlumnadoNSSProjection> getAlumnadoNSS(Integer cAnno);


 @Query(value= "SELECT '123456' AS CCC, '159753456' AS nuss, SYSDATE AS fechaRealAlta "
   + "FROM DUAL ", nativeQuery = true)
 List<AlumnadoAltasBajasProjection> getAlumnadoAltasBajas(Integer cAnno);


 @Query(value= "select sysdate AS fechaComunicacionDatos,  "
   + "       alu.c_numide AS dni,  "
   + "       alu.t_nuss AS nuss,    "
   + "    alu.t_apellido1 || ' ' || alu.t_apellido2  AS apellidos,   "
   + "       alu.t_nombre As nombre,  "
   + "      (select NVL(MIN(aper.fh_inicio),MIN(cper.fh_inicio)) "
   + "       from fct_conv_proghoraper cper,  "
   + "            fct_conv_progaluhoraper aper "
   + "       where cper.id_conv_prog = cpa.id_conv_prog "
   + "       and aper.id_conv_prog = cpa.id_conv_prog "
   + "       and aper.x_matricula = mat.x_matricula) AS fechaInicioPrac,   "
   + "    (select NVL(MAX(aper.fh_fin),MAX(cper.fh_fin)) "
   + "       from fct_conv_proghoraper cper,  "
   + "            fct_conv_progaluhoraper aper "
   + "       where cper.id_conv_prog = cpa.id_conv_prog "
   + "       and aper.id_conv_prog = cpa.id_conv_prog "
   + "       and aper.x_matricula = mat.x_matricula) AS fechaFinPrac,        "
   + "       decode(cpa.lg_erasmus,2,'Sí',1,'No','') AS alumnErasmusConBeca,    "
   + "    decode(cpa.lg_erasmus,2,'No',1,'Sí','') AS alumnErasmusSinBeca         "
   + "from  FCT_CONVPROG_ALU cpa,         "
   + "   TLMATALU mat,      "
   + "   TLALUMNOS alu           "
   + "where mat.c_anno = :cAnno  "
   + "and mat.x_centro = :idCentro "
   + "and cpa.x_matricula = mat.x_matricula         "
   + "and mat.x_alumno = alu.x_alumno "
   + "union        "
   + "select  SYSDATE AS fechaComunicacionDatos,  "
   + "        alu.c_numide AS dni,  "
   + "        alu.t_nuss AS nuss,    "
   + "  alu.t_apellido1 || ' ' || alu.t_apellido2  AS apellidos,  "
   + "        alu.t_nombre As nombre,  "
   + "       (select NVL(MIN(aper.fh_inicio),MIN(cper.fh_inicio)) "
   + "        from fct_conv_proyhoraper cper,  "
   + "             fct_conv_proyaluhoraper aper "
   + "        where cper.id_conv_proy = cpa.id_conv_proy "
   + "        and aper.id_conv_proy = cpa.id_conv_proy "
   + "        and aper.x_matricula = mat.x_matricula) AS fechaInicioPrac,  "
   + "  (select NVL(MAX(aper.fh_fin),MAX(cper.fh_fin)) "
   + "        from fct_conv_proyhoraper cper,  "
   + "             fct_conv_proyaluhoraper aper "
   + "        where cper.id_conv_proy = cpa.id_conv_proy "
   + "        and aper.id_conv_proy = cpa.id_conv_proy "
   + "        and aper.x_matricula = mat.x_matricula) AS fechaFinPrac,  "
   + "        decode(cpa.lg_erasmus,2,'Sí',1,'No','') AS alumnErasmusConBeca,    "
   + "     decode(cpa.lg_erasmus,2,'No',1,'Sí','') AS alumnErasmusSinBeca "
   + "from FCT_CONVPROY_ALU cpa,         "
   + "     TLMATALU mat,      "
   + "     TLALUMNOS alu     "
   + "where mat.c_anno = :cAnno      "
   + "and mat.x_centro = :idCentro "
   + "and cpa.x_matricula = mat.x_matricula         "
   + "and mat.x_alumno = alu.x_alumno", nativeQuery = true)
 List<AlumnadoAltasProjection> getAlumnadoAltas(Integer cAnno, Long idCentro);

 @Query(value= "select sysdate AS fechaComunicacionDatos,  "
   + "       alu.c_numide AS dni,  "
   + "       alu.t_nuss AS nuss,    "
   + "    alu.t_apellido1 || ' ' || alu.t_apellido2  AS apellidos,   "
   + "       alu.t_nombre As nombre, "
   + "       (select sum(nu_dias)  "
   + "        from fct_parsem_aluprog par "
   + "        where par.id_convprog_alu = cpa.id_convprog_alu "
   + "        and TO_CHAR(fh_inisem,'MM') = :nMes "
   + "        and TO_CHAR(fh_inisem,'yyyy') = TO_CHAR(sysdate,'yyyy')) AS numDiasRealizados, "
   + "        '' AS numDiasInc, "
   + "        '' AS numDiasEmb "
   + "from  FCT_CONVPROG_ALU cpa,         "
   + "   TLMATALU mat,      "
   + "   TLALUMNOS alu           "
   + "where mat.c_anno = :cAnno  "
   + "and mat.x_centro = :idCentro "
   + "and cpa.x_matricula = mat.x_matricula         "
   + "and mat.x_alumno = alu.x_alumno "
   + "union        "
   + "select  SYSDATE AS fechaComunicacionDatos,  "
   + "        alu.c_numide AS dni,  "
   + "        alu.t_nuss AS nuss,    "
   + "  alu.t_apellido1 || ' ' || alu.t_apellido2  AS apellidos,  "
   + "        alu.t_nombre As nombre, "
   + "        (select sum(nu_dias)  "
   + "        from fct_parsem_aluproy par "
   + "        where par.id_convproy_alu = cpa.id_convproy_alu "
   + "        AND TO_CHAR(fh_inisem,'MM') = :nMes "
   + "        AND TO_CHAR(fh_inisem,'yyyy') = TO_CHAR(sysdate,'yyyy')) AS numDiasRealizados, "
   + "        '' AS numDiasInc, "
   + "        '' AS numDiasEmb "
   + "from FCT_CONVPROY_ALU cpa,         "
   + "     TLMATALU mat,      "
   + "     TLALUMNOS alu     "
   + "where mat.c_anno = :cAnno      "
   + "and mat.x_centro = :idCentro "
   + "and cpa.x_matricula = mat.x_matricula         "
   + "and mat.x_alumno = alu.x_alumno", nativeQuery = true)
 List<ComunicacionDiasPracticasProjection> getComunicacionDiasPracticas(Integer cAnno, Long idCentro, Integer nMes );

 
 @Query(value= "select count (*) from ( "
   + "select distinct prog.id_conv_prog convenio  "
   + "from fct_convprog_alu prog, delphos_segedu.tlempleados emp, tlalumnos alu, tlmatalu mat "
   + "where emp.x_empleado = :idEmpleadoComunica "
   + "and alu.c_numide = emp.c_numide "
   + "and mat.x_alumno = alu.x_alumno "
   + "and mat.c_anno = :cAnno "
   + "and mat.x_centro = :xCentro "
   + "and prog.x_matricula = mat.x_matricula "
   + "UNION "
   + "select distinct proy.id_conv_proy convenio "
   + "from fct_convproy_alu proy, delphos_segedu.tlempleados emp, tlalumnos alu, tlmatalu mat "
   + "where emp.x_empleado = :idEmpleadoComunica "
   + "and alu.c_numide = emp.c_numide "
   + "and mat.x_alumno = alu.x_alumno "
   + "and mat.c_anno = :cAnno "
   + "and mat.x_centro = :xCentro "
   + "and proy.x_matricula = mat.x_matricula)", nativeQuery = true)
Integer getNumeroProgProy(Integer cAnno, Long xCentro, Long idEmpleadoComunica);

 
 @Query(value= " select  tut.id_tutorfctdual as id,           "
   + "        EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2  as descripcion               "
   + "                 from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "                     FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,               "
   + "                 TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP               "
   + "                 where empl.x_empleado = :idEmpleadoComunica "
   + "                 and alu.c_numide = empl.c_numide "
   + "                 and mat.x_alumno = alu.x_alumno "
   + "                 and tut.id_tutorfctdual = pro.id_tutorfctdual               "
   + "                 and pro.id_programa = cp.id_programa               "
   + "                 and cp.id_conv_prog = cpa.id_conv_prog               "
   + "                 and cpa.x_matricula = mat.x_matricula        "
   + "                 and emp.x_empleado = tut.x_empleado      "
   + "                 and cp.id_convenio = con.id_convenio                     "
   + "                 and (:sTodos = 1 or con.lg_lofp = 0)  "
   + "                 and pro.x_centro = :idCentro               "
   + "                 and :cAnno between pro.c_anno_desde and pro.c_anno_hasta               "
   + "                 and :tipoEmpresa in (-1,1)               "
   + "                 and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)               "
   + "                 union               "
   + "      select tut.id_tutorfctdual as id,               "
   + "             EMP.NOMBRE || ' ' || EMP.APELLIDO1  ||' ' || EMP.APELLIDO2 as descripcion            "
   + "                 from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "                 FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,               "
   + "                 TLMATALU mat, FCT_CONVENIOS con, TLEMPLEADOS EMP                "
   + "                 where empl.x_empleado = :idEmpleadoComunica "
   + "                 and alu.c_numide = empl.c_numide "
   + "                 and mat.x_alumno = alu.x_alumno "
   + "                 and tut.id_tutorfctdual = pro.id_tutorfctdual               "
   + "                 and pro.id_proyecto = cp.id_proyecto               "
   + "                 and cp.id_conv_proy = cpa.id_conv_proy               "
   + "                 and cpa.x_matricula = mat.x_matricula         "
   + "                          and emp.x_empleado = tut.x_empleado     "
   + "                 and cp.id_convenio = con.id_convenio                      "
   + "                 and (:sTodos = 1 or con.lg_lofp = 0)  "
   + "                 and pro.x_centro = :idCentro                "
   + "                 and :cAnno between pro.c_anno_desde and pro.c_anno_hasta               "
   + "                 and :tipoEmpresa in (-1,2)               "
   + "                 and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)     "
   + "              order by descripcion", nativeQuery = true)
List<ElementoSelectProjection> findTutoresCentroAlumnado(Long idCentro, 
               Integer cAnno, 
               Integer tipoEmpresa,
               Long idEmpresa, 
               Long idEmpleadoComunica,
               Integer sTodos);


 @Query(value= " select omg.x_ofertamatrig as idOfertamatrig,        "
   + "        omg.d_ofertamatrig as curso        "
   + "        from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "        FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,        "
   + "        TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg        "
   + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)    "
   + "        and empl.x_empleado = :idEmpleadoComunica "
   + "        and alu.c_numide = empl.c_numide "
   + "        and mat.x_alumno = alu.x_alumno "
   + "        and tut.id_tutorfctdual = pro.id_tutorfctdual        "
   + "        and pro.id_programa = cp.id_programa        "
   + "        and cp.id_conv_prog = cpa.id_conv_prog        "
   + "        and cpa.x_matricula = mat.x_matricula        "
   + "        and cp.id_convenio = con.id_convenio        "
   + "        and (:sTodos = 1 OR con.lg_lofp = 0) "
   + "        and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
   + "        and mat.x_ofertamatrig = omg.x_ofertamatrig        "
   + "        and pro.x_centro = :idCentro        "
   + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta        "
   + "        and :tipoEmpresa in (-1,1)        "
   + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)        "
   + "        union        "
   + "        select omg.x_ofertamatrig as idOfertamatrig,        "
   + "        omg.d_ofertamatrig as curso        "
   + "        from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "        FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,        "
   + "        TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg        "
   + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)   "
   + "        and empl.x_empleado = :idEmpleadoComunica "
   + "        and alu.c_numide = empl.c_numide "
   + "        and mat.x_alumno = alu.x_alumno "
   + "        and tut.id_tutorfctdual = pro.id_tutorfctdual        "
   + "        and pro.id_proyecto = cp.id_proyecto        "
   + "        and cp.id_conv_proy = cpa.id_conv_proy        "
   + "        and cpa.x_matricula = mat.x_matricula        "
   + "        and cp.id_convenio = con.id_convenio        "
   + "        and (:sTodos = 1 OR con.lg_lofp = 0) "
   + "        and (:sTodos = 1 OR  omg.d_ofertamatrig not like '%LOFP%') "
   + "        and mat.x_ofertamatrig = omg.x_ofertamatrig        "
   + "        and pro.x_centro = :idCentro        "
   + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta        "
   + "        and :tipoEmpresa in (-1,2)        "
   + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)        "
   + "        order by curso ", nativeQuery = true)
List<CursoModalidadProjection> findCursosEmpleadoCentroAlumnado(Long idTutorfctdual, 
                Long idCentro, 
                Integer cAnno,
                Integer tipoEmpresa, 
                Long idEmpresa, 
                Long idEmpleadoComunica,
                Integer sTodos);

 @Query(value= " select uni.x_unidad as id,    "
   + "    uni.t_nombre as nombre    "
   + "    from  delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "    FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,    "
   + "    TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni    "
   + "    where (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)    "
   + "    and empl.x_empleado = :idEmpleadoComunica "
   + "    and alu.c_numide = empl.c_numide "
   + "    and mat.x_alumno = alu.x_alumno "
   + "    and tut.id_tutorfctdual = pro.id_tutorfctdual    "
   + "    and pro.id_programa = cp.id_programa    "
   + "    and cp.id_conv_prog = cpa.id_conv_prog    "
   + "    and cpa.x_matricula = mat.x_matricula    "
   + "    and cp.id_convenio = con.id_convenio    "
   + "    and mat.x_unidad = uni.x_unidad    "
   + "    and pro.x_centro = :idCentro    "
   + "    and :cAnno between pro.c_anno_desde and pro.c_anno_hasta    "
   + "    and :tipoEmpresa in (-1,1)    "
   + "    and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)    "
   + "    and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig)    "
   + "    union    "
   + "    select uni.x_unidad as idUnidad,    "
   + "    uni.t_nombre as nombre    "
   + "    from delphos_segedu.tlempleados empl, tlalumnos alu,  "
   + "    FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,    "
   + "    TLMATALU mat, FCT_CONVENIOS con, TLUNIDADESCEN uni    "
   + "    where (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual) "
   + "    and empl.x_empleado = :idEmpleadoComunica "
   + "    and alu.c_numide = empl.c_numide "
   + "    and mat.x_alumno = alu.x_alumno "
   + "    and tut.id_tutorfctdual = pro.id_tutorfctdual    "
   + "    and pro.id_proyecto = cp.id_proyecto    "
   + "    and cp.id_conv_proy = cpa.id_conv_proy    "
   + "    and cpa.x_matricula = mat.x_matricula    "
   + "    and cp.id_convenio = con.id_convenio    "
   + "    and mat.x_unidad = uni.x_unidad    "
   + "    and pro.x_centro = :idCentro    "
   + "    and :cAnno between pro.c_anno_desde and pro.c_anno_hasta    "
   + "    and :tipoEmpresa in (-1,2)    "
   + "    and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)    "
   + "    and ( :idOfertamatrig = -1 or :idOfertamatrig = mat.x_ofertamatrig)    "
   + "    order by nombre  ", nativeQuery = true)
List<UnidadCursoProjection> findUnidadesEmpleadoCentroAlumnado(Long idTutorfctdual, 
                                                         Long idCentro, 
                                                         Integer cAnno,
                                                         Integer tipoEmpresa, 
                                                         Long idEmpresa, 
                                                         Long idOfertamatrig, 
                                                         Long idEmpleadoComunica);

 
 @Query(value= " select alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,     "
   + "      emp.d_empresa as nombreEmpresa,     "
   + "      'FCT' as tipoEmpresa,     "
   + "      omg.d_ofertamatrig curso,     "
   + "      uni.t_nombre unidad,     "
   + "      pro.ds_programa descripcion,     "
   + "      (select count(*) from FCT_PARSEM_ALUPROG     "
   + "      where id_convprog_alu = cpa.id_convprog_alu) partes,     "
   + "      cpa.ID_CONVPROG_ALU AS id,     "
   + "      cpa.id_evafir_rodal AS idevarodal,      "
   + "      cpa.tx_evafir_fichero AS txevarodal,       "
   + "               cpa.f_firma AS ffirma,     "
   + "      alu.t_nuss as tnuss,     "
         + "        NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
   + "      cpa.lg_cotiza as cotiza,     "
   + "    (SELECT DECODE(puedeBorrar, 0, 0, 1) "
   +      "    FROM ( "
   +      "        SELECT SUM(id) AS puedeBorrar "
   +      "        FROM ( "
   +      "            SELECT COUNT(*) AS id  "
   +      "            FROM fct_parsem_anexosprog "
   +      "            WHERE id_convprog_alu = cpa.id_convprog_alu "
   +      "            UNION ALL "
   +      "            SELECT COUNT(*) AS id "
   +      "            FROM fct_parsem_aluprog "
   +      "            WHERE id_convprog_alu = cpa.id_convprog_alu "
   +      "            ) "
   +      "        )) as puedeBorrar, "
   + "    0 as avisoMes "
   + "      from delphos_segedu.tlempleados empl, "
   + "      FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,     "
   + "      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,     "
   + "      TLOFEMATRGEN omg, TLUNIDADESCEN uni     "
   + "      where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
   + "      and empl.x_empleado = :idEmpleadoComunica "
   + "      and alu.c_numide = empl.c_numide "
   + "      and tut.id_tutorfctdual = pro.id_tutorfctdual     "
   + "      and pro.id_programa = cp.id_programa     "
   + "      and cp.id_conv_prog = cpa.id_conv_prog     "
   + "      and cpa.x_matricula = mat.x_matricula     "
   + "      and mat.x_alumno = alu.x_alumno     "
   + "      and mat.c_anno = :cAnno "
   + "      and cp.id_convenio = con.id_convenio     "
   +"       and con.lg_lofp = 0 "
   + "      and omg.d_ofertamatrig not like '%LOFP%' "
   + "      and con.x_empresa = emp.x_empresa     "
   + "      and mat.x_ofertamatrig = omg.x_ofertamatrig     "
   + "      and mat.x_unidad = uni.x_unidad     "
   + "      and pro.x_centro = :idCentro     "
   + "      and :cAnno between pro.c_anno_desde and pro.c_anno_hasta     "
   + "      and mat.c_anno = :cAnno     "
   + "      and :tipoEmpresa in (-1,1)     "
   + "      and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)     "
   + "      and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)     "
   + "      and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)     "
   + "      union     "
   + "      select alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre as nombreAlumno,     "
   + "      emp.d_empresa as nombreEmpresa,     "
   + "      'FP Dual' as tipoEmpresa,     "
   + "      omg.d_ofertamatrig curso,     "
   + "      uni.t_nombre unidad,     "
   + "      pro.ds_proyecto descripcion,     "
   + "      (select count(*) from FCT_PARSEM_ALUPROY     "
   + "      where id_convproy_alu = cpa.id_convproy_alu) partes,     "
   + "      cpa.ID_CONVPROY_ALU AS id,        "
   + "      cpa.id_evafir_rodal AS idevarodal,      "
   + "      cpa.tx_evafir_fichero AS txevarodal,       "
   + "               cpa.f_firma AS ffirma,     "
   + "      alu.t_nuss as tnuss,     "
         + "        NVL((SELECT CD_NUSS_OLD FROM ( "
         + "            SELECT CD_NUSS_OLD "
         + "            FROM FCT_CORRECCIONES_NUSS cn "
         + "            WHERE cn.X_MATRICULA = mat.x_matricula "
         + "            ORDER BY cn.ID_CORRECCION_NUSS DESC "
         + "        ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
   + "      cpa.lg_cotiza as cotiza,     "
   + "    (SELECT DECODE(puedeBorrar, 0, 0, 1) "
   +      "    FROM ( "
   +      "        SELECT SUM(id) AS puedeBorrar "
   +      "        FROM ( "
   +      "            SELECT COUNT(*) AS id  "
   +      "            FROM fct_parsem_anexosproy "
   +      "            WHERE id_convproy_alu = cpa.id_convproy_alu "
   +      "            UNION ALL "
   +      "            SELECT COUNT(*) AS id "
   +      "            FROM fct_parsem_aluproy "
   +      "            WHERE id_convproy_alu = cpa.id_convproy_alu "
   +      "            ) "
   +      "        )) as puedeBorrar, "
   + "      0 as avisoMes "
   + "      from delphos_segedu.tlempleados empl, "
   + "      FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,     "
   + "      TLMATALU mat, TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp,     "
   + "      TLOFEMATRGEN omg, TLUNIDADESCEN uni     "
   + "      where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual) "
   + "      and  empl.x_empleado = :idEmpleadoComunica "
   + "      and alu.c_numide = empl.c_numide  "
   + "      and tut.id_tutorfctdual = pro.id_tutorfctdual     "
   + "      and pro.id_proyecto = cp.id_proyecto     "
   + "      and cp.id_conv_proy = cpa.id_conv_proy     "
   + "      and cpa.x_matricula = mat.x_matricula     "
   + "      and mat.x_alumno = alu.x_alumno     "
   + "      and cp.id_convenio = con.id_convenio     "
   + "      and con.x_empresa = emp.x_empresa     "
   +"       and con.lg_lofp = 0 "
   + "      and omg.d_ofertamatrig not like '%LOFP%' "
   + "      and mat.x_ofertamatrig = omg.x_ofertamatrig     "
   + "      and mat.x_unidad = uni.x_unidad     "
   + "      and mat.c_anno = :cAnno "
   + "      and pro.x_centro = :idCentro     "
   + "      and :cAnno between pro.c_anno_desde and pro.c_anno_hasta     "
   + "      and mat.c_anno = :cAnno     "
   + "      and :tipoEmpresa in (-1,2)     "
   + "      and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)     "
   + "      and ( :idOfertamatrig = -1 or  :idOfertamatrig = mat.x_ofertamatrig)     "
   + "      and ( :idUnidad = -1 or  :idUnidad = mat.x_unidad)     "
   + "      order by nombreAlumno, nombreEmpresa, tipoEmpresa, curso, unidad  ", nativeQuery = true) 
List<ListadoAlumnadoTutorProjection> findListadoAlumnosTutorAlumnado(Long idTutorfctdual, 
                     Long idCentro, 
                     Integer cAnno,
                     Integer tipoEmpresa, 
                     Long idEmpresa, 
                     Long idOfertamatrig, 
                     Long idUnidad, 
                     Long idEmpleadoComunica);
 
 @Query(value= " select alu.* from tlalumnos alu , tlmatalu mat "
 		+ "where mat.x_matricula = :idMatricula "
 		+ "and alu.x_alumno = mat.x_alumno  ", nativeQuery = true) 
 List<Alumnado> getNUSSTLalumnos(Long idMatricula);

 @Query(value = "select unique mat.x_matricula " +
         "       from delphos_segedu.tlempleados empl, tlalumnos alu, tlmatalu mat,   " +
         "           FCT_PROGRAMAS prog, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,     " +
         "           FCT_CONVENIOS con, TLEMPRESAS emp     " +
         "       where empl.x_empleado = :idEmpleadoComunica  " +
         "       and alu.c_numide = empl.c_numide  " +
         "       and mat.x_alumno = alu.x_alumno  " +
         "       and mat.c_anno = :cAnno  " +
         "       and cpa.x_matricula = mat.x_matricula   " +
         "       and cp.id_conv_prog = cpa.id_conv_prog" +
         "       and prog.id_programa = cp.id_programa     " +
         "       and cp.id_convenio = con.id_convenio     " +
        // "       and :cAnno between prog.c_anno_desde and prog.c_anno_hasta" +
         "       and con.x_empresa = emp.x_empresa     ", nativeQuery = true)
 Optional<Long> findAlumnoProgramaByAnnoAndIdDelphos(Integer cAnno, Long idEmpleadoComunica);

 @Query(value = "select unique mat.x_matricula " +
         "       from delphos_segedu.tlempleados empl, tlalumnos alu, tlmatalu mat,   " +
         "           FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,     " +
         "           FCT_CONVENIOS con, TLEMPRESAS emp     " +
         "       where empl.x_empleado = :idEmpleadoComunica  " +
         "       and alu.c_numide = empl.c_numide  " +
         "       and mat.x_alumno = alu.x_alumno  " +
         "       and mat.c_anno = :cAnno  " +
         "       and cpa.x_matricula = mat.x_matricula   " +
         "       and pro.id_proyecto = cp.id_proyecto     " +
         "       and cp.id_conv_proy = cpa.id_conv_proy     " +
         "       and cp.id_convenio = con.id_convenio     " +
         "       and con.x_empresa = emp.x_empresa     " +
         //"       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta " +
         "       and pro.lg_lofp = 0", nativeQuery = true)
 Optional<Long> findAlumnoProyectoByAnnoAndIdDelphos(Integer cAnno, Long idEmpleadoComunica);

 @Query(value = "select mat.c_anno || '-' || (mat.c_anno+1) anno, " +
         "        (select emp.apellido1 || ' ' || emp.apellido2 || ', ' || emp.nombre      " +
         "           from fct_convprog_alu conva, fct_conv_prog conv,    " +
         "             fct_programas prog, fct_tutorfctdual tut, tlempleados emp    " +
         "           where conva.x_matricula = mat.x_matricula   " +
         "           and conv.id_conv_prog = conva.id_conv_prog   " +
         "           and prog.id_programa = conv.id_programa " +
         "           and tut.id_tutorfctdual = prog.id_tutorfctdual   " +
         "           and emp.x_empleado = tut.x_empleado   " +
         "           and rownum = 1 " +
         "         ) tutor,  " +
         "         omg.d_ofertamatrig curso,      " +
         "         uni.t_nombre unidad,      " +
         "         pro.ds_programa descripcion,      " +
         "         TO_CHAR(cpa.ID_CONVPROG_ALU) AS id,      " +
         "         TO_CHAR(cpa.id_evafir_rodal) AS idEvaRodal,       " +
         "         cpa.tx_evafir_fichero AS txEvaRodal,        " +
         "         TO_CHAR(cpa.f_firma,'DD/MM/YYYY') AS fFirma,      " +
         "         TO_CHAR(cpa.lg_cotiza) as cotiza,      " +
         "         emp.d_empresa as empresaNombre,      " +
         "         empl.tx_apellido1 || ' ' || empl.tx_apellido2 || ', ' || empl.tx_nombre AS empresaTutor, " +
         "         sed.t_correo empresaEmail,   " +
         "         TO_CHAR(sed.n_telefono) empresaTlf, " +
         "         TO_CHAR(cp.fh_inicio,'DD/MM/YYYY') || '-' || TO_CHAR(cp.fh_fin,'DD/MM/YYYY') AS periodo   " +

//         "         (select count(*) from FCT_PARSEM_ALUPROG      " +
//         "          where id_convprog_alu = cpa.id_convprog_alu) as partes, " +
//         "         (SELECT DECODE(puedeBorrar, 0, 0, 1)  " +
//         "             FROM (  " +
//         "                 SELECT SUM(id) AS puedeBorrar  " +
//         "                 FROM (  " +
//         "                     SELECT Count(*) AS id   " +
//         "                     FROM fct_parsem_anexosprog  " +
//         "                     WHERE id_convprog_alu = cpa.id_convprog_alu  " +
//         "                     UNION ALL  " +
//         "                     SELECT COUNT(*) AS id  " +
//         "                     FROM fct_parsem_aluprog  " +
//         "                     WHERE id_convprog_alu = cpa.id_convprog_alu  " +
//         "                     )  " +
//         "                 )) as puedeBorrar " +


         "         from FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, TLMATALU mat, " +
         "           TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, TLOFEMATRGEN omg, TLUNIDADESCEN uni," +
         "           EMP_TRAEMP tra, EMP_SEDEMP sed, EMP_EMPLEADOS empl " +
         "         where mat.x_matricula = :idMatricula " +
         "         and cpa.x_matricula = mat.x_matricula " +
         "         and mat.x_alumno = alu.x_alumno " +
         "         and cpa.id_conv_prog = cp.id_conv_prog " +
         "         and cp.id_convenio = con.id_convenio " +
         "         and con.lg_lofp = 0 " +
         "         and omg.d_ofertamatrig not like '%LOFP%' " +
         "         and con.x_empresa = emp.x_empresa " +
         "         and con.id_sedemp = sed.id_sedemp " +
         "         and tra.id_traemp = cp.id_traemp " +
         "         and empl.id_empleado = tra.id_empleado " +
         "         and mat.x_ofertamatrig = omg.x_ofertamatrig " +
         "         and mat.x_unidad = uni.x_unidad " +
         "         and pro.id_programa = cp.id_programa " +
         "         order by cp.fh_inicio ", nativeQuery = true)
 List<Object[]> getDatosFormacionPrograma(Long idMatricula);


 @Query(value = "select mat.c_anno || '-' || (mat.c_anno+1) anno,  " +
         "                 (select emp.apellido1 || ' ' || emp.apellido2 || ', ' || emp.nombre       " +
         "                    from fct_convproy_alu conva, fct_conv_proy conv,     " +
         "                      fct_proyectos proy, fct_tutorfctdual tut, tlempleados emp     " +
         "                    where conva.x_matricula = mat.x_matricula    " +
         "                    and conv.id_conv_proy = conva.id_conv_proy    " +
         "                    and proy.id_proyecto = conv.id_proyecto " +
         "                    and tut.id_tutorfctdual = proy.id_tutorfctdual    " +
         "                    and emp.x_empleado = tut.x_empleado    " +
         "                    and rownum = 1  " +
         "                  ) tutor,   " +
         "                  omg.d_ofertamatrig curso,       " +
         "                  uni.t_nombre unidad,       " +
         "                  pro.ds_proyecto descripcion,       " +
         "                  TO_CHAR(cpa.ID_CONVPROY_ALU) AS id,       " +
         "                  TO_CHAR(cpa.id_evafir_rodal) AS idEvaRodal,        " +
         "                  cpa.tx_evafir_fichero AS txEvaRodal,         " +
         "                  TO_CHAR(cpa.f_firma,'DD/MM/YYYY') AS fFirma,       " +
         "                  TO_CHAR(cpa.lg_cotiza) as cotiza,       " +
         "                  emp.d_empresa as empresaNombre,       " +
         "                  empl.tx_apellido1 || ' ' || empl.tx_apellido2 || ', ' || empl.tx_nombre AS empresaTutor,  " +
         "                  sed.t_correo empresaEmail,    " +
         "                  TO_CHAR(sed.n_telefono) empresaTlf  " +

//         "         (select count(*) from FCT_PARSEM_ALUPROG      " +
//         "          where id_convprog_alu = cpa.id_convprog_alu) as partes, " +
//         "         (SELECT DECODE(puedeBorrar, 0, 0, 1)  " +
//         "             FROM (  " +
//         "                 SELECT SUM(id) AS puedeBorrar  " +
//         "                 FROM (  " +
//         "                     SELECT Count(*) AS id   " +
//         "                     FROM fct_parsem_anexosprog  " +
//         "                     WHERE id_convprog_alu = cpa.id_convprog_alu  " +
//         "                     UNION ALL  " +
//         "                     SELECT COUNT(*) AS id  " +
//         "                     FROM fct_parsem_aluprog  " +
//         "                     WHERE id_convprog_alu = cpa.id_convprog_alu  " +
//         "                     )  " +
//         "                 )) as puedeBorrar " +

         "                  from FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, TLMATALU mat,  " +
         "                    TLALUMNOS alu, FCT_CONVENIOS con, TLEMPRESAS emp, TLOFEMATRGEN omg, TLUNIDADESCEN uni, " +
         "                    EMP_TRAEMP tra, EMP_SEDEMP sed, EMP_EMPLEADOS empl " +
         "                  where mat.x_matricula = :idMatricula  " +
         "                  and cpa.x_matricula = mat.x_matricula  " +
         "                  and mat.x_alumno = alu.x_alumno  " +
         "                  and cpa.id_conv_proy = cp.id_conv_proy  " +
         "                  and cp.id_convenio = con.id_convenio  " +
         "                  and con.lg_lofp = 0  " +
         "                  and omg.d_ofertamatrig not like '%LOFP%'  " +
         "                  and con.x_empresa = emp.x_empresa  " +
         "                  and con.id_sedemp = sed.id_sedemp  " +
         "                  and tra.id_traemp = cp.id_traemp  " +
         "                  and empl.id_empleado = tra.id_empleado  " +
         "                  and mat.x_ofertamatrig = omg.x_ofertamatrig  " +
         "                  and mat.x_unidad = uni.x_unidad  " +
         "                  and pro.id_proyecto = cp.id_proyecto", nativeQuery = true)
 List<Object[]> getDatosFormacionProyecto(Long idMatricula);

}