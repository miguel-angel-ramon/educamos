package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades.model.DatosProyectosFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno", description = "Descripcion para el modelo de alumno proyecto")
public class ParsemActProyDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la tabla ParsemActProg")
	private Long id;
	// ---------- Relationships -----------	
	
	@Schema(description = "Id de la tabla ParsemAluProgDto")
	private ParsemAluProyDto parsemAluProy;
	
	@Schema(description = "Id Datos del programa")
	private DatosProyectosFctDto datosProyecto;

}
