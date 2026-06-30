package es.jccm.edu.proyectosfct.application.domain.empresas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name = "EMP_SEDEMP")
public class SedeEmpresa extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SEDEMP")
	private Long id;

	@Column(name = "T_DOMICILIO")
	private String domicilio;

	@Column(name = "T_NUMERO")
	private String numero;

	@NotBlank
	@Column(name = "T_ESCALERA")
	private String escalera;

	@Column(name = "T_PISO")
	private String piso;
	
	@Column(name = "C_POSTAL")
	private String codigoPostal;
	
	@Column(name = "N_TELEFONO")
	private Long telefono;
	
	@Column(name = "N_TELEFONO2")
	private Integer otroTelefono;
	
	@Column(name = "N_FAX")
	private Integer fax;
	
	@Column(name = "T_CORREO")
	private String correo;
	
	@Column(name = "LG_PRINCIPAL")
	private Boolean lgPrincipal;
	
	@Column(name = "T_LETRA")
	private String letra;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_EMPRESA")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_TIPOVIA")
	private TipoVia tipoVia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "X_LOCALIDAD")
	private Localidad localidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ 
			@JoinColumn(name = "C_MUNICIPIO", referencedColumnName = "C_MUNICIPIO"),
			@JoinColumn(name = "C_PROVINCIA", referencedColumnName = "C_PROVINCIA") })
	private Municipio municipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "C_PAIS")
	private CodigoPais codigoPais;
}
