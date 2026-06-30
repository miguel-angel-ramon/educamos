package es.jccm.edu.documentosGC.application.domain.estadodoc.projection;

import java.util.Date;

public interface LineaEstadoProjection {
	
	Long getIdHistorial();
	
	Long getIdEstado();
	
	String getNombre();
	
	Date getFhregistro();
	
	String getLactual();
	
	Integer getNivel();
	
	Integer getLgfinal();
	
	Integer getLhistorico();
	
	String getAbrev();
	

}
