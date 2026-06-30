package es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "ListadoHistoricoAltasProjection", description = "Listado que muestra el histórico del alta de la SS de un alumno tanto de programa como de proyecto")
public interface ListadoHistoricoAltasProjection {

    @Schema(description = "Nombre alumno")
    String getNombreCompleto();

    @Schema(description = "Fecha de Inicio")
    Date getFechaInicio();

    @Schema(description = "warnings que indican los errores que puede haber en los datos enviados")
    String getDsWarnings();

    @Schema(description = "Fecha de Fin")
    Date getFechaFin();

    @Schema(description = "Erasmus Con Beca")
    String getErasmusCb();

    @Schema(description = "Erasmus Sin Beca")
    String getErasmusSb();

    @Schema(description = "Anulado")
    String getAnulado();

    @Schema(description = "Fecha de Envío")
    String getFechaEnvio();
    
    @Schema(description = "Acción")
    String getAccion();
    
    @Schema(description = "Numero peticion")
    String getNuPeticion();

    
    
    

}
