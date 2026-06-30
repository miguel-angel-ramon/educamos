package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import java.util.Date;

import lombok.Data;

@Data
public class UsuariotDto {
	
    private Long OID;
    private Long OID_PERSONA;
    private String T_LOGIN;
    private String T_CORREO_E;
    private String T_CLAVE;
    private String L_ACTIVO;
    private String T_IDENTIFICACION;
    private Long OID_TIPO_DOCUMENTACION;
    private String T_NOMBRE;
    private String T_APELLIDO1;
    private String T_APELLIDO2;
    private Date F_NACIMIENTO;
    private String ES_DOCENTE;
    private String ES_ALUMNO;
    private String L_PENDIENTE;
    private String UID_AZURE;
    private String CORREO_AULA;
    private String CENTRO;
    private String UID_LDAP;
    private String MAIL_LDAP;
    private Integer LG_EQUIDIRECTIVO;
    private String LISTACARGOS;
    private Integer TIPO_PERSONAL;
    private Integer LG_TUTOR_UNIDAD;
    private String CURSO_TUTOR_UNIDAD;
    private String DEPARTAMENTO;
    private String UNIDAD_ORGANIZATIVA;
    private String PTOTRAEMP;
    private Integer LG_COMISIONCOORDPEDA;
    private Integer LG_NOCTURNO;
    private Long C_USUCREACION;
    private Date F_CREACION;
    private Long C_USUACTUALIZA;
    private Date F_ACTUALIZA;
    private Integer LG_HABILITAOWA;
    private Integer LG_ACCESOBUZON;
    private Integer LG_ACCESOBUZONALUMNADO;
    private Long OID_ANT;
    private Long CRT_PT;
    private String CD_PERMISO_CORREO_ALUMNADO;
}
