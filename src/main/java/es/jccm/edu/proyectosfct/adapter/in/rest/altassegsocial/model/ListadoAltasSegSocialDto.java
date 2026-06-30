package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "ListadoAltasSegSocialDto", description = "Listado de los alumnos dados de alta en la Seguridad Social")
public class ListadoAltasSegSocialDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "id")
    private Long id;
    
    @Schema(name = "idAluCon")
    private Long idAluCon;
    
    @Schema(name="centro")
    private String centro;
    
    @Schema(name="tutor")
    private String tutor;

    @Schema(name = "dni")
    private String dni;
    
    @Schema(name = "nuss")
    private String nuss;

    @Schema(name = "nussActualizado")
    private String nussActualizado;

    @Schema(name="nombreAlumno")
    private String nombreAlumno;

    @Schema(name="nombreEmpresa")
    private String nombreEmpresa;

    @Schema(name="tipoEmpresa")
    private String tipoEmpresa;

    @Schema(name="curso")
    private String curso;

    @Schema(name="unidad")
    private String unidad;    
    
    @Schema(name = "inicio")
    private Date inicio;

    @Schema(name = "fin")
    private Date fin;

    @Schema(name = "aluConBeca")
    private Integer aluConBeca;

    @Schema(name = "aluSinBeca")
    private Integer aluSinBeca;

    @Schema(name = "valTut")
    private String valTut;

    @Schema(name = "valCen")
    private String valCen;

    @Schema(name = "valDel")
    private String valDel;    
    
    @Schema(name = "cif")
    private String cif;

    @Schema(name = "ccodigo")
    private String ccodigo;
    
    @Schema(name = "enviado")
    private String enviado;
    
    @Schema(name = "puede rechazar")
    private Integer puederechazar;
    
    @Schema(name = "promociona")
    private String promociona;

    @Schema(name = "fechaEnvioss")
    private String fechaEnvioss;
    
    @Schema(name = "situacion")
    private String situacion;    
    
    @Schema(name = "anulado")
    private Integer anulado;

    @Schema(name = "dsWarning")
    private String dsWarnings;
    
    @Schema(name = "esCorrecto")
    private Integer esCorrecto;
    
    @Schema(name = "inicioHist")
    private String inicioHist;
    
    @Schema(name = "finHist")
    private String finHist;
    
    @Schema(name = "dsErrors")
    private String dsErrors;
    
    @Schema(name = "idGestora")
    private Long idGestora;
    
    @Schema(name = "idInterno")
    private Long idInterno;   
    
    @Schema(name = "nuevoperiodo")
    private String nuevoperiodo;
    
    @Schema(name="fhNacimiento")
    private String fhNacimiento;
    
    @Schema(name = "textofin")
    private String textofin;
    
    @Schema(name="nombreAlumnoEnvio")
    private String nombreAlumnoEnvio;

    @Schema(name="esExcluible")
    private Integer esExcluible;

    @Schema(name="nussProvisional")
    private Integer nussProvisional;

    @Schema(name="textoSexo")
    private String textoSexo;

}
