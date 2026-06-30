package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "curso programacion aula")
public class NivelCurricularProgramacionAulaDTO {

	private Long idOferMatrigNivelCurricular;
	   
	private String descripcionNivelCurricular;
	
	private Long acneae;
	
	private Long idDidactica;

	private Long idPonderacion;

	private String abrev;
}
