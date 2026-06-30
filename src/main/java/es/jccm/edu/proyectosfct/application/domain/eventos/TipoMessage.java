package es.jccm.edu.proyectosfct.application.domain.eventos;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class TipoMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String uuidRequest;
	
	private  List<EventoDocumentacion> documentaciones;
	
	Boolean loginGenerado = false; 
	
	Boolean claveGenerada = false;
	
	String login = "";
	
	String claveSinCifrar;
	
	Boolean nuevoUsuario = false;
	
	String oid = "";
	
	Integer usuarioPeticionario = 0;	

}



