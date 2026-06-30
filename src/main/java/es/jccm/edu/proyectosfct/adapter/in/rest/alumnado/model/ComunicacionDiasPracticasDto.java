package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ComunicacionDiasPracticasDto", description = "Descripcion para el modelo dias practicas")
public class ComunicacionDiasPracticasDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "Número de días efectivos de prácticas formativas")
	private Date fechaComunicacionDatos;	
	
	@Schema (name = "Dni, Nie o pasaporte")
	private String dni;
	
	@Schema (name = "Número de la Seguridad Social")
	private String tNuss;
	
	@Schema (name = "Apellidos")
	private String apellidos;
	
	@Schema (name = "Nombre")
	private String nombre;	
	
	@Schema(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasRealizados;
	
	@Schema(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasInc;
	
	@Schema(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasEmb;
}
