package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Estado autorizacion", description = "Descripcion para el modelo de estado autorizacion")
public class EstadoAutorizacionDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del estado/autorizacion")
	private Long id;
	
	@Schema(description = "Abreviatura del estado/autorizacion")
	private String abreviatura;
	
	@Schema(description = "Nombre del estado/autorizacion")
	private String nombre;
	
	@Schema(description = "Fecha inicio estado/autorizacion")
	private Date fechaInicio;
	
	@Schema(description = "Fecha fin estado/autorizaciono")
	private Date fechaFin;
	
	@Schema(description = "Texto que se mostrará en pantalla con consideraciones a tener en cuenta en este estado")
	private String aviso;
}
