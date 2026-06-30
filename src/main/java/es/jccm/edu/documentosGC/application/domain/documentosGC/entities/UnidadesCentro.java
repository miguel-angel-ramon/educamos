package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

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

import es.jccm.edu.documentosGC.application.domain.frompfc.DgcCentro;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLUNIDADESCEN")
public class UnidadesCentro  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_UNIDAD")
	private Long id;
	
	@Column(name="C_ANNO")
	private Integer cAnno;
	
	@Column(name="F_TOMAPOS")
	private Date fechaTomaPosesion;
	
	@Column(name="N_PERIODO")
	private Integer periodo;
	
	@Column(name="N_ORDEN")
	private Integer orden;
	
	@Column(name="C_TIPO")
	private String tipo;
	
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@Column(name="N_ALUMNOS")
	private Integer numeroAlumnos;
	
	@Column(name="N_CAPACIDAD")
	private Integer capacidad;
	
	@Column(name="C_UBIEXT")
	private String ubicacionExterna;
	
	@Column(name="D_HORARIO")
	private String horario;
	
	@Column(name="L_PUBLICADA")
	private String publicada;
	
	@Column(name="L_VISIBLE")
	private String visible;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private DgcCentro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private DgcEmpleado empleado;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="C_PROVINCIA")
	@Column(name="C_PROVINCIA")
	private Long provincia;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="C_MUNICIPIO")
	@Column(name="C_MUNICIPIO")
	private Long municipio;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_LOCALIDAD")
	@Column(name="X_LOCALIDAD")
	private Long localidad;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_ACTAULADU")
	@Column(name="X_ACTAULADU")
	private Long actAulaDu;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_TURNO")
	@Column(name="X_TURNO")
	private Long turno;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_MATRICULA_DELEGADO")
	@Column(name="X_MATRICULA_DELEGADO")
	private Long matriculaDelegado;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_MATRICULA_SUBDELEGADO")
	@Column(name="X_MATRICULA_SUBDELEGADO")
	private Long matriculaSubdelegado;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_AGRUPACION")
	@Column(name="X_AGRUPACION")
	private Long agrupacion;

}