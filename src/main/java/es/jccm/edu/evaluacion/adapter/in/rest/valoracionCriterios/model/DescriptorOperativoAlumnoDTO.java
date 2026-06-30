package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Descriptor operativo alumno", description = "Proyección para rescatar los descriptores operativos evaluados")
public class DescriptorOperativoAlumnoDTO {

	@Schema(description = "Id descriptor operativo")
	Long idDescriptorOperativo;
	
	@Schema(description = "Descripción del descriptor operativo")
	String descDescriptorOperativo;
	
	@Schema(description = "Abreviatura del descriptor operativo")
	String abrevDescriptorOperativo;
	
	@Schema(description = "Id de la calificación")
    Long idCalifica;
	
	@Schema(description = "Descripción de la nota del alumno")
    String descCal;

	@Schema(description = "Nota numérica de la calificación")
	Long nota;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Id de la matrícula")
    Long idMatricula;
    
    @Schema(description = "Id interno de la convocatoria")
	Long idConvCentroOmc;

	@Schema(description = "Indica si se ha cambiado la nota")
	Boolean notaCambiada = false;
	
	@Schema(description = "Id interno de la valoración temporal del descriptor operativo del alumno")
	Long idValDesOpeAluTemp;
	
}
