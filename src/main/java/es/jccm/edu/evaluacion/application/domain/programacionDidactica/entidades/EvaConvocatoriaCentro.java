package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

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

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLCONVCENTROS")
public class EvaConvocatoriaCentro extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CONVCENTRO")
	private Long id;	
	
	@Column(name="C_ANNO")
	private Integer anno;
	
	@Column(name="D_CONVOCATORIA")
	private String descripcionConvocatoria;
	
	@Column(name="S_CONVOCATORIA")
	private String descripcionCortaConvocatoria;
	
	@Column(name="T_ABREV")
	private String abreviatura;
	
	@Column(name="F_FECINICON")
	private Date fechaInicioConvocatoria;
	
	@Column(name="F_FECFINCON")
	private Date fechaFinConvocatoria;
	
	@Column(name="F_CONVOCATORIA")
	private Date fechaConvocatoria;	
	
	@Column(name="X_CONVOCATORIA")
	private Long idConvocatoria;
	
	@Column(name="X_CONVOGENERAL")
	private Long idConvocatoriaGeneral;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private EvaCentro centro;

}