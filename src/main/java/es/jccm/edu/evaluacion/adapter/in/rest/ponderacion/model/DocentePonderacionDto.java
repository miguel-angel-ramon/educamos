package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Docente ponderación", description = "Listado de docentes mismo centro y misma materia")
public class DocentePonderacionDto implements Serializable	{
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del empleado")
	Long idEmpleado;

	@Schema(description = "Nombre del docente")
	String nombre;

	@Schema(description = "Apellidos del docente")
	String apellidos;
}
