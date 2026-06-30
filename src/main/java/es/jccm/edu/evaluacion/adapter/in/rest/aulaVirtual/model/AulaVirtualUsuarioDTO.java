package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CentroDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.EmpleadoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AulaVirtualUsuarioDTO", description = "DTO Aula Virtual Usuario")
public class AulaVirtualUsuarioDTO {
	
	@Schema(description = "Id del Aula Virtual Usuario")
	private Long id;
	
	private String inversa;
	
	@Schema(description = "Descripción del Aula Virtual Usuario")
	private String descripcion;
	
	@Schema(description = "Id. del tipo del Aula Virtual Usuario")
	private Long idTipo;
	
	@Schema(description = "Rol del Aula Virtual Usuario")
	private Long idRol;
	
	// ---------- Relationships -----------

	@Schema(description = "Aula Virtual del Aula Virtual Usuario ")
	private AulaVirtualDTO aulaVirtual;
	
	@Schema(description = "Empleado del Aula Virtual Usuario")
	private EmpleadoDTO empleado;
	
	@Schema(description = "Alumno del Aula Virtual Usuario")
	private AlumnoDTO alumno;
	
	@Schema(description = "Grupo Act. Pro. Alumno del Aula Virtual Usuario")
	private GrupoActProAlumnoDTO grupoActProAlumno;
	
	@Schema(description = "Unidad centro del Aula Virtual Usuario")
	private CentroDTO unidadCentro;
}