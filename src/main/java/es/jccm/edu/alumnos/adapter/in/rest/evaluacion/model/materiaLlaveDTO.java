package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Calificacion", description = "Calificaciones de los alumnos")
public class materiaLlaveDTO implements Serializable {

    @Schema(description = "Id de la materia ")
    Long idMateriaOmg;

    @Schema(description = "Id de la materia llave")
    Long idMateriaOmgLlave;
}
