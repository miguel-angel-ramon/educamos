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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TLOFEMATRCEN")
public class DgcOfertaMatriculaCentro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_OFERTAMATRIC")
	private Long id;

	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="L_VIGENCIA")
	private String lVigencia;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private DgcCentro centro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIG")
	private DgcOfertaMatriculaGenerico ofertaMatriculaGenerico;

}