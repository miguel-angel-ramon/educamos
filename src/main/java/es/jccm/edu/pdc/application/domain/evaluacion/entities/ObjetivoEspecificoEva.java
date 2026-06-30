package es.jccm.edu.pdc.application.domain.evaluacion.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.LineaActuacionDto;
import lombok.Data;

@Data
@Entity
public class ObjetivoEspecificoEva implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idObjEsp;
	
	private Long idObjetivo;
	
	private Long idCentro;
	
	private String descripcion;
	
	private Integer anno;

}