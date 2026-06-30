package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria Unidad", description = "Descripcion para el modelo Convocatoria Unidad")
public class ConvocatoriaUnidadListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de la convocatoria unidad")
	private Long id;
	
	@Schema(description = "Descripcion")
	private String descripcion;
	
	@Schema(description = "Descripcion convocatoria")
	private String convocatoria;
	
	@Schema(description = "Descripcion unidad")
	private String unidad;
	
	@Schema(description = "Descripcion id curso")
	private Long idOfertacurso;
	
	@Schema(description = "Descripcion id conv centro")
	private Long idConvcentro;
	
	@Schema(description = "Fecha sesion convocatorias finales")
	private String fsesion;
	
}
