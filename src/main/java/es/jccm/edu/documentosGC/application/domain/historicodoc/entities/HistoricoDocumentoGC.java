package es.jccm.edu.documentosGC.application.domain.historicodoc.entities;

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
import lombok.Data;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;

@Data
@Entity
@Table(name="DGC_DOCUMENTO_HISTORIAL")
public class HistoricoDocumentoGC {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date registro;
	
	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name="ID_DOCHIS_RODAL")
	private String idRodal;
	
	@Column(name="TX_DOCHIS_FICHERO")
	private String txFicheroRodal;
	
	@Column(name="X_USUARIO")
	private Long usuario;	
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DOCUMENTO")
	private DocumentosGC documento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_FLUJO")
	private FlujoDocumentoGC flujo;
	
	

}
