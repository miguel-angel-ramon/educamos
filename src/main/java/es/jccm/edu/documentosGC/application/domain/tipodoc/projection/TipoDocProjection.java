package es.jccm.edu.documentosGC.application.domain.tipodoc.projection;

public interface TipoDocProjection {
	
	Long getId();
	
	Integer getOrden();
	
	String getAbrev();
	
	String getDescripcion();
	
	String getAnual();
	
	Integer getAnnodesde();
	
	Integer getAnnohasta();
	
	String getLgobligatorio();
	
	Integer getNoborrable();
	
	String getNombrepadre();
	

}
