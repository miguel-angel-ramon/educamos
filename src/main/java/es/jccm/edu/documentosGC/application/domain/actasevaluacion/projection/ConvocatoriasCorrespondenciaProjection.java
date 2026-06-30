package es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection;

import java.util.Date;

public interface ConvocatoriasCorrespondenciaProjection {	
	
	Long getId();
	
	String getDescripcion();
	
	Date getSesion();
	
	Date getFechainicio();
	
	Date getFechafin();
	
	Long getIdConvOmc();

}
