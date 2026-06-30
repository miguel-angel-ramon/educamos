package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class CriterioAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long criEva;

	private Long idCriterio;

	private String abreviatura;

	private String descripcion;

	private Float porcentaje;

	private Long idTipoOperacion;

	private String nombreTipoOperacion;

	private Long idCrialu;

	private Long idCalifica;
	
	private Long idActividad;

	private Long nota;
	
	private Long idUnidadProgramacion;

	private String descCal;

	private String aprueba;
	
	private Integer peso;

	private Integer lprocedeMoodle;

	private Boolean notaChanged = false;
	
	private Long usuCreacion;
}
