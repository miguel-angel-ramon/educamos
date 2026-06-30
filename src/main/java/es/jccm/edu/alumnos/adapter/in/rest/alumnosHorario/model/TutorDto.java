package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.TlefDetalle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tutor", description = "Tutores de la base de datos de comunica")
public class TutorDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Tutor")
	private Long idTutor;

	@Schema(description = "Nombre del tutor")
	private String nombre;

	@Schema(description = "Primer apellido del tutor")
	private String apellido1;

	@Schema(description = "Segundo apellido del tutor")
	private String apellido2;

	@Schema(description = "Nº de identificacion")
	private String numide;

	@Schema(description = "Telefono")
	private String telefono;

	@Schema(description = "Telefono urgencia")
	private String tlefnourg;

	@Schema(description = "Lista de telefonos")
	private List<TlefDetalle> listTelf;
}
