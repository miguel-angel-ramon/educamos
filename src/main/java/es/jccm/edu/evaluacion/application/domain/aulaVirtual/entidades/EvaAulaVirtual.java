package es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaMateriaCursoOfertaMatriculaGenerica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaEmpleado;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaGenerico;
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
@Table(name = "TLAULAS", schema = "DELPHOS_SEGEDU")
public class EvaAulaVirtual extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "X_AULA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 20, message = "No puede superar los 20 caracteres")
	@Column(name="T_LOCALIZADOR")
	private String localizador;
	
	@Column(name = "N_PERIODO")
	private Integer periodo;
	
	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name = "L_ACTIVA")
	private String lActiva;
	
	@Size(max = 16, message = "No puede superar los 16 caracteres")
	@Column(name="ID_PLATAFORMA")
	private String idPlataforma;
	
	@Size(max = 16, message = "No puede superar los 16 caracteres")
	@Column(name = "ID_CURSO")
	private String idCurso;
	
	@Size(max = 480, message = "No puede superar los 480 caracteres")
	@Column(name="D_AULA")
	private String descripcion;
	
	@Column (name="C_ANNO")
	private Integer  anno;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIG")
	private EvaOfertaMatriculaGenerico ofertaMatriculaGenerico;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_MATERIAOMG")
	private EvaMateriaCursoOfertaMatriculaGenerica materiaOMG;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private EvaEmpleado empleado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private EvaCentro centro;
	
}