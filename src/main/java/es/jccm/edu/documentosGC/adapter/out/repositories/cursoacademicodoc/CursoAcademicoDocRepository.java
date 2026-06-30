package es.jccm.edu.documentosGC.adapter.out.repositories.cursoacademicodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.QCursoAcademicoDoc;
import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface CursoAcademicoDocRepository extends AbstractRepository<CursoAcademicoDoc, Long, QCursoAcademicoDoc> {

	List<CursoAcademicoDoc> findByEsVisible(String esVisible);	
	CursoAcademicoDoc findByCursoActual(String cursoActual);
}
