package es.jccm.edu.documentosGC.application.domain.firmantesdigital.projection;

import java.util.Date;

public interface DetalleFirmanteProjection {	
	
	Long getId();	

	String getTipo();
	
	String getDocumento();
	
	String getEstado();
	
	String getUsuario();
	
	Integer getOrden();
	
	Date getFecha();
	
	Integer getFirmado();

}


