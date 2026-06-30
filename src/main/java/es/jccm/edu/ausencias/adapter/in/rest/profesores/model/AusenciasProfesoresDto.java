package es.jccm.edu.ausencias.adapter.in.rest.profesores.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AusenciasProfesores", description = "Dto del modelo de AusenciaProfesores")
public class AusenciasProfesoresDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador de la ausencia")
	private Long idAusencia;
	
	@Schema(description = "Identificador interno de empleado")
	private Long idEmpleado;

	@Schema(description = "Motivo")
	private String motivo;
	
	@Schema(description = "Fecha de inicio de la ausencia")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioAusencia;

	@Schema(description = "Fecha de fin de la ausencia")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinAusencia;
	
	@Schema(description = "Nombre empleado")
	private String nombre;
	
	@Schema(description = "Teléfono")
	private String telefono;
	
	@Schema(description = "Correo")
	private String correo;

}
