package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "CompetenciasEspecificasDto", description = "Materias y unidades dónde se imparten y alumnos que están matriculados en ellas")
public class CompetenciasEspecificasDto {

    @Schema(description = "Id relacion de la competencia")
    Long idRelacionCompe;

    @Schema(description = "Id de la competencia")
    private Long idCompetencia;

    @Schema(description = "Código de la competencia")
    private String codigoCompe;

    @Schema(description = "Descripcion de la competencia")
    String descripcionCompe;

    @Schema(description = "Porcentaje de la competencia")
    Float porcentajeCompe;

    @Schema(description = "Peso de la competencia")
    Integer pesoCompe;

    @Schema(description = "Lista de criterios")
    private List<CriterioListDto> criterios;
}
