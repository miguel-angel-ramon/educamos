package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@Entity
public class CriterioEvaluacionConPorcentajeYPeso extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idCriEva;

	private Long idValCriAluTemp;

	private Long idCalifica;

	private Integer numero;

	private Integer peso;

	private Float porcentaje;
	

}