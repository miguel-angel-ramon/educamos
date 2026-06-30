package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MateriaLlave", description = "Materia junto con su respectiva materia llave")
public interface materiaLlaveProjection {

    @Schema(description = "Id de la materia ")
    Long getIdMateriaOmg();

    @Schema(description = "Id de la materia llave")
    Long getIdMateriaOmgLlave();
}
