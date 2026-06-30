package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS" , schema = "DELPHOS_MODACC")
@Audited
public class Usuario extends BaseAudited{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_USUARIOS")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_USUARIOS", sequenceName = "DELPHOS_MODACC.SEQ_USUARIOS", allocationSize = 1)
    @Column(name = "OID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OID_PERSONA", referencedColumnName="OID")
	private Persona persona;
	
	@Column(name = "T_LOGIN")
	private String login;
	
	@Column(name = "T_CLAVE")
	private String clave;
	
	@Column(name = "L_CREDENCIALES_UN_SOLO_USO")
	private String credencialesUnSoloUso;
	
	@Column(name = "L_ACTIVO")
	private String activo;
	
	@Column(name = "L_BLOQUEADO")
	private String bloqueado;
	
	@Column(name = "N_INTENTOS_FALLIDOS_ACCESO")
	private Long intentosFallidosAcceso;
	
	@Column(name = "OID_APLICACION_FAVORITA")
	private Long oidAplicacionFavorita;
	
	@Column(name = "F_CADUCIDAD")
	private Date caducidad;
	
	@Column(name = "T_CORREO_E")
	private String correoE;

	
	@Column(name = "MODO_ALTA")
	private String modoAlta;
	
	@Column(name = "F_LOPD")
	private Date fLopd;
	
	@Column(name = "CERBEROS")
	private String cerberos;

	@Column(name = "SECRET_KEY_2FA")
	private String secretKey2FA;

	@Column(name = "CODIGO_2FA")
	private String codigo2FA;

	@Column(name = "FECHA_GENERACION_CODIGO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaGeneracionCodigo;
	
	@Column(name = "UID_LDAP")
	private String uidLdap;
	
	@Column(name = "MAIL_LDAP")
	private String mailLdap;
	

}
