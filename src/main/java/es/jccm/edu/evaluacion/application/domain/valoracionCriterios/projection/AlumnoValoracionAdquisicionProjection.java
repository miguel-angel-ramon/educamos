package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno en la valoración de su adquisición curricular", description = "Valoración de la adquisición curricular de los alumnos por unidad")
public interface AlumnoValoracionAdquisicionProjection {

	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id Matrícula")
	Long getIdMatricula();
	
	@Schema(description = "Id Unidad del centro")
	Long getIdUnidadCentro();
	
	@Schema(description = "Id Oferta de matrícula genérica")
	Long getIdOfertamatrig();
	
	@Schema(description = "Número escolar del Alumno")
	String getNumEscolar();
	
	@Schema(description = "Nombre de la unidad del centro")
	String getNombreUnidadCentro();
	
	@Schema(description = "Descripción de la oferta de matrícula genérica")
	String getNombreCurso();
	
	@Schema(description = "Nombre y apellidos del alumno")
	String getNombreAlumno();
	
	@Schema(description = "Es ACNEE")
	Integer getAcnee();
	
	@Schema(description = "Nivel curricular alumno ACNEE")
	String getNivelCurricular();
	
	@Schema(description = "Id Etapa adaptación alumno ACNEE")
	Long getIdEtapaAdaptacion();

}
