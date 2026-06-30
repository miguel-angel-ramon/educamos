package es.jccm.edu.pdc.application.domain.planActuacion.entities;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoEspecifico;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoGeneral;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.PuntoPartida;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Sugerencia;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class DetalleAmbito implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCompetencia;

	@ManyToMany(mappedBy = "idObjetivo")
	private List<ObjetivoGeneral> objetivoGeneral;

	@OneToMany(mappedBy = "idSugNiv")
	private List<Sugerencia> sugerencia;

	@OneToMany(mappedBy = "idOpcion")
	private List<PuntoPartida> puntoPartida;

	
}
