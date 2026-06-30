package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class CompetenciaAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long comEsp;

	private Long idCompetencia;

	private String abreviatura;

	private String descripcion;

	private Float porcentaje;

	private Long idComAlu;

	private Long idCalifica;

	private Long nota;

	private String descCal;

	private String aprueba;

	private Boolean notaChanged = false;

	@OneToMany(mappedBy = "idCriterio")
	private List<CriterioAlumno> criterios;
}
