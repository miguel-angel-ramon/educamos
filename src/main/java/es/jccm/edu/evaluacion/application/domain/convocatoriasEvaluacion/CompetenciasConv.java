package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import es.jccm.edu.evaluacion.application.domain.ponderacion.CriterioList;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class CompetenciasConv implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long idRelacionCompe;

	private Long idCompetencia;
	
	private String codigo;
	
	private String descripcion;

	private Float peso;

	private String valoracion;

	private String aprueba;

	@OneToMany(mappedBy = "idRelacionCri")
	private List<CriterioConv> criterios;
}
