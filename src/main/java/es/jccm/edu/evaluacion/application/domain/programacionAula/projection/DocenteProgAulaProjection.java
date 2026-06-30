package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Actividades unidades de programación")
public interface DocenteProgAulaProjection {


    @Schema(description = "Id del empleado")
    Long getIdEmpleado();

    @Schema(description = "Nombre del empledao")
    String getNombre();

}
