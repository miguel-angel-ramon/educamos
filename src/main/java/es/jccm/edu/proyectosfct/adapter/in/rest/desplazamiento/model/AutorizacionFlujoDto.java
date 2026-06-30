package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion desplazamiento", description = "Descripcion para el modelo de autorizacion desplazamiento")
public class AutorizacionFlujoDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del flujo")
	private Long id;
	
	@Schema(description = "Requiere adjunto")
	private Integer requiereAdjunto;
	
	@Schema(description = "Flag para un borrado lógico de la autorización")
	private Integer borrado;
	
	@Schema(description = "Fecha del borrado lógico de la autorización")
	private Date fechaBorrado;
	
	@Schema(description = "Idenficador del usuario del borrado lógico")
	private Long usuarioBorrado;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Identificador del tipo autorizacion")
	private TipoAutorizacionDto tipoAutorizacion;
	
	@Schema(description = "Id del perfil")
	private Long idPerfil;
	
	@Schema(description = "Idenficador del estado inicial")
	private Long idEstadoOrigen;
	
	@Schema(description = "Idenficador del estado final")
	private Long idEstadoDestino;
}
