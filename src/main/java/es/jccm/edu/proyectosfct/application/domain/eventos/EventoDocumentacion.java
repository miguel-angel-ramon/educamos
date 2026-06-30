package es.jccm.edu.proyectosfct.application.domain.eventos;

import java.io.Serializable;

import lombok.Data;

@Data
public class EventoDocumentacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String documentacion;
	
	String tipoDocumento;
}
