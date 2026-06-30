package es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresa empleados", description = "Descripcion para el modelo de empleados de una empresa")
public class EmpresaEmpleadosListDto {
	
	@Schema(description = "Identificador")
	private Long id;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "DNI")
	private String dni;
	
	@Schema(description = "Departamento")
	private String departamento;

	@Schema(description = "Representante")
	private String representante;

	@Schema(description = "Responsable")
	private String responsable;
	
	

}
