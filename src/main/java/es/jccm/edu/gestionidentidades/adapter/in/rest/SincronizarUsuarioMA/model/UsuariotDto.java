package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import lombok.Data;

import java.util.Date;

@Data
public class UsuariotDto {

    private Long oid;

    private Long oidPersona;

    private String login;

    private String correo;

    private String clave;

    private Character activo;

    private String identificacion;

    private Long tipoDocumentacion;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private Date fechaNacimiento;

    private Character docente;

    private Character alumno;

    private Character pendiente;

    private String uidAzure;

    private String correoAula;

    private String centro;

    private String uidLdap;

    private String mailLdap;

    private Character equipoDirectivo;

    private String listaCargos;

    private Long tipoPersonal;

    private Character tutorUnidad;

    private String cursoTutorUnidad;

    private String departamento;

    private String unidadOrganizativa;

    private Character comisioncoordinacionpedagojica;

    private String ptotraemp;

    private Character nocturno;

}
