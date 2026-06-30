package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QParsemAluProg;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ParsemAluProgRepository extends AbstractRepository<ParsemAluProg, Long, QParsemAluProg> {
	

	Optional<ParsemAluProg> findByAluConvProgIdAndFechaInicioSemana(Long idConvProgAlu, Date fIni);

	Optional<List<ParsemAluProg>> findByAluConvProgId(Long idconvProgAlu);
}
