package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



	@Data
	@Schema(name = "Alumnado erasmus", description = "Descripcion para el modelo del alumnado altas y bajas")
	public class AlumnadoErasmusDto implements Serializable {

		@Schema (name = "Fecha de comunicación de datos")
		private Date fechaComunicacionDatos;
		
		@Schema (name = "Dni, Nie o pasaporte")
		private String dni;
		
		@Schema (name = "Número de la Seguridad Social")
		private String nuss;
		
		@Schema (name = "Apellidos")
		private String apellidos;
		
		@Schema (name = "Nombre")
		private String nombre;
		
		@Schema (name = "Fecha de inicio de prácticas")
		private Date fechaInicioPrac;
		
		@Schema (name = "Fecha Prevista de finalización de prácticas")
		private Date fechaFinPrac;
		
		@Schema (name = "Alumnado Erasmus con Beca")
		private String  alumnErasmusBeca;
		
		@Schema (name = "Alumnado Erasmus sin Beca")
		private String alumnErasmusSinBeca;
		
		@Schema (name = "Fecha de comunicación de dats")
		private Date fechaComDatos;
		
		@Schema (name = "Número de días realizados de prácticas")
		private Integer numDiasRealizados;
		
		@Schema (name = "Numero de días previstos y no realizados por IT, accidente laboral o enfermedad profesional")
		private Integer numDiasIt;
		
		@Schema (name = "Numero de días previstos y no realizados por nacimieno y cuidado, riesgo durante embarazo o lactancia")
		private Integer numDiasEmbarazo;
		
		
		
}
