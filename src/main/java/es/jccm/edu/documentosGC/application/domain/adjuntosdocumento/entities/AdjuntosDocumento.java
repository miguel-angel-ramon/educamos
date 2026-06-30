package es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities;
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
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_HISTORIAL_ADJUNTOS")
public class AdjuntosDocumento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ADJUNTO")
	private Long id;
	
	@Column(name="LG_FIRMADO")
	private Integer firmado;	
	
	@Column(name="ID_DOCHIS_RODAL")
	private String idRodal;
	
	@Column(name="TX_DOCHIS_FICHERO")
	private String txFicheroRodal;
	
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_HISTORIAL")
	private HistoricoDocumentoGC historial;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_ADJUNTO")
	private TipoDocumentoAdjuntoGC tipo;

}
