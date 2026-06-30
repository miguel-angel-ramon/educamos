package es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLDOCUMENTOS")
public class TlDocumento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_DOCUMENTO")
	private Long id;
	
	@Column(name="X_TIPDOC")
	private Long idTipDoc;
	
	@Column(name="D_DOCUMENTO")
	private String documento;
	
	@Column(name="L_ACTIVO")
	private String activo;
	
	@Column(name="N_ORDENPRES")
	private Integer ordenPresentacion;
	
	@Column(name="X_DOCUMENTOPADRE")
	private Long idDocumentoPadre;
	
	@Column(name="L_DISPONIBLE")
	private String disponible;
	
	@Column(name="L_RAMFINPRE")
	private String ramaFinalPresentacion;
	
	@Column(name="C_MODULO")
	private String modulo;

	@Column(name="L_REGISTRABLE")
	private String registable;	
	
	@Column(name="T_CLAVALDOC")
	private String claValDoc;		
	
	@Column(name="L_DILIGENCIA")
	private String diligencia;		
	
	@Column(name="L_CENTRO_ADSCRIP")
	private String centroAdscripcion;	
	
	@Column(name="L_ACTADGC")
	private Integer actaDgc;	

}
