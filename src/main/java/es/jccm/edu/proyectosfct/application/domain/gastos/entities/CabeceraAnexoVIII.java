package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CabeceraAnexoVIII implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String provincia;
	
	private String liquidacion;

	private Integer numCentros;
	
	private Double totalTutores;
	
	private Double totalAlumnado;
	
	private Double total;
	
	private String nombre;
	
}