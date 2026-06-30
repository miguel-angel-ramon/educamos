package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HorarioSemanalAlumno", description = "Detalle del horario recuperado de la base de datos de comunica")
public interface HorarioSemanalAlumnoProjection {

    @Schema(description = "Número del día de la semana (1=Lunes, 2=Martes, ...)")
    Long getn_DiaSemana();

    @Schema(description = "Valor del tramo horario")
    Long getx_Tramo();

    @Schema(description = "Número de orden dentro del día")
    Long getn_Orden();

    @Schema(description = "Descripción del tramo horario (ej. 8:30 - 9:25)")
    String getd_Tramo();

    @Schema(description = "Nombre del profesor con asignatura")
    String getProfesor();

    @Schema(description = "Código de inicio del horario")
    Long getn_Inicio();
}
