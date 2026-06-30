package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoGeneral;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.PuntoPartida;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Sugerencia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Detalle Ámbito", description = "Descripcion para el modelo del detalle del ámbito")
public class DetalleAmbitoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ObjetivoGeneral> objetivoGeneral;

	private List<Sugerencia> sugerencia;

	private List<PuntoPartida> puntoPartida;



}
