package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class NuevoParte {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;	
	private Integer centro;	
	private Integer anno;
	private Integer mes;	
	private Date fremision;	
	private Integer annonatural;	
	private Integer usuario;

}
