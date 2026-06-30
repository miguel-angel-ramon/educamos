package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class HorarioList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idHorario;

	private Date fechaInicio;

	private Date fechaFin;
	
	private Integer diaSemana;
	
	private String horaInicio;
	
	private String horaFin;
	
	private String horaInicioCadena;
	
	private String horaFinCadena;
	
	private String requnidad;
	
	private String alumnos;
	
	private Integer idActividad;
	
	private String abreviaturaActividad;
	
	private String descripcionActividad;
	
	private Integer idMateria;
	
	private Integer idUnidad;
	
	private String abreviaturaMateria;
	
	private String descripcionMateria;
	
	private String abreviaturaAula;
	
	private String descripcionAula;

	private String docencia;

	private String minutos;
	
	private String consolidado;

}
