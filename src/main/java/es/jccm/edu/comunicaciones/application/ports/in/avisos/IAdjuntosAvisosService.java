package es.jccm.edu.comunicaciones.application.ports.in.avisos;

import java.util.List;

import es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model.AvisoAdjuntosDto;


public interface IAdjuntosAvisosService {
	
	List<AvisoAdjuntosDto> getListaFicherosAdjuntoAviso(String idAviso);
	
	byte[] getFicheroAdjuntoAviso(String idAdjunto);
	
	byte[] getFicherosAdjuntosZIPAviso(List<String> idsAdjuntos);

}
