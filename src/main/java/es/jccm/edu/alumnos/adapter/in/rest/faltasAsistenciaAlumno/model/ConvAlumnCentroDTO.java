package es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


	@Data
	@Schema(name = "ConvCentro", description = "Datos rescatados del centro para el módulo de simulación de usuarios del escritorio")
	public class ConvAlumnCentroDTO implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Schema(description = "Id Convocatoria")
		private Long indice;
		
		@Schema(description = "Descripcion Convocatoria")
		private String descripcion;
		
		@Schema(description = "fecha inicio Convocatoria")
		private String fecinicon;
		
		@Schema(description = "fecha fin Convocatoria")
		private String fecfincon;

}
