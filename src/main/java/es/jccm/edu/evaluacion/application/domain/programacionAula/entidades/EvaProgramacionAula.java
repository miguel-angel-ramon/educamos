package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaProgramacionDidactica;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EVA_PROGAULA", schema = "DELPHOS")
public class EvaProgramacionAula extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PROGAULA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_PROGAULA")
	@SequenceGenerator(name = "SQ_EVA_PROGAULA", sequenceName = "SQ_EVA_PROGAULA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	@NotBlank
	@Size(max = 100, message = "No puede superar los 100 caracteres")
	@Column(name = "TX_NOMBRE")
	private String nombre;
	
	@Column(name="F_ACTUALIZA_MOODLE")
	private Date actualizaMoodle;
	
	// califiacion de actividades - perfil director
	@Transient
	private String nombreEmpleado = null;

	@Transient
	private String nombreDePila = null;

	@Transient
	private String apellido1 = null;

	@Transient
	private String apellido2 = null;
	

	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROGDIDAC")
	private EvaProgramacionDidactica programacionDidactica;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_AULA")
	private EvaAulaVirtual aulaVirtual;

	@OneToMany(fetch=FetchType.LAZY,mappedBy = "programacionAula")
	private List<EvaRelacionProgramacionAulaEmpleado> relacionesProgramacionAulaEmpleado;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "programacionAula")
	private List<EvaRelacionProgramacionAulaActividad> relacionesProgramacionAulaActividad;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "programacionAula")
	private List<EvaRelacionProgramacionAulaUnidad> relacionesProgramacionAulaUnidad;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "programacionAula")
	private List<EvaRelacionProgramacionAulaAlumno> relacionesProgramacionAulaAlumno;

}