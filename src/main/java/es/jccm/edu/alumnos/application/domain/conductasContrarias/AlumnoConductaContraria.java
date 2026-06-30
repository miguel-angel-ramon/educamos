package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="TLALUCONCON")
public class AlumnoConductaContraria implements Serializable {
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_ALUCONCON")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="X_MATRICULA")
	private MatriculaAlumno matricula;
	
	@Column(name="F_ALUCONCON")
	private Date  fechaCondContraria;
	
	@Column(name="D_ALUCONCON")
	private String  DescripcionCondContraria;
	
	@Column (name="X_CONCONCOL")
	private Long idConductaColectiva;
	

	
	
	
	
	
	

}
