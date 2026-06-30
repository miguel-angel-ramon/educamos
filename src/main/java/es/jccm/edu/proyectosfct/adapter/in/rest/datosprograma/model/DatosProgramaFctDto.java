package es.jccm.edu.proyectosfct.adapter.in.rest.datosprograma.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ProgramaFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosProgramaFct", description = "Descripcion para el modelo de Datos Programas FCT")
public class DatosProgramaFctDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id datos programa")
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
	
	@Schema(description = "Programa")
	private ProgramaFctDto programa;
	
}
