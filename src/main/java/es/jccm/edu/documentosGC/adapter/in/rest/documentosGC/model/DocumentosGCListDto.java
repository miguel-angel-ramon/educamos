package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios", description = "Descripcion para el modelo de Documentos para la pantalla multirregistros")
public class DocumentosGCListDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Tipo")
	private String tipo;	
	
	@Schema(description = "Estado")
	private String estado;	
	
	@Schema(description = "Descripcion")
	private String descripcion;	
	
	@Schema(description = "Fecha registro")
	private Date fhregistro;	
	
	@Schema(description = "Id del fichero de Rodal")
	private String idRodal;	
	
	@Schema(description = "Nombre del fichero de Rodal")
	private String fichero;		
	
	@Schema(description = "Nombre del fichero de Rodal")
	private String provincia;		
	
	@Schema(description = "Nombre del fichero de Rodal")
	private String municipio;		
	
	@Schema(description = "Nombre del fichero de Rodal")
	private String centro;		
	
	@Schema(description = "Descripcion del parte mensual")
	private String dsParaus;	
	
	@Schema(description = "Descripcion del aviso que se presenta para el cambio de estado")
	private String aviso;	
	
	@Schema(description = "Indica si se anexan adjuntos distintos al principal")
	private Integer permiteadjuntos;
	
	@Schema(description = "Identificador del adjunto")
	private Long idAdjunto;
	
	@Schema(description = "Indica si el documento se puede firmar")
	private Integer permitefirmar;
	
	@Schema(description = "Indica el total de adjuntos")
	private Integer totalAdjuntos;
	
	@Schema(description = "Indica la descripcion del aviso que debe aparecer en la columna Adjuntos pendientes de firma")
	private String aviso2;	
	
	@Schema(description = "Indica la descripcion del aviso que debe aparecer en la columna Adjuntos pendientes de mi firma")
	private String aviso3;

}
