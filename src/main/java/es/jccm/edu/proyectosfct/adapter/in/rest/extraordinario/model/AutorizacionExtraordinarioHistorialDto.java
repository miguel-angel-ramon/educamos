package es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model;

import java.io.Serializable;
import java.util.Date;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinario;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion extraordinario", description = "Descripcion para el modelo de autorizacion extraordinario")
public class AutorizacionExtraordinarioHistorialDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del historial")
	private Long id;
	
	@Schema(description = "Fecha del registro del usuario")
	private Date fechaRegistro;
	
	@Schema(description = "Observaciones cambio de estado")
	private String txtObservaciones;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Idenficador de la autorizacion")
	private AutorizacionExtraordinario autorizacionExtraordinario;
	
	@Schema(description = "Idenficador del flujo de autorizacion")
	private AutorizacionFlujo flujo;
	
	@Schema(description = "Idenficador del usuario que registra el historico")
	private Long idUsuario;
}
