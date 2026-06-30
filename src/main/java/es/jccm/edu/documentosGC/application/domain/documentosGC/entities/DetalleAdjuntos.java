package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.util.List;

import lombok.Data;

@Data
public class DetalleAdjuntos {
	
	private Long idHistorial;
	
    private Long idCentro;
	
	private Long idAnno;
	
	private List<DetalleAdjuntosRequest> adjuntos;

}
