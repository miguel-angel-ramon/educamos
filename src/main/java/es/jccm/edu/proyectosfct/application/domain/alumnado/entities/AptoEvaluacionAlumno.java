package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AptoEvaluacionAlumno implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="DESCRIPCIONCORTA")
	private String descripcionCorta;
	
	@Column(name="DESCRIPCIONLARGA")
	private String descripcionLarga;
	
}
