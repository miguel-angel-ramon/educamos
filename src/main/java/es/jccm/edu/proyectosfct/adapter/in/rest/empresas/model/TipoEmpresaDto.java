package es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoEmpresaDto", description = "Descripcion para el modelo de Tipos de Empresa")
public class TipoEmpresaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id tipo empresa")
	private Long id;
	
	@Schema(description = "Decripcion del tipo de empresa")
	private String descripcionTipo;


}
