package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion estado Flujo", description = "Descripcion para el modelo estado Flujo")
public class AutorizacionFlujoSiguienteDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gasto estado flujo")
	private Long id;
	
	@Schema(description = "Abreviatura")
	private String dsabrev;
	
	@Schema(description = "Nombre")
	private String dsnombre;
	
	@Schema(description = "Fecha inicio")
	private Date fhinicio;
	
	@Schema(description = "Fecha fin")
	private Date fhfin;

	@Schema(description = "Texto que se mostrará en pantalla con consideraciones a tener en cuenta en este tipo gasto")
	private String txaviso;
	
	@Schema(description = "Requiere adjunto")
	private Integer adjunto;
	
	@Schema(description = "Id del perfil")
	private Long idPerfil;
}
