package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Competencia clave", description = "Proyección para rescatar las competencias clave")
public class CompetenciaClaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id competencia clave")
    private Long idCompetenciaClave;

    @Schema(description = "Descripcion")
    private String descripcion;

    @Schema(description = "Abreviatura")
    private String abreviatura;

    @Schema(description = "Indica si se muestran o no sus descriptores")
    private boolean mostrar = false;
    
    @Schema(description = "Descriptores operativos asociados a la competencia clave")
    private List<DescriptorOperativoDTO> descriptoresOperativos;
    
}
