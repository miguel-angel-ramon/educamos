package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLTIPCORCONNEG")
public class TipoCorreccionConductaNegativa implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_TIPCORCONNEG")
	private Long id;
	
	@Column (name="D_TIPCORCONNEG")
	private String descripcion;
	
	@OneToOne
	@JoinColumn(name="X_ALUCONCON")
	private AlumnoConductaContraria idAlumnoConCon;
	
	@Column(name="N_ORDPRE")
	private Integer orden;

	

}
