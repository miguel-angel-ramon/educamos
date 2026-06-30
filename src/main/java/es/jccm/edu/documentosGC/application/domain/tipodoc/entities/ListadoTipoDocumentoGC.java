package es.jccm.edu.documentosGC.application.domain.tipodoc.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoTipoDocumentoGC implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer orden;
	
	private String abrev;
	
	private String descripcion;	
	
	private Integer anual;	
	
	private Integer annodesde;
	
	private Integer annohasta;
	
	private String lgObligatorio;
	
	private Integer noborrable;
	
	private String nombrepadre;	

}



