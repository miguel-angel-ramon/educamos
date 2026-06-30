package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Annos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long anno;

	private String tramo;

	private String annoActual;
}
