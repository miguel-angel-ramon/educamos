package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "nuevo parte", description = "Modelo generar nuevo parte")
public class NuevoParteDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Centro")
	private Integer centro;	
	
	@Schema(description = "Año")
	private Integer anno;	
	
	@Schema(description = "Mes")
	private Integer mes;	
	
	@Schema(description = "Fecha de remision")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fremision;	
	
	@Schema(description = "Año natural")
	private Integer annonatural;	
	
	@Schema(description = "Usuario")
	private Integer usuario;	
	
	


}
