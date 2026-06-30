package es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class ListadoSegSocialCotizaMes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "centro")
    private String centro;

    @Column(name = "nuDiasInte")
    private Integer nuDiasInte;

    @Column(name = "idAluCon")
    private Long idAluCon;

    @Column(name = "dsWarnings")
    private String dsWarnings;

    @Column(name = "nombreEmpresa")
    private String nombreEmpresa;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nuDiasNacu")
    private Integer nuDiasNacu;

    @Column(name = "tipoEmpresa")
    private String tipoEmpresa;

    @Column(name = "nuDiasReal")
    private Integer nuDiasReal;

    @Column(name = "valTut")
    private String valTut;

    @Column(name = "valCen")
    private String valCen;

    @Column(name = "valDel")
    private String valDel;

    @Column(name = "nombreAlumno")
    private String nombreAlumno;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "nuMes")
    private Integer nuMes;

    @Column(name = "nuss")
    private String nuss;

    @Column(name = "tutor")
    private String tutor;

    @Column(name = "curso")
    private String curso;

    @Column(name = "cif")
    private String cif;
    
    @Column(name = "Codigo centro")
    private String ccodigo;
    
    @Column(name = "Enviado")
    private String enviado;

    @Schema(name = "diasInteEra")
    private Integer diasInteEra;
    
    @Column(name = "Puede rechazar")
    private Integer puederechazar;

    @Column(name = "lgErasBec")
    private Integer lgErasBec;

    @Column(name = "fechaEnvio")
    private String fechaEnvio;

    @Column(name = "esCorrecto")
    private Integer esCorrecto;

    @Column(name = "esCorrectoAlta")
    private String esCorrectoAlta;

    @Column(name = "inicio")
    String inicio;

    @Column(name = "fin")
    String fin;

    @Column(name = "diasRestantes")
    Integer diasRestantes;

    @Column(name = "tipoErasmus")
    String tipoErasmus;

    @Column(name = "avisoMes")
    private Integer avisoMes;

    @Column(name = "bloqueoMes")
    private Integer bloqueoMes;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

}
