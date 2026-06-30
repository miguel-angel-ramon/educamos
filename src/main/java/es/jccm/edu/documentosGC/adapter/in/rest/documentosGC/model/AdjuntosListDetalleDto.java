package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Lista adjuntos", description = "Descripcion para el modelo de actilización de  adjuntos")
public class AdjuntosListDetalleDto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long idAdjunto;
	
	@Schema(description = "Id tipo de documento")
	private Long idTipoDoc;	
	
	@Schema(description = "Id tipo de adjunto")
	private Long idTipoAdjunto;	
	
	@Schema(description = "Orden del adjunto")
	private Integer orden;	
	
	@Schema(description = "¿Es tipo de adjunto principal?")
	private Integer principal;	
	
	@Schema(description = "Nombre")
	private String nombre;	
	
	@Schema(description = "Descripcion")
	private String descripcion;	
	
	@Schema(description = "¿Es tipo firmable?")
	private Integer firmable;	
	
	@Schema(description = "Tamaño maximo del tipo de adjunto")
	private Integer tamano;	
	
	@Schema(description = "Año inicial de vigencia de tipo de adjunto")
	private Integer annodesde;	
	
	@Schema(description = "Año final de vigencia de tipo de adjunto")
	private Integer annohasta;	
	
	@Schema(description = "Id histoial")
	private Long idHistorial;	
	
	@Schema(description = "¿Está firmado?")
	private Integer lgFirmado;	
	
	@Schema(description = "Idenficacion Rodal")
	private String IdDocHisRodal;	
	
	@Schema(description = "Descripcion fichero Rodal")
	private String TxDocHisFichero;	
	
	@Schema(description = "Descripcion de la fecha que pone en pantalla")
	private String fechaPantalla;	

}

