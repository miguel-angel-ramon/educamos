package es.jccm.edu.horarios.adapter.in.rest.actividades.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Actividad", description = "Entidad para rescatar las actividades de un profesor")
public class ActividadListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Descripción")
	private String descripcion;
	
	@Schema(description = "Orden")
	private Integer orden;
	

}
