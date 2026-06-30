package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades.model.DatosProyectosFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectoAlumnoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "EvaAluActProyDto", description = "Descripcion para el modelo de evaluacion alumno datos proyecto")
public class EvaAluActProyDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la tabla FCT_EVAALU_ACTPROY")
	private Long id;
	
	@Schema(description = "Actividad realizada")
	private Integer realizada;
	
	@Schema(description = "Criterio adquirido")
	private Integer adquirida;
	
	@Schema(description = "Observaciones")
	private String observaciones;

	@Schema(description = "criterios")
	private String criterios;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Convenio proyecto alumno")
	private ConveniosProyectoAlumnoDto aluConvProy;
	
	@Schema(description = "Datos proyecto")
	private DatosProyectosFctDto datosProyecto;

}
