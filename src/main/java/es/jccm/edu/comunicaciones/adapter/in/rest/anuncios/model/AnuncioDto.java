package es.jccm.edu.comunicaciones.adapter.in.rest.anuncios.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Anuncio", description = "Anuncio rescatado de la BBDD de comunica")
public class AnuncioDto extends BaseAuditedDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de el anuncio")
    private Long id;

}
