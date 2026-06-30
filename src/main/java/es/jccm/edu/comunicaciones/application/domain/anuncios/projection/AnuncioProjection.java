package es.jccm.edu.comunicaciones.application.domain.anuncios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TablonAnuncios", description = "Proyección para rescatar el tablón de anuncios de un centro")
public interface AnuncioProjection {

    @Schema(description = "Id del anuncio")
    Long getIdAnuncio();

    @Schema(description = "Id de la sección del anuncio")
    Long getIdSeccionAnuncio();

    @Schema(description = "Fecha de publicación")
    Date getFechaPublicacion();

    @Schema(description = "Fecha de fin de vigencia")
    Date getFechaFinVigencia();

    @Schema(description = "Título")
    String getTitulo();

    @Schema(description = "Cuerpo")
    String getCuerpo();

    @Schema(description = "Indica si la noticia ha sido enviada a la Web")
    String getNoEnviadaWeb();

}
