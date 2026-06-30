package es.jccm.edu.ausencias.adapter.in.rest.guardias.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosProfesoresGuardias", description = "Datos de los profesores de guardia de un tramo horario")
public class DatosProfesoresGuardiasDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id Usuario")
	private Long idUsuario;
	
	@Schema(description = "oId Usuario")
	private String oIdUsuario;
	
	@Schema(description = "Nombre y apellidos del usuario")
	private String nombre;
	
	@Schema(description = "Teléfono")
	private String telefono;
	
	@Schema(description = "Correo electrónico")
	private String correo;

}
