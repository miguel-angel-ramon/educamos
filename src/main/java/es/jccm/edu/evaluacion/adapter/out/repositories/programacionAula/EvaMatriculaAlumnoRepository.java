package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaMatriculaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaMatriculaAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMatriculaAlumnoRepository extends AbstractRepository<EvaMatriculaAlumno, Long, QEvaMatriculaAlumno> {
    
}