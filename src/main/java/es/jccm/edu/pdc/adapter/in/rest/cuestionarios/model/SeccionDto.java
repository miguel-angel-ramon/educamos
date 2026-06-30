package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Seccion", description = "Descripcion para el modelo de Secciones")
public class SeccionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idSeccion;
	
	private String nombre;
	
	private String descripcion;

	private Integer orden;
	
	private List<PreguntaDto> preguntas;

}
