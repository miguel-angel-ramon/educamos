package es.jccm.edu.evaluacion.application.domain.ponderacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class CriterioList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idRelacionCri;

	private Long idCriterio;
	
	private String descripcionCri;

	private String codigoCri;

	private Float porcentajeCri;

	private Integer pesoCri;
	
	private Long idTipoOperacion;

	private String nombreTipoOperacion;

}