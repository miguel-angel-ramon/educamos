package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TLALUMNOS")
public class AlumnoHorario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "X_ALUMNO")
    private Long idAlumnoHorario;

    @NotBlank
    @Column(name = "T_NOMBRE")
    private String nombre;

    @NotBlank
    @Column(name = "T_APELLIDO1")
    private String apellido1;

    @Column(name = "T_APELLIDO2")
    private String apellido2;
    
    private byte[] foto;
    
    @Column(name = "C_NUMESCOLAR")
    private String idescolar;
    
    @Column(name = "C_NUMIDE")
    private String numide;
    
    private String direccion;
    
    @Column(name = "F_NACIMIENTO")
    private Date fechaNacimiento;
    
    @Column(name = "T_CORREO_E")
    private String correo;
    
    @Column(name = "T_TELEFONO")
    private String telefono;
    
    @Column(name = "T_TELEFONOURG")
    private String tlefnourg;
    
    private String comentarioNotificacion;
    
    private String motivoNotificacion;
    
    private String nombreUsuarioNotificacion;
    
    private String observacion;
    
    private String idExpediente;
}
