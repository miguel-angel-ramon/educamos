package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosListaModuloAnexo2PlanProjection", description = "Fuente de datos para la lista principal dentro de la tabla Modulos del Anexo2Plan")
public interface DatosListaModuloAnexo2PlanProjection {

    @Schema (description = "Id del módulo")
    Long getIdModuloCurso();

    @Schema(description = "Nombre del módulo." )
    String getModulo();

    @Schema(description = "Código asociado al módulo.")
    String getCodigo();

    @Schema(description = "Indicador de colaboración (1 = sí, 0 = no).")
    Integer getColaboracion();

}
