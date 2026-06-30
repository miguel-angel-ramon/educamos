package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidad curso", description = "Descripcion para el modelo de unidad de un curso")
public class UnidadCursoDto {

	@Schema(description = "Id de la unidad")
	private Long id;
	
	@Schema(description = "Id oferta matricula")
	private Long idOfertamatric;
	
	@Schema(description = "Nombre de la unidad")
	private String nombreUnidad;
	
	@Schema(description = "Tipo de unidad")
	private String tipoUnidad;

	@Schema(description = "Capacidad de la unidad")
	private Integer capacidad;
	
	@Schema(description = "Numero de alumnos asignados")
	private Integer alumnosAsignados;
	
	@Schema(description = "Nombre del tutor")
	private String nombreTutor;
	
	@Schema(description = "Fecha del cese del tutor")
	private Date fechaCese;
	
	@Schema(description = "Actuacion")
	private String actuacion;
	
	@Schema(description = "Id de la matricula del delegado")
	private Long idMatriculaDelegado;
	
	@Schema(description = "Id de la matricula del subdelegado")
	private Long idMatriculaSubDelegado;
	
	@Schema(description = "Turno")
	private String turno;
	
}
