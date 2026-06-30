package es.jccm.edu.documentosGC.application.domain.documentosGC.projection;

import java.util.Date;

public interface HistDocumentosGCListProjection {
	
	Long getId();
	
	Date getFecha();
	
	String getUsuario();
	
	String getEstado();
	
	String getIdRodal();
	
	String getDocumento();
	
	String getComentarios();
	
	Integer getLgfirmable();
	
	String getLgfirmado();
	
	Long getIdadjunto();

}
