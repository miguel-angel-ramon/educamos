package es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.DatosEmpresaTrabajador;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresa trabajador", description = "Descripcion para el modelo de Trabajadores de una empresa")
public class EmpresaTrabajadorDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del trabajador de la empresa")
	private Long id;

//	@Schema(description = "CIF/NIF del trabajador de la empresa")
//	private String numeroDocumento;
//
//	@Schema(description = "Tipo de documento")
//	private String tipoDocumento;

	@Schema(description = "Es representante de la empresa")
	private Boolean esRepresentante;

//	@Schema(description = "Primero apellido")
//	private String apellido1;
//
//	@Schema(description = "Segundo apellido")
//	private String apellido2;
//
//	@Schema(description = "Nombre")
//	private String nombre;

		@Schema(description = "departamento")
		private String departamento;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Id de la Empresa para la que trabaja")
	private Long empresaId;
	
	@Schema(description = "Datos del trabajador")
	private DatosEmpresaTrabajador datosEmpresaTrabajador;


}
