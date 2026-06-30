package es.jccm.edu.ausencias.application.domain.guardias.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuardiasProfesoresProjection", description = "Proyección para recuperar los datos de las guardias de los profesores")
public interface GuardiasProfesoresProjection {

	Long getIdTramo();
	
	Date getFechaInicio();
	
	Date getFechaFin();
	
	String getHoraInicio();
	
	String getHoraFin();
	
	String getDiaSemana();
	
	Integer getNumEmpleados();
	
}
