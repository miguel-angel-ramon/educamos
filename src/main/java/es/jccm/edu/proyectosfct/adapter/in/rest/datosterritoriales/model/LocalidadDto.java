package es.jccm.edu.proyectosfct.adapter.in.rest.datosterritoriales.model;

import java.io.Serializable;
import java.util.Date;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoVia", description = "Descripcion para el modelo de TipoVia")
public class LocalidadDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id")
	private Long id;
	
	@Schema(description = "codigoINE")
	private Integer codigoINE;
	
	@Schema(description = "descripcionLarga")
	private String descripcionLarga;
	
	@Schema(description = "descripcionCorta")
	private String descripcionCorta;
	
	@Schema(description = "fechaVigencia")
	private Date fechaVigencia;
	
	@Schema(description = "idUsuarioCreacion")
	private Long idUsuarioCreacion;
	
	@Schema(description = "fechaCreacion")
	private Date fechaCreacion;
	
	@Schema(description = "idUsuarioModificacion")
	private Long idUsuarioModificacion;
	
	@Schema(description = "fechaModificacion")
	private Date fechaModificacion;

	@Schema(description = "nombreSirhus")
	private String nombreSirhus;


	// ---------- Relationships -----------
	
	@Schema(description = "municipio")
	private MunicipioDto municipio;

}
