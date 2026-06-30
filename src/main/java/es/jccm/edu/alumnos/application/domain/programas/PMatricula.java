package es.jccm.edu.alumnos.application.domain.programas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name=" TLMATALU")
public class PMatricula  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_MATRICULA")
	private Long id;
	
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_ALUMNO")
	private PAlumno alumno;
	
	@Column(name="F_PROMOCION")
	private Date f_promocion;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_UNIDAD")
	private PUnidad unidad;
	
	@Column (name="X_OFERTAMATRIC")
	private Long ofertaMatriculacion;
	
	@Column(name="X_OFERTAMATRIG")
	private Long idOMG;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn (name="X_ESTGENMATR")
	private PEstadoMatricula  estadoMatricula;
	
	@Column (name="C_ANNO")
	private int annio;
		
	@Column(name="L_DIVERSIFICA")
	private String diversificacion;
	
	@Column(name="C_RESULTADO")
	private int  resultado;
	
	@Column(name="X_CENTRO")
	private long idCentro;
	

}
