package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ObservacionPKConv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "X_MATRICULA")
	private Long idMatricula;
	
	@Column(name = "X_CONVCENTROOMC")
	private Long idConvCentroOmc;
	
}
