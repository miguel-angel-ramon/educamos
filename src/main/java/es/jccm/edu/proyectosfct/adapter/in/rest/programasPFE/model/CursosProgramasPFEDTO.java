package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Cursos anexo V", description = "Combos para la pantalla de listado anexo V")
public class CursosProgramasPFEDTO {

   @Schema(description = "Identificador ofertamatrig")
   Long idOfertamatrig;

   @Schema(description = "Descripcion curso")
   String curso;

   @Schema(description = "Identificador orden")
   Long orden;


}
