package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo de centro")
public class TipoCentroDto extends BaseAuditedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del centro")
	private Long id;

	@Schema(description = "Descripcion del centro")
	private String descripcionTipoCentro;
	
	@Schema(description = "Nivel")
	private String nivel;
	
	@Schema(description = "Indica si el tipo de centro es público o no")
	private String esPublico;
	
	@Schema(description = "Tipo de centro que lo engloba")
	private String tipoCentroEngloba;
	
	@Schema(description = "Tipo de centro que indica el régimen al que pertenece")
	private String tipoCentroRegimen;
	
	@Schema(description = "Tipo de centro del que cuelga")
	private String tipoCentroCuelga;
	
	@Schema(description = "Usuario de creación de registro")
	private Long idUsuarioCreacion;;
	
	@Schema(description = "Descripcion del centro")
	private Long idUsuarioModificacion;
	
	@Schema(description = "Fecha de creación de registro")
	private Date fechaCreacion;
	
	@Schema(description = "Fecha de última modificación de registro")
	private Date fechaModificacion;
	
	

	// ---------- Relationships -----------
}
