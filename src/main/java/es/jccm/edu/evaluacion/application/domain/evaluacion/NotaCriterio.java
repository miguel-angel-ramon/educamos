package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class NotaCriterio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idCriAlu;

	private Long idCalifica;

	private Long nota;

	private String descCal;

	private String aprueba;
}
