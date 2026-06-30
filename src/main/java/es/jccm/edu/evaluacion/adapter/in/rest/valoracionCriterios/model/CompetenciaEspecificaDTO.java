package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Competencia especifica", description = "Proyección para rescatar las competencias especifica evaluadas")
public class CompetenciaEspecificaDTO {

	@Schema(description = "Materia de la competencia")
	String materia;
	
	@Schema(description = "Abreviatura competencia especifica")
	String competenciaEspAbrev;
	
	@Schema(description = "Id competencia especifica")
	Long idCompetenciaEsp;
	
	@Schema(description = "Descripción de competencia especifica")
	String competenciaEspD;
	
	@Schema(description = "Abreviatura de la calificacion de la competencia")
	String valoracion;

	@Schema(description = "Indica con 'S' o 'N' si aprueba con la calificacion")
	String aprueba;

	
}
