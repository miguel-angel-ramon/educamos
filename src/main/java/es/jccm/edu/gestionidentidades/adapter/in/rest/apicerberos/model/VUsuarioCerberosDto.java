package es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AuthoritiesJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;
import lombok.Data;

@Data
public class VUsuarioCerberosDto {

	@JsonProperty("oid_username")
    private Long oidUsername;
	
    //private String correo;
	
	@JsonProperty("oid_persona")
    private Long oidPersona;
	
	@JsonProperty("given_name")
    private String givenName;
	
	@JsonProperty("family_name_1")
    private String familyName1;
	
	@JsonProperty("family_name_2")
    private String familyName2;
	
    private String sexo;
	
	
    private Date fNacimiento;
	
	@JsonProperty("oid_tipodocumento")
    private Long oidTipodocumento;
	
    //private String identificacion;
	
    private String login;
	
    private String clave;
	
    private Boolean activo;
	
    private Boolean bloqueado;
	
    private Boolean docente;
	
    private Boolean alumno;
	
	@JsonProperty("uid_azure")
    private String uidAzure;
	
	@JsonProperty("mail_aula")
    private String mailAula;
	
	@JsonProperty("uid_ldap")
    private String uidLdap;
	
	@JsonProperty("mail_ldap")
    private String mailLdap;

    //Campos V_USUARIOS_CERBEROS
    
    private Long idUsuarioDelphos;
    private Long idUsuarioComunica;
    private String usuarioDelphos;
    private String usuarioComunica;
    private Long idEmpleadoDelphos;
    private Long idEmpleadoComunica;
    private String nif;
    private String email;
    
    //Aplicaciones
    List<AplicacionUsuarioJwt> aplicaciones;
    
    //Roles
    List<RolUsuarioJwt> roles;
    
    //Nivel de permisos sesguridad
    private Long securityLevel;
    
    //Authorities
    AuthoritiesJwt authorities;
}
