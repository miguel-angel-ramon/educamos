package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionCompetenciaEspecificaMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionCompetenciaEspecificaMateria;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionCompetenciaEspecificaMateriaRepository extends AbstractRepository<EvaRelacionCompetenciaEspecificaMateria, Long, QEvaRelacionCompetenciaEspecificaMateria> {

	List<EvaRelacionCompetenciaEspecificaMateria> findAllByIdMateriaOmg(Long idMateriaOmg);
	
}