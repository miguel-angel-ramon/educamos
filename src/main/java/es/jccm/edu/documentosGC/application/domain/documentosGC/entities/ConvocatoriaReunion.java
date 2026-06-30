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

import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLCONVREUNIONES")
public class ConvocatoriaReunion  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CONVREUNION")
	private Long id;
	
	@Column(name="C_TIPO")
	private String tipo;
	
	@Column(name="F_CONVOCATORIA")
	private Date fechaConvocatoria;
	
	@Column(name="F_REUNION")
	private Date fechaReunion;
	
	@Column(name="N_HORA")
	private Integer numHoras;
	
	@Column(name="F_TOMAPOS")
	private Date fechaTomaPosesion;
	
	@Column(name="C_ESTADO")
	private String estado;	
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_EMPLEADO")
	private DgcEmpleado empleado;
	
	@Column(name="X_ORGANO")
	private Long idOrgano;
	
	@Column(name="X_DEPENDENCIA")
	private Long idDependencia;

}