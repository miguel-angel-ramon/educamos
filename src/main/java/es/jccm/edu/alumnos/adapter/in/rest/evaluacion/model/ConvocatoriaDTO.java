package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria", description = "Convocatoria del centro por año")
public class ConvocatoriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id Convocatoria")
	private Long idConvocatoria;
	
	@Schema(description = "nombre Convocatoria")
	private String nombre;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Schema(description = "fecha inicio Convocatoria")
	private String fechaInicio;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Schema(description = "fecha fin Convocatoria")
	private String fechaFin;
	
	@Schema(description = "tipo Convocatoria")
	private String tipoConvocatoria;
	
	@Schema(description = "estado Convocatoria")
	private String estado;
	
}
