package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Docente", description = "Descripcion para el modelo de docentes de un centro")
public interface DocenteProjection {
	
	@Schema(description = "Id del docente (empleado)")
	Long getIdEmpleado();
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Europe/Madrid")
	@Schema(description = "Fecha de la toma de posesion")
	Date getIdFechaTomaPosesion();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "Primer Apellido")
	String getApellido1();
	
	@Schema(description = "Segundo Apellido")
	String getApellido2();
	
	@Schema(description = "Dni del docente")
	String getDniEmpleado();
	
	@Schema(description = "Nombre Sustituto")
	String getSustituto();
	
}
