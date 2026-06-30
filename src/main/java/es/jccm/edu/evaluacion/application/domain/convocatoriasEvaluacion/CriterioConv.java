package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class CriterioConv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idRelacionCri;

	private Long idCriterio;
	
	private String descripcion;

	private String codigo;

	private Float peso;

	private String valoracion;

	private String aprueba;

}