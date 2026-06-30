package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.TipoOperacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaTipoOperacionRepository;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaTipoOperacion;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaTipoOperacionService;

@Service
public class EvaTipoOperacionService implements IEvaTipoOperacionService {

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private EvaTipoOperacionRepository tipoOperacionRepository;
	
	@Override
	public List<TipoOperacionDTO> getTiposOperacion() {
		List<EvaTipoOperacion> tiposOperacion = (List<EvaTipoOperacion>) tipoOperacionRepository.findAll();
		return tiposOperacion.stream().map(x -> modelMapper.map(x, TipoOperacionDTO.class)).collect(Collectors.toList());
	}

}