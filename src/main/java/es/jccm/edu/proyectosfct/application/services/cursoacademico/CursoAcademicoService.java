package es.jccm.edu.proyectosfct.application.services.cursoacademico;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.cursoacademico.CursoAcademicoRepository;
import es.jccm.edu.proyectosfct.application.domain.cursoacademico.CursoAcademico;
import es.jccm.edu.proyectosfct.application.ports.in.cursoacademico.ICursoAcademicoService;



@Service
public class CursoAcademicoService implements ICursoAcademicoService  {
	
	@Autowired
	private CursoAcademicoRepository cursoAcademicoRepository;	
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CursoAcademico> getAllCursosAcademicos() {
		
		List<CursoAcademico> curso = cursoAcademicoRepository.findByEsVisible("S");
		
		return curso;
	}
	
	@Override
	public CursoAcademico getCursoActual() {
		
		CursoAcademico curso = cursoAcademicoRepository.findByCursoActual("S");
		
		return curso;
	}
	
	@Override
	public CursoAcademico getCursoActualAnno(Long cAnno) {
		
		Optional<CursoAcademico> curso = cursoAcademicoRepository.findById(cAnno);
		
		return curso.orElse(null);
	}
}
