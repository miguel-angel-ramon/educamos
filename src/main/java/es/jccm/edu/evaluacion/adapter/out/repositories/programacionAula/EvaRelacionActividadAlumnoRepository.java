package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionActividadAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionActividadAlumnoRepository extends AbstractRepository<EvaRelacionActividadAlumno, Long, QEvaRelacionActividadAlumno> {

    List<EvaRelacionActividadAlumno> findAllByActividadId(Long idActividad);

    EvaRelacionActividadAlumno findByActividadIdAndMatriculaId(Long idActividad, Long idMatricula);
    
    void deleteAllByActividadId(Long idActividad);

}