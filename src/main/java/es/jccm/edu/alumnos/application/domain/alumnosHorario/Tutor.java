package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLTUTORES")
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "X_TUTOR")
    private Long idTutor;

    @NotBlank
    @Column(name = "T_NOMBRE")
    private String nombre;

    @NotBlank
    @Column(name = "T_APELLIDO1")
    private String apellido1;

    @Column(name = "T_APELLIDO2")
    private String apellido2;
    
    @Column(name = "C_NUMIDE")
    private String numide;
    
    @NotBlank
    @Column(name = "T_TELEFONO")
    private String telefono;
    
    @NotBlank
    @Column(name = "T_TELEFONOURG")
    private String tlefnourg;
    
    @ManyToMany(fetch=FetchType.LAZY)
    private List<TlefDetalle> listTelf;
    
   
    private Long idUsuarioComunica;
    

}