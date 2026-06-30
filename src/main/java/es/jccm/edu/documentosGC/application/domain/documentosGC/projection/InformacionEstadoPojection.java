package es.jccm.edu.documentosGC.application.domain.documentosGC.projection;

import java.util.Date;

public interface InformacionEstadoPojection {

	String getUsuario();
	
	String getEstado();
	
	Date getFhregistro();
	
	String getIdFichero();
	
	String getNombreFichero();
	
	String getObservaciones();
	
	String getAviso();
	
}
