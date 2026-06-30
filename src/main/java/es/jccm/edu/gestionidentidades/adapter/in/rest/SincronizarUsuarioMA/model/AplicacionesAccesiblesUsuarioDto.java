package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(name = "AplicacionesAccesiblesUsuario", description = "Aplicaciones accesibles del usuario")
public class AplicacionesAccesiblesUsuarioDto implements Serializable{

	private static final long serialVersionUID = 5711845601293688637L;

	private Long id;
	
	private Long login;
	
	private Long idUsuario;
	
	private String bloqueado;
	
	private Long usuCreacion;
	
	private Date fCreacion;
	
	private Long usuActualiza;
	
	private Date fActualiza;

}
