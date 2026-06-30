package es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Anexo Extraordinario Aux", description = "Descripcion para el modelo de autorizacion extraordinario auxiliar")
public class AnexoExtraordinarioAuxDto extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Texto de la justifiacion")
	private String txjustificacion;
	
	@Schema(description = "Texto de la control")
	private String txcontrol;
	
	@Schema(description = "Texto de los costes")
	private String txcostes;
	
}
