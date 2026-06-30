package es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoHistoricoCotizaMes", description = "Listado del histórico de las cotizaciones mensuales")
public interface ListadoHistoricoCotizaMesProjection {

    @Schema(description = "Nombre del alumno")
    String getNombreCompleto();

    @Schema(description = "warnings que indican los errores que puede haber en los datos enviados")
    String getDsWarnings();

    @Schema(description = "Fecha de Envío")
    String getFechaEnvio();

    @Schema(description = "Numero peticion")
    String getNuPeticion();

    @Schema(description = "Número de dias realizados")
    Integer getNuDiasRealMesProg();

    @Schema(description = "Número de dias del alumno/a con INCAPACIDAD TEMPORAL POR ACCIDENTE LABORAL O ENFERMEDAD PROFESIONAL")
    Integer getNuDiasInteMesProg();

    @Schema(description = "Número de dias del alumno/a con erasmus con NACIMIENTO Y CUIDADO DE MENOR, RIESGO DURANTE EL EMBARAZO O DURANTE LA LACTANCIA NATURAL")
    Integer getNuDiasNacuMesProg();

    @Schema(description = "Número de dias del alumno/a con INCAPACIDAD TEMPORAL POR ACCIDENTE NO LABORAL O ENFERMEDAD COMUN ERASMUS CON BECA")
    Integer getNuDiasInteEraMesProg();

    @Schema(description = "Describe el estado actual del registro")
    String getAccion();
    
    @Schema(description = "Mes de cotización")
    String getMes();
    


}
