package es.jccm.edu.ausencias.application.domain.profesores.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AusenciasProfesoresProjection", description = "Proyección para rescatar las ausencias de los profesores")
public interface AusenciasProfesoresProjection {
	
	Long getIdAusencia();
	
	Long getIdEmpleado();
	
	String getMotivo();
	
	Date getFechaInicioAusencia();

	Date getFechaFinAusencia();

	String getNombre();
	
	String getTelefono();
	
	String getCorreo();

}
