package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import java.io.Serializable;

import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ConfiguracionUsuario", description = "ConfiguracionUsuario de la plataforma")
public class ConfiguracionUsuarioDto implements Serializable{

	
	private static final long serialVersionUID = 1652466561834119601L;

	private Long oidConfUsuarios;
	
	private Usuario oidUsuario;
	
	private String defaultView;
	
	private String defaultRol;
	
	private String defaultCen;
	
	private String lgSkipTour;
	
	private String lgAccPiloto;
	
}
