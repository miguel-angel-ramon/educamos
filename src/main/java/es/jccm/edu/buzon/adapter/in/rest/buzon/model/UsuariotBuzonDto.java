package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import java.sql.Date;

import lombok.Data;

@Data
public class UsuariotBuzonDto {

	private Long oid;

    private String login;
    
    private Long oidPersona;

    private String correo;

    private Character activo;
    
    private String clave;

    private String identificacion;

    private String nombre;
    
    private Long tipoDocumentacion;

    private String apellido1;

    private Date fechaNacimiento;
    
    private String apellido2;

    private Character docente;

    private Character pendiente;
    
    private Character alumno;

    private String uidAzure;

    private String centro;
    
    private String correoAula;

    private String uidLdap;

    private Character equipoDirectivo;
    
    private String mailLdap;

    private String listaCargos;

    private Character tutorUnidad;
    
    private Long tipoPersonal;

    private String cursoTutorUnidad;

    private String unidadOrganizativa;
    
    private String departamento;

    private Character comisioncoordinacionpedagojica;

    private Date fechaBaja;

    private Character nocturno;
    
    private String ptotraemp;
    
    private Date fechaAlta;
    
    

}
