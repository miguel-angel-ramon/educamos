package es.jccm.edu.pdc.application.domain.planActuacion.entities;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoEspecifico;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class AmbitoCompleto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long xCentro;

	private Long idCompetencia;

	private String sector;

	private String nivel;

	private Long codCompetencia;

	private String descCompetencia;

	private Long valor;

	private Long respuestas;

	private Long anno;

	private DetalleAmbito detalleAmbitos;

	@OneToMany(mappedBy = "idObjEsp")
	private List<ObjetivoEspecifico> objetivosEspecificos;

}
