package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ParteGeneradoDocumentosGC {	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private Date fremision;

	private Date fgenera;
	
	private String norden;
	
	private String nmes;
	
	private String canno;
	
	private String lgenerado;

}
