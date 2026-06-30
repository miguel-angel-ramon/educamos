package es.jccm.edu.proyectosfct.application.domain.empresas.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Empleados", description = "Descripcion para el modelo de empleados de una empresa")
public interface EmpresaEmpleadosProjection {
	
	@Schema(description = "Id empleado")
	Long getId();
	
	@Schema(description = "Nombre empleado")
	String getNombre();
	
	@Schema(description = "Dni empleado")
	String getDni();
	
	@Schema(description = "Departamento empleado")
	String getDepartamento();
	
	@Schema(description = "Es representante")
	String getRepresentante();
	
	@Schema(description = "Es responsable")
	String getResponsable();
	

}
