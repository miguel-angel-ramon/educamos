package es.jccm.edu.ausencias.application.domain.guardias.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosProfesoresGuardiasProjection", description = "Proyección para rescatar los datos de los profesores de gaurdia de un tramo horario")
public interface DatosProfesoresGuardiasProjection {

	Long getIdUsuario();
	
	String getOIdUsuario();
	
	String getNombre();
	
	String getTelefono();
	
	String getCorreo();
	
}
