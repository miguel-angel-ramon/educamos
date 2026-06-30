package es.jccm.edu.pdc.application.domain.evaluacion.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class EvaluacionHome implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long xSeguiLinAct;
	
	private Integer porcEjec;
		
	private String estado;
	
	private Date factualizacion;
	
}
