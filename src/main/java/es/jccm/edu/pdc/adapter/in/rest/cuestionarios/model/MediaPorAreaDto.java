package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "MediaPorAreaDto", description = "Media total o por area")
public class MediaPorAreaDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String nombreArea;
	
	String cNivel;

	Double media;
}
