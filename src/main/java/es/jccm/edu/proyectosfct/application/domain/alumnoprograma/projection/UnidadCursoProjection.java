package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidad Curso", description = "Descripcion para el modelo de Unidad Curso")
public interface UnidadCursoProjection {
	
	@Schema(description = "Id de la unidad)")
	Long getId();
	
	@Schema(description = "Id oferta matricula")
	Long getIdOfertamatric();
	
	@Schema(description = "Nombre de la unidad")
	String getNombre();
	
	@Schema(description = "Tipo de unidad")
	String getTipo();
	
	@Schema(description = "Capacidad de la unidad")
	Integer getCapacidad();
	
	@Schema(description = "Numero de alumnos asignados")
	Integer getAlumnosAsignados();
	
	@Schema(description = "Nombre del tutor")
	String getNombreTutor();
	
	@Schema(description = "Fecha del cese del tutor")
	Date getFechaCese();
	
	@Schema(description = "Actuacion")
	String getActuacion();
	
	@Schema(description = "Id de la matricula del delegado")
	Long getIdMatriculaDelegado();
	
	@Schema(description = "Id de la matricula del Subdelegado")
	Long getIdMatriculaSubDelegado();
	
	@Schema(description = "Turno")
	String getTurno();	
}

