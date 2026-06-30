package es.jccm.edu.documentosGC.application.services.cursoacademicodoc;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.cursoacademicodoc.CursoAcademicoDocRepository;
import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;
import es.jccm.edu.documentosGC.application.ports.in.cursoacademicodoc.ICursoAcademicoDocService;


@Service
public class CursoAcademicoDocService implements ICursoAcademicoDocService {
	@Autowired
	private CursoAcademicoDocRepository cursoAcademicoDocRepository;	
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CursoAcademicoDoc> getAllCursosAcademicos() {
		
		List<CursoAcademicoDoc> curso = cursoAcademicoDocRepository.findByEsVisible("S");
		
		return curso;
	}
	
	@Override
	public CursoAcademicoDoc getCursoActual() {
		
		CursoAcademicoDoc curso = cursoAcademicoDocRepository.findByCursoActual("S");
		
		return curso;
	}

	@Override
	public CursoAcademicoDoc findById(Long IdAnno) {
		
		Optional<CursoAcademicoDoc> curso =  cursoAcademicoDocRepository.findById(IdAnno) ;
		
		return curso.orElse(null);
		
	}

}
