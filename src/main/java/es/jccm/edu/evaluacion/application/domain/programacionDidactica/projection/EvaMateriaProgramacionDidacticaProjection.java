package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Materias Programación Didáctica", description = "Proyección para rescatar las materias de un centro")
public interface EvaMateriaProgramacionDidacticaProjection {
	
	@Schema(description = "Id de materia de curso genérica")
	Long getIdMateriaOmg();
	
	@Schema(description = "Descripción de la materia")
	String getMateria();

	@Schema(description = "Id de la oferta de la matrícula")
	Long getIdOfertaMatrig();

	@Schema(description = "Id del departamento de la asignatura")
	Long getIdDepartamento();

	@Schema(description = "Nombre del departamento de la asignatura")
	String getNombreDepartamento();

	@Schema(description = "Id de curso del centro")
	Long getIdCurso();
	
	@Schema(description = "Descripción del curso")
	String getCurso();

	@Schema(description = "Descripción del curso de nivel de adaptación cucrricular")
	String getNivelCurricular();
	
	@Schema(description = "Id del curso de nivel de adaptación curricular")
	Long getIdNivelCurricular();
	
	@Schema(description = "Estado de la programación didáctica")
	String getEstado();

	@Schema(description = "Id de la programación didáctica")
	Long getIdProgramacionDidactica();
	
	@Schema(description = "Número de unidades de programación asociadas a la programación didáctica")
	Integer getCountUnidadesProgramacion();
	
	@Schema(description = "Nombres separados por comas de las unidades de programación asociadas a la programación didáctica")
	String getNombresUnidadesProgramacion();
	
	@Schema(description = "Identificador del empleado que ha creado y por tanto tan solo él puede editar o borrar la programación didáctica")
	Long getIdEmpleado();
	
    @Schema(description = "Nombre del empleado que ha creado y por tanto tan solo él puede editar o borrar la programación didáctica")
    String getNombreEmpleado();
    
    @Schema(description = "Abreviatura de la materia")
    String getAbreviatura();

	@Schema(description = "Abreviatura de la materia acnee")
	String getAbreviaturaAcnee();

	@Schema(description = "Id del empleado responsable actual de la materia de la programación didáctica")
	Long getIdEmpleadoResponsableActual();

	@Schema(description = "Id documento rodal")
	String getIdRodal();

	@Schema(description = "Nombre del fichero")
	String getNombreFichero();

}
