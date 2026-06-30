package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="TLALUMNOS")
public class Alumnado implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="X_ALUMNO")
	private Long id;

	@Column(name="T_NUSS")
	private String tnuss;

}
