package es.jccm.edu.buzon.application.services.estadoSolicitud;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.EstadoSolicitudDTO;
import es.jccm.edu.buzon.adapter.out.repository.estadoSolicitud.EstadoSolicitudRepository;
import es.jccm.edu.buzon.application.domain.estadoSolicitud.EstadoSolicitud;
import es.jccm.edu.buzon.application.ports.in.estadoSolicitud.IEstadoSolicitudService;

@Service
public class EstadoSolicitudService implements IEstadoSolicitudService {
	
	@Autowired
	ModelMapper modelMapper;
	
	//@Autowired
	EstadoSolicitudRepository estadoSolicitudRepository;
	
	  public EstadoSolicitudService(EstadoSolicitudRepository  estadoSolicitudRepository) {
	        this.estadoSolicitudRepository = estadoSolicitudRepository;
	    } 	  	  
	  
	  @Override
		public List<EstadoSolicitudDTO> findAll() {
			List<EstadoSolicitud> solicitudes = new ArrayList<>();
			solicitudes=estadoSolicitudRepository.findAll();
			return solicitudes.stream().map(x -> modelMapper.map(x, EstadoSolicitudDTO.class)).collect(Collectors.toList());
		}

}

