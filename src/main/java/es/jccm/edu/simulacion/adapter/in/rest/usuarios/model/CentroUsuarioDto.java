package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CentroUsuario", description = "Datos rescatados de los centros de un usuario para el módulo de simulación de usuarios del escritorio")
public class CentroUsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Código del centro")
	private Long codCentro;
	
	@Schema(description = "Denominación del centro")
	private String denominacionCentro;
	
	@Schema(description = "Nombre del centro")
	private String nombreCentro;
	
	@Schema(description = "Id del perfil")
	private Long idPerfil;
	
	@Schema(description = "Id del centro")
	private Long idCentro;
	
	@Schema(description = "Fecha de toma de posesión")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaTomaPosesion;

}
