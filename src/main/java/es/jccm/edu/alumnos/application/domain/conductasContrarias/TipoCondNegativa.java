package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLTIPCONNEG")
public class TipoCondNegativa implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="X_TIPCONNEG")
	private Long id;
	
	@Column(name="X_GRAPERCONNEG")
	private Long idGravedadConductaNegativa;

	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_GRUTIPCONNEG")
	private GrupoCondNegativa grupoCondNegativa;
	
	@Column(name="S_TIPCONNEG")
	private String descripcionCorta;
	
	@Column(name="D_TIPCONNEG")
	private String descripcion;
	
	@Column(name="C_TIPCONNEG")
	private String codigo;
	
	@Column(name="N_ORDPRE")
	private Integer orden;
	
	
	

}
