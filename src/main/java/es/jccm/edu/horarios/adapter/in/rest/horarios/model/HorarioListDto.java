package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Horario", description = "Descripcion para el modelo de horario para la el módulo del escritorio")
public class HorarioListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Horario")
	private Integer idHorario;
	
	@Schema(description = "Fecha de inicio")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;

	@Schema(description = "Fecha de fin")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;
	
	@Schema(description = "Día de la semana")
	private Integer diaSemana;
	
	@Schema(description = "Hora de inicio")
	private String horaInicio;
	
	@Schema(description = "Hora de fin")
	private String horaFin;
	
	@Schema(description = "getHoraInicioCadena")
	private String horaInicioCadena;
	
	@Schema(description = "getHoraFinCadena")
	private String horaFinCadena;
	
	@Schema(description = "Requnidad")
	private String requnidad;
	
	@Schema(description = "Alumnos")
	private String alumnos;
	
	@Schema(description = "Id de Actividad")
	private Integer idActividad;
	
	@Schema(description = "Abreviatura de la Actividad")
	private String abreviaturaActividad;
	
	@Schema(description = "Descripción de la Actividad")
	private String descripcionActividad;
	
	@Schema(description = "Id de la Materia")
	private Integer idMateria;
	
	@Schema(description = "Id de la Unidad")
	private Integer idUnidad;
	
	@Schema(description = "Abreviatura de la Materia")
	private String abreviaturaMateria;
	
	@Schema(description = "Descripción de la Materia")
	private String descripcionMateria;
	
	@Schema(description = "Abreviatura del Aula")
	private String abreviaturaAula;
	
	@Schema(description = "Descripción del Aula")
	private String descripcionAula;
	
	@Schema(description = "Docencia")
	private String docencia;
	
	@Schema(description = "Minutos")
	private String minutos;
	
	@Schema(description = "Consolidado")
	private String consolidado;

}
