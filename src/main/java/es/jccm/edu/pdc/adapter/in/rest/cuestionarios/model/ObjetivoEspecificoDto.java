package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Cuestionario", description = "Descripcion para el modelo de cuestionario")
public class ObjetivoEspecificoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idObjEsp;
	
	private Long idObjetivo;
	
	private String descripcion;
	
	private String activo;

	private List<LineaActuacionDto> lineasActuacion;

}
