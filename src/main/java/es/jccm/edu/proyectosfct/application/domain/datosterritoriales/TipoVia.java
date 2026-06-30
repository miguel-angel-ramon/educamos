package es.jccm.edu.proyectosfct.application.domain.datosterritoriales;

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
@Table(name="TLTIPOVIAS")
public class TipoVia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_TIPOVIA")
	private long id;
	
	@NotBlank
	@Column(name="D_TIPOVIA")
	private String descripcionLarga;
	
	@NotBlank
	@Column(name="S_TIPOVIA")
	private String descripcionCorta;
	
	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;

	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;
	
	@Column(name="C_EQUIVALE")
	private String equivale;

	@Column(name="C_INETIPOVIA")
	private String ineTipoVia;


}
