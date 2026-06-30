package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ActividadDTO", description = "DTO Actividad")
public class ActividadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Actividad")
	private Long id;

	@Schema(description = "Abreviatura de la Actividad")
	private String abreviatura;

	@Schema(description = "Nombre de la Actividad")
	private String nombre;
	
	@Schema(description = "Descripción de la Actividad")
	private String descripcion;

	@Schema(description = "Indica si se ha seleccionado la actividad")
	private boolean seleccionada = false;

	@Schema(description = "Estado de la calificación de la actividad")
	private String calificada;

	@Schema(description = "Orden de la actividad")
	private Integer orden;
	
	@Schema(description = "Indica si procede de moodle")
	private Integer lprocedeMoodle;
	
	@Schema(description = "Fecha inicio")
	private Date fechaInicio;
	
	@Schema(description = "Fecha fin")
	private Date fechaFin;
	
	@Schema(description = "Unidad de Programación asociada a la Actividad")
    private Long idUnidadProgramacion;
	@Schema(description = "Fecha fin")
	private String nombreUnidad;
	@Schema(description = "Convocatoria asociada a la Actividad")
    private Long idConvCentroOmc;

	@Schema(description = "Descripción de la convocatoria")
	private String descripcionConvocatoria;
	
	@Schema(description = "Intrumento evaluación asociado a la Actividad")
    private Long idInstrumentoEvaluacion;

	@Schema(description = "Indica si la convocatoria es la misma que la de unidad o no")
	private Boolean convocatoriaUnidad;
	
	@Schema(description = "Lista de Ids de criterios de evaluación")
    private List<Long> criteriosEvaluacionIds;
	
	@Schema(description = "Lista de Ids de criterios de evaluación con sus pesos de media ponderada")
    private List<CriterioPesoDTO> criteriosPesos;
	
	@Schema(description = "Lista de criterios de evaluación")
    private List<CriterioEvaluacionDTO> criteriosEvaluacion;
	
	@Schema(description = "Lista de Ids de alumnos de la actividad")
    private List<Long> alumnosIds;
	
	@Schema(description = "Lista de alumnos de la actividad")
    private List<AlumnoDTO> alumnos;
	
	@Schema(description = "Programación de aula asociada a la actividad")
	private Long idProgramacionAula;
	
	@Schema(description = "Id. de la Actividad en Moodle")
	private Long idActividadMoodle;

	@Schema(description = "Si coinciden todos los criterios")
	private boolean coincidenciaCriterios;
}