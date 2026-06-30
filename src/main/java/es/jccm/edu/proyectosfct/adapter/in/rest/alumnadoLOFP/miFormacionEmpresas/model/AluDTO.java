package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(name = "AluDTO", description = "")
public class AluDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    public AluDTO(String idConvProgAlu) {
        this.idConvProgAlu = idConvProgAlu;
    }

    @Schema(description = "IdConvProgAlu")
    private String idConvProgAlu;

}