package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "curso programacion aula")
public class CursoProgramacionAulaDTO {

	private Long idOfermatrig;
	   
	private String descripcionCorta;
}
