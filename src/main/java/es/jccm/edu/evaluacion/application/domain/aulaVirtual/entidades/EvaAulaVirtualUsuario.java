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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaUnidadCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaEmpleado;
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
@Table(name = "TLAULASUSUARIOS", schema = "DELPHOS_SEGEDU")
public class EvaAulaVirtualUsuario extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "X_AULAUSUARIOS")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name="B_INVERSA")
	private String inversa;
	
	@Size(max = 480, message = "No puede superar los 480 caracteres")
	@Column(name="D_AULAUSUARIOS")
	private String descripcion;
	
	@NotNull
	@Column (name="X_TIPO")
	private Long idTipo;
	
	@Column (name="X_ROL")
	private Long idRol;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_AULA")
	private EvaAulaVirtual aulaVirtual;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private EvaEmpleado empleado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_ALUMNO")
	private EvaAlumno alumno;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_GRUACTPROALU")
	private EvaGrupoActProAlumno grupoActProAlumno;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_UNIDAD")
	private EvaUnidadCentro unidadCentro;
	
}