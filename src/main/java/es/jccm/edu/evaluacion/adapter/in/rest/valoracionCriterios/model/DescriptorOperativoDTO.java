package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Descriptor operativo alumno", description = "Proyección para rescatar los descriptores operativos")
public class DescriptorOperativoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id Descriptor operativo")
    private Long idDescriptorOperativo;

    @Schema(description = "Descripción")
    private String descripcion;

    @Schema(description = "Abreviatura")
    private String abreviatura;

    @Schema(description = "Id de la Competencia clave")
    private Long idCompetenciaClave;

    @Schema(description = "Id de la etapa")
    private Long idEtapa;
    
}
