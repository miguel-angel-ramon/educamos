package es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado anexo", description = "Descripcion para el modelo de Listado gastos anexo")
public interface ListadoAutorizacionesAnexosProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	
	@Schema(description = "Anno periodo")
	Integer getAnnoPeriodo();
	
	@Schema(description = "Fecha inicio periodo")
	Date getFInicio();
	
	@Schema(description = "Fecha fin periodo")
	Date getFFin();
	
	@Schema(description = "Nombre tipo")
	String getNombreTipo();	
	
	@Schema(description = "Descripcion del estado del flujo")
	String getEstado();	
	
	@Schema(description = "Id tutor")
	Long getIdTutor();
	
	@Schema(description = "Nombre tutor")
	String getTutor();	
	
	@Schema(description = "Id rodal")
	String getIdRodal();	
	
	@Schema(description = "Nombre fichero rodal")
	String getFichero();	
	
	@Schema(description = "idHistorial Rodal")
	Long getIdHistorialRodal();
	
	@Schema(description = "idHistorial")
	Long getIdHistorial();
	
	@Schema(description = "Periodo")
	String getPeriodo();
	
	@Schema(description = "Familia")
	String getFamilia();	
	
	@Schema(description = "Curso")
	String getCurso();	
	
	@Schema(description = "Unidad")
	String getUnidad();	
	
	@Schema(description = "Id Periodo")
	Long getIdPeriodo();
	
	@Schema(description = "Id Familia")
	Long getIdFamilia();
	
	@Schema(description = "Id Curso")
	Long getIdCurso();
	
	@Schema(description = "Id Unidad")
	Long getIdUnidad();
	
	@Schema(description = "Puede firmar")
	Integer getPuedefirmar();
	
	@Schema(description = "Id Perfil")
	Long getIdPerfil();
	
	@Schema(description = "Puede generar")
	Integer getPuedegenerar();
	
	@Schema(description = "Puede editar")
	Integer getPuedeeditar();
	
	@Schema(description = "idHistorial Adjunto")
	Long getIdHistorialAdjunto();
	
	@Schema(description = "Nombre adjunto rodal")
	String getAdjunto();	
	
	@Schema(description = "Fecha de ultima generación del anexo")
	String getFultgen();

	@Schema(description = "Fecha de actualización de la última autorización asignada")
	String getFhAutMax();

	@Schema(description = "Número de petición")
	Integer getNuPeticion();
	
	@Schema(description = "Puede editar")
	Integer getPuedecrear();

	@Schema(description = "Anexo visitado")
	Integer getComunicacion();
}

