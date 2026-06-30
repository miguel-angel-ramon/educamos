package es.jccm.edu.comunicaciones.application.domain.mensajes.projection;

import java.util.Date;

import javax.persistence.Lob;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Mensajes", description = "Proyección para rescatar los mensajes de un usuario")
public interface MensajeProjection {

    @Schema(description = "Id del mensaje")
	Long getId();

    @Schema(description = "Id del usuario destinatario")
	Long getIdDestinatarioMensaje();
	
    @Schema(description = "Asunto del mensaje")
	String getAsunto();
    
    @Schema(description = "Cuerpo del mensaje")
	String getCuerpoMensaje();
	
    @Schema(description = "Id del usuario remitente")
	Long getIdRemitente();
	
    @Schema(description = "Nombre completo del usuario remitente")
	String getRemitente();
	
    @Schema(description = "Id del grupo")
	Long getIdGrupo();
	
    @Schema(description = "Nombre del grupo")
	String getGrupo();
	
    @Schema(description = "Nombre de los destinatario")
	String getDestinatarios();
	
    @Schema(description = "Estado de mensaje respondido")
	Boolean getRespuesta();
	
    @Schema(description = "Fecha del mensaje")
	Date getFechaMensaje();
	
    @Schema(description = "Comprobación de lectura")
	Boolean getLeido();
	
    @Schema(description = "Comprobación de ficheros adjuntos")
	Boolean getAdjuntos();
	
    @Schema(description = "Comprobación de respuesta")
	Boolean getRespondido();
	
    @Schema(description = "Número de destinatarios")
	Long getNumeroDestinatarios();
	
    @Schema(description = "Número destinatarios que han leído el mensaje")
	Long getNumeroLeidos();
	
    @Schema(description = "Comprobación de borrado para todos")
	Boolean getBorradoParaTodos();
    
    @Lob
    @Schema(description = "Foto del remitente")
    byte[] getFotoRemitente();
}
