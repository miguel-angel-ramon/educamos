package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QParsemAluProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ParsemAluProyRepository extends AbstractRepository<ParsemAluProy, Long, QParsemAluProy> {
	

	Optional<ParsemAluProy> findByAluConvProyIdAndFechaInicioSemana(Long idConvProyAlu,Date fIni);

	Optional<List<ParsemAluProy>> findByAluConvProyId(Long idconvProyAlu);
}
