package es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(name = "ListadoAltasSegSociProg", description = "Listado de los alumnos dados de alta en la Seguridad Social")
public class ListadoSegSocialCotizaMesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "nuMes")
    private Integer nuMes;

    @Schema(name = "nombreAlumno")
    private String nombreAlumno;

    @Schema(name = "nuDiasNacu")
    private Integer nuDiasNacu;

    @Schema(name = "centro")
    private String centro;

    @Schema(name = "valTut")
    private String valTut;

    @Schema(name = "dni")
    private String dni;

    @Schema(name = "nombreEmpresa")
    private String nombreEmpresa;

    @Schema(name = "idAluCon")
    private Long idAluCon;

    @Schema(name = "esCorrecto")
    private Integer esCorrecto;

    @Schema(name = "esCorrectoAlta")
    private Integer esCorrectoAlta;

    @Schema(name = "nuDiasReal")
    private Integer nuDiasReal;

    @Schema(name = "nuDiasInte")
    private Integer nuDiasInte;

    @Schema(name = "DS_WARNINGS")
    private String dsWarnings;

    @Schema(name = "valCen")
    private String valCen;

    @Schema(name = "tipoEmpresa")
    private String tipoEmpresa;

    @Schema(name = "valDel")
    private String valDel;

    @Schema(name = "curso")
    private String curso;

    @Schema(name = "unidad")
    private String unidad;

    @Schema(name = "tutor")
    private String tutor;

    @Schema(name = "nuss")
    private String nuss;
    
    @Schema(name = "cif")
    private String cif;
    
    @Schema(name = "Codigo centro")
    private String ccodigo;
    
    @Schema(name = "Enviado")
    private String enviado;
    
    @Schema(name = "Puede rechazar")
    private Integer puederechazar;    

    @Schema(name = "id")
    private Long id;

    @Schema(name = "fechaEnvio")
    private String fechaEnvio;

    @Schema(name = "lgErasBec")
    private Integer lgErasBec;

    @Schema(name = "diasInteEra")
    private Integer diasInteEra;

    @Schema(name = "tipoErasmus")
    private String tipoErasmus;

    @Schema(name = "inicio")
    private String inicio;

    @Schema(name = "fin")
    private String fin;

    @Schema(name = "diasRestantes")
    private Integer diasRestantes;

    @Schema(name = "avisoMes")
    private Integer avisoMes;

    @Schema(name = "bloqueoMes")
    private Integer bloqueoMes;
}
