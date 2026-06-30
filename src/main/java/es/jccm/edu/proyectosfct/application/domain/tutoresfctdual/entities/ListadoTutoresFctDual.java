package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoTutoresFctDual implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombreCompleto;
	
	private String dni;	
	
	private String fechaInicioTutoria;	
	
	private String fechaBaja;

}


