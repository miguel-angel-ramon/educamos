package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TipoCentro;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo de centro")
public class DenominacionDto extends BaseAuditedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de denominación")
	private Long id;

	@Schema(description = "Descripcion de denominación")
	private String descripcionDenominacion;
	
	@Schema(description = "Código de denominación")
	private String codigoDenominacion;
	
	
	
	@Schema(description = "Usuario de creación de registro")
	private Long idUsuarioCreacion;;
	
	@Schema(description = "Descripcion del centro")
	private Long idUsuarioModificacion;
	
	@Schema(description = "Fecha de creación de registro")
	private Date fechaCreacion;
	
	@Schema(description = "Fecha de última modificación de registro")
	private Date fechaModificacion;
	
	

	// ---------- Relationships -----------
	
	@Schema(description = "Relación tipo centro")
	private TipoCentro tipoCentro;
}
