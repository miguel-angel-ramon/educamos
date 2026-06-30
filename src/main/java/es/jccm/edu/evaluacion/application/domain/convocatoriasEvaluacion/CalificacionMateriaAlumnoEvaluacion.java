package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CalificacionMateriaAlumnoEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idConvCentroOmc;
	
	private Long idConvocatoria;

	private Long idCalificaCal;

	private String notaCal;
	
	private String apruebaCal;

	private Long idCalificaDef;
	
	private String notaDef;
	
	private String apruebaDef;
	
}
