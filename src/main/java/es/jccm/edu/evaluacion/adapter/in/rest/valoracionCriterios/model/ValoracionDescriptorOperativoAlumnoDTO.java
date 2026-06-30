package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Descriptor operativo alumno", description = "Proyección para rescatar los descriptores operativos evaluados")
public class ValoracionDescriptorOperativoAlumnoDTO {

	@Schema(description = "Id descriptor operativo")
	private Long idDescriptorOperativo;
	
	@Schema(description = "Nombre abreviado del descriptor operativo")
	private String nombreDescriptorOperativo;
	
	@Schema(description = "Id de la calificación")
	private Long idCalifica;
	
	@Schema(description = "Descripción de la nota del alumno")
	private String descCal;

	@Schema(description = "Nota numérica de la calificación")
	private Long nota;

    @Schema(description = "Indica si la nota es aprobada o no")
    private String aprueba;
    
    @Schema(description = "Descripción sistema de calificación")
	private String descDetCal;

	@Schema(description = "Indica si se ha cambiado la nota")
	private Boolean notaCambiada = false;
	
}
