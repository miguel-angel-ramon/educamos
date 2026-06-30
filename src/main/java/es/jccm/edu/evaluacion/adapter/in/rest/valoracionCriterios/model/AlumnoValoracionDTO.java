package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Evaluacion curricular", description = "Evaluacion curricular de alumnos por unidad")
public class AlumnoValoracionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Matricula")
	private Long idMatricula;
	
	@Schema(description = "Id Alumno")
	private Long idAlumno;
	
	@Schema(description = "Id Etapa")
	private Long idEtapa;
	
	@Schema(description = "Id interno de la convocatoria")
	private Long idConvCentroOmc;
	
	@Schema(description = "Id número escolar del alumno")
	private String numEscolar;

	@Schema(description = "Nombre y apellidos de alumno")
	private String nombreAlumno;
	
	@Schema(description = "Nombre de la etapa")
	private String nombreEtapa;
	
	@Schema(description = "Nombre de la convocatoria")
	private String nombreConvocatoria;
	
	@Schema(description = "Si el alumno se ha evaluado de todas las materias")
	private Boolean todasMateriasEvaluadas;
	
	@Schema(description = "Foto del alumno")
    private byte[] foto;

	@Schema(description = "Es ACNEE")
	private Integer acnee;

	@Schema(description = "Nivel curricular alumno ACNEE")
	private String nivelCurricular;
	
	@Schema(description = "Id Etapa adaptación alumno ACNEE")
	private Long idEtapaAdaptacion;
	
	@Schema(description = "Valoraciones de las competenecias clave del alumno")
	private List<ValoracionCompetenciaClaveAlumnoDTO> valoracionesCompetenciasClave;

	@Schema(description = "Materias no evaluadas")
	private List<String> listMateriasNoEvaluadas;

}
