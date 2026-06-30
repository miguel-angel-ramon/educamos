package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ConvocatoriaCentrosOMCDTO", description = "DTO para convocatoria centros oferta matricula centro")
public class ConvocatoriaCentrosOMCDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de convocatoria centro omc")
	private Long id;
	
	@Schema(description = "Fecha inicio convocatoria")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioConvocatoria;
	
	@Schema(description = "Fecha fin convocatoria")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinConvocatoria;
	
	@Schema(description = "Esta bloqueada")
	private String lBloqueada;
	
	@Schema(description = "Id. Convocatoria final")
	private Long idConvocatoriaFinal;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Convocatoria centro")
	private ConvocatoriaCentroDTO convocatoriaCentro;
	
	@Schema(description = "Oferta matricula centro")
	private OfertaMatriculaCentroDTO ofertaMatriculaCentro;
	
}
