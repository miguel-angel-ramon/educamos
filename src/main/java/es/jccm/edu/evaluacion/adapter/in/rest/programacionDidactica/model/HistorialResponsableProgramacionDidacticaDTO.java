package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Historial de Responsables de Programación Didáctica", description = "Proyección para rescatar el historial de los responsables de una programación didáctica")
public class HistorialResponsableProgramacionDidacticaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del empleado responsable de la programación didáctica")
	private Long idEmpleado;
	
	@Schema(description = "Nombre y apellidos del empleado")
	private String nombreEmpleado;
	
	@Schema(description = "Identificador del departamento")
	private Long idDepartamento;
	
	@Schema(description = "Nombre del departamento")
	private String nombreDepartamento;
	
	@Schema(description = "Fecha de inicio de la responsabilidad del empleado en la programación didáctica")
	private Date fechaInicio;
	
	@Schema(description = "Fecha de cese de la responsabilidad del empleado en la programación didáctica")
	private Date fechaFin;
	
	@Schema(description = "Está activa la responsabilidad del empleado en la programación didáctica")
	private Boolean activo;

}
