package es.jccm.edu.documentosGC.adapter.in.rest.perfiles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Perfiles", description = "Descripcion para el modelo de TLPERFILES")
public class PerfilGCDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description ="D_PERFIL")
	private String descripcion;
	
	@Schema(description ="C_CODIGO")
	private String codigo;
	
	@Schema(description ="C_AMBVISCEN")
	private String ambito;

}
