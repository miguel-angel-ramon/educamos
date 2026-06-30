package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.anexosPFE.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Info Anexos", description = "Descripcion para el info de los anexos.")
public class InfoAnexosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "idRodal")
	private String idRodal;

	@Schema(description = "fichero")
	private String fichero;
	
	@Schema(description = "tipo")
	private String tipo;



}
