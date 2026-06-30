package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class ObjetivoGeneral implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idGrupoObjetivo;

	private Long idObjetivo;

	private Long idCompetencia;

	private Long codCompetencia;

	private String desCompetencia;

	private String tituloCompetencia;

	private Long idSubDimension;

	private Long tipObjetivo;

	private String activo;

	private String desObjetivo;


}
