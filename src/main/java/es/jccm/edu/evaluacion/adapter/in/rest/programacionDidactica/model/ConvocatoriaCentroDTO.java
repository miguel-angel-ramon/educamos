package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ConvocatoriaCentroDTO", description = "DTO Convocatoria Centros")
public class ConvocatoriaCentroDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de convocatoria centros")
	private Long id;	
	
	@Schema(description = "Año")
	private Integer anno;
	
	@Schema(description = "Descripcion larga de la convocatoria")
	private String descripcionConvocatoria;
	
	@Schema(description = "Descripcion corta de la convocatoria")
	private String descripcionCortaConvocatoria;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Fecha inicio de la convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioConvocatoria;
	
	@Schema(description = "Fecha fin de la convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinConvocatoria;
	
	@Schema(description = "Fecha de la convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaConvocatoria;	
	
	@Schema(description = "Convocatoria")
	private Long idConvocatoria;
	
	@Schema(description = "Convocatoria general")
	private Long idConvocatoriaGeneral;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Centro")
	private CentroDTO centro;
}