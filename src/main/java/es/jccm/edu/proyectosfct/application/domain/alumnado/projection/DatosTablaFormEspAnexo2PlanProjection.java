package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Proyección para obtener información adicional del plan")
public interface DatosTablaFormEspAnexo2PlanProjection {

        @Schema(description = "Descripción del plan")
        String getDescripcion();

        @Schema(description = "Calendario del horario asociado al plan")
        String getCalendario();

        @Schema(description = "Resultado previsto del plan")
        String getResultadoPrevisto();

        @Schema(description = "Contenidos a desarrollar en el plan")
        String getContenidos();

        @Schema(description = "Actividades formativas asociadas al plan")
        String getActividades();
    }
