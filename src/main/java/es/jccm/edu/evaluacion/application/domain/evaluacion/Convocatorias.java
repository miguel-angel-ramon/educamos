package es.jccm.edu.evaluacion.application.domain.evaluacion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Convocatorias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idConvCentro;

	private Long idConvCentroOmc;

	private Long estadoConv;

	private String descEstadoConv;

	private Long idTipoConv;

	private String convocatoria;

	private String nombreMaterias;
}
