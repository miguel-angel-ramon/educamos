package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.TiposProyectosRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.TipoProyecto;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.ITiposProyectosService;


@Service
public class TiposProyectosService implements ITiposProyectosService {

	@Autowired
	private TiposProyectosRepository tiposProyectosRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<TipoProyecto> getAllTiposProyectos() {
		
		return (List<TipoProyecto>) tiposProyectosRepository.findAll();
	}
	
	

}
