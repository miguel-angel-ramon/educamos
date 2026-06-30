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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaConvocatoriaCentrosOMC;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
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
@Table(name = "EVA_ACTIVIDAD", schema = "DELPHOS")
public class EvaActividad extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ACTIVIDAD")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_ACTIVIDAD")
	@SequenceGenerator(name = "SQ_EVA_ACTIVIDAD", sequenceName = "SQ_EVA_ACTIVIDAD", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	@NotBlank
	@Size(max = 100, message = "No puede superar los 100 caracteres")
	@Column(name="TX_NOMBRE")
	private String nombre;

	@Size(max = 10, message = "No puede superar los 10 caracteres")
	@Column(name="TX_ABREV")
	private String abreviatura;

	@Size(max = 4000, message = "No puede superar los 4000 caracteres")
	@Column(name="DS_ACTIVIDAD")
	private String descripcion;

	@Column(name = "NU_ORDENPRES")
	private Integer orden;

	@Column(name = "LG_VIENEMOODLE", columnDefinition = "NUMBER(1,0) DEFAULT 0")
	private Integer lprocedeMoodle;
	
	@Column(name="F_INICIO")
	private Date fechaInicio;
	
	@Column(name="F_FIN")
	private Date fechaFin;

	// ---------- Relationships -----------

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_UNIDADPROG")
	private EvaUnidadProgramacion unidadProgramacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CONVCENTROOMC")
	private EvaConvocatoriaCentrosOMC convCentroOmc;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_INSTEVA")
	private EvaInstrumentoEvaluacion instrumentoEvaluacion;

	@OneToMany(mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<EvaRelacionProgramacionAulaActividad> relacionesProgramacionAulaActividad;

	@OneToMany(mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<EvaRelacionActividadCriterioEvaluacion> relacionesActividadCriterios;
	
	@OneToMany(mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<EvaRelacionActividadAlumno> relacionesActividadAlumnos;

	@OneToMany(mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<EvaRelacionProgramacionAulaActividad> relacionProgramacionAulaActividad;
	
	@Column(name="ID_ACTIVIDAD_MOODLE")
	private Long idActividadMoodle;
	
	
}