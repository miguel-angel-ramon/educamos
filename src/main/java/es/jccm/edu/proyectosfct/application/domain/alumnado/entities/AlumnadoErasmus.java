package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class AlumnadoErasmus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column (name = "Fecha de comunicación de datos")
	private Date fechaComunicacionDatos;
	
	@Column (name = "Dni, Nie o pasaporte")
	private String dni;
	
	@Column (name = "Número de la Seguridad Social")
	private String nuss;
	
	@Column (name = "Apellidos")
	private String apellidos;
	
	@Column (name = "Nombre")
	private String nombre;
	
	@Column (name = "Fecha de inicio de prácticas")
	private Date fechaInicioPrac;
	
	@Column (name = "Fecha Prevista de finalización de prácticas")
	private Date fechaFinPrac;
	
	@Column (name = "Alumnado Erasmus con Beca")
	private String  alumnErasmusBeca;
	
	@Column (name = "Alumnado Erasmus sin Beca")
	private String alumnErasmusSinBeca;
	
	@Column (name = "Fecha de comunicación de dats")
	private Date fechaComDatos;
	
	@Column (name = "Número de días realizados de prácticas")
	private Integer numDiasRealizados;
	
	@Column (name = "Numero de días previstos y no realizados por IT, accidente laboral o enfermedad profesional")
	private Integer numDiasIt;
	
	@Column (name = "Numero de días previstos y no realizados por nacimieno y cuidado, riesgo durante embarazo o lactancia")
	private Integer numDiasEmbarazo;
	
}
