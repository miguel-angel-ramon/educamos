package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaConvocatoriaCentrosOMC;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaSaberBasico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UnidadProgramacionDTO", description = "DTO Unidad Programación")
public class UnidadProgramacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la Unidad de Programación")
    private Long id;
    
    @Schema(description = "Nombre de la Unidad de Programación")
	private String nombre;

    @Schema(description = "Abreviatura de la Unidad de Programación")
	private String abreviatura;
	
    @Schema(description = "Descripción de la Unidad de Programación")
	private String descripcion;
	
    @Schema(description = "Orden de la Unidad de Programación")
	private Integer orden;

    //TODO: cambiar a DTO y ver implicaciones
    @Schema(description = "Convocatoria asociada a la Unidad de Programación")
    private EvaConvocatoriaCentrosOMC convCentroOmc;
    
    @Schema(description = "Numero de criterios de una unidad de programación")
    private Long numCriterios;
    
    @Schema(description = "Lista de  criterios de evaluación")
    private List<CriterioEvaluacionDTO> criteriosEvaluacion;

    @Schema(description = "Lista de Ids de criterios de evaluación")
    private List<Long> criteriosEvaluacionIds;

    @Schema(description = "Lista de abreviaturas de criterios de evaluación")
    private List<String> criteriosEvaluacionAbrev;
    
    @Schema(description = "Lista de actividades de la Unidad de Programación")
    private List<ActividadDTO> actividades;

    @Schema(description = "Saberes basicos de la unidad")
    private List<SaberBasicoDTO> saberesBasicos;
    
    @Schema(description = "Lista de Ids de saberes básicos")
    private List<Long> saberesBasicosIds;

    @Schema(description = "Lista de abreviaturas de saberes básicos")
    private List<String> saberesBasicosAbrev;
    
}
