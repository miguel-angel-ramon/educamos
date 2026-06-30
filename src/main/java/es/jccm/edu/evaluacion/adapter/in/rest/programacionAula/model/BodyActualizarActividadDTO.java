package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "BodyActualizarActividadDTO", description = "")
public class BodyActualizarActividadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "actividades")
	private List<ActividadDTO> actividades;

	@Schema(description = "alumnos")
	private List<AlumnosPorMateriaDTO> alumnos;


}