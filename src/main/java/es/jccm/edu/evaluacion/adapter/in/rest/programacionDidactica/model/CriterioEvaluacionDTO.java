package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CriterioEvaluacionDTO", description = "DTO Criterio Evaluación")
public class CriterioEvaluacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del criterio")
	private Long id;

	@Schema(description = "Nombre del criterio")
	private String nombre;

	@Schema(description = "Abreviatura del criterio")
	private String abreviatura;
	
	@Schema(description = "Id del ciclo")
	private Long idCiclo;
	
	@Schema(description = "Peso del ciclo")
	private Integer peso;

	@Schema(description = "Id del tipo de operación")
	private Long idTipoOperacion;
	
	@Schema(description = "Competencia específica")
    private CompetenciaEspecificaDidacticaDTO competenciaEspecifica;
	
	@Schema(description = "Orden")
	private Integer orden;
	
	@Schema(description = "Número de unidades de programación asociadas")
	private Long numUnidadesProgramacion;
	
	@Schema(description = "Seleccionado")
	private Boolean seleccionado = false;
	
	@Schema(description = "Id competencia específica")
    private Long idCompetenciaEspecifica;
	
	@Schema(description = "Indica si el criterio está en la Unidad de Programación")
	private boolean enUnidadProgramacion;

	@Schema(description = "Indica si tiene calificaciones asociadas")
	private Boolean tieneValoraciones = false;
}
