package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Docente sustituto", description = "Rescata los docentes sustitutos")
public class DocenteSustitutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del empleado de Delphos")
	private Long idEmpleadoDelphos;
	
	@Schema(description = "Id del empleado de Comunica")
	private Long idEmpleadoComunica;
	
	@Schema(description = "Fecha de la toma de posesion")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaTomaPosesion;
	
	@Schema(description = "Fecha cese")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaCese;
	
	@Schema(description = "Id del centro")
	private Long idCentro;
	
	@Schema(description = "Año académico")
	private Integer anno;
	
	@Schema(description = "oid usuario")
	private Long oidUsuario;

}
