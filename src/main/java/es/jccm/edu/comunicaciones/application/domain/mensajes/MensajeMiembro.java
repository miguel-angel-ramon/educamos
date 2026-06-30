package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MensajeMiembro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String descripcion;

}
