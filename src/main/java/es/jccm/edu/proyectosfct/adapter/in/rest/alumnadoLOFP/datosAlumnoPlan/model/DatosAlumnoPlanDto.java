package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "DatosAlumnoPlanDto", description = "Información completa sobre los datos del alumno relacionados con el plan")
public class DatosAlumnoPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador del registro en la tabla FCT_DATOSALU_PLAN")
    private Long idDatosAluPlan;

    @Schema(description = "Matrícula del alumno")
    private Long xMatricula;

    @Schema(description = "Flag que indica si dispone de PRL")
    private Integer lgPrl;

    @Schema(description = "Flag que indica si dispone de certificación adicional")
    private Integer lgCertificacion;

    @Schema(description = "Descripción de la certificación adicional")
    private String dsCertificacion;

    @Schema(description = "Flag que indica si requiere adaptaciones")
    private Integer lgAdaptaciones;

    @Schema(description = "Descripción de las adaptaciones requeridas")
    private String dsAdaptaciones;

    @Schema(description = "Flag que indica si requiere autorizaciones extraordinarias")
    private Integer lgAutorizaciones;

    @Schema(description = "Descripción de las autorizaciones requeridas")
    private String dsAutorizaciones;

    @Schema(description = "Observaciones relacionadas con el plan del alumno")
    private String dsObservaciones;

    // Campos adicionales
    @Schema(description = "Nombre completo del alumno")
    private String nombreCompleto;

    @Schema(description = "DNI del alumno")
    private String dni;

    @Schema(description = "NUSS del alumno")
    private String nuss;

    @Schema(description = "Teléfono del alumno")
    private String telefono;

    @Schema(description = "Correo electrónico del alumno")
    private String email;
}
