package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Valores Ambito Cinco", description = "Descripcion para el modelo de Valores Ambito Cinco")
public class ValoresAmbitoCincoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long countNivel;
	private String nivel;
	private String subdimension;
	
}
