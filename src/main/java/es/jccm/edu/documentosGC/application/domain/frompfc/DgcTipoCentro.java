package es.jccm.edu.documentosGC.application.domain.frompfc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="TLTIPOCENTROS")
public class DgcTipoCentro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="C_TIPOCENTRO")
	private Long id;

	@NotBlank
	@Column(name="D_TIPOCENTRO")
	private String descripcionTipoCentro;
	
	@Column(name="C_NIVEL")
	private String nivel;

	@Column(name="L_PUBLICO")
	private String esPublico;

	@Column(name="C_TIPOCENTRO_NAT")
	private String tipoCentroEngloba;
	
	@Column(name="C_TIPOCENTRO_REG")
	private String tipoCentroRegimen;
	
	@Column(name="C_TIPOCENTRO_VIENE")
	private String tipoCentroCuelga;

	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	// ---------- Relationships -----------
	
}