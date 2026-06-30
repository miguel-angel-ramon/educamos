package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatorias extraordinaria", description = "Descripcion para el modelo de convocatorias extraordinaria")
public class ConvocatoriasCorrespondenciaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha sesion")
	private Date sesion;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio convocatoria")
	private Date fechainicio;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio convocatoria")
	private Date fechafin;
	
	private Long idConvOmc;

}
