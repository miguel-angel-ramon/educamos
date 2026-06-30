package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model.TlDocumentoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.centrodoc.model.CentroDocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.cursoacademicodoc.model.CursoAcademicoDocDto;
import es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model.TipoDocumentoGCDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DocumentoGCDto", description = "Descripcion para el modelo de Documentos de Gestión de Centro")
public class DocumentosGCDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Descripcion")
	private String descripcion;	
	
	@Schema(description = "Observaciones")
	private String observaciones;	
	
	@Schema(description = "Descripcion parte mensual")
	private String dsParaus;	
	
	@Schema(description = "Identificador de convocatoria acta evaluacion")
	private Long idConvunidad;	
	
	@Schema(description = "Identificadores de adjuntos")
	private List<Long> idAdjuntosFirmantes;
	
	@Schema(description = "Fecha de sesion de una convocatoria final")
	private Date fsesion;	
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Convocatoria reunion")
	private TlDocumentoDto tldocumento;
	
	@Schema(description = "Convocatoria reunion")
	private ConvocatoriaReunionDto convocatoriaReunion;
	
	@Schema(description = "Convocatoria unidad")
	private ConvocatoriaUnidadDto convocatoriaUnidad;
	
	@Schema(description = "Anno asociada al documento")
	private CursoAcademicoDocDto anno;
	
	@Schema(description = "Centro asociado al documento")
	private CentroDocumentosGCDto centro;
	
	@Schema(description = "Tipo asociado al documento")
	private TipoDocumentoGCDto idTipoDocumento;
	
	@Schema(description = "Curso")
	private OfertamatrigDto ofertamatrig;
	
	@Schema(description = "Materia")
	private MateriacDto materiac;
	
	@Schema(description = "Convocatoria centro")
	private ConvocatoriaCentrosDto convocatoriac;
	
	@Schema(description = "Etapa")
	private EtapaDto etapa;
	
	
}
