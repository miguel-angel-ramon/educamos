package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class PuntoPartida implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long idOpcion;

	private String desCompetencia;

	private String desPregunta;

	private String desOpcion;
	
	private Long x_cuepre;

	private String comoMejorar;


}
