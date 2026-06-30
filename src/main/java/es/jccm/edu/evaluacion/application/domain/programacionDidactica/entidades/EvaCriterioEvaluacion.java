package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "TLCRIEVA", schema = "DELPHOS")
public class EvaCriterioEvaluacion extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "X_CRIEVA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_CRIEVA2")
	@SequenceGenerator(name = "TLS_CRIEVA2", sequenceName = "TLS_CRIEVA2", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	@Size(max=750, message = "No puede superar los 750 caracteres")
	@Column(name = "D_CRIEVA")
	private String nombre;

	@Size(max=20, message = "No puede superar los 20 caracteres")
	@Column(name = "T_ABREV")
	private String abreviatura;
	
	@NotNull
	@Column(name = "X_CICLO")
	private Long idCiclo;
	
	@Column(name = "N_ORDENPRES")
	private Integer orden;
	
	// ---------- Relationships -----------
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_COMESP")
    private EvaCompetenciaEspecificaDidactica competenciaEspecifica;
	

	@ToString.Exclude
	@OneToMany(mappedBy = "criterioEvaluacion", fetch = FetchType.LAZY)
	private List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> relacionesUnidadProgramacionCriteriosEvaluacion;

	@ToString.Exclude
	@OneToMany(mappedBy = "criteriosEvaluacion", fetch = FetchType.LAZY)
	private List<EvaRelacionPonderacionCriteriosEvaluacion> relacionesPonderacionCriteriosEvaluacion;

	@ToString.Exclude
	@OneToMany(mappedBy = "criteriosEvaluacion", fetch = FetchType.LAZY)
	private List<EvaValoracionCriterioEvaluacionAlumno> valoracionesCriteriosEvaluacionAlumnos;

}
