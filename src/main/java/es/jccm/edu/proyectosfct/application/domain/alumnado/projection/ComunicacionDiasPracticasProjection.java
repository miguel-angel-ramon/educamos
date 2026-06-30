package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "COMUNICACION_DIAS_PRACTICAS", description = "Descripcion para el modelo de comunicación dias prácticas")
public interface ComunicacionDiasPracticasProjection {
	
	@Schema (name = "Fecha de comunicación de datos")
	Date getFechaComunicacionDatos();
	
	@Schema (name = "Dni, Nie o pasaporte")
	String getDni();
	
	@Schema (name = "Número de la Seguridad Social")
	String getNuss();
	
	@Schema (name = "Apellidos")
	String getApellidos();
	
	@Schema (name = "Nombre")
	String getNombre();
	
	@Schema(name = "Número de días realizados")
	Integer getNumDiasRealizados();
	
	@Schema(name = "Número de días previstos prácticas-pago directo")
	Integer getNumDiasInc();
	
	@Schema(name = "Número de días previstos prácticas-IT AT")
	String getNumDiasEmb();	
	
}
