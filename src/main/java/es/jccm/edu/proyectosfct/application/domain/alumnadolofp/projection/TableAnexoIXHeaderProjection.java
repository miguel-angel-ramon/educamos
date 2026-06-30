package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Listado modulo empresa para anexo IX", description = "Proyección para obtener datos del modulo")
public interface TableAnexoIXHeaderProjection {

    @Schema(description = "Id módulo")
    Long getIdModuloCurso();

    @Schema(description = "Código + módulo")
    String getCodModulo();

    @Schema(description = "Horas Totales")
    String getHorasEmpresa();
}
