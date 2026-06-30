package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class DocumentoGCNuevo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idDocumento;
	
	private Long idCentro;
	
	private Long idAnno;
	
	private Long idTipo;
	
	private Long idFlujo;

	private String dsDescripcion;
	
	private String txObservaciones;
	
	private Long idPerfil;
	
	private Long idUsuario;

	private Long idConvreunion;
	
	private String dsParaus;
	
	private Long idConvunidad;
	
	private Long idTlDocumento;
	
	private List<Long> tipoAdjuntos;
	
	private Long idOfertamatrig;
	
	private Long idMateriac;
	
	private Long idConvCentro;
	
	private String fsesion;
	
	private Long idEtapa;

}
