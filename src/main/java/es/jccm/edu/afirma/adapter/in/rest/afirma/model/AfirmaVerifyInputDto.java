package es.jccm.edu.afirma.adapter.in.rest.afirma.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "AfirmaVerifyInputDto", description = "")
public class AfirmaVerifyInputDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Firma en base 64")
    private String firmaBase64;

    @Schema(description = "Documento en base 64")
    private String hashDocumentoBase64;
}