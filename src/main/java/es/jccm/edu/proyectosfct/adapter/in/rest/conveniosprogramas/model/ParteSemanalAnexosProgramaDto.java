package es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="ParteSemanal", description = "Tabla que recoge los anexos asociados a los partes semanales de FCT")
public class ParteSemanalAnexosProgramaDto implements Serializable {

    @Schema(description="Id de la entidad ParteSemanalAnexosPrograma")
    private Long id;

    @Schema(description="Id del anexo de Rodal")
    private String idAnexoRodal;

    @Schema(description="Nombre fichero")
    private String txAnexoFichero;
	
}
