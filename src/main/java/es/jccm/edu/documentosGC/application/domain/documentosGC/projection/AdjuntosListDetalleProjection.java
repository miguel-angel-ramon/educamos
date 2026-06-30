package es.jccm.edu.documentosGC.application.domain.documentosGC.projection;

public interface AdjuntosListDetalleProjection {
	
	Long getIdAdjunto();	

	Long getIdTipoDoc();	
	
	Long getIdTipoAdjunto();
	
	Integer getOrden();	
	
	Integer getPrincipal();	
	
	String getNombre();
	
	String getDescripcion();
	
	Integer getFirmable();
	
	Integer getTamano();
	
	Integer getAnnodesde();
	
	Integer getAnnohasta();
	
	Long getIdHistorial();
	
	Integer getLgFirmado();
	
	String getIdDocHisRodal();
	
	String getTxDocHisFichero();
	
	String getFechaPantalla();
	
}








