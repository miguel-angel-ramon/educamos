package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLREGPRO")
public class RegistroTemporal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
//	@SequenceGenerator(name="generator", sequenceName="TLS_RAPXIDENTREG", allocationSize = 1, schema="DELPHOS")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	@Column(name = "X_IDENTREG", unique=true, nullable=false, precision=20, scale=0)
	private Long idEntreg;

//	@SequenceGenerator(name="generator", sequenceName="TLS_RAPXSECUENCIA", allocationSize = 1, schema="DELPHOS")
//	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	
	@Column(name = "X_EJECUCION")
	private Long idEjecucion;
	
	@NotBlank
	@Column(name="C_PROMAS")
	private String codPromas;
	
	@NotBlank
	@Column(name = "D_IDENTREG")
	private String nombreEntreg;
	
	@NotBlank
	@Column(name = "T_ESTADO")
	private String estado;
	
	@Column(name = "N_CLAVE1")
	private Long idClave1;
	
	@Column(name = "C_CLAVE1")
	private String codigoClave1;
	
	@Column(name = "N_CLAVE2")
	private Long idClave2;
	
	@Column(name = "C_CLAVE2")
	private String codigoClave2;
	
	@Column(name = "D_CLAVE2")
	private Date fechaClave2;
	
	@Column(name = "N_CLAVE3")
	private Long idClave3;
	
	@Column(name = "C_CLAVE3")
	private String codigoClave3;
	
	@Column(name = "N_CLAVE4")
	private Long idClave4;
	
	@Column(name = "C_CLAVE4")
	private String codigoClave4;
	
	@Column(name = "N_CLAVE5")
	private Long idClave5;
	
	@Column(name = "C_CLAVE5")
	private String codigoClave5;
	
	@Column(name = "N_CLAVE6")
	private Long idClave6;
	
	@Column(name = "C_CLAVE6")
	private String codigoClave6;
	
	@Column(name = "N_CLAVE7")
	private Long idClave7;
	
	@Column(name = "C_CLAVE7")
	private String codigoClave7;
	
	@Column(name = "N_CLAVE8")
	private Long idClave8;
	
	@Column(name = "C_CLAVE8")
	private String codigoClave8;
	
	@Column(name = "N_CLAVE9")
	private Long idClave9;
	
	@Column(name = "C_CLAVE9")
	private String codigoClave9;
	
	@Column(name = "N_CLAVE10")
	private Long idClave10;
	
	@Column(name = "C_CLAVE10")
	private String codigoClave10;
	
	@Column(name = "T_AUXILIAR")
	private String auxiliar;
	
	@Column(name = "F_ACTUALIZA")
	private Date fechaActualizacion;
	
}
