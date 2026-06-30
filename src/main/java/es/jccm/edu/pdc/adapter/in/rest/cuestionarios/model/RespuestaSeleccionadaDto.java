package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Respuesta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Respuesta seleccionada", description = "Descripcion para el modelo de respuesta seleccionada")
public class RespuestaSeleccionadaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCuePubUsu;

	private Long idPregunta;

	private Long idRespuesta;

	private String textoRespuesta;
}
