package es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BandejaFirmasList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String principal;
	
	private String tipodocumento;
	
	private String tipoadjunto;
	
	private String nombre;
	
	private Date fechageneracion;
	
	private Date fechafirma;
	
	private Integer lgfirmado; 
	
	private String estado;
	
	private String idrodal;
	
	private String fichero;

	private Long idAdjunto;

	private Integer permitefirmar;
	

}


