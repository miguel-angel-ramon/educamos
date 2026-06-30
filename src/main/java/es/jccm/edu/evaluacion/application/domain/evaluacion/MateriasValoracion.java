package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class MateriasValoracion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idMateria;

	private Long idEtapa;

	private String materia;

	private Long idOfertaMatrig;

	private Long idOfertaMatric;

	private Long cerrada;

	private Boolean pendiente;

	private Long idOfertaMatrigAlumno;

	private Boolean hayProgDidac;
}
