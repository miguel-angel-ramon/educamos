package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class HistDocumentosGC {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date fecha;
	private String usuario;
	private String estado;
	private String idRodal;
	private String documento;
	private String comentarios;
	private Integer lgfirmable;
	private String lgfirmado;
	private Long idadjunto;
	

}
