package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCentro;
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
@Table(name="TLUNIDADESCEN", schema = "DELPHOS")
public class EvaUnidadCentro  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_UNIDAD")
	private Long id;
	
	@Column(name="C_ANNO")
	private Integer anno;
	
	@Column(name="F_TOMAPOS")
	private Date fechaTomaPosesion;
	
	@Column(name="N_PERIODO")
	private Integer periodo;
	
	@Column(name="N_ORDEN")
	private Integer orden;
	
	@NotBlank
	@Size(max=1, message = "No puede superar 1 caracter")
	@Column(name="C_TIPO")
	private String tipo;
	
	@NotBlank
	@Size(max=10, message = "No puede superar los 10 caracteres")
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@Column(name="N_ALUMNOS")
	private Integer numeroAlumnos;
	
	@Column(name="N_CAPACIDAD")
	private Integer capacidad;
	
	@Size(max=40, message = "No puede superar los 40 caracteres")
	@Column(name="C_UBIEXT")
	private String ubicacionExterna;
	
	@Size(max=2000, message = "No puede superar los 2000 caracteres")
	@Column(name="D_HORARIO")
	private String horario;
	
	@Size(max=1, message = "No puede superar 1 caracter")
	@Column(name="L_PUBLICADA")
	private String publicada;
	
	@Size(max=1, message = "No puede superar 1 caracter")
	@Column(name="L_VISIBLE")
	private String visible;
	
	@Column(name="C_PROVINCIA")
	private Long provincia;
	
	@Column(name="C_MUNICIPIO")
	private Long municipio;
	
	@Column(name="X_LOCALIDAD")
	private Long localidad;
	
	@Column(name="X_ACTAULADU")
	private Long actAulaDu;
	
	@Column(name="X_TURNO")
	private Long turno;
	
	@Column(name="X_MATRICULA_DELEGADO")
	private Long matriculaDelegado;
	
	@Column(name="X_MATRICULA_SUBDELEGADO")
	private Long matriculaSubdelegado;
	
	@Column(name="X_AGRUPACION")
	private Long agrupacion;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private EvaCentro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private EvaEmpleado empleado;
	
}