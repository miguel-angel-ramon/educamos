package es.jccm.edu.evaluacion.application.domain.ponderacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class CompetenciasEspecificas implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long idRelacionCompe;

	private Long idCompetencia;
	
	private String codigoCompe;
	
	private String descripcionCompe;
	
	private Float porcentajeCompe;

	private Integer pesoCompe;

	@OneToMany(mappedBy = "idRelacionCri")
	private List<CriterioList> criterios;
}
