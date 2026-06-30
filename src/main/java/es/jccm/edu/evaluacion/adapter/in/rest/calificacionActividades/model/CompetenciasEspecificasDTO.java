package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CompetenciasEspecificasDTO", description = "Materias y unidades dónde se imparten y alumnos que están matriculados en ellas")
public class CompetenciasEspecificasDTO {

    @Schema(description = "Id relacion de la competencia")
    Long idRelacionCompe;

    @Schema(description = "Id de la competencia")
    private Long idCompetencia;

    @Schema(description = "Código de la competencia")
    private String codigoCompe;

    @Schema(description = "Descripcion de la competencia")
    private String descripcion;

    @Schema(description = "Porcentaje de la competencia")
    private Float porcentaje;

    @Schema(description = "Peso de la competencia")
    private Integer peso;

    @Schema(description = "Lista de criterios")
    private List<CriterioListDTO> criterios;
}