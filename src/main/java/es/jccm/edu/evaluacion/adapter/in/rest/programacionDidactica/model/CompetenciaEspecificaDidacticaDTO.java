package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CompetenciaEspecificaDidacticaDTO", description = "DTO Competecias Especificas Didacticas")
public class CompetenciaEspecificaDidacticaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador")
	private Long id;	
	
	@Schema(description = "Año")
	private String descripcion;
	
	@Schema(description = "Descripcion larga de la convocatoria")
	private String abrev;
	
	@Schema(description = "Descripcion corta de la convocatoria")
	private Long idCiclo;
	
	@Schema(description = "Abreviatura")
	private Integer nOrdenPres;
	
	@Schema(description = "Lista de criterios")
    private List<CriterioEvaluacionDTO> criterios;
}
