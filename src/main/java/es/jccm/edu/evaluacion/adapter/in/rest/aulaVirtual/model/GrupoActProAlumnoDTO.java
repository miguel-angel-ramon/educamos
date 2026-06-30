package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import java.util.Date;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CentroDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.EmpleadoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GrupoActProAlumnoDTO", description = "DTO Grupo Act. Pro. Alumno")
public class GrupoActProAlumnoDTO {
	
	@Schema(description = "Id del Grupo Act. Pro. Alumno")
	private Long id;
	
	@Schema(description = "Descripción del Grupo Act. Pro. Alumno")
	private String descripcion;
	
	@Schema(description = "Descripción corta del Grupo Act. Pro. Alumno")
	private String descripcionCorta;
	
	@Schema(description = "Código del Grupo Act. Pro. Alumno")
	private String codigo;
	
	@Schema(description = "Fecha de toma de pos.")
    private Date tomaPos;
	
	@Schema(description = "Año")
	private Integer  anno;
	
	// Esta actividad es de TLACTIVIDADES (no de EVA_ACTIVIDAD)
	@Schema(description = "Id. de la actividad del Grupo Act. Pro. Alumno")
	private Long idActividad;

	// ---------- Relationships -----------

	@Schema(description = "Empleado del Grupo Act. Pro. Alumno")
	private EmpleadoDTO empleado;
	
	@Schema(description = "Centro del Grupo Act. Pro. Alumno")
	private CentroDTO centro;

	// Esta actividad es de TLACTIVIDADES
}