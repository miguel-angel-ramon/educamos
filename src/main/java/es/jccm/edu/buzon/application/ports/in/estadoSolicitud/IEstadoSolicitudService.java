package es.jccm.edu.buzon.application.ports.in.estadoSolicitud;

import java.util.List;


import es.jccm.edu.buzon.adapter.in.rest.buzon.model.EstadoSolicitudDTO;


public interface IEstadoSolicitudService {	
	List<EstadoSolicitudDTO> findAll();
	
}

