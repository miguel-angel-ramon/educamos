package es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection;

import java.util.Date;

public interface BandejaFirmasListProjection {	
	
	Long getId();

	String getPrincipal();	
	
	String getTipodocumento();	
	
	String getTipoadjunto();	
	
	String getNombre();
	
	Date getFechageneracion();
	
	Date getFechafirma();
	
	Integer getlgfirmado();
	
	String getEstado();
	
	String getIdrodal();
	
	String getFichero();
	
	Long getIdAdjunto();
	
	Integer getPermitefirmar();	

}




