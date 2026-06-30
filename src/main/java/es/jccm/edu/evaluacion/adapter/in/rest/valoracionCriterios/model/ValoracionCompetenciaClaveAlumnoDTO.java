package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Valoración competencia clave alumno", description = "Proyección para rescatar las valoraciones de las competencias clave evaluadas")
public class ValoracionCompetenciaClaveAlumnoDTO {

	@Schema(description = "Id competencia clave")
	private Long idCompetenciaClave;
	
	@Schema(description = "Nombre abreviado de la competencia clave")
	private String nombreCompetenciaClave;
	
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
	
	@Schema(description = "Valoración de descriptores operativos asociados a la competencia clave")
	private transient List<ValoracionDescriptorOperativoAlumnoDTO> valoracionesDescriptoresOperativos;
}
