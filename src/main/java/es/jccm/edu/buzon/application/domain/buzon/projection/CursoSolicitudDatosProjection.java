package es.jccm.edu.buzon.application.domain.buzon.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CursoSolicitudDatosProjection", description = "Proyección para rescatar los datos necesarios para la solicitud del correo al alumnado")
public interface CursoSolicitudDatosProjection {
	
	@Schema(description = "Nombre de la etapa")
	String getEtapa();
	
	@Schema(description = "Id del ciclo")
	Long getIdCiclo();
	
	@Schema(description = "Id del curso")
	Long getIdOfertamatricCurso();
	
	@Schema(description = "Nombre del curso")
	String getCurso();
	
	@Schema(description = "Id de la etapa")
	Long getIdEtapa();
	
	@Schema(description = "Id de la etapas del curso")
	Long getIdEtapas();
	
	@Schema(description = "Orden de la etapa")
	Long getOrdenetapa();
	
	@Schema(description = "Orden del curso")
	Long getOrdencurso();
	
	@Schema(description = "Número de alumnos")
	Long getNumAlum();
	
	@Schema(description = "Número de alumnosT")
	Long getNumAlumT();
	
	@Schema(description = "Número de alumnosTOWAN")
	Long getNumAlumTOWAN();
	
	@Schema(description = "Número de alumnosTOWAS")
	Long getNumAlumTOWAS();

}
