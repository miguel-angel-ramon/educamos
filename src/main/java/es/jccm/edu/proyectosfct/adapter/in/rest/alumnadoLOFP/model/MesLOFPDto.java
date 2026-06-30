package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Meses", description = "Descripcion para el modelo de meses del seguimiento alumnado")
public class MesLOFPDto {

	@Schema(description = "Número del mes")
	private String mesNumero;

	@Schema(description = "Nombre del mes")
	private String mesNombre;

	@Schema(description = "Flag para saber si es visible o no el mes")
	private Integer mesVisible;

	private List<SemanaLOFPDto> semanas;
	
}
