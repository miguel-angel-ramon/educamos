package es.jccm.edu.comunicaciones.application.ports.in.mensajes;

import java.util.List;

public interface IAdjuntosMensajesService {
	
	byte[] getFicheroAdjuntoMensaje(String idAdjunto);
	
	byte[] getFicherosAdjuntosZIPMensaje(List<String> idsAdjuntos);

}
