package es.jccm.edu.proyectosfct.application.domain.altassegsocial.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class ListadoAltasSegSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;   
    
    @Column(name = "idAluCon")
    private Long idAluCon;
    
    @Column(name = "inicio")
    private Date inicio;
    
    @Column(name="tipoEmpresa")
    private String tipoEmpresa;
    
    @Column(name="centro")
    private String centro;
    
    @Column(name="nombreAlumno")
    private String nombreAlumno;
    
    @Column(name="tutor")
    private String tutor;

    @Column(name = "dni")
    private String dni;
    
    @Column(name = "nuss")
    private String nuss;

    @Column(name = "nussActualizado")
    private String nussActualizado;

    @Column(name = "fin")
    private Date fin;

    @Column(name = "aluConBeca")
    private Integer aluConBeca;

    @Column(name = "aluSinBeca")
    private Integer aluSinBeca;

    @Column(name = "valTut")
    private String valTut;

    @Column(name = "valCen")
    private String valCen;
    
    @Column(name="nombreEmpresa")
    private String nombreEmpresa;

    @Column(name = "valDel")
    private String valDel;    
    
    @Column(name = "cif")
    private String cif;

    @Column(name = "ccodigo")
    private String ccodigo;
    
    @Column(name = "enviado")
    private String enviado;
    
    @Column(name = "puede rechazar")
    private Integer puederechazar;
    
    @Column(name="curso")
    private String curso;

    @Column(name="dsWarnings")
    private String dsWarnings;
    
    @Column(name="promociona")
    private String promociona;

    @Column(name="unidad")
    private String unidad;

    @Column(name = "fechaEnvioss")
    private String fechaEnvioss;
    
    @Column(name="situacion")
    private String situacion;
    
    @Column(name = "anulado")
    private Integer anulado;
    
    @Column(name = "esCorrecto")
    private Integer esCorrecto;
    
    @Column(name="inicioHist")
    private String inicioHist;
    
    @Column(name="finHist")
    private String finHist;
    
    @Column(name = "idGestora")
    private Long idGestora;
    
    @Column(name="dsErrors")
    private String dsErrors;
    
    @Column(name="nuevoperiodo")
    private String nuevoperiodo;
    
    @Column(name="idInterno")
    private Long idInterno;
    
    @Column(name="textofin")
    private String textofin;
    
    @Column(name="nombreAlumnoEnvio")
    private String nombreAlumnoEnvio;

    @Column(name="esExcluible")
    private Integer esExcluible;

    @Column(name="fhNacimiento")
    private String fhNacimiento;

    @Column(name="nussProvisional")
    private Integer nussProvisional;
    
    @Column(name="textoSexo")
    private String textoSexo;

}
