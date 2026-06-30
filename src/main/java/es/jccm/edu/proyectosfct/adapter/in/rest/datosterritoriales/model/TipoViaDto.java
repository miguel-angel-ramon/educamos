package es.jccm.edu.proyectosfct.adapter.in.rest.datosterritoriales.model;

import java.io.Serializable;

import java.util.Date;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoVia", description = "Descripcion para el modelo de TipoVia")
public class TipoViaDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id")
	private long id;
	
	@Schema(description = "descripcionLarga")
	private String descripcionLarga;
	
	@Schema(description = "descripcionCorta")
	private String descripcionCorta;
	
	@Schema(description = "idUsuarioCreacion")
	private Long idUsuarioCreacion;

	@Schema(description = "fechaCreacion")
	private Date fechaCreacion;

	@Schema(description = "idUsuarioModificacion")
	private Long idUsuarioModificacion;

	@Schema(description = "fechaModificacion")
	private Date fechaModificacion;
	
	@Schema(description = "equivale")
	private String equivale;

	@Schema(description = "ineTipoVia")
	private String ineTipoVia;

}
