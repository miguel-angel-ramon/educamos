package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReunionOrganoCentroProjection", description = "Proyección para recuperar las reuniones de los órganos del centro")
public interface ReunionOrganoCentroProjection {

	Long getIdReunion();
	
	String getTitulo();
	
	String getTipo();
	
	Date getFecha();
	
	String getHoraInicio();
	
	String getHoraFin();
		
}
