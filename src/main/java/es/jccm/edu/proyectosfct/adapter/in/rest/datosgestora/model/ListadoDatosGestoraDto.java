package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestora.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "ListadoDatosGestoraDto", description = "Listado de los datos de la Seguridad Social ofrecidos por la gestora")
public class ListadoDatosGestoraDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "id")
    private Long id;
    
    @Schema(name = "tutor")
    private String tutor;

    @Schema(name = "dni")
    private String dni;
    
    @Schema(name = "centro")
    private String centro;

    @Schema(name = "curso")
    private String curso;
    
    @Schema(name = "nombreAlumno")
    private String nombreAlumno;

    @Schema(name = "unidad")
    private String unidad;
    
    @Schema(name = "nuss")
    private String nuss;

    @Schema(name = "fechaAlta")
    private String fechaAlta;

    @Schema(name = "fechaBaja")
    private String fechaBaja;

    @Schema(name = "erasmusCB")
    private String erasmusCB;

    @Schema(name = "erasmusSB")
    private String erasmusSB;

    @Schema(name = "estado")
    private String estado;

    @Schema(name = "tipo")
    private String tipo;
    
    @Schema(name = "ds_error")
    private String ds_error;

    @Schema(name = "idGestora")
    private Integer idGestora;

    @Schema(name = "file")
    private Integer file;

    @Schema(name = "data")
    private Integer data;

}
