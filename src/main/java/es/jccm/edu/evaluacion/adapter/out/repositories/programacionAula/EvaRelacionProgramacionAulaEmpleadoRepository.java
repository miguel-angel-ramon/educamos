package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaEmpleado;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionProgramacionAulaEmpleado;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionProgramacionAulaEmpleadoRepository extends AbstractRepository<EvaRelacionProgramacionAulaEmpleado, Long, QEvaRelacionProgramacionAulaEmpleado> {

	void deleteAllByProgramacionAula(EvaProgramacionAula programacionAula);
    
}