package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "HistoricoCuestionarioDto", description = "devuelve Año y xCuestionario")
public class HistoricoCuestionarioDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer anio;

	private String idCuestionario;
}
