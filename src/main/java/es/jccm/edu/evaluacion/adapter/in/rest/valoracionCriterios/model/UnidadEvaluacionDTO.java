package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.application.domain.evaluacion.Convocatorias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Unidad de evaluación", description = "Proyección para rescatar las unidades de evaluación")
public class UnidadEvaluacionDTO {

    @Schema(description = "Id de la unidad")
    Long idUnidad;

    @Schema(description = "Descripción de la unidad")
    String unidad;
    
    @Schema(description = "Id del curso")
    Long idCurso;
	
    @Schema(description = "Descripción de la curso")
	String curso;
	
    @Schema(description = "Id de la etapa")
	Long idEtapa;

    @Schema(description = "Id de la etapa secundario para bachiller")
    Long idEtapaSec;

    @Schema(description = "Id del ciclo")
    Long idCiclo;
	
    @Schema(description = "Descripción de la etapa")
	String etapa;

    @Schema(description = "Id de la ponderacion")
    Long idPonderacion;
    
    @Schema(description = "Id de la oferta de matrícula genérica")
    Long idOfertamatrig;

    @Schema(description = "Id de la oferta de matrícula genérica")
    Long idOfertamatric;

    @Schema(description = "Estado de la convocatoria")
    Long estadoConvFinal;

    @Schema(description = "Descripción del estado de la convocatoria")
    String descEstadoConvocatoriaFinal;

    @Schema(description = "Número de Competencias Específicas evaluadas en la unidad")
    Integer competenciasEvaluadas;

    @Schema(description = "Nombre de las materias con competencias específicas sin evaluar a algun alumno")
    String nombreMaterias;

    @Schema(description = "Convocatorias Asociadas")
    List<Convocatorias> convocatorias;
}
