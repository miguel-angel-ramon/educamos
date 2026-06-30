package es.jccm.edu.proyectosfct.application.services.convenios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.EmpresaTrabajadorRepository;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.DatosEmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IEmpresaTrabajadorService;

@Service
public class EmpresaTrabajadorService implements IEmpresaTrabajadorService {
	
	@Autowired
	private EmpresaTrabajadorRepository empresaTrabajadorRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	// Read

	public List<EmpresaTrabajador> getTrabajadoresByEmpresa(Long idEmpresa) {
		return empresaTrabajadorRepository.findAllByEmpresaIdAndEsRepresentante(idEmpresa, true);
	}

	public EmpresaTrabajador getTrabajadorById(Long idTrabajador) {
		Optional<EmpresaTrabajador> res = empresaTrabajadorRepository.findById(idTrabajador);
		
		return  res.isPresent() ? res.get() : null;
	}

	
	public List<EmpresaTrabajador> getRepresentanteEmpresabySede(Long idSede) {		
		return empresaTrabajadorRepository.findAllBySedeIdAndEsRepresentante(idSede, true);
	}

	@Override
	public List<EmpresaTrabajador> getResponsablesByEmpresa(Long idEmpresa) {
		return empresaTrabajadorRepository.findAllByEmpresaIdAndEsResponsable(idEmpresa, true);
	}

	@Override
	public List<EmpresaTrabajador> getResponsablesBySede(Long idSede) {
		return empresaTrabajadorRepository.findAllBySedeIdAndEsResponsable(idSede, true);
	}

	

}
