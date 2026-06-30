package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GrupoActividad", description = "Grupos de actividad de un profesor para un año académico")
public class GrupoActividadDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del grupo de actividad")
	private Long idGrupoActividad;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Id unidad")
	private Long idUnidad;

}
