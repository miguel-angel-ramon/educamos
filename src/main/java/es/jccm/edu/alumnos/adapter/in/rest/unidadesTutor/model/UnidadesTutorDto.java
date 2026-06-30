package es.jccm.edu.alumnos.adapter.in.rest.unidadesTutor.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoDetalle", description = "AlumnoDetalle rescatados de la BBDD de comunica")
public class UnidadesTutorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la unidad")
	private Long idUnidad;
	
	@Schema(description = "Descripción de la unidad")
	private String nombreUnidad;
	
	@Schema(description = "Fecha de la toma de posesion")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaTomaPosesion;

	@Schema(description = "Fecha cese")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaCese;

	@Schema(description = "Indica con 'S'")
	private String sustituye;
	
	@Schema(description = "Codigo centro")
	private String codigoCentro;

}
