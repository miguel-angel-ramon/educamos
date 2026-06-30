package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "OfertaMatriculaCentroDTO", description = "DTO Oferta Matrícula Centro")
public class OfertaMatriculaCentroDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Oferta Matrícula Centro")
	private Long id;

	@Schema(description = "Indica si tiene vigencia o no")
	private String lVigencia;
	
	@Schema(description = "Indica si la OMC necesita baremación previa a la admisión")
	private String lBaremacion;
	
	@Schema(description = "Indica si la OMC y sus periodos provienen de Registro de Centros")
	private String lProRegCen;
	
	// ---------- Relationships -----------

	@Schema(description = "Centro")
	private CentroDTO centro;

	@Schema(description = "Oferta Matrícula Genérico")
	private OfertaMatriculaGenericoDTO ofertaMatriculaGenerico;

}