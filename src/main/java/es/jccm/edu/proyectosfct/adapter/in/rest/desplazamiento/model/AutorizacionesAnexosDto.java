package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;


import java.io.Serializable;

import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.PeriodoGastoDto;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion anexo", description = "Descripcion para el modelo de autorizacion de anexos")
public class AutorizacionesAnexosDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador de la autorizacion")
	private Long id;
	
	@Schema(description = "Identificador del periodo")
	private PeriodoGastoDto periodoGasto;
	
	@Schema(description = "Identificador del periodo")
	private TipoAutorizacionDto tipo;
	
	@Schema(description = "Anno")
	private Integer idAnno;
	
	@Schema(description = "Identificador del centro")
	private Long idCentro;
	
	@Schema(description = "Identificador del tutor")
	private Long idTutorFct;
	
	@Schema(description = "Identificador de la familia")
	private Long idFamilia;
	
	@Schema(description = "Identificador del curso")
	private Long idCurso;
	
	@Schema(description = "Identificador de la unidad")
	private Long idUnidad;
	
	@Schema(description = "Justificacion")
	private String txtJustificacion;
	
	@Schema(description = "Control")
	private String txtControl;
	
	@Schema(description = "Costes")
	private String txtCostes;
	
	

	
	
}
