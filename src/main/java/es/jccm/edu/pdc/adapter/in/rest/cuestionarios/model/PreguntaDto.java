package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Respuesta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Pregunta", description = "Descripcion para el modelo de Pregunta")
public class PreguntaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPregunta;

	private String titulo;

	private String descripcion;

	private Long orden;

	private String obligatoria;

	private String permiteTexto;

	private String codTipo;

	private String tipo;

	private List<Respuesta> listaRespuesta;
}
