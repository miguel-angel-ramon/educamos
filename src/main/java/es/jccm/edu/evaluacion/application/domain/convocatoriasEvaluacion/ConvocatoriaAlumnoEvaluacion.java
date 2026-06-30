package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ConvocatoriaAlumnoEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idConvocatoria;
	
	private String nombreConvocatoria;
	
	private String descripcionConvocatoria;
	
	private Long idConvCentroOmc;
	
	private String estado;
	
	private String observaciones;
	
	private Boolean esFinal;
	
}
