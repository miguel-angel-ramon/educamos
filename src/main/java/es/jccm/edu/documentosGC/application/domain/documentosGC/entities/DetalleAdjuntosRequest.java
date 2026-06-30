package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import lombok.Data;

@Data
public class DetalleAdjuntosRequest {
	
	private Long idAdjunto;
	
	private Long idTipoAdjunto;

	private String idDocHisRodal;

	private String operacion;
	
	private String firmable;
	
}
