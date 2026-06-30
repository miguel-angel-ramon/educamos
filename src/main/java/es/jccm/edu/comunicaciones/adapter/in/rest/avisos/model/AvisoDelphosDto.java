package es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AvisoDelphos", description = "Avisos rescatados para el módulo de escritorio en función del grupo de aviso")
public class AvisoDelphosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del aviso")
	private Long idAviso;
	
	@Schema(description = "Contenido del aviso")
	private String mensaje;
	
	@Schema(description = "Fecha de inicio de vigencia")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaInicioVigencia;
	
	@Schema(description = "Fecha de fin de vigencia")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaFinVigencia;
	
	@Schema(description = "Comprobación de si está activo el aviso")
	private String activo;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Identificador del grupo de avisos al que pertenece")
	private Integer idGrupoAvisos;

}
