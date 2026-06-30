package es.jccm.edu.alumnos.application.domain.acneae;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLMATINFNEE")
public class MatriculaNEE implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_MATINFNEE")
	private Long id;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@ManyToOne
	@JoinColumn(name="X_ANCES")
	private CompensacionDesigualdad cEducativa;
	
	@ManyToOne
	@JoinColumn(name="X_ACNEES")
	private NecesidadEducativa nEducativa;
	
	
	

}
