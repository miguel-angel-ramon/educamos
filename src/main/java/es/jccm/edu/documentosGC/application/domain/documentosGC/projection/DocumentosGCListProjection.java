package es.jccm.edu.documentosGC.application.domain.documentosGC.projection;

import java.util.Date;

public interface DocumentosGCListProjection {
	
	Long getId();	
	
	String getTipo();
	
	String getEstado();
	
	String getDescripcion();
	
	Date getFhregistro();
	
	String getIdRodal();
	
	String getFichero();	
	
	String getProvincia();
	
	String getMunicipio();
	
	String getCentro();
	
	String getDsparaus();
	
	String getAviso();
	
	Integer getPermiteAdjuntos();
	
	Long getIdAdjunto();
	
	Integer getPermiteFirmar();
	
	Integer getTotalAdjuntos();
	
	String getAviso2();
	
	String getAviso3();
	
}
