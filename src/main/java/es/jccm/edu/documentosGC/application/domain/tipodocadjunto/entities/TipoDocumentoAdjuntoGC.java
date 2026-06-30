package es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities;

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
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_TIPDOC_ADJUNTOS")
public class TipoDocumentoAdjuntoGC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO_ADJUNTO")
	private Long id;
	
	@Column(name="NU_ORDEN", nullable = false)
	private Integer orden;	
	
	@Column(name="LG_PRINCIPAL", nullable = false)
	private Integer principal;
	
	@Column(name="LG_FIRMABLE", nullable = false)
	private Integer firmable;	
	
	@Column(name="DS_NOMBRE", nullable = false)
	private String nombre;	
	
	@Column(name="DS_DESCRIPCION")
	private String descripcion;
	
	@Column(name="NU_TAMANO", nullable = false)
	private Integer tamano;		
	
	@Column(name="C_ANNO_DESDE", nullable = false)
	private Integer annoDesde;
	
	@Column(name="C_ANNO_HASTA")
	private Integer annoHasta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO")
	private TipoDocumentosGC tipoDocumento;		
	
}
