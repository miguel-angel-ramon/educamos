package es.jccm.edu.evaluacion.application.domain.evaluacion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class UnidadEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idUnidad;

	private String unidad;
	
	private Long idCurso;
	
	private String curso;
	
	private Long idEtapa;

	private Long idEtapaSec;

	private Long idCiclo;
	
	private String etapa;

	private Long idPonderacion;
	
	private Long idOfertamatrig;

	private Long idOfertamatric;

	private Integer competenciasEvaluadas;

	private String nombreMaterias;

}
