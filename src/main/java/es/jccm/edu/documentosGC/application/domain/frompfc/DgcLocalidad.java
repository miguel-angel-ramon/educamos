package es.jccm.edu.documentosGC.application.domain.frompfc;

import java.io.Serializable;
import java.util.Date;

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

import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import lombok.Data;

@Data
@Entity
@Table(name="TLLOCALIDADES")
public class DgcLocalidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_LOCALIDAD")
	private Long id;
	
	@NotBlank
	@Column(name="C_LOCALIDAD")
	private Integer codigoINE;
	
	@NotBlank
	@Column(name="D_LOCALIDAD")
	private String descripcionLarga;
	
	@NotBlank
	@Column(name="S_LOCALIDAD")
	private String descripcionCorta;
	
	@Column(name="F_FINVIG")
	private Date fechaVigencia;
	
	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;
	
	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;
	
	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="D_ULTNOMSIR")
	private String nombreSirhus;


	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="C_MUNICIPIO", referencedColumnName="C_MUNICIPIO"),
		@JoinColumn(name="C_PROVINCIA", referencedColumnName="C_PROVINCIA")
		})
	private MunicipioDoc municipio;

}
