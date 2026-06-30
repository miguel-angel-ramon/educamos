package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Respuesta", description = "Descripcion para el modelo de las Respuestas")
public class RespuestaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idRespuesta;

	private String nombre;

	private String valor;

	private Long orden;



}
