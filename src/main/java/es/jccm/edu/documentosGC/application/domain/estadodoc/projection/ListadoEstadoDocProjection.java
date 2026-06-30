package es.jccm.edu.documentosGC.application.domain.estadodoc.projection;

import java.util.Date;

public interface ListadoEstadoDocProjection {
	
	Long getId();
	
	String getAbreviatura();
	
	String getNombre();
	
	Date getFinicio();
	
	Date getFfin();
	
	String getEsFinal();	
	
	Integer getNoborrable();

}
