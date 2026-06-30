package es.jccm.edu.documentosGC.adapter.in.rest.datosterritoriales.model;


import java.io.Serializable;
import java.util.Date;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.CodigoPaisDoc;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoVia", description = "Descripcion para el modelo de TipoVia")
public class ProvinciaDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id")
	private long id;
	
	@Schema(description = "descripcionLarga")
	private String descripcionLarga;

	@Schema(description = "ofrecerDefecto")
	private String ofrecerDefecto;

	@Schema(description = "nombreSirhus")
	private String nombreSirhus;

	@Schema(description = "esManchega")
	private String esManchega;

	@Schema(description = "fFifechaVigencianvig")
	private Date fFifechaVigencianvig;

	// ----------- Relationships ------------
	
	@Schema(description = "codigoPais")
	private CodigoPaisDoc codigoPais;

}
