package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado gastos anexo", description = "Descripcion para el modelo de Listado gastos anexo")
public interface ListadoGastoAnexoProjection {
	
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
	
	@Schema(description = "Curso")
	String getCurso();	
	
	@Schema(description = "Unidad")
	String getUnidad();	
	
	@Schema(description = "Fecha de la última generación anexo")
	String getFultgen();
	
	@Schema(description = "Id Periodo")
	Long getIdPeriodo();
	
	@Schema(description = "Id Curso")
	Long getIdCurso();
	
	@Schema(description = "Id Unidad")
	Long getIdUnidad();
	
	@Schema(description = "Puede firmar")
	Integer getPuedefirmar();
	
	@Schema(description = "Id Perfil")
	Integer getIdPerfil();	
	
	@Schema(description = "Puede generar")
	Integer getPuedegenerar();

	@Schema(description = "Fecha de actualización/creación del último gasto asignado")
	String getFhGastMax();
}

