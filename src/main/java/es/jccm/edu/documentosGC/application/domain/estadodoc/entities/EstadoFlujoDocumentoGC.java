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
public class EstadoFlujoDocumentoGC implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFlujo;
	
	private String dsAbrev;
	
	private String dsNombre;
	
	private Date fhInicio;
	
	private Date fhFin;
	
	private Integer lgFinal;
	
	private String txAviso;
	
	private Integer adjunto;

}
