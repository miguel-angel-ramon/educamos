package es.jccm.edu.documentosGC.application.domain.centrodoc.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.ProvinciaDoc;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcCentro;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcDenominacion;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcLocalidad;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcTipoVia;
import lombok.Data;


@Data
@Entity
@Table(name="TLDATOSCEN")
public class DatosCentroDGC implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_DATDELCEN")
	private Long id;

	
	@Column(name="F_INI_VIGEN")
	@NotBlank
	private Date fechaInicioVigencia;

	@Column(name="D_ESPECIFICA")
	private String descripcionLargaCentro;
	
	@Column(name="L_VIGENTE")
	@NotBlank
	private String esVigente;
	
	@Column(name="D_TITULO")
	private String denominacionCentroTitulo;
	
	@Column(name="F_FIN_VIGEN")
	private Date fechaFinVigencia;
	
	@Column(name="D_DOMICILIO")
	private String descripcionLargaDomicilioCentro;
	
	@Column(name="C_POSTAL")
	private String codigoPostal;
	
	@Column(name="N_TELEFONO")
	private String telefono;
	
	@Column(name="N_FAX")
	private String fax;
	
	@Column(name="T_CORREO_E")
	private String correoElectronico;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="L_PUBLICO")
	@NotBlank
	private String esPublico;
	
	@Column(name="D_ULTDESREGCEN")
	private String descripcionRegistroCentro;
	
	@Column(name="N_TIPCENRESELE")
	private String tipoCentroResultadoElecciones;
	
	@Column(name="L_ADMISION")
	@NotBlank
	private String esAdmisible;
	
	@Column(name="C_NIFCEN")
	private String nifCentro;	
	
	// ---------- Relationships -----------
	
	@NotBlank
	@Column(name="X_CENTRO")
	private DgcCentro idCentro;
	
	@Column(name="X_TIPOVIA")
	private DgcTipoVia idTipoVia;
	
	@Column(name="C_MUNICIPIO")
	@NotBlank
	private MunicipioDoc codigoMunicipio;
	
	@Column(name="C_PROVINCIA")
	@NotBlank
	private ProvinciaDoc codigoProvincia;
	
	@Column(name="X_DENGEN")
	@NotBlank
	private DgcDenominacion denominacion;
	
	@Column(name="X_LOCALIDAD")
	@NotBlank
	private DgcLocalidad localidad;	
}