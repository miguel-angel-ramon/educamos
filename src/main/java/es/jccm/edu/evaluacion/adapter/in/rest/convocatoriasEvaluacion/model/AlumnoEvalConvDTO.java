package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los alumnos de la agrupación de la evaluación")
public class AlumnoEvalConvDTO {

	@Schema(description = "Id del alumno")
    Long idAlumno;

    @Schema(description = "Id de la matrícula")
    Long idMatricula;
	
    @Schema(description = "Id del grupo actividad")
    Long idGrupoActividad;
    
    @Schema(description = "Id de la oferta de la matrícula")
	Long idOfertaMatrig;
	
    @Schema(description = "Año académico")
    Long anno;
	
    @Schema(description = "Id numero escolar")
    String numEscolar;

    @Schema(description = "Nombre del alumno")
    String nombre;

    @Schema(description = "Apellidos del alumno")
    String apellidos;
	
    @Schema(description = "Nombre del curso")
    String nombreCurso;
	
	@Schema(description = "Foto del alumno")
    byte[] foto;
	
	@Schema(description = "Número de materias en las que se ha matriculado el alumno")
	Integer numMaterias;
	
	@Schema(description = "Id de la promoción")
	Long idPromocion;
	
	@Schema(description = "Id del estado de la promoción")
	Long idEstado;
	
	@Schema(description = "Descripción del estado de la promoción")
	String descripcionEstado;
	
	@Schema(description = "Fecha de la sesión de evaluacion")
	String fechaSesion;
	
	@Schema(description = "Resultado de la promoción")
	Long cResultado;
	
	@Schema(description = "Id de la etapa")
	Long idEtapa;
	
	@Schema(description = "Descripción de la etapa")
	String descripcionEtapa;
    
    @Schema(description = "Es ACNEE")
    private Integer acnee;

    @Schema(description = "Nivel curricular alumno ACNEE")
    private String nivelCurricular;

    @Schema(description = "Indica si se muestra el alumno o no")
    boolean mostrar = false;
	
}
