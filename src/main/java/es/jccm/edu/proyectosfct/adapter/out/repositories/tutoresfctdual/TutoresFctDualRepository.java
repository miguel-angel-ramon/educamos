		package es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.PuestoTrabajoEmpleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QTutorFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DatosSustitutoProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.EventoTutorFctDtoProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.ListadoTutoresFctDualProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TutoresFctDualRepository extends AbstractRepository<TutorFctDual, Long, QTutorFctDual> {
 
 Page<TutorFctDual> findByPuestoTrabajoEmpleadoEmpleadoNombreContainingIgnoreCase(String nombre, Pageable pageable);

 List<TutorFctDual> findByPuestoTrabajoEmpleadoIn(List<PuestoTrabajoEmpleado> listPuestos); 

 @Query(value = "SELECT id, "
   + "DECODE(nombresustituto,NULL,nombrecompleto,nombrecompleto||nombresustituto) nombrecompleto, "
   + "dni, "
   + "fechainiciotutoria, "
   + "fechabaja FROM ( SELECT tut.id_tutorfctdual AS id, "
   + " EMP.APELLIDO1 || ' ' || EMP.APELLIDO2  ||', ' || EMP.NOMBRE  as nombrecompleto,  "
   + "     (SELECT LISTAGG( "
   + "            ' (sustituido/a por ' || emp1.apellido1 || ' ' || emp1.apellido2 || ', ' || emp1.nombre || ')', "
   + "            ', ' "
   + "     ) WITHIN GROUP (ORDER BY emp1.apellido1) "
   + "     FROM TLPTOTRAEMP pto1 "
   + "     INNER JOIN TLEMPLEADOS emp1 ON pto1.x_empleado = emp1.x_empleado "
   + "     WHERE pto1.x_centro = pto.x_centro "
   + "     AND pto1.x_empleado_sustituye = pto.x_empleado "
   + "     AND pto1.f_tomapos_sustituye = pto.f_tomapos "
   + "     AND emp1.L_ACTIVO = 'S' "
   + "     AND TRUNC(pto1.F_TOMAPOS) <= SYSDATE "
   + "     AND (pto1.F_CESE IS NULL OR TRUNC(pto1.F_CESE) >= SYSDATE)) AS nombresustituto, "     
   + "emp.c_numide AS dni, "
   + "tut.f_initutoria AS fechainiciotutoria, "
   + " tut.f_baja AS fechabaja   "
   + "FROM FCT_TUTORFCTDUAL tut, TLPTOTRAEMP pto, TLEMPLEADOS EMP "
   + "WHERE pto.x_centro = ?1 "
   + "AND tut.x_empleado = pto.x_empleado "
   + "AND tut.f_tomapos =  pto.f_tomapos "
   + "AND (PTO.X_EMPLEADO_SUSTITUYE IS NULL OR exists (select 1 from tlempleados emp1 where emp1.x_empleado = pto.X_EMPLEADO_SUSTITUYE))  "
   + "AND emp.x_empleado = pto.x_empleado order by id desc) ", nativeQuery = true)
 List<ListadoTutoresFctDualProjection> getListaTutoresCentro(Long idCentro);
 
  @Query(value = "select distinct id, "
  + "    DECODE(nombresustituto,NULL,nombrecompleto,nombrecompleto||nombresustituto) nombrecompleto, "
  + "    dni, "
  + "   fechainiciotutoria, "
  + "   fechabaja FROM ("
  + "SELECT tut.id_tutorfctdual AS id, "
  + "     EMP.apellido1 || ' ' || EMP.apellido2 || ', ' || EMP.nombre AS nombrecompleto, " 
  + "     (SELECT LISTAGG( "
     + "            ' (sustituido/a por ' || emp1.apellido1 || ' ' || emp1.apellido2 || ', ' || emp1.nombre || ')', "
     + "            ', ' "
     + "     ) WITHIN GROUP (ORDER BY emp1.apellido1) "
     + "     FROM TLPTOTRAEMP pto1 "
     + "     INNER JOIN TLEMPLEADOS emp1 ON pto1.x_empleado = emp1.x_empleado "
     + "     WHERE pto1.x_centro = pto.x_centro "
     + "     AND pto1.x_empleado_sustituye = pto.x_empleado "
     + "     AND pto1.f_tomapos_sustituye = pto.f_tomapos "
     + "     AND emp1.L_ACTIVO = 'S' "
     + "     AND TRUNC(pto1.F_TOMAPOS) <= SYSDATE "
     + "     AND (pto1.F_CESE IS NULL OR TRUNC(pto1.F_CESE) >= SYSDATE)) AS nombresustituto, "
  + "     emp.c_numide AS dni, "
  + "     tut.f_initutoria AS fechainiciotutoria, "
  + "     tut.f_baja AS fechabaja"
  + " FROM FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLPTOTRAEMP pto"
  + " WHERE emp.x_empleado = tut.x_empleado"
  + " AND pto.x_empleado = emp.x_empleado"
  + " AND (pto.f_tomapos = tut.f_tomapos OR  pto.f_tomapos IN (select pto1.f_tomapos from tlptotraemp pto1, tlcursoaca cur "
  + "        where pto1.x_empleado = pto1.x_empleado "
  + "        and pto1.x_centro = pto.x_centro "
  + "        and cur.c_anno = tlf_annoactual "
  + "        and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, cur.f_inicio, cur.f_final ) = 1)) "
  + " AND pto.x_centro in (SELECT dcen1.x_centro "
  + "                     from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
  + "                          TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv  "
  + "                     WHERE u.x_usuario = :idUsuario "
  + "                     AND pop.x_perfil = :idPerfil "
  + "                     AND pto.x_centro = :idCentro "
  + "                     AND pto.x_empleado=u.x_empleado "
  + "                     AND pop.x_empleado=pto.x_empleado "
  + "                     AND pop.f_tomapos = pto.f_tomapos "
  + "                     AND pto.x_centro = dcen.x_centro "
  + "                     AND dcen.c_provincia = prv.c_provincia  "
  + "                     AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
  + "                     AND dcen1.c_provincia = prv.c_provincia "
  + "                     AND (-1 = :idCentroCombo OR dcen1.x_centro =  :idCentroCombo)  "
  + "                     AND dcen1.l_vigente = 'S' "
  + "                     AND cen.x_centro = dcen1.x_centro "
  + "                     AND cen.l_delegacion = 'N' "
  + "                     AND cen.l_extranjero = 'N'))"
  + "order by nombrecompleto asc ", nativeQuery = true)
 List<ListadoTutoresFctDualProjection> getListaTutoresDelegacion(Long idUsuario, Long idPerfil, Long idCentro, Long idCentroCombo);

   @Query(value = "select distinct id,  "
       + "        DECODE(nombresustituto,NULL,nombrecompleto,nombrecompleto||nombresustituto) nombrecompleto,  "
       + "        dni,  "
       + "       fechainiciotutoria,  "
       + "       fechabaja FROM ( "
       + "    SELECT tut.id_tutorfctdual AS id,  "
       + "         EMP.apellido1 || ' ' || EMP.apellido2 || ', ' || EMP.nombre AS nombrecompleto,  "
     + "     (SELECT LISTAGG( "
        + "            ' (sustituido/a por ' || emp1.apellido1 || ' ' || emp1.apellido2 || ', ' || emp1.nombre || ')', "
        + "            ', ' "
        + "     ) WITHIN GROUP (ORDER BY emp1.apellido1) "
        + "     FROM TLPTOTRAEMP pto1 "
        + "     INNER JOIN TLEMPLEADOS emp1 ON pto1.x_empleado = emp1.x_empleado "
        + "     WHERE pto1.x_centro = pto.x_centro "
        + "     AND pto1.x_empleado_sustituye = pto.x_empleado "
        + "     AND pto1.f_tomapos_sustituye = pto.f_tomapos "
        + "     AND emp1.L_ACTIVO = 'S' "
        + "     AND TRUNC(pto1.F_TOMAPOS) <= SYSDATE "
        + "     AND (pto1.F_CESE IS NULL OR TRUNC(pto1.F_CESE) >= SYSDATE)) AS nombresustituto, "
       + "          emp.c_numide AS dni,  "
       + "        tut.f_initutoria AS fechainiciotutoria,  "
       + "             tut.f_baja AS fechabaja "
       + "     FROM FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLPTOTRAEMP pto "
       + "     WHERE emp.x_empleado = tut.x_empleado "
       + "     AND pto.x_empleado = emp.x_empleado "
       + "     AND pto.f_tomapos = tut.f_tomapos "
       + "     AND pto.x_centro in (SELECT dcen.x_centro  "
       + "                         from TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
       + "                              TLCENTROS cen, FCT_CONVENIOS conv   "
       + "                         WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia) "
       + "                         AND dcen.c_provincia = prv.c_provincia   "
       + "                         AND dcen.c_provincia = prv.c_provincia  "
       + "                         AND dcen.l_vigente = 'S'  "
       + "                         AND cen.x_centro = dcen.x_centro  "
       + "                         AND cen.l_delegacion = 'N'  "
       + "                         AND cen.l_extranjero = 'N')) "
       + "    order by nombrecompleto asc ", nativeQuery = true)
   List<ListadoTutoresFctDualProjection> getListaTutoresDelegacionProvincias( Long idProvincia);

 
 @Query(value = "SELECT id, "
   + "DECODE (nombresustituto,NULL,nombrecompleto,nombrecompleto||nombresustituto) nombrecompleto, "
   + "dni, "
   + "fechainiciotutoria, "
   + "fechabaja FROM ( SELECT tut.id_tutorfctdual AS id, "
   + "EMP.nombre || ' ' || EMP.apellido1 || ' ' || EMP.apellido2 AS nombrecompleto, "   
   + "     ( SELECT LISTAGG( "
   + "           ' (sustituido/a por ' || emp1.apellido1 || ' ' || emp1.apellido2 || ', ' || emp1.nombre || ')', "
   + "              ', ' "
   + "          ) WITHIN GROUP (ORDER BY emp1.apellido1) "
   + "          FROM TLPTOTRAEMP pto1 "
   + "          INNER JOIN TLEMPLEADOS emp1 ON pto1.x_empleado = emp1.x_empleado "
   + "          WHERE pto1.x_centro = pto.x_centro "
   + "              AND pto1.x_empleado_sustituye = tut.x_empleado "
   + " and (pto1.f_tomapos_sustituye = tut.f_tomapos OR pto1.f_tomapos_sustituye IN (select f_tomapos from TLPTOTRAEMP pto2, tlcursoaca aca  "
   + "        where pto2.x_empleado = pto1.x_empleado  "
   + "        and pto2.x_centro = pto1.x_centro "
   + "        and aca.c_anno = tlf_annoactual "
   + "        and TLF_INTERSECPER( pto2.f_tomaposrea, pto2.f_cese, aca.f_inicio, aca.f_final ) = 1)) "      
   + "              AND TRUNC(pto1.F_TOMAPOS) <= SYSDATE " 
   + "              AND (pto1.F_CESE IS NULL OR TRUNC(pto1.F_CESE) >= SYSDATE) "
   + "      ) AS nombresustituto,       "
   + "emp.c_numide AS dni, "
   + "tut.f_initutoria AS fechainiciotutoria, "
   + "tut.f_baja AS fechabaja   "
   + "FROM FCT_TUTORFCTDUAL tut, TLPTOTRAEMP pto, TLEMPLEADOS EMP "
   + "WHERE pto.x_centro = ?1 "
   + "AND tut.id_tutorfctdual = ?2 "
   + "AND tut.x_empleado = pto.x_empleado "
   + "AND tut.f_tomapos =  pto.f_tomapos "
   + "AND emp.x_empleado = pto.x_empleado order by id desc) ", nativeQuery = true)
 List<ListadoTutoresFctDualProjection> getListaTutoresIdCentro(Long idCentro, Long idTutor); 
 
 List<TutorFctDual> findByPuestoTrabajoEmpleadoCentroId(Long idCentro);

 Optional<TutorFctDual> findByPuestoTrabajoEmpleado(PuestoTrabajoEmpleado empleado);

 @Query(value = "SELECT DISTINCT EMP.X_EMPLEADO AS id, "
   + "EMP.NOMBRE || ' ' || EMP.APELLIDO1 || ' ' || EMP.APELLIDO2 AS nombre, "
   + "TUT.ID_TUTORFCTDUAL  as idtutorfctdual, "
   + "TUT1.ID_TUTORFCTDUAL  as idtutorfctdualsus "
   + "FROM TLPTOTRAEMP PTO "
   + "INNER JOIN TLEMPLEADOS EMP ON EMP.X_EMPLEADO  = PTO.X_EMPLEADO "
   + "INNER JOIN TLPUESTOS PUT ON PTO.X_PUESTO = PUT.X_PUESTO  "
   + "INNER JOIN FCT_TUTORFCTDUAL TUT ON TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE "
   + "INNER JOIN FCT_TUTORFCTDUAL TUT1 ON TUT1.X_EMPLEADO = PTO.X_EMPLEADO "
   + "WHERE PTO.F_TOMAPOS = TO_DATE(?2,'DD-MM-YYYY') "
   + "AND PTO.X_EMPLEADO = ?1 "
   + "AND PUT.L_DOCENTE ='S'  "
   + "AND TRUNC(PTO.F_TOMAPOS) <= SYSDATE "
   + "AND (PTO.F_CESE IS NULL OR TRUNC(PTO.F_CESE) >= SYSDATE)  order by NOMBRE", nativeQuery = true)
 Optional<DatosSustitutoProjection> getIdTutorSustituido(Long xEmpleado, String fTomapos);
 
 @Query(value = "SELECT DISTINCT EMP.X_EMPLEADO AS ID, "
   + "EMP.NOMBRE || ' ' || EMP.APELLIDO1 || ' ' || EMP.APELLIDO2 AS NOMBRE, "
   + "-1  IDTUTORFCTDUAL "
   + "FROM TLPTOTRAEMP PTO, TLEMPLEADOS EMP, TLPUESTOS PUT "
   + "WHERE PTO.X_EMPLEADO_SUSTITUYE = ?1 "
   + "AND PTO.F_TOMAPOS_SUSTITUYE = TO_DATE(?2,'DD-MM-YYYY') "
   + "AND EMP.X_EMPLEADO =  PTO.X_EMPLEADO "
   + "AND PTO.X_PUESTO = PUT.X_PUESTO "
   + "AND PUT.L_DOCENTE ='S' "
   + "AND TRUNC(PTO.F_TOMAPOS) <= SYSDATE "
   + "AND (PTO.F_CESE IS NULL OR TRUNC(PTO.F_CESE) >= SYSDATE) ", nativeQuery = true)
 Optional<DatosSustitutoProjection> getTutorSustituto(Long xEmpleado, String fTomapos);

 @Query(value = " SELECT C_CODIGO FROM TLPERFILES WHERE X_PERFIL = :idPerfil "
         + " UNION "
         + " SELECT C_CODIGO FROM DELPHOS_SEGEDU.TLPERFILES WHERE X_PERFIL = :idPerfil ", nativeQuery = true)
 String getCodigoPerfil(Long idPerfil);

 
 @Query(value = "select doc.t_identificacion nif,        "
 		+ "       CASE "
 		+ "       WHEN doc.oid_tipo_documentacion = 1 THEN 'NIF'  "
 		+ "       WHEN doc.oid_tipo_documentacion = 2 THEN 'PASAPORTE' "
 		+ "       WHEN doc.oid_tipo_documentacion = 3 THEN 'NIE' "
 		+ "       ELSE 'NOSE' END tipide "
 		+ "from fct_tutorfctdual tut,      "
 		+ "     tlempleados emp,       "
 		+ "     delphos_modacc.documentaciones doc "
 		+ "where tut.x_empleado = emp.x_empleado   "
 		+ "and doc.t_identificacion = emp.c_numide "
 		+ "and tut.id_tutorfctdual = :id ", nativeQuery = true)
 EventoTutorFctDtoProjection getDatosEventos(Long id); 
 
}
