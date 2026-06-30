package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class DocumentosGCList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String tipo;	
	
	private String estado;	
	
	private String descripcion;	
	
	private Date fhregistro;
	
	private String idRodal;	
	
	private String fichero;	
	
	private String provincia;	
	
	private String municipio;	
	
	private String centro;	
	
	private String dsParaus;
	
	private String aviso;
	
	private Integer permiteadjuntos;
	
	private Long idAdjunto;
	
	private Integer permitefirmar;
	
	private Integer totalAdjuntos;
	
	private String aviso2;
	
	private String aviso3;

}
