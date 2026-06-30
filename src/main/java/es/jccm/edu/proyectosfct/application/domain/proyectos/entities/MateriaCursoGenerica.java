package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TLMATERIASCURSO")
public class MateriaCursoGenerica implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_MATERIAC")
	private Long id;
	
	
	@Column(name="D_MATERIAC")
	private String descripcionMateria;

}
