package es.jccm.edu.proyectosfct.adapter.out.repositories.cursoacademico;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.cursoacademico.CursoAcademico;
import es.jccm.edu.proyectosfct.application.domain.cursoacademico.QCursoAcademico;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface CursoAcademicoRepository extends AbstractRepository<CursoAcademico, Long, QCursoAcademico> {

	List<CursoAcademico> findByEsVisible(String esVisible);	
	CursoAcademico findByCursoActual(String cursoActual);

}
