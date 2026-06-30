package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Informacion Estodo documento", description = "Descripcion para el modelo información de un estado de documento")
public class InformacionEstadoPojectionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Usuario")
	private String usuario;
	
	@Schema(description = "Estado")
	private String estado;	
	
	@Schema(description = "Fecha registro")
	private Date fhregistro;	
	
	@Schema(description = "Id fichero")
	private String idFichero;	
	
	@Schema(description = "Id fichero")
	private String nombreFichero;	
	
	@Schema(description = "Onservaciones documento")
	private String observaciones;
	
	@Schema(description = "Aviso cambio estado")
	private String aviso;

}


