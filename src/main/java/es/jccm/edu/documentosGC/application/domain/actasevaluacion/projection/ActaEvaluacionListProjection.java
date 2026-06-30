package es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection;

import java.util.Date;

public interface ActaEvaluacionListProjection {
	
	Long getId();
	
	Long getIdConvocatoria();

	String getConvocatoria();
	
	String getTipo();
	
	Long getIdCurso();
	
	String getCurso();
	
	Long getIdOmc();
	
	Long getIdTipoexp();
	
	String getAbreviatura();
	
	Long getIdUnidad();
	
	Integer getNPeriodo();
	
	String getUnidad();
	
	Date getSesion();
	
	String getEstado();
	
	Long getIdConvUnidad();	
	
	String getIdRodal();
	
	String getFichero();
	
	String getProvincia();
	
	String getMunicipio();
	
	String getCentro(); 
	
	String getActa(); 
	
	Long getIdDocumento();
	
	Integer getPermiteadjuntos();
	
	Integer getPermitegenerar();
	
	Date getFfinconvomc();
	
	Date getFfinconvcen();
	
	Long getIdmateriac();	
	
	String getMateria();
	
	Long getIdConvOMC();	
	
	Long getIdAdjunto();

	Integer getPermiteFirmar();
	
	Integer getTotalAdjuntos();
	
	String getAviso2();
	
	String getAviso3();
	
}
