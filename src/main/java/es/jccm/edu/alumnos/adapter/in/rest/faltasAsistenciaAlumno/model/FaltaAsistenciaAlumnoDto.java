package es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FaltaAsistenciaAlumno", description = "FaltaAsistenciaAlumno rescatados del front")
public class FaltaAsistenciaAlumnoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la matrícula del alumno")
    private Long idMatricula;

    @Schema(description = "Tramo del horario")
    private Long idTramo;

    @Schema(description = "Tipo de falta")
    private String tipoFalta;

    @Schema(description = "Modo falta")
    private String modo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    @Schema(description = "Fecha de la falta")
    private String fecha;

    @Schema(description = "Id de la materia")
    private Long idMateria;

    @Schema(description = "Nombre del alumno")
    private String nombreAlumno;

}
