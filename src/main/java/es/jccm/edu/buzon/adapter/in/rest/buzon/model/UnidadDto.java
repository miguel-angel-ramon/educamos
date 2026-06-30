package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidades", description = "Unidades que tienen los alumnos de un Centro, Año y Curso")
public class UnidadDto {
	
	@Schema(description = "Id de la unidad de los alumnos")
    Long idUnidad;
	
	@Schema(description = "Nombre de la unidad de los alumnos")
    String nombreUnidad;
}

