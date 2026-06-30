package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "DatosListaModuloAnexo2PlanProjection", description = "Fuente de datos para la lista principal dentro de la tabla Modulos del Anexo2Plan")
public interface TableAnexoIXProjection {

    @Schema (description = "Aprendizaje")
    String getAprendizaje();

    @Schema (description = "Realización del plan exclusivamente en empresas")
    String getIntEmpresa();

    @Schema (description = "Asignatura aprobada")
    String getAprobada();

    @Schema (description = "Grado de superación")
    String getGradoSuperacion();

    @Schema (description = "Motivacion observaciones")
    String getMotivacion();

    @Schema (description = "Orden resultado aprendizaje")
    String getOrden();


}
