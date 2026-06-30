package es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DetalleFirmante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String tipo;
	
	private String documento;
	
	private String estado;
	
	private String usuario;
	
	private Integer orden;	

	private Date fecha;	
	
	private Integer firmado;
	
	

}
