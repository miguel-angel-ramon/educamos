package es.jccm.edu.proyectosfct.application.services.sedeempresa;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.sedeempresa.SedeEmpresaRepository;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import es.jccm.edu.proyectosfct.application.ports.in.sedeempresa.ISedeEmpresaService;

@Service
public class SedeEmpresaService implements ISedeEmpresaService {
	
	@Autowired
	private SedeEmpresaRepository sedeEmpresaRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<SedeEmpresa> getAllSedesEmpresaById(Long idEmpresa) {
		
		List<SedeEmpresa> sedes = sedeEmpresaRepository.findByEmpresaId(idEmpresa);
		
		return sedes;
	}

	@Override
	public SedeEmpresa getSedeByIdConvenio(Long idConvenio) {
		return sedeEmpresaRepository.findByIdConvenio(idConvenio);
	}

	@Override
	public SedeEmpresa getSedeById(Long idSede) {
		return sedeEmpresaRepository.findByIdSede(idSede);
	}

}
