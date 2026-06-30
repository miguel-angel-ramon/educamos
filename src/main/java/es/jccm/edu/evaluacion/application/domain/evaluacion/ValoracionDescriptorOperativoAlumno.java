package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ValoracionDescriptorOperativoAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idDescriptorOperativo;
	
	private Long id;
	
	private String nombreDescriptorOperativo;
	
	private Long idCalifica;
	
	private String descCal;

	private Long nota;

	private String aprueba;
	
	private String descDetCal;

	private Boolean notaCambiada = false;
}
