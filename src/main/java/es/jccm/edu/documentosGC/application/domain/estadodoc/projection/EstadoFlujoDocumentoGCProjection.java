package es.jccm.edu.documentosGC.application.domain.estadodoc.projection;

import java.util.Date;

public interface EstadoFlujoDocumentoGCProjection {	
	
	Long getIdFlujo();

    String getDsAbrev();
	
    String getDsNombre();
    
    Date getFhInicio();
    
    Date getFhFin();
    
    Integer getLgFinal();
    
    String getTxAviso();
    
    Integer getAdjunto();
    

}






