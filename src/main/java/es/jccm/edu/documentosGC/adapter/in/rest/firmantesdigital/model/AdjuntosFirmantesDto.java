package es.jccm.edu.documentosGC.adapter.in.rest.firmantesdigital.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc.DgcEmpleadoDto;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.AdjuntosDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Adjuntos firmantes", description = "Adjuntos firmantes")
public class AdjuntosFirmantesDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Id historial adjunto")
	private AdjuntosDocumento idAdjunto;	
	
	@Schema(description = "Orden de la firma")
	private Integer orden;	
	
	@Schema(description = "Id empleado")
	private DgcEmpleadoDto idEmpleado;	
	
	@Schema(description = "Indica el tipo de firma")
	private String tipoFirma;	
	
	@Schema(description = "Indica si esta firmado")
	private Integer isFirmado;	

	@Schema(description = "Fecha de la firma")
	private Date fechaFirma;	
	
	@Schema(description = "Descripcion")
	private String descripcion;	
}
