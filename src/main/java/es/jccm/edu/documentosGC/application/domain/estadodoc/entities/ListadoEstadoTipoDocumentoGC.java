package es.jccm.edu.documentosGC.application.domain.estadodoc.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoEstadoTipoDocumentoGC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String abreviatura;
	
	private String nombre;
	
	private Date fInicio;	
	
	private Date fFin;
	
	private String esfinal;
	
	private Integer noborrable;
	
}

