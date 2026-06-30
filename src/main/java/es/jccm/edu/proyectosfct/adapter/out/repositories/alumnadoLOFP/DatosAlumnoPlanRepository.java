package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.entities.DatosAlumnoPlan;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.entities.QDatosAlumnoPlan;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection.DatosAlumnoPlanProjection;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection.ListadoAlumnosPlanProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatosAlumnoPlanRepository extends AbstractRepository<DatosAlumnoPlan, Long, QDatosAlumnoPlan> {

    /**
     * Busca un registro de DatosAlumnoPlan por la matrícula
     *
     * @param xMatricula Matrícula del alumno
     * @return Proyección con los datos del alumno
     */


    @Query(value = "SELECT " +
            "   dp.ID_DATOSALU_PLAN AS idDatosAluPlan, " +
            "   ma.X_MATRICULA AS xMatricula, " +
            "   NVL(dp.LG_PRL, 0) AS lgPrl, " +
            "   NVL(dp.LG_CERTIFICACION, 0) AS lgCertificacion, " +
            "   dp.DS_CERTIFICACION AS dsCertificacion, " +
            "   NVL(dp.LG_ADAPTACIONES, 0) AS lgAdaptaciones, " +
            "   dp.DS_ADAPTACIONES AS dsAdaptaciones, " +
            "   NVL(dp.LG_AUTORIZACIONES, 0) AS lgAutorizaciones, " +
            "   dp.DS_AUTORIZACIONES AS dsAutorizaciones, " +
            "   dp.DS_OBSERVACIONES AS dsObservaciones, " +
            "   dp.C_USUCREACION AS cUsuCreacion, " +
            "   dp.F_CREACION AS fCreacion, " +
            "   dp.C_USUACTUALIZA AS cUsuActualiza, " +
            "   dp.F_ACTUALIZA AS fActualiza, " +
            "   (al.t_apellido1 || ' ' ||al.t_apellido2 || ', ' || al.t_nombre) AS nombreCompleto, " +
            "   al.C_NUMIDE AS dni, " +
            "   al.T_NUSS AS nuss, " +
            "   al.T_TELEFONO AS telefono, " +
            "   al.T_CORREO_E AS email " +
            "FROM " +
            "   TLALUMNOS al " +
            "JOIN " +
            "   TLMATALU ma ON al.X_ALUMNO = ma.X_ALUMNO " +
            "LEFT JOIN " +
            "   FCT_DATOSALU_PLAN dp ON dp.X_MATRICULA = ma.X_MATRICULA " +
            "WHERE " +
            "   ma.X_MATRICULA = :xMatricula",
            nativeQuery = true)
    Optional<DatosAlumnoPlanProjection> findProjectionByXMatricula(Long xMatricula);

    @Query(value = "select distinct (alu.t_apellido1 || ' ' ||alu.t_apellido2 || ', ' || alu.t_nombre) as nombreCompleto, " +
            "            mat.x_matricula as xMatricula, " +
            "            alu.c_numide as dni, " +
            "            alu.t_nuss as nuss, " +
            "            NVL(datplan.lg_prl,0) AS lgPrl, " +
            "            NVL(datplan.lg_adaptaciones,0) AS lgAdaptaciones, " +
            "            NVL(datplan.lg_autorizaciones,0) AS lgAutorizaciones " +
            "from fct_proyectos proy, " +
            "              fct_conv_proy conv, " +
            "              fct_convproy_alu cona, " +
            "              tlmatalu mat, " +
            "              tlalumnos alu, " +
            "              fct_datosalu_plan datplan " +
            "where proy.id_proyecto = :idProyecto " +
            "and conv.id_proyecto = proy.id_proyecto " +
            "and cona.id_conv_proy =  conv.id_conv_proy " +
            "and mat.x_matricula = cona.x_matricula " +
            "and alu.x_alumno = mat.x_alumno " +
            "and datplan.x_matricula(+) = mat.x_matricula order by nombreCompleto", nativeQuery = true)
    List<ListadoAlumnosPlanProjection> findListadoAlumnosPlanByProyecto(Long idProyecto);


    @Query(value = "SELECT " +
            "               dp.ID_DATOSALU_PLAN AS idDatosAluPlan, " +
            "               dp.X_MATRICULA AS xMatricula, " +
            "               NVL(dp.LG_PRL, 0) AS lgPrl, " +
            "               NVL(dp.LG_CERTIFICACION, 0) AS lgCertificacion, " +
            "               dp.DS_CERTIFICACION AS dsCertificacion, " +
            "               NVL(dp.LG_ADAPTACIONES, 0) AS lgAdaptaciones, " +
            "               dp.DS_ADAPTACIONES AS dsAdaptaciones, " +
            "               NVL(dp.LG_AUTORIZACIONES, 0) AS lgAutorizaciones, " +
            "               dp.DS_AUTORIZACIONES AS dsAutorizaciones, " +
            "               dp.DS_OBSERVACIONES AS dsObservaciones, " +
            "               dp.C_USUCREACION AS cUsuCreacion, " +
            "               dp.F_CREACION AS fCreacion, " +
            "               dp.C_USUACTUALIZA AS cUsuActualiza, " +
            "               dp.F_ACTUALIZA AS fActualiza " +
            "            FROM FCT_DATOSALU_PLAN dp " +
            "               WHERE dp.X_MATRICULA = :xMatricula",
            nativeQuery = true)
    DatosAlumnoPlanProjection findAlumnoPlanByXMatricula(Long xMatricula);



}