package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Programación de aula", description = "Proyección para rescatar el listado de programaciones de aula de un profesor")
public interface ProgramacionAulaProjection {
	
	@Schema(description = "Id porgramación de aula")
	Long getIdAula();

	@Schema(description = "Nombre de la programación de aula")
	String getNombreAula();

	@Schema(description = "Nombre del profesor")
	String getNombreEmpleado();

	@Schema(description = "Id del aula virtual")
	Long getIdAulaVirtual();

	@Schema(description = "Id de la MateriaOmg")
	Long getIdMateria();

	@Schema(description = "Nombre de la materia")
	String getNombreMateria();

	@Schema(description = "Id del curso, OFERTAMATRIG")
	Long getIdCurso();
	
	@Schema(description = "Nombre del curso")
	String getNombreCurso();

	@Schema(description = "Nombre del curso de nivel de adaptación cucrricular")
	String getNivelCurricular();
	
	@Schema(description = "Id del curso de nivel de adaptación curricular")
	Long getIdNivelCurricular();
	
	@Schema(description = "Año del curso académico")
	Integer getAnno();
	
	@Schema(description = "Id de la programación didáctica de referencia")
	Long getIdProgramacionDidactica();
	
	@Schema(description = "Suma de las unidades de programación")
	Integer getCountUnidadesProgramacion();
	
	@Schema(description = "Nombre de las unidades de programación separados por comas")
	String getNombresUnidadesProgramacion();
	
	@Schema(description = "Suma de las actividades de todas las unidades de programación")
	Integer getCountActividades();
	
	@Schema(description = "Abreviatura de la materia")
	String getAbreviatura();
}
