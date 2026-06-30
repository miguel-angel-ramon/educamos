package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EVA_RELACTCRIEVA", schema = "DELPHOS")
public class EvaRelacionActividadCriterioEvaluacion extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELACTCRIEVA")
	@SequenceGenerator(name = "SQ_EVA_RELACTCRIEVA", sequenceName = "SQ_EVA_RELACTCRIEVA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELACTCRIEVA")
	private Long id;

	//TODO: lPonderada tipo Integer
	@Column(name = "LG_PONDERADA", columnDefinition = "NUMBER(1,0) DEFAULT 0")
	private String lPonderada;
	
	@Column(name = "NU_PESO")
	private Integer peso;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD")
	private EvaActividad actividad;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CRIEVA")
	private EvaCriterioEvaluacion criterioEvaluacion;

}