package es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ProyectosDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosProyectosFct", description = "Descripcion para el modelo de Datos Proyectos FCT")
public class DatosProyectosFctDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id datos proyectos")
	private Long id;
	
	@Schema(description = "Descripcion actividades formativas")
	private String actividadFormativo;
	
	@Schema(description = "Descripcion actividades evaluacion")
	private String actividadEvaluacion;
	
	@Schema(description = "Descripcion resultado")
	private String resultado;
	
	@Schema(description = "Descripcion criterios")
	private String criterios;
	
	@Schema(description = "orden del programa")
	private Integer orden;
	
	// ----------- Relationships ------------
	
	@Schema(description = "Proyecto")
	private ProyectosDto proyecto;
	
}
