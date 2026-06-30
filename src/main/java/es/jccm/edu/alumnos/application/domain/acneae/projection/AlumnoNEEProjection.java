package es.jccm.edu.alumnos.application.domain.acneae.projection;

import java.util.Date;

public interface AlumnoNEEProjection {
	
	Long getId();
	String getNecesidadEducativa();
	String getNombre();
	String getApellido1();
	String getApellido2();
	String getEstadoMatricula();
	Date getFechaNacimiento();
	Long getIdMatricula();
	String getNivelAdaptacion();
}
