package es.jccm.edu.gestionidentidades.adapter.out.repository;

import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.QResultadoInterfaz;
import es.jccm.edu.gestionidentidades.application.domain.ResultadoInterfaz;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ResultadoInterfazRepository extends AbstractRepository<ResultadoInterfaz, Integer, QResultadoInterfaz>{
	
}
