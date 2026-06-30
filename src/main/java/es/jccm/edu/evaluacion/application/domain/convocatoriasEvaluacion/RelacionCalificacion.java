package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class RelacionCalificacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idRelacionCalificacion;
	
	private Long idCalificacion;
	
	private String descCal;
	
	private String aprueba;
	
	private Float minima;
	
	private Float maxima;
	
}
