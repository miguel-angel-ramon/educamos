package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CentroAlumnos {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOMBRECOMPLETO")
	private String nombreCompleto;
	
	@Column(name="CENTROPROVINCIA")
	private String centroProvincia;


}
