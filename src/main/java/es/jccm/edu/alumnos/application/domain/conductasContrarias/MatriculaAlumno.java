package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLMATALU")

public class MatriculaAlumno implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name="X_MATRICULA")
	private Long idMatricula;
	
	@OneToOne 
	@JoinColumn(name="X_ALUMNO")
	private CCAlumno alumno;
	
	@Column (name="C_ANNO")
	private Integer  anno;
	
	@Column (name="X_OFERTAMATRIC")
	private Long idOfertaMatriculacion;
	
	@Column (name="X_UNIDAD")
	private Long idUnidad;
	
	@OneToMany (mappedBy="matricula")
	private List<AlumnoConductaContraria> alumnoConContraria;
	



	
	

}
