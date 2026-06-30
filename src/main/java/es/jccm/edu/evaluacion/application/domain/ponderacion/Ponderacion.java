package es.jccm.edu.evaluacion.application.domain.ponderacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Ponderacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idPonderacion;
	
	private Long idMateria;

	private Long idDocente;

	private String nombreMateria;

	private String editable;

	@OneToMany(mappedBy = "idRelacionCompe")
	private List<CompetenciasEspecificas> competencias;
}