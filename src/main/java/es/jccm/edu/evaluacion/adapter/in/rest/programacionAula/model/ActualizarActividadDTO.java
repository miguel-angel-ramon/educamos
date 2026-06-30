package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Schema(name = "ActualizarActividadDTO", description = "DTO que indica que se ha actualizado en una actividad de moodle")
public class ActualizarActividadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la actividad")
	private Long id;

	@Schema(description = "Id de la actividad en moodle")
	private Long idActividadMoodle;

	@Schema(description = "Nombre de la actividad")
	private String nombre;

	@Schema(description = "Indica si el título se ha actualizado")
	private boolean checkTitulo = false;

	@Schema(description = "Indica si la descripción se ha actualizado")
	private boolean checkDescripcion = false;

	@Schema(description = "Indica si la fecha de inicio se ha actualizado")
	private boolean checkFechaInicio = false;

	@Schema(description = "Indica si la fecha de fin se ha actualizado")
	private boolean checkFechaFin = false;

	@Schema(description = "Indica si los criterios se han actualizado")
	private boolean checkCriterios = false;

	@Schema(description = "Indica si los alumnos se han actualizado")
	private boolean checkAlumnos = false;

	@Schema(description = "Indica si las calificaciones se han actualizado")
	private boolean checkCalificaciones = false;
}