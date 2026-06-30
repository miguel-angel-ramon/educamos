package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo autorizacion desplazamiento", description = "Descripcion para el modelo de Tipo autorizacion desplazamiento")
public class TipoAutorizacionDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del tipo autorizacion")
	private Long id;
	
	@Schema(description = "Abreviatura del tipo autorizacion")
	private String abreviatura;

	@Schema(description = "Nombre del tipo autorizacion")
	private String nombre;
	
	@Schema(description = "Fecha inicio tipo autorizacion")
	private Date fechaInicio;
	
	@Schema(description = "Fecha fin tipo autorizacion")
	private Date fechaFin;
	
	@Schema(description = "Texto que se mostrará en pantalla con consideraciones a tener en cuenta en este tipo autorizacion")
	private String aviso;
}
