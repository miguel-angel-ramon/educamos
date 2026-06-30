package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Combos anexo V", description = "Combos para la pantalla de listado anexo V")
public class CombosProgramasPFEDTO {


    @Schema(description = "Identificador único del combo)")
    private Long id;

    @Schema(description = "Código identificativo o descripción del combo")
    private String descripcion;

}
