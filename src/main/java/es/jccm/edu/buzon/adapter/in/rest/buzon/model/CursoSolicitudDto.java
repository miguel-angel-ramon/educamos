package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CursoSolicitud", description = "Cursos y alumnos para solicitud de correo")
public class CursoSolicitudDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la etapa")
	private Long idEtapa;
	
	@Schema(description = "Nombre de la etapa")
	private String nombreEtapa;
	
	@Schema(description = "Id del curso")
	private Long idCurso;
	
	@Schema(description = "Nombre del curso")
	private String nombreCurso;
	
	@Schema(description = "Estado de la solicitud")
	private String estadoSolicitud;
	
	@Schema(description = "Número de alumnos con correo habilitado")
	private Long numAlumNoHab;
	
	@Schema(description = "Número de alumnos con correo habilitado")
	private Long numAlumHab;
	
	@Schema(description = "Fecha de la última solicitud")
	private Date fechaSolicitud;

}
