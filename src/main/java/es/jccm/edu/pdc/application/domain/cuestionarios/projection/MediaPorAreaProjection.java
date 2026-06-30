package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "Media por area del usuario", description = "Proyección para rescatar las medias de un ususario por area")
public interface MediaPorAreaProjection {
	
	@Schema(description = "D_SUBDIMENSION")
	String getNombreArea();
	@Schema(description = "C_NIVEL")
	String getCNivel();
	@Schema(description = "MEDIA")
	Double getMedia();
}
