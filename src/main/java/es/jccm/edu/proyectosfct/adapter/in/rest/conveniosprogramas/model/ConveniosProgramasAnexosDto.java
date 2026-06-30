package es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios programas anexos", description = "Descripcion para el modelo de donvenios programas anexos")
public class ConveniosProgramasAnexosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificar del convenio programa")
	private Long id;
	
	@Schema(description = "Idenficador del id Rodal")
	private String idAnexoRodal;
	
	@Schema(description = "Descripcion del fichero")
	private String nombreFichero;	


}
