package es.jccm.edu.documentosGC.application.domain.flujodoc.entities;

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
import  es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.perfiles.entities.PerfilGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;

@Data
@Entity
@Table(name="DGC_ESTADOS_FLUJO")
public class FlujoDocumentoGC {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_FLUJO")
	private Long id;
	
	@Column(name="LG_REQADJUNTO")
	private Integer adjunto;	
	
	@Column(name="LG_BORRADO")
	private Integer borrado;	
	
	@Column(name="FH_BORRADO")
	private Date fechaBorrado;	
	
	@Column(name="C_USUBORRADO")
	private Long usuBorrado;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_PERFIL")
	private PerfilGC perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_ORI")
	private EstadoDocumentoGC origen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_DES")
	private EstadoDocumentoGC destino;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO")
	private TipoDocumentosGC tipo;	

}
