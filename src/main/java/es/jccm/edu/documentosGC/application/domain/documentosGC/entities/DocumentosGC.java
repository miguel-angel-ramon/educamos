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
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.TlDocumento;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_DOCUMENTOS")
public class DocumentosGC extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DOCUMENTO")
	private Long id;

	@Column(name="DS_DESCRIPCION")
	private String descripcion;

	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name="DS_PARAUS")
	private String dsParaus;	
	
	@Column(name="F_SESION")
	private Date fsesion;		

	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_DOCUMENTO")
	private TlDocumento tldocumento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_CONVREUNION")
	private ConvocatoriaReunion convocatoriaReunion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_CONVUNIDAD")
	private ConvocatoriaUnidad convocatoriaUnidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "C_ANNO")
	private CursoAcademicoDoc anno;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_CENTRO")
	private CentroDocumentosGC centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DOCUMENTO")
	private TipoDocumentosGC idTipoDocumento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_OFERTAMATRIG")
	private OfertaCursoGenerico ofertamatrig;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_MATERIAC")
	private OfertaMateriaGenerica materiac;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_CONVCENTRO")
	private ConvocatoriaCentros convocatoriacentro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_ETAPA")
	private Etapa etapa;
     
}
