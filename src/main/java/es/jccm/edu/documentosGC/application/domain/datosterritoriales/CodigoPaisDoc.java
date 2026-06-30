package es.jccm.edu.documentosGC.application.domain.datosterritoriales;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "TLPAIS")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class CodigoPaisDoc implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="C_PAIS")
	private String id;

	@NotBlank
	@Column(name="D_PAIS")
	private String descripcionLarga;

	@NotBlank
	@Column(name="S_PAIS")
	private String descripcionCorta;

	@NotBlank
	private String gentilicio;

	@NotBlank
	@Column(name="L_ESPANA")
	private String esEspania;

	@NotBlank
	@Column(name="L_COMUNITARIO")
	private String esComunitario;

	private String nacionalidad;

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

	@NotBlank
	@Column(name="L_OFRECERDEFECTO")
	private String ofrecerDefecto;

	@Column(name="C_CODPAITIT")
	private String codigoPais;

	@Column(name="D_ULTNOMSIR")
	private String nombreSirhus;

	@Column(name="L_ACTIVO")
	private String paisActivo;

}
