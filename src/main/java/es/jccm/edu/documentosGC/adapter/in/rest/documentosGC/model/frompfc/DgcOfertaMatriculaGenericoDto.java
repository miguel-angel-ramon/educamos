package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Oferta Matricula Generico", description = "Descripcion para el modelo que contiene la información de las ofertas")
public class DgcOfertaMatriculaGenericoDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la oferta generica")
	private Long id;

	@Schema(description = "Año de la oferta")
	private Integer cAnno;

	@Schema(description = "Descripcion")
	private String descripcionOfertaMatricula;
	

}
