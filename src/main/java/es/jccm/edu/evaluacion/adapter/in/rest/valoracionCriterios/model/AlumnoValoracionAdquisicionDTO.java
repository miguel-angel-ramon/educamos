package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno en la valoración de su adquisición curricular", description = "Valoración de la adquisición curricular de los alumnos por unidad")
public class AlumnoValoracionAdquisicionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Alumno")
	private Long idAlumno;
	
	@Schema(description = "Id Matrícula")
	private Long idMatricula;
	
	@Schema(description = "Id Unidad del centro")
	private Long idUnidadCentro;
	
	@Schema(description = "Id Oferta de matrícula genérica")
	private Long idOfertamatrig;
	
	@Schema(description = "Id Etapa")
	private Long idEtapa;
	
	@Schema(description = "Número escolar del Alumno")
	private String numEscolar;
	
	@Schema(description = "Nombre de la unidad del centro")
	private String nombreUnidadCentro;
	
	@Schema(description = "Descripción de la oferta de matrícula genérica")
	private String nombreCurso;
	
	@Schema(description = "Nombre de la etapa")
	private String nombreEtapa;
	
	@Schema(description = "Nombre y apellidos del alumno")
	private String nombreAlumno;
	
	@Schema(description = "Es ACNEE")
	private Integer acnee;
	
	@Schema(description = "Nivel curricular alumno ACNEE")
	private String nivelCurricular;
	
	@Schema(description = "Id Etapa adaptación alumno ACNEE")
	private Long idEtapaAdaptacion;
	
	@Schema(description = "Foto del alumno")
    private byte[] foto;
	
	@Schema(description = "Valoraciones temporales de las competencias clave del alumno")
	private List<ValoracionTemporalCompetenciaClaveAlumnoDTO> valoracionesCompetenciasClave;
	
}
