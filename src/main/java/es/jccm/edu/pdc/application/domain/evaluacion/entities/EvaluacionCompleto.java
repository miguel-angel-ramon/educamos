package es.jccm.edu.pdc.application.domain.evaluacion.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class EvaluacionCompleto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idObjetivo;
	
	private Long idCentro;
	
	private Long idObjLinAct;
	
	private Long idSeguimiento;

	private Long idCompetencia;
	
	private String objetivo;
	
	private String competencia;
	
	private String tituloLinAct;
	
	private String responsable;
		
	private String tareas;
	
	private String valoracion;
	
	private String dificultades_acciones;
	
	private String comentarios;
	
	private Integer anno;
	
	private Integer porcentaje;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioEjecucion;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinEjecucion;

}