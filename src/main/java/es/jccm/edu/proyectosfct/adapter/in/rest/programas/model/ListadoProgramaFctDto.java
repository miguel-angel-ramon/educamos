package es.jccm.edu.proyectosfct.adapter.in.rest.programas.model;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoProgramasDto", description = "Descripcion para el modelo de listado de programas dto")
public class ListadoProgramaFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del programa")
	private Long id;

	@Schema(description = "Nombre de programa")
	private String descripcion;
	
	@Schema(description = "Tutor del programa")
	private String tutor;
	
	@Schema(description = "Provincia del programa")
	private String provincia;
	
	@Schema(description = "Centro del programa")
	private Long centro;
	
	@Schema(description = "Familia del programa")
	private String familia;
	
	@Schema(description = "Oferta del programa")
	private String ofertaMatriculaGenerico;
	
	@Schema(description = "Identificador del tutor del programa")
	private Long idTutorFct;
	
	@Schema(description = "Identificador del tutor del programa")
	private String convenio;

}
