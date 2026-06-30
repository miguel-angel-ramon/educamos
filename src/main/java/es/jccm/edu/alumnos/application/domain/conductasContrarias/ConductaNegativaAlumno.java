package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLCONNEGALU")
public class ConductaNegativaAlumno implements Serializable {
	private static final long  serialVersionUID=1L;
	
	@Id
	@Column (name="X_CONNEGALU")
	private Long id;
	
	@OneToOne 
	@JoinColumn(name="X_ALUCONCON")
	private AlumnoConductaContraria alumnoCondContraria;
	
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_TIPCONNEG")
	private TipoCondNegativa tipoCondNegativa;
	
	
	
	
	

}
