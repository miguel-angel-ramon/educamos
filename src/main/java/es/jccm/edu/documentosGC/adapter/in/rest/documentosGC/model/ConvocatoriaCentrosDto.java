package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc.DgcCentroDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria centros", description = "Descripcion para el modelo de convocatoria centros")
public class ConvocatoriaCentrosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de convocatoria centros")
	private Long id;	
	
	@Schema(description = "Anno")
	private Integer cAnno;
	
	@Schema(description = "Descripcion larga de la convocatoria")
	private String descripcionConvocatoria;
	
	@Schema(description = "Descripcion corta de la convocatoria")
	private String descripcionCortaConvocatoria;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Fecha inicio de la convocatoria")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioConvocatoria;
	
	@Schema(description = "Fecha fin de la convocatoria")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinConvocatoria;
	
	@Schema(description = "Fecha de la convocatoria")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaConvocatoria;	
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Centro")
	private DgcCentroDto centro;
	
	@Schema(description = "Convocatoria")
	private Long convocatoria;
	
	@Schema(description = "Convocatoria general")
	private Long convocatoriaGeneral;
}
