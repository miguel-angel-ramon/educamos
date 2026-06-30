package es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.ActaEvaluacionListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Bandeja firmas", description = "Modelo para el listado bandeja de firmas documentos gc")
public class BandejaFirmasListDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Documento principal")
	private String principal;	
	
	@Schema(description = "Tipo documento")
	private String tipodocumento;	
	
	@Schema(description = "Tipo adjunto")
	private String tipoadjunto;
	
	@Schema(description = "Nombre documento")
	private String nombre;
	
	@Schema(description = "Fecha sesion convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechageneracion;
	
	@Schema(description = "Fecha firma")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechafirma;
	
	@Schema(description = "Indicador de documento firmado por el empleado")
	private Integer lgfirmado;
	
	@Schema(description = "Estado documento")
	private String estado;

	@Schema(description = "Identificador rodal documento")
	private String idrodal;
	
	@Schema(description = "Nombre del documento")
	private String fichero;
	
	@Schema(description = "Id Adjunto")
	private Long idAdjunto;
	
	@Schema(description = "Puede firmar documento")
	private Integer permitefirmar;
	
	

}
