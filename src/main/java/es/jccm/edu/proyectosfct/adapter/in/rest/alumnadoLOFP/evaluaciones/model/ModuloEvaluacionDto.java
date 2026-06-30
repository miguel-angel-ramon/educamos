package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "ModuloEvaluacionDto", description = "Información sobre los módulos a evaluar")
public class ModuloEvaluacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del módulo")
    private Long moduloId;

    @Schema(description = "Nombre del módulo")
    private String moduloNombre;

    @Schema(description = "Código del módulo")
    private Long moduloCodigo;

    @Schema(description = "Orden del módulo")
    private Integer moduloOrden;

    @Schema(description = "Id de matrícula del módulo")
    private Long moduloMatriculaId;

    @Schema(description = "Lista de resultados de aprendizaje asociados al módulo")
    private List<ResultadoAprendizajeEvaluacionDto> resultadosAprendizaje;
}
