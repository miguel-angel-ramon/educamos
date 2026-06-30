package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PartesMensualesDocumentosGC {	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String descripcion;

}
