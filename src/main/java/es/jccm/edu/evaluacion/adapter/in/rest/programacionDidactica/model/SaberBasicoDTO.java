package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;
@Data
public class SaberBasicoDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "nombre del saber basico")
    private String descripcion;

    @Schema(description = "abreviatrura del saber basico")
    private String abreviatura;
    
    @Schema(description = "id del bloque de saberes básicos")
    private Long idBloque;
    
    @Schema(description = "id del saber básico del que depende")
    private Long idSaberBasicoDepende;
    
    @Schema(description = "orden")
    private Long orden;
}
