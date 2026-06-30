package es.jccm.edu.evaluacion.application.domain.ponderacion;

import javax.persistence.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CriteriosComEsp implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idPonderacion;

	private Long idCriterio;
}
