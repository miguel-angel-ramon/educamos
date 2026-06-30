package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaEmpleado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProgramacionAulaDTO", description = "DTO Programación Aula")
public class ProgramacionAulaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Programación Aula")
	private Long id;

	@Schema(description = "Nombre de la Programación Aula")
	private String nombre;

	@Schema(description = "Indica si tiene actividades asociadas")
	private boolean tieneActividades;

	@Schema(description = "Descripción del curso de nivel de adaptación cucrricular")
	private String nivelCurricular;

	@Schema(description = "Id empleado")
	private Long idEmpleado;
	
	@Schema(description = "Nombre y apellidos empleado")
	private String nombreEmpleado;

	@Schema(description = "Nombre del empleado")
	private String nombreDePila;

	@Schema(description = "apellido 1 del empleado")
	private String apellido1;

	@Schema(description = "apellido 2 del empleado")
	private String apellido2;



	// ---------- Relationships -----------
	
	@Schema(description = "Programación Didáctica")
	private ProgramacionDidacticaDTO programacionDidactica;
	
	@Schema(description = "Aula Virtual")
	private AulaVirtualDTO aulaVirtual;

	@Schema(description = "Moodle")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "es-ES", timezone = "Europe/Madrid")
	private Date actualizaMoodle;
	
	@Schema(description = "Lista de Alumnos Seleccionados")
	private List<AlumnosPorMateriaDTO> alumnosSeleccionados;
	
	@Schema(description = "Lista de relaciones Programación aula - Actividades")
	private List<RelacionProgramacionAulaActividadDTO> relacionesProgramacionAulaActividad;
	
	@Schema(description = "Lista de relaciones Programación aula - Alumno")
	private List<RelacionProgramacionAulaAlumnoDTO> relacionesProgramacionAulaAlumno;
}