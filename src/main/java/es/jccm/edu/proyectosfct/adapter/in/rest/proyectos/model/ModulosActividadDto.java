package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades.model.DatosProyectosFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Modulos a Actividades", description = "Descripcion para el modelo de asignar modulos a actividades")
public class ModulosActividadDto {

	
	@Schema(description = "Id de modulo a actividad")
	private Long id;
	
	@Schema(description = "Modulo curso")
	private ModulosCursoDto moduloCurso;
	
	@Schema(description = "Actividades")
	private DatosProyectosFctDto datoProyecto;
	
}
