package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "AlumnoEvaluacionDto", description = "Información sobre la evaluación del alumno")
public class AlumnoEvaluacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre completo del alumno")
    private String nombreAlumno;

    @JsonProperty("xMatricula")
    @Schema(description = "Matrícula del alumno")
    private Long xMatricula;
}
