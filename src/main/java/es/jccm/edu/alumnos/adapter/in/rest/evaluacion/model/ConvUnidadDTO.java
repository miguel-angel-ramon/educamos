package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria Unidad", description = "Convocatoria Unidad")
public class ConvUnidadDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id convocatoria unidad")
	private Long idConvUnidad;
	
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "fecha sesion")
	private Date descripcion;
	
	@Schema(description = "idEstado")
	private String estado;
	
}
