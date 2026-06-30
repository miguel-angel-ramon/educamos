package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidad Convocatoria", description = "Unidad de la convocatoria del centro")
public class UnidadConvDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id Unidad")
	private Long idUnidad;
	
	@Schema(description = "nombre Unidad")
	private String nombre;
	
	@Schema(description = "estado")
	private String estado;
	
	@Schema(description = "Id ConvUnidad")
	private Long idConvUnidad;
	
	@Schema(description = "idOfermatrig")
	private Long idOfermatrig;
	
	@Schema(description = "idConvOmc")
	private Long idConvOmc;
	
	@Schema(description = "etapa")
	private String etapa;
	
	@Schema(description = "idCentro de la unidad")
	private Long idCentro;
	
	@Schema(description = "etapa")
	private Long idEtapa;
	
	@Schema(description = "idOfermatric")
	private Long idOfermatric;
	
}
