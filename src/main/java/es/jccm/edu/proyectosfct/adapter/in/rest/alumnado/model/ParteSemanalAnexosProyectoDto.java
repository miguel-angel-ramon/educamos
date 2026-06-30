package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="ParteSemanal", description="Tabla que recoge los anexos asociados a los partes semanales alumnado DUAL")
public class ParteSemanalAnexosProyectoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="Id de la entidad ParteSemanalAnexosProyecto")
    private Long id;

    @Schema(description="Id del anexo de Rodal")
    private String idAnexoRodal;

    @Schema(description="Nombre del anexo o fichero")
    private String txAnexoFichero;


}
