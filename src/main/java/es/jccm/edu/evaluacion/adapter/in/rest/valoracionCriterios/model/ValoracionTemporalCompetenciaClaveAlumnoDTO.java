package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CalificacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Valoración temporal competencia clave alumno", description = "Proyección para rescatar las valoraciones temporales de las competencias clave evaluadas para el alumno")
public class ValoracionTemporalCompetenciaClaveAlumnoDTO {

	@Schema(description = "Id valoración temporal competencia clave alumno")
	Long id;
	
	@Schema(description = "Id competencia clave")
	Long idCompetenciaClave;
	
	@Schema(description = "Nombre abreviado de la competencia clave")
	String nombreCompetenciaClave;
	
	@Schema(description = "Id de la calificación")
	Long idCalifica;
	
	@Schema(description = "Id matrícula del alumno")
	Long idMatricula;
	
	@Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Nota de la competencia clave")
    Integer nota;
    
    @Schema(description = "Calificación de la competencia clave")
	CalificacionDTO calificacion;
    
    @Schema(description = "Descripción sistema de calificación")
    String descDetCal;
    
    @Schema(description = "Valoraciones temporales de los descriptores operativos asociados a la competencia clave que ha adquirido el alumno")
	List<ValoracionTemporalDescriptorOperativoAlumnoDTO> valoracionesDescriptoresOperativos;
}
