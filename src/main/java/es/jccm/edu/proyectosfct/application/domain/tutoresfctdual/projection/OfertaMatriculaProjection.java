package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Oferta Matricula", description = "Objeto usado para el select de ofertas")
public interface OfertaMatriculaProjection {
	
	@Schema(description = "Id de la oferta (oferta matricula centro)")
	Long getId();
	
	@Schema(description = "Descripción de la oferta (oferta matricula generico)")
	String getDescripcion();

}
