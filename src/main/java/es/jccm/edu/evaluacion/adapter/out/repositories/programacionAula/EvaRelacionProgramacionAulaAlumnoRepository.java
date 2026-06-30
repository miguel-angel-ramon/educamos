package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaUnidadCentro;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionProgramacionAulaAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionProgramacionAulaAlumnoRepository extends AbstractRepository<EvaRelacionProgramacionAulaAlumno, Long, QEvaRelacionProgramacionAulaAlumno> {

	void deleteAllByProgramacionAula(Long idprogramacionAula);
	
	List<EvaRelacionProgramacionAulaAlumno> findAllByProgramacionAulaAndUnidadCentro(Long programacionAula, Long unidadCentro);
    
}