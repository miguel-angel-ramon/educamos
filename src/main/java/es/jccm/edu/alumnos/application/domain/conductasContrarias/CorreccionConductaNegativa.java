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
@Table(name="TLCORCONNEGALU")
public class CorreccionConductaNegativa implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_CORCONNEGALU")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="X_TIPCORCONNEG")
	private TipoCorreccionConductaNegativa tipoCorrreccion;
	
	@OneToOne
	@JoinColumn(name="X_ALUCONCON")
	private AlumnoConductaContraria alumnoConCon;


}
