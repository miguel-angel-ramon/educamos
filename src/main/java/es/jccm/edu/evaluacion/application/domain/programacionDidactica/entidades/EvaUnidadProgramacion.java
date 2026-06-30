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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
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
@Table(name = "EVA_UNIDADPROG", schema = "DELPHOS")
public class EvaUnidadProgramacion extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_UNIDADPROG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_UNIDADPROG")
	@SequenceGenerator(name = "SQ_EVA_UNIDADPROG", sequenceName = "SQ_EVA_UNIDADPROG", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	@NotBlank
	@Size(max=100, message = "No puede superar los 100 caracteres")
	@Column(name = "TX_NOMBRE")
	private String nombre;

	@Size(max=10, message = "No puede superar los 10 caracteres")
	@Column(name = "TX_ABREV")
	private String abreviatura;
	
	@Size(max=4000, message = "No puede superar los 4000 caracteres")
	@Column(name = "DS_UNIDAD")
	private String descripcion;
	
	@Column(name = "NU_ORDENPRES")
	private Integer orden;
	
	// ---------- Relationships -----------

	@OneToMany(mappedBy = "unidadProgramacion", fetch = FetchType.LAZY)
	private List<EvaActividad> actividades;
	
	@OneToMany(mappedBy = "unidadProgramacion", fetch = FetchType.LAZY)
	private List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> relacionesUnidadProgramacionCriteriosEvaluacion;
	
	@OneToMany(mappedBy = "unidadProgramacion", fetch = FetchType.LAZY)
	private List<EvaRelacionUnidadProgramacionSaberBasico> relacionesUnidadProgramacionSaberBasico;
	
	@OneToOne(mappedBy = "unidadProgramacion", fetch = FetchType.LAZY)
	private EvaRelacionProgramacionDidacticaUnidadProgramacion relacionProgramacionDidacticaUnidadProgramacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CONVCENTROOMC")
	private EvaConvocatoriaCentrosOMC convCentroOmc;
}
