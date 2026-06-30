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
@Table(name="TLCONVCENOMC")
public class EvaConvocatoriaCentrosOMC  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CONVCENTROOMC")
	private Long id;
	
	@Column(name="F_FECINICONOMC")
	private Date fechaInicioConvocatoria;
	
	@Column(name="F_FECFINCONOMC")
	private Date fechaFinConvocatoria;
	
	@Column(name="L_EVABLO")
	private String lBloqueada;
	
	@Column(name="X_CONVFINAL")
	private Long idConvocatoriaFinal;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CONVCENTRO")
	private EvaConvocatoriaCentro convocatoriaCentro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIC")
	private EvaOfertaMatriculaCentro ofertaMatriculaCentro;
}