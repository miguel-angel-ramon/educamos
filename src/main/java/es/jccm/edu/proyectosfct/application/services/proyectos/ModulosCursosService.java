package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ModulosCursoRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IModulosCursosService;

import javax.persistence.EntityNotFoundException;


@Service
public class ModulosCursosService implements IModulosCursosService {

	
	
	@Autowired
	private ModulosCursoRepository modulosCursoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ModulosCurso> getAllModulosCurso() {
		
		return (List<ModulosCurso>) modulosCursoRepository.findAll();
		
	}

	@Override
	public List<ModulosCurso> findAllModuloCursoByProyectoId(Long idProyecto) {
		
		return (List<ModulosCurso>) modulosCursoRepository.findAllModuloCursoByProyectoId(idProyecto);
	}

	@Override
	public ModulosCurso getById(Long id) {
		return modulosCursoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ModulosCurso con ID " + id + " no encontrado"));
	}
}
