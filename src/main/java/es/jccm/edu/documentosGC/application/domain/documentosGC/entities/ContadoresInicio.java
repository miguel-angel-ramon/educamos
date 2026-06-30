package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ContadoresInicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idTipo;
	
	private Integer nutotal;
	
	private Integer nuobl;
	
	private Integer nupf;
	
	private Integer numifir;	

}
