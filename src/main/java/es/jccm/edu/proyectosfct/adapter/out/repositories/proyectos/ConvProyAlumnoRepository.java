package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.ConvenioSegSocialAlumnoProjection;
import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConveniosProyectoAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ConvProyAlumnoRepository extends AbstractRepository<ConveniosProyectoAlumno, Long, QConveniosProyectoAlumno> {

 List<ConveniosProyectoAlumno> findAllByConvenioProyectoId(Long id);

 List<ConveniosProyectoAlumno> findAllById(Long id);
 
 @Query(value = "SELECT alu.X_ALUMNO AS ID, "
    + "            mat.X_MATRICULA AS IDMATRICULA, "
    + "            mat.X_UNIDAD AS IDUNIDAD, "
    + "           TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto, "
    + "           alu.T_NOMBRE || ' ' || alu.T_APELLIDO1 ||  ' ' || alu.T_APELLIDO2 AS nombre, "
    + "           uni.T_NOMBRE AS nombreUnidad, "
       + "           alu.T_NUSS as tnuss, "
       + "           NVL(alp.LG_COTIZA,0) as lgCotiza, "
       + "           NVL(alp.LG_NUSS,0) as lgNuss, "
       + "           alp.id_convproy_alu idConvAlu, "
       + "           CASE "
       + "           WHEN (alu.t_nuss is null OR alp.lg_nuss = 1) THEN 1 "
       + "           ELSE 0 END AS lgEditable, "
       + "        NVL(alp.LG_ERASMUS,0) as lgErasmus, "
       + "    CASE "
         + "       WHEN (SELECT COUNT(*) FROM fct_altass_proy WHERE id_convproy_alu = alp.id_convproy_alu AND f_envioss IS NOT NULL AND NVL(lg_anulado,0) !=1) > 0 "
         + "            AND (SELECT COUNT(*) FROM fct_convproy_alu WHERE x_matricula = alp.x_matricula AND id_conv_proy <> alp.id_conv_proy) = 0 "
         + "       THEN 'Enviada alta SS' "
       + "       WHEN (select count(*) from fct_historicoaltas_proy where id_altass_proy = (select id_altass_proy from fct_altass_proy where id_convproy_alu = alp.id_convproy_alu and NVL(lg_anulado,0) !=1 and rownum = 1)) >0 AND (SELECT COUNT(*) FROM fct_convproy_alu WHERE x_matricula = alp.x_matricula AND id_conv_proy <> alp.id_conv_proy) = 0 THEN 'Enviada alta SS' "
       + "       WHEN (select count(*) from fct_parsem_aluproy where id_convproy_alu = alp.id_convproy_alu) >0 THEN 'Contiene partes semanales' "
       + "       ELSE '' END AS dsMotivo "
    + "    FROM TLMATALU mat, "
    + "         TLALUMNOS alu, "
    + "         FCT_CONVPROY_ALU alp, "
    + "         TLUNIDADESCEN uni "
    + "    WHERE nvl(mat.C_RESULTADO,99) > 1  "
    + "    AND mat.X_ALUMNO = alu.X_ALUMNO "
    + "    AND alp.X_MATRICULA = mat.X_MATRICULA "
    + "    AND alp.ID_CONV_PROY = ?1 "
    + "    AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 List<AlumnoProjection> getAlumnosConvenioSeleccionados(Long idConvProy);

 Optional<ConveniosProyectoAlumno> findByIdEvaRodal(String idEvaRodal);
 
 Integer countByConvenioProyectoId(Long idConvProy);

// @Query(value = " SELECT x_alumno from TLMATALU where x_matricula = :idMatricula", nativeQuery = true)
// Long getXAlumnoFromMat(Long idMatricula);

 @Query(value = "SELECT ALP.ID_CONVPROY_ALU as idConvProyAlu, "
  +"      emp.d_empresa || ' - ' || ds_proyecto AS nombreEmpresa, "
  +"      emp.x_empresa AS idEmpresa, "
  +"              NVL(alp.LG_COTIZA, 0) AS centroSS, "
  +"             NVL(alp.LG_ERASMUS, 0) AS erasmus, "
  +"             alp.ID_CONV_PROY AS idProyecto "
  +"      FROM FCT_CONV_PROY convpr "
  +"      JOIN FCT_CONVPROY_ALU alp ON alp.id_conv_proy = convpr.id_conv_proy "
  +"      JOIN EMP_TRAEMP traemp ON convpr.id_traemp = traemp.id_traemp "
  +"      JOIN TLEMPRESAS emp ON traemp.x_empresa = emp.x_empresa "
  +"      JOIN FCT_PROYECTOS proy ON proy.id_proyecto = convpr.id_proyecto " 
  +"      WHERE alp.X_MATRICULA = :matricula ", nativeQuery = true)
 List<ConvenioSegSocialAlumnoProjection> findDatosSeguridadSocialByMatricula(Long matricula);

 @Query(value = "SELECT * FROM FCT_CONVPROY_ALU WHERE X_MATRICULA = :matricula", nativeQuery = true)
 List<ConveniosProyectoAlumno> findByMatricula(Long matricula);
 @Query(value = "SELECT proy.* "
         + "FROM FCT_CONVPROY_ALU proy "
         + "JOIN FCT_CONVPROY_ALU ref ON proy.x_matricula = ref.x_matricula "
         + "WHERE ref.id_convproy_alu = :idConvproyAlu", nativeQuery = true)
 List<ConveniosProyectoAlumno> findByIdMatricula(Long idConvproyAlu);

 @Query(value = "SELECT NVL(TO_CHAR((SELECT MIN(per.fh_inicio) FROM fct_conv_proyaluhoraper per WHERE per.ID_CONV_PROY = proy.ID_CONV_PROY), 'DD/MM/YYYY'), TO_CHAR(proy.fh_inicio, 'DD/MM/YYYY')) || ' - ' || " +
         "              NVL(TO_CHAR((SELECT MAX(per.fh_fin) FROM fct_conv_proyaluhoraper per WHERE per.ID_CONV_PROY = proy.ID_CONV_PROY), 'DD/MM/YYYY'), TO_CHAR(proy.fh_fin, 'DD/MM/YYYY')) AS periodo " +
         "       FROM fct_conv_proy proy " +
         "       WHERE proy.ID_CONV_PROY = :idConvProy ", nativeQuery = true)
 String getPeriodoEval(Long idConvProy);
}
