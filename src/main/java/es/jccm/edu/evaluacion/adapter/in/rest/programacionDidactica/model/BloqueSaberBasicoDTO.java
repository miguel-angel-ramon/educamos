package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Bloque de saberes basicos", description = "Bloque de saberes basicos")
public class BloqueSaberBasicoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "nombre del bloque")
    private String descripcion;

    @Schema(description = "abreviatura")
    private String abreviatura;

    @Schema(description = "orden de la escala")
    private Long orden;
    
    @Schema(description = "Lista de los saberes basicos")
    private List<SaberBasicoDTO> saberesBasicos;


}
