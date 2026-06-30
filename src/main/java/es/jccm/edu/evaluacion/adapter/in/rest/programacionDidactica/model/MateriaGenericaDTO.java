package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriaGenericaDTO", description = "DTO Materia Genética")
public class MateriaGenericaDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Materia Genética")
	private Long id;

	@Schema(description = "Descripción de la Materia Genética")
	private String descripcion;

	@Schema(description = "Descripción corta de la Materia Genética")
	private String descripcionCorta;

	@Schema(description = "Abreviatura de la Materia Genética")
	private String abreviatura;
	
	@Schema(description = "Indica si es de aplicación general.")
	private String lAutorizacion;
	
	@Schema(description = "Indica si se imparte dentro de la Comunidad Andaluza")
	private String lImparte;
	
	@Schema(description = "Código del campo 'Asignat' de la tabla 'TTCursos' de SENU (cambiando '0abc' por '01bc' y cambiando 'xpqr' por '1pqr', sólo si x>0)")
	private String codigoEnu;
	
	@Schema(description = "Última descripción en SENU")
	private String descripcionSenu;
	
	private String lMatSelPau;
	
	private String lMatoBlPau;
	
	// ---------- Relationships -----------
}