package es.jccm.edu.comunicaciones.adapter.in.rest.anuncios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tablon Anuncios", description = "Descripcion para el modelo de anuncios para la el módulo del escritorio")
public class AnuncioListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del anuncio")
    private Long idAnuncio;

    @Schema(description = "Id de la sección del anuncio")
    private Long idSeccionAnuncio;

    @Schema(description = "Fecha de publicación")
    @JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
    private Date fechaPublicacion;

    @Schema(description = "Fecha de fin de vigencia")
    @JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
    private Date fechaFinVigencia;

    @Schema(description = "Titulo")
    private String titulo;

    @Schema(description = "Cuerpo")
    private String cuerpo;

    @Schema(description = "Indica si la noticia ha sido enviada a la Web")
    private String noEnviadaWeb;

}
