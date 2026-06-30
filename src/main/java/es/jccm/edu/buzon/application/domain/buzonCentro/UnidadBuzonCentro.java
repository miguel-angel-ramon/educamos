package es.jccm.edu.buzon.application.domain.buzonCentro;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class UnidadBuzonCentro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idOfertamatrig;

	private String unidad;
	
	private Long idEtapa;

	private Long idEtapaSec;

	private Long idCiclo;
	
	private String curso;
	
	private Long idCurso;
	
	private String etapa;

	private Long idPonderacion;

	@Id
	private Long idUnidad;

}
