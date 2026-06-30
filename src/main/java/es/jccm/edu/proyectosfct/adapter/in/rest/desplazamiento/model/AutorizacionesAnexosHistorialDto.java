package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion historial anexo", description = "Descripcion para el modelo de autorizacion de historial anexos")
public class AutorizacionesAnexosHistorialDto extends BaseAudited implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del historial")
	private Long id;
	
	@Schema(description = "Fecha del registro del usuario")
	private Date fechaRegistro;
	
	@Schema(description = "Observaciones cambio de estado")
	private String observaciones;
	
	@Schema(description = "Observaciones cambio de estado")
	private String idAneFctRodal;
	
	@Schema(description = "Observaciones cambio de estado")
	private String nombreAnexo;
	
	@Schema(description = "Observaciones cambio de estado")
	private Date fechaRegistroAnexo;

	@Schema(description = "ID del fichero generado automáticamente")
	private String idAnefctRodalAuto;

	@Schema(description = "Nombre del fichero generado automáticamente")
	private String txAnefctFicheroAuto;

	@Schema(description = "Fecha de registro del fichero generado automáticamente")
	private Date fhRegistroAnexoAuto;
	
	@Schema(description = "lgVisitado")
	private Integer lgVisitado;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Idenficador de la autorizacion")
	private AutorizacionesAnexos autorizacionAnexo;
	
	@Schema(description = "Idenficador del flujo de autorizacion")
	private AutorizacionFlujo flujo;
	
	@Schema(description = "Idenficador del usuario que registra el historico")
	private Long idUsuario;

}
