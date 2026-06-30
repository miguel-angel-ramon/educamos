package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Competencias convocatoria", description = "Proyección para rescatar las competencias de una convocatoria")
public class CompetenciasConvDto {

    @Schema(description = "Id relacion de la competencia")
    Long idRelacionCompe;

    @Schema(description = "Id de la competencia")
    Long idCompetencia;

    @Schema(description = "Código de la competencia")
    String codigo;

    @Schema(description = "Descripcion de la competencia")
    String descripcion;

    @Schema(description = "Porcentaje de la competencia")
    Float peso;

    @Schema(description = "Valoración del alumno")
    String valoracion;

    @Schema(description = "Si el alumno aprueba o no")
    String aprueba;

    @Schema(description = "Lista de criterios")
    private List<CriteriosConvDto> criterios;
}
