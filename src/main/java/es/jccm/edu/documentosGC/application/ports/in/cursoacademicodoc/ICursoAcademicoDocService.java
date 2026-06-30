package es.jccm.edu.documentosGC.application.ports.in.cursoacademicodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;

public interface ICursoAcademicoDocService {

	List<CursoAcademicoDoc> getAllCursosAcademicos();

	CursoAcademicoDoc getCursoActual();
	
	CursoAcademicoDoc findById(Long IdAnno);
	
}
