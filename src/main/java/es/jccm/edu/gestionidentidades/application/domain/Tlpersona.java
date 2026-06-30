package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TLPERSONAS", schema="DELPHOS")
public class Tlpersona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS.TLS_PERXPERSONA")
    @SequenceGenerator(name = "DELPHOS.TLS_PERXPERSONA", sequenceName = "DELPHOS.TLS_PERXPERSONA", allocationSize = 1)
    @Column(name = "X_PERSONA")
    private Long xPersona;

    @Column(name = "C_TIPIDE")
    private String tipIde;

    @Column(name = "C_NUMIDE")
    private String numIde;

    @Column(name = "C_GENERO")
    private Sexo genero;

    /*@Column(name = "C_NACIONALIDAD")
    private Tlpais pais;*/

    @Column(name = "T_NOMBRE")
    private String nombre;

    @Column(name = "T_APELLIDO1")
    private String apellido1;

    @Column(name = "T_APELLIDO2")
    private String apellido2;

    @Column(name = "F_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "L_ACTPRODATPER")
    private Character actprodatper;

    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    Set<MotivoBloqueoRegistroPersona> motivoBloqueoRegistroPersonas;

}
