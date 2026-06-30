package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Historial de Responsables de Programación Didáctica", description = "Proyección para rescatar el historial de los responsables de una programación didáctica")
public interface HistorialResponsableProgramacionDidacticaProjection {

	@Schema(description = "Identificador del empleado responsable de la programación didáctica")
	public Long getIdEmpleado();
	
	@Schema(description = "Nombre y apellidos del empleado")
	public String getNombreEmpleado();
	
	@Schema(description = "Identificador del departamento")
	public Long getIdDepartamento();
	
	@Schema(description = "Nombre del departamento")
	public String getNombreDepartamento();
	
	@Schema(description = "Fecha de inicio de la responsabilidad del empleado en la programación didáctica")
	public Date getFechaInicio();
	
	@Schema(description = "Fecha de cese de la responsabilidad del empleado en la programación didáctica")
	public Date getFechaFin();
	
	@Schema(description = "Está activa la responsabilidad del empleado en la programación didáctica")
	public Boolean getActivo();
	
}
