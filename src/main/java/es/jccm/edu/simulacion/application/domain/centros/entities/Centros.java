package es.jccm.edu.simulacion.application.domain.centros.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Centros implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCentro;
	
	private Long codCentro;
	
	private String tipo;
	
	private String denominacion;
	
	private String nombre;

}
