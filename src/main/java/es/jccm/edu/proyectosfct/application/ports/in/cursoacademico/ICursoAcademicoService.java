package es.jccm.edu.proyectosfct.application.ports.in.cursoacademico;

import java.util.List;
import es.jccm.edu.proyectosfct.application.domain.cursoacademico.CursoAcademico;

public interface ICursoAcademicoService {

	List<CursoAcademico> getAllCursosAcademicos();

	CursoAcademico getCursoActual();

	CursoAcademico getCursoActualAnno(Long cAnno);
	
}
