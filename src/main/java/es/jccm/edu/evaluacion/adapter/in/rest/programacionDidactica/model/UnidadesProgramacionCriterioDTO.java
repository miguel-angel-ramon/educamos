package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UnidadesProgramacionCriterioDTO", description = "DTO Unidades Programacion Criterio")
public class UnidadesProgramacionCriterioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Criterio")
    private Long idCriterio;
   
	@Schema(description = "Número de unidades de programación asociadas al Criterio")
	private Long numUnidadesProgramacion;
	
}