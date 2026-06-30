package es.jccm.edu.comunicaciones.application.domain.avisos;

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
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLAVISOS")
public class Aviso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "X_AVISO")
	private Integer idAviso;

	@NotBlank
	@Column(name = "T_MENSAJE")
	private String mensaje;

	@NotBlank
	@Column(name = "F_INIVIG")
	private Date fechaInicioVigencia;

	@Column(name = "F_FINVIG")
	private Date fechaFinVigencia;

	@NotBlank
	@Column(name = "L_ACTIVO")
	private String avisoActivo;
	
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "X_GRUAVI")
	private GrupoAvisos idGrupoAvisos;

}
