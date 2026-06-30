package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Horario", description = "Horario de un profesor rescatado de la BBDD de delphos")
public class HorarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del aviso")
	private Long idHorario;
	
	@Schema(description = "Id del colectivo")
	private Long idColectivo;
	
	@Schema(description = "Nombre del colectivo")
	private String colectivo;
	
	@Schema(description = "Título del aviso")
	private String titulo;
	
	@Schema(description = "Contenido del aviso")
	private String texto;
	
	@Schema(description = "Fecha de inicio de vigencia")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioVigencia;
	
	@Schema(description = "Fecha de fin de vigencia")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinVigencia;
	
	@Schema(description = "Organismo de procedencia")
	private String procedencia;

}
