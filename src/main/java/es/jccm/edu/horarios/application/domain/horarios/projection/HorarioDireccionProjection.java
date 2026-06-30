package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HorarioDireccionProjection", description = "Proyección para recuperar los eventos del equipo directivo")
public interface HorarioDireccionProjection {

	Long getIdTramo();
	
	Date getFechaInicio();
	
	Date getFechaFin();
	
	String getHoraInicio();
	
	String getHoraFin();
	
	String getDiaSemana();
	
	Integer getNumEmpleados();
	
	String getTitulo();
	
}
