package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoEspecifico;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.DetalleAmbito;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Informe", description = "Informe generado.")
public class AmbitoCompletoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long xCentro;

	private Long idCompetencia;

	private String sector;

	private String nivel;

	private Long codCompetencia;

	private String descCompetencia;

	private Long valor;

	private Long respuestas;

	private Long anno;

	private DetalleAmbitoDTO detalleAmbitos;

	private List<ObjetivoEspecificoDto> objetivosEspecificos;

}
