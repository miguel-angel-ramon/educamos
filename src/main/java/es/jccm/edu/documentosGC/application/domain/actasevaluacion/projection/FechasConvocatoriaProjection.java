package es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection;

import java.util.Date;

public interface FechasConvocatoriaProjection {
	
	Long getId();
	
	Date getInicio();

	Date getFin();
	
	String getExtraordinaria();

}
