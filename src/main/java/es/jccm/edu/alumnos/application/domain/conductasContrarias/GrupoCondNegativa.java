package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLGRUTIPCONNEG")
public class GrupoCondNegativa implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="X_GRUTIPCONNEG")
	private Long id;
	
	@Column(name="S_GRUTIPCONNEG")
	private String descripcionCorta;
	
	@Column(name="D_GRUTIPCONNEG")
	private String descripcion;
	
	@Column(name="C_GRUTIPCONNEG")
	private String codigo;
	

}
