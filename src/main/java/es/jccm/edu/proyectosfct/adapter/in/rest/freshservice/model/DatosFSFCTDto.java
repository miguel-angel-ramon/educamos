package es.jccm.edu.proyectosfct.adapter.in.rest.freshservice.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Datos FS FCT", description = "Modelo para datos devueltos de FreshService")
public class DatosFSFCTDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Descripcion DNI usuario")
	private String nif;	
	
	@Schema(description = "Descripcion correo módulo de acceso")
	private String correo;	
	
	@Schema(description = "Descripcion telefono usuario")
	private String telefono;	

}
