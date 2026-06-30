package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "curso programacion aula")
public class MateriaProgramacionAulaDTO {

	private Long idMateriaOmg;
	   
	private String descripcionMateria;
	
	private String abreviatura;

	private boolean tieneNivelCurricular;

	private boolean tieneProgramaciones;
}
