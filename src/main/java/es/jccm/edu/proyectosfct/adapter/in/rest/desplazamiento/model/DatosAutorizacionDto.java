package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizaciones desplazamiento", description = "Descripcion para el modelo de autorizaciones desplazamiento")
public class DatosAutorizacionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Fecha inicio")
	private String fini;
	
	@Schema(description = "Fecha fin")
	private String ffin;
	
	@Schema(description = "Itinerario")
	private String itinerario;
	
	@Schema(description = "Horario")
	private String horario;

}
