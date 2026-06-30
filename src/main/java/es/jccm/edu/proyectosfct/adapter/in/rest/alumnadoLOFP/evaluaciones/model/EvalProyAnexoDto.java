package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "EvalProyAnexoDto", description = "DTO para procesar anexos firmados en Rodal")
public class EvalProyAnexoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador del anexo de evaluación")
    private Long id;

    @Schema(description = "Identificador de la matrícula del alumno")
    private Long xMatricula;

    @Schema(description = "Identificador de la empresa asociada")
    private Long xEmpresa;

    @Schema(description = "Identificador del documento en Rodal")
    private String idEvaFirRodal;

    @Schema(description = "Nombre del fichero adjunto")
    private String dsEvaFirFichero;

    @Schema(description = "Fecha de firma del documento")
    private Date fFirma;

    @Schema(description = "Identificador del usuario que firmó el documento")
    private Long cUsuFirma;

    @Schema(description = "Indicador de actualización del anexo (1: Sí, 0: No)")
    private Integer lgActualizado;
}
