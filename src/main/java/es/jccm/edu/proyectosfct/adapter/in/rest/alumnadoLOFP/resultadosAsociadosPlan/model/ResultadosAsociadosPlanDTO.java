package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModulosCursoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ResultadosAsociadosPlanDTO", description = "DTO para la entidad ResultadosAsociadosPlan")
public class ResultadosAsociadosPlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID del Resultado Asociado al Módulo")
    private Long idResultadoaModulo;

    @Schema(description = "Identificador del Centro")
    private Integer lg_centro;

    @Schema(description = "Identificador de la Empresa")
    private Integer lg_empresa;

    @Schema(description = "Identificador de Comesp")
    private Long x_comesp;

    // ----------- Relaciones ------------

    @Schema(description = "Información del Módulo Curso")
    private ModulosCursoDto modulosCurso;
}
