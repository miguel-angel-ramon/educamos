package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Horario personal", description = "Rescata el horario personal")
public class HorarioPersonalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de registro")
	private Long idRegistro;

	@Schema(description = "Fecha anotación en la agenda personal.")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaAnotacion;

	@Schema(description = "Hora de la anotación")
	private String hora;

	@Schema(description = "Título de la anotación")
	private String titulo;

	@Schema(description = "Día de la anotación")
	private String dia;

}

