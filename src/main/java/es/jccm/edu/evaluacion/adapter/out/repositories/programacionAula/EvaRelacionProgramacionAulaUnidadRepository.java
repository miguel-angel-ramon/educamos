package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaUnidad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionProgramacionAulaUnidad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionProgramacionAulaUnidadRepository extends AbstractRepository<EvaRelacionProgramacionAulaUnidad, Long, QEvaRelacionProgramacionAulaUnidad> {

	void deleteAllByProgramacionAula(Long idprogramacionAula);
    
}