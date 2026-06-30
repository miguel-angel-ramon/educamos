package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "DELPHOS_MODACC", name = "USUARIOS_T")
public class Usuariot implements Serializable {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "oid_persona")
	private Long oidPersona;

	@Column(name = "t_login")
	private String login;

	@Column(name = "t_correo_e")
	private String correo;

	@Column(name = "t_clave")
	private String clave;

	@Column(name = "l_activo")
	private Character activo;

	//private PersonaId personaId;

	@Column(name = "t_identificacion")
	private String identificacion;

	@Column(name = "oid_tipo_documentacion")
	private Long tipoDocumentacion;

	@Column(name = "t_nombre")
	private String nombre;

	@Column(name = "t_apellido1")
	private String apellido1;

	@Column(name = "t_apellido2")
	private String apellido2;

	@Column(name = "f_nacimiento")
	private Date fechaNacimiento;

	@Column(name = "es_docente")
	private Character docente;

	@Column(name = "es_alumno")
	private Character alumno;

	@Column(name = "l_pendiente")
	private Character pendiente;

	@Column(name = "uid_azure")
	private String uidAzure;

	@Column(name = "correo_aula")
	private String correoAula;

	@Column(name = "centro")
	private String centro;

	@Column(name = "uid_ldap")
	private String uidLdap;

	@Column(name = "mail_ldap")
	private String mailLdap;

	@Column(name = "lg_equidirectivo")
	private boolean equipoDirectivo;

	@Column(name = "listacargos")
	private String listaCargos;

	@Column(name = "tipo_personal")
	private Long tipoPersonal;

	@Column(name = "lg_tutor_unidad")
	private boolean tutorUnidad;

	@Column(name = "curso_tutor_unidad")
	private String cursoTutorUnidad;

	@Column(name = "departamento")
	private String departamento;

	@Column(name = "unidad_organizativa")
	private String unidadOrganizativa;

	@Column(name = "lg_comisioncoordpeda")
	private boolean comisioncoordinacionpedagojica;

	@Column(name = "ptotraemp")
	private String ptotraemp;

	@Column(name = "lg_nocturno")
	private Character nocturno;
}
