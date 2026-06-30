package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno", description = "Descripcion para el modelo de alumnos")
public interface AlumnoProjection {
	
	@Schema(description = "Id del alumno)")
	Long getId();
	
	@Schema(description = "Nombre completo")
	String getnombreCompleto();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "id de la matricula")
	Long getIdMatricula();
	
	@Schema(description = "id de la unidad")
	String getIdUnidad();
	
	@Schema(description = "Nombre de la unidad")
	String getNombreUnidad();

	@Schema(description = "Campo que recoge el número de la seguridad social del alumno")
	String getTnuss();

	@Schema(description = "Campo que recoge 0 si el alumno no cotiza en la SS o 1 si el alumno si cotiza en la SS")
	Integer getLgCotiza();

	@Schema(description = "Campo que recoge 0 si el alumno ya tenía un número de la SS y 1 si el alumno no tenía número de la SS")
	Integer getLgNuss();
	
	@Schema(description = "id convenio")
	Long getIdConvAlu();
	
	@Schema(description = "Campo que recoge 0 si el dato de la Seguridad Social es Editable")
	Integer getLgEditable();
	
	@Schema(description = "Campo que recoge 0 si el alumnado es mayor de 16 años y si 1 si es menor")
	Integer getLgMenor();
	
	@Schema(description = "Campo que recoge 0 si el alumno no es erasmus, 1 si tiene erasmus sin beca y 2 para erasmus con beca")
	Integer getLgErasmus();

	@Schema(description = "Campo cuyo valor recoge un mensaje que indica o no si se puede quitar un alumno de un programa")
	String getDsMotivo();

	@Schema(description = "Campo que recoge la localidad donde el alumno realiza la FCT")
	String getDsLocalidadFct();

	@Schema(description = "Campo que indica la vinculación del alumno con el convenio y proyecto (empresa)")
	Integer getLgAlumnoEnEmpresa();
}

