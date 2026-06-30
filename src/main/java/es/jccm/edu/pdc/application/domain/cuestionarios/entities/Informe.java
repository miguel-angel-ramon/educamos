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
public class Informe implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codCentro;

	private Long idCompetencia;
	private String sector;

	private String nivel;

	private String codCompetencia;

	private String descCompetencia;

	private Long valor;

	private Long respuestas;

	private Long anno;
	
	@ElementCollection(targetClass = String.class)
	private List<String> descripcionesObjetivo;


}
