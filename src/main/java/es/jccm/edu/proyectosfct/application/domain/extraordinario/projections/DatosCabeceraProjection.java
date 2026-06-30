package es.jccm.edu.proyectosfct.application.domain.extraordinario.projections;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado autorizacion extraordinario", description = "Descripcion para el modelo del listado autorizacion extraordinario")
public interface DatosCabeceraProjection {
	
	@Schema(description = "Centro")
	String getCentro();
	
	@Schema(description = "Localidad")
	String getLocalidad();
	
	@Schema(description = "Tutor")
	String getTutor();
	
	@Schema(description = "Curso")
	String getCurso();

}
