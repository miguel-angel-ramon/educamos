package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DocumentoGCNEW nuevo", description = "Descripcion para el modelo de nuevo documento request")
public class DocumentoGCNuevoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del documento")
	private Long idDocumento;
	
	@Schema(description = "Centro del documento")
	private Long idCentro;
	
	@Schema(description = "Anno del documento")
	private Long idAnno;
	
	@Schema(description = "Tipo documento")
	private Long idTipo;
	
	@Schema(description = "Flujo siguiente del documento")
	private Long idFlujo;
	
	@Schema(description = "Descripcion documento")
	private String dsDescripcion;
	
	@Schema(description = "Observaciones documento")
	private String txObservaciones;
	
	@Schema(description = "Identificador del perfil")
	private Long idPerfil;
	
	@Schema(description = "Identificador del usuario")
	private Long idUsuario;
	
	@Schema(description = "Identificador de convocatoria reunion")
	private Long idConvreunion;	
	
	@Schema(description = "Descripcion del parte mensual")
	private String dsParaus;
	
	@Schema(description = "Identificador de convocatoria acta evaluacion")
	private Long idConvunidad;
	
	@Schema(description = "Identificador de tldocumento")
	private Long idTlDocumento;	
	
	@Schema(description = "Descripcion tipos")
	private List<Long> tipoAdjuntos;	
	
	@Schema(description = "Identificador del curso acta evaluacion")
	private Long idOfertamatrig;
	
	@Schema(description = "Identificador de la materia acta evaluacion")
	private Long idMateriac;
	
	@Schema(description = "Identificador de la convocatoria centro")
	private Long idConvCentro;
	
	@Schema(description = "Cadena con la fecha de sesion de la convocatoria final")
	private String fsesion;
	
	@Schema(description = "Identificador de la etapa")
	private Long idEtapa;
	
	
}
