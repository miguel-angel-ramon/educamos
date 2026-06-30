package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MensajeColectivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private String codigoColectivo;
	
	private String descripcionCortaColectivo;
	
	private String descripcionLargaColectivo;
	
	private Boolean permiteFiltrar;
	
	private Boolean permiteRespuesta;
	
}
