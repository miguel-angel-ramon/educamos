package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema (name = "ALUMNADO_ERASMUS", description = "Descripcion para el modelo de Alumnado Erasmus")
public interface AlumnadoErasmusProjection {

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
	
	@Schema (name = "Fecha de inicio de prácticas")
	Date getFechaInicioPrac();
	
	@Schema (name = "Fecha Prevista de finalización de prácticas")
	Date getFechaFinPrac();
	
	@Schema (name = "Alumnado Erasmus con Beca")
	String  getAlumnErasmusBeca();
	
	@Schema (name = "Alumnado Erasmus sin Beca")
	String getAlumnErasmusSinBeca();
	
	@Schema (name = "Fecha de comunicación de dats")
	Date getFechaComDatos();
	
	@Schema (name = "Número de días realizados de prácticas")
	Integer getNumDiasRealizados();
	
	@Schema (name = "Numero de días previstos y no realizados por IT, accidente laboral o enfermedad profesional")
	Integer getNumDiasIt();
	
	@Schema (name = "Numero de días previstos y no realizados por nacimieno y cuidado, riesgo durante embarazo o lactancia")
	Integer getNumDiasEmbarazo();
	
}
