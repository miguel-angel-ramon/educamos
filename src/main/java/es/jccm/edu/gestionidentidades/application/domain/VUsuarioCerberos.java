package es.jccm.edu.gestionidentidades.application.domain;

import javax.persistence.*;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "V_USUARIOS_CERBEROS", schema = "DELPHOS_MODACC")
public class VUsuarioCerberos {

    @Id
    @Column(name = "OID")
    private Long oidUsername;

    @Column(name = "T_CORREO_E")
    private String correo;

    @Column(name = "OID_PERSONA")
    private Long oidPersona;

    @Column(name = "T_NOMBRE")
    private String givenName;

    @Column(name = "T_APELLIDO1")
    private String familyName1;

    @Column(name = "T_APELLIDO2")
    private String familyName2;

    @Column(name = "C_SEXO")
    private String sexo;

    @Column(name = "F_NACIMIENTO")
    private Date fNacimiento;

    @Column(name = "OID_TIPO_DOCUMENTACION")
    private Long oidTipodocumento;

    @Column(name = "T_IDENTIFICACION")
    private String identificacion;

    @Column(name = "T_LOGIN")
    private String login;

    @Column(name = "T_CLAVE")
    private String clave;

    @Column(name = "L_ACTIVO")
    private String isActive;

    @Column(name = "L_BLOQUEADO")
    private String isBlocked;

    @Column(name = "ES_DOCENTE")
    private String isTeacher;

    @Column(name = "ES_ALUMNO")
    private String isEstudiante;

    @Column(name = "UID_AZURE")
    private String uidAzure;

    @Column(name = "CORREO_AULA")
    private String mailAula;

    @Column(name = "UID_LDAP")
    private String uidLdap;

    @Column(name = "MAIL_LDAP")
    private String mailLdap;
}

