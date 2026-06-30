package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Detalle firmante", description = "Descripcion para el modelo del detalle de un firmante")
public class DetalleFirmanteDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador del firmante")
	private Long id;
	
	@Schema(description = "Tipo de convocatoria")
	private String tipo;
	
	@Schema(description = "Nombre del documento")
	private String documento;
	
	@Schema(description = "Estado del documento")
	private String estado;
	
	@Schema(description = "Nombre del firmante")
	private String usuario;
	
	@Schema(description = "Orden de firma")
	private Integer orden;
	
	@Schema(description = "Fecha de la firma del documento")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fecha;
	
	@Schema(description = "Estado de la firma 0/1 Pendiente/Firmado")
	private Integer firmado;
	
	
}


