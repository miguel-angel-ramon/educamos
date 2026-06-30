package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities;

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
@Table(name = "TLOFEMATRGEN")
public class OfertaMatriculaGenerico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_OFERTAMATRIG")
	private Long id;

	@NotBlank
	@Column(name="C_ANNO")
	private Integer cAnno;

	@NotBlank
	@Column(name="D_OFERTAMATRIG")
	private String descripcionOfertaMatricula;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="N_ORDEN")
	private Long nOrden;

}