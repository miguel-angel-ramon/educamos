package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "HorarioSemanalAlumno", description = "lista de alumnos por grupo actividad")
public class HorarioSemanalAlumnoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @Schema(description = "Número del día de la semana (1=Lunes, 2=Martes, ...)")
    private Long n_DiaSemana;

    @Schema(description = "Valor del tramo horario")
    private Long x_Tramo;

    @Schema(description = "Número de orden dentro del día")
    private Long n_Orden;

    @Schema(description = "Descripción del tramo horario (ej. 8:30 - 9:25)")
    private String d_Tramo;

    @Schema(description = "Nombre del profesor con asignatura")
    private String profesor;

    @Schema(description = "Código de inicio del horario")
    private Long n_Inicio;

}

