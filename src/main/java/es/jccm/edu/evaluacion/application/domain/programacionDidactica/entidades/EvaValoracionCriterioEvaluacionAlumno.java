package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

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
@Table(name = "TLVALCRIALU", schema = "DELPHOS")
public class EvaValoracionCriterioEvaluacionAlumno extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_TLVALCRIALU")
	@SequenceGenerator(name = "TLS_TLVALCRIALU", sequenceName = "TLS_TLVALCRIALU", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="X_VALCRIALU")
	private Long id;
	
	@Column(name = "X_CALIFICA")
	private Long idCalifica;
	
	@Column(name = "X_MATMATRICULA")
	private Long idMatMatricula;
	
	@Column(name = "X_CONVCENTROOMC")
	private Long idConvCentroOmc;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_PONDERACION")
	private EvaPonderacion ponderacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CRIEVA")
	private EvaCriterioEvaluacion criteriosEvaluacion;

}