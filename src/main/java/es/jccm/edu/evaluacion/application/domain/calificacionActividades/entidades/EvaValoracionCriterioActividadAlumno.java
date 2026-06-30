package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EVA_VALCRIACTALU", schema = "DELPHOS")
public class EvaValoracionCriterioActividadAlumno extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_VALCRIACTALU")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_VALCRIACTALU")
	@SequenceGenerator(name = "SQ_EVA_VALCRIACTALU", sequenceName = "SQ_EVA_VALCRIACTALU", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RELACTCRIEVA")
	private EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RELACTALUM")
	private EvaRelacionActividadAlumno relacionActividadAlumno;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;
}