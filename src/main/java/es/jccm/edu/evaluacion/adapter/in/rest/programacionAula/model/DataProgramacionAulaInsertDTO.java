package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DataProgramacionAulaInsertDTO", description = "DTO de la inserción de una programacion de aula")
public class DataProgramacionAulaInsertDTO implements Serializable{
	
	@Schema(description = "Objecto Programacion Aula")
	private ProgramacionAulaDTO programacionAula ;
	
	@Schema(description = "Unidades")
	private List<UnidadPorMateriaDTO> unidades;
	
	@Schema(description = "Alumnos")
	private List<AlumnosPorMateriaDTO> alumnos;

	@Schema(description = "Nombre")
	private String name;

}
