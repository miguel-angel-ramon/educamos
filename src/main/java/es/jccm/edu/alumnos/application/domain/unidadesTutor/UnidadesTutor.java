package es.jccm.edu.alumnos.application.domain.unidadesTutor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UnidadesTutor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	Long idUnidad;
	
	String nombreUnidad;
	
	Date fechaTomaPosesion;

	Date fechaCese;

	String sustituye;

	String codigoCentro;

}
