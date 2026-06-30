package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoTutoresFctDualDto", description = "Descripcion para el modelo de listado de tutores dto")
public class ListadoTutoresFctDualDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del tutor")
	private Long id;

	@Schema(description = "Nombre completo del tutor")
	private String nombreCompleto;
	
	@Schema(description = "Identificador del tutor")
	private String dni;

	@Schema(description = "Fecha de inicio de la tutoria")
	private String fechaInicioTutoria;
	
	@Schema(description = "Fecha de finalización de la tutoria")
	private String fechaBaja;

}
