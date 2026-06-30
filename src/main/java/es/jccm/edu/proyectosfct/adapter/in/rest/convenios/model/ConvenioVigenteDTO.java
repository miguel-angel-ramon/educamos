package es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "Convenios vigente", description = "Descripción de convenios para saber si esta duplicado.")
public class ConvenioVigenteDTO {
    @Schema(description = "Tipo de convenio 1 proyectos, 2 programas")
    private Integer tipoConvenio;

    @Schema(description = "Fecha inicio del convenio")
    private Date fhIni;

    @Schema(description = "Fecha fin del convenio")
    private Date fhFin;

    @Schema(description = "Id del centro")
    private Long xCentro;

    @Schema(description =  "Id de la empresa")
    private Long xEmpresa ;

}
