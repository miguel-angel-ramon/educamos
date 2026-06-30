package es.jccm.edu.documentosGC.application.domain.documentosGC.projection;


public interface TipoAdjuntosListProjection {
	
	Long getId();	

	Long getIdTipo();	
	
	Integer getOrden();	
	
	Integer getPrincipal();	
	
	String getNombre();
	
	String getDescripcion();
	
	Integer getFirmable();
	
	Integer getTamano();
	
	Integer getAnnodesde();
	
	Integer getAnnohasta();

}
