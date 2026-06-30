package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CalificacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Valoración temporal descriptor operativo alumno", description = "Proyección para rescatar las valoraciones temporales de los descriptores operativos evaluados")
public class ValoracionTemporalDescriptorOperativoAlumnoDTO {

	@Schema(description = "Id valoración temporal descriptor operativo alumno")
	Long id;
	
	@Schema(description = "Id descriptor operativo")
	Long idDescriptorOperativo;
	
	@Schema(description = "Nombre abreviado del descriptor operativo")
	String nombreDescriptorOperativo;
	
	@Schema(description = "Id de la calificación")
	Long idCalifica;
	
	@Schema(description = "Id matrícula del alumno")
	Long idMatricula;
	
	@Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Nota del descriptor operativo")
    Integer nota;
    
    @Schema(description = "Calificación del descriptor operativo")
	CalificacionDTO calificacion;
    
    @Schema(description = "Descripción sistema de calificación")
    String descDetCal;
	
}
