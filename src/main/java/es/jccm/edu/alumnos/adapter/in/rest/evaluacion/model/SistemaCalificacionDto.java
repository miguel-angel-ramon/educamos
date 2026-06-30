package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.alumnos.application.domain.evaluacion.ListCalificaciones;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Sistema Calificacion", description = "Sistema de calificación de un grupo actividad")
public class SistemaCalificacionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id GrupoActividad")
	private Long idGrupoActividad;
	
	@Schema(description = "Número de sistemas de calificaciones distintos del grupo de actividad")
	private Integer numSisCal;
	
	@Schema(description = "Lista de calificaciones del sistema")
	private List<ListCalificaciones> listaCalificaciones;
}
