package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class PonderacionConv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idPonderacion;

	@OneToMany(mappedBy = "idRelacionCompe")
	private List<CompetenciasConv> competencias;
}