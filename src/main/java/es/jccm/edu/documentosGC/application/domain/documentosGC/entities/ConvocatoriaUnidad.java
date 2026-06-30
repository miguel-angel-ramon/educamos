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
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLCONVUNIDAD")
public class ConvocatoriaUnidad  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CONVUNIDAD")
	private Long id;
	
	@Column(name="F_ESTADO")
	private Date fechaEstado;
	
	@Column(name="F_SESION")
	private Date fechaSesion;
	
	@Column(name="F_ULTPUB")
	private Date fechaUltimaPublicacion;
	
	@Column(name="F_ULTCIERREDEF")
	private Date fechaUltimoCierreDefinitivo;
	
	@Column(name="N_ULTCIERREDEF")
	private String horaUltimoCierreDefinitivo;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_UNIDAD")
	private UnidadesCentro unidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CONVCENTROOMC")
	private ConvocatoriaCentrosOMC convocatoriaCentroOmc;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_ESTADOCONV")
	@Column(name="X_ESTADOCONV")
	private Long estadoConvocatoria;

}