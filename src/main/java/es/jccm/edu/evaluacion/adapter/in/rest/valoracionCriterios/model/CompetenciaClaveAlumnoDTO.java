package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Competencia clave alumno", description = "Proyección para rescatar las competencias clave evaluadas")
public class CompetenciaClaveAlumnoDTO {

	@Schema(description = "Id competencia clave")
	Long idCompetenciaClave;
	
	@Schema(description = "Descripción de la competencia clave")
	String descCompetenciaClave;
	
	@Schema(description = "Abreviatura de la competencia clave")
	String abrevCompetenciaClave;
	
	@Schema(description = "Id de la calificación")
	Long idCalifica;
	
	@Schema(description = "Descripción de la nota del alumno")
	String descCal;

	@Schema(description = "Nota numérica de la calificación")
	Long nota;

	@Schema(description = "Indica si la nota es aprobada o no")
	String aprueba;
	
	@Schema(description = "Descriptores operativos asociados a la competencia clave")
	List<DescriptorOperativoAlumnoDTO> descriptoresOperativos;
	
	@Schema(description = "Id de la matrícula")
	Long idMatricula;
	
	@Schema(description = "Id interno de la convocatoria")
	Long idConvCentroOmc;

	@Schema(description = "Indica si se muestran o no sus descriptores")
	private boolean mostrar = true;
	
	@Schema(description = "Id interno de la valoración temporal de la competencia clave del alumno")
	Long idValComClaAluTemp;

}
