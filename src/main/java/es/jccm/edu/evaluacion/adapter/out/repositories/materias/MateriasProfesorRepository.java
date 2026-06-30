package es.jccm.edu.evaluacion.adapter.out.repositories.materias;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.projection.AlumnoMateriasUnidadProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.materiasProfesor.MateriasProfesor;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.QMateriasProfesor;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.projection.MateriasProfesorProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MateriasProfesorRepository extends AbstractRepository<MateriasProfesor, Long, QMateriasProfesor> {

    @Query(value = "SELECT DISTINCT matCurso.X_MATERIAC idMateria, matCurso.D_MATERIAC descripcion, matcurso.T_ABREV abreviatura "
            + "FROM TLUNIAFEGRUACTPRO grupo, TLGRUACTPROALU grupoActividad, TLMATOFEMATRG mat, TLMATERIASCURSO matCurso "
            + "WHERE grupo.X_GRUACTPROALU = grupoActividad.X_GRUACTPROALU "
            + "AND grupoActividad.X_EMPLEADO = :idEmpleado "
            + "AND grupoActividad.C_ANNO = :anno "
            + "AND grupo.X_MATERIAOMG = mat.X_MATERIAOMG "
            + "AND mat.X_MATERIAC = matCurso.X_MATERIAC "
            + "ORDER BY descripcion", nativeQuery = true)
    List<MateriasProfesorProjection> findMateriasProfesor(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);

    @Query(value = "Select gap.x_gruactproalu, gap.x_centro, gap.c_anno, gap.x_empleado, gap.f_tomapos, "
            + "       mcu.x_materiac idMateria, mcu.d_materiac descripcionMateria, mcu.s_materiac nombreMateria, "
            + "       mcu.t_abrev materiaAbreviatura, udc.x_unidad idUnidad, udc.t_nombre nombreCurso, mua.x_matricula, mma.x_matmatricula, "
            + "       alu.x_alumno idAlumno, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos "
            + "  from tlgruactproalu gap "
            + "       inner join tlmatalu mua on gap.c_anno = mua.c_anno and gap.x_centro = mua.x_centro and mua.x_unidad = :idUnidad and nvl(mua.c_resultado, 99) > 1 and tlf_valida_grupo_matricula(gap.x_gruactproalu, mua.x_matricula) = 1 "
            + "       inner join tlmatmatrialu mma on mma.x_matricula = mua.x_matricula "
            + "       inner join tlmatofematrg mog on mma.x_materiaomg = mog.x_materiaomg and mog.x_materiac = :idMateria "
            + "       inner join tlmateriascurso mcu on mcu.x_materiac = mog.x_materiac "
            + "       inner join tlalumnos alu on alu.x_alumno = mua.x_alumno "
            + "       inner join tlunidadescen udc on mua.x_unidad = udc.x_unidad "
            + " where gap.x_gruactproalu = :idGrupoActividad ", nativeQuery = true)
    List<AlumnoMateriasUnidadProjection> findAlunosMaterias(@Param("idMateria") Long idMateria, @Param("idUnidad") Long idUnidad, @Param("idGrupoActividad") Long idGrupoActividad);
}
