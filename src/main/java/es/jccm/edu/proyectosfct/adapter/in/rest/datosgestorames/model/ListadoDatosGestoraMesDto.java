package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestorames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "ListadoDatosGestoraMesDto", description = "Listado de los datos ofrecidos por la gestora por mes")
public class ListadoDatosGestoraMesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "id")
    private Long id;

    @Schema(name = "centro")
    private String centro;
    
    @Schema(name = "unidad")
    private String unidad;

    @Schema(name = "curso")
    private String curso;
    
    @Schema(name = "dni")
    private String dni;

    @Schema(name = "estado")
    private String estado;

    @Schema(name = "nuMes")
    private Integer nuMes;

    @Schema(name = "nuDiasRealMes")
    private Integer nuDiasRealMes;

    @Schema(name = "nuDiasInteMes")
    private Integer nuDiasInteMes;

    @Schema(name = "nuDiasNacuMes")
    private Integer nuDiasNacuMes;
    
    @Schema(name = "tutor")
    private String tutor;
    
    @Schema(name = "nombreAlumno")
    private String nombreAlumno;

    @Schema(name = "nuDiasInteEraMes")
    private Integer nuDiasInteEraMes;

    @Schema(name = "idGestora")
    private Integer idGestora;
    
    @Schema(name = "nuss")
    private String nuss;

}
