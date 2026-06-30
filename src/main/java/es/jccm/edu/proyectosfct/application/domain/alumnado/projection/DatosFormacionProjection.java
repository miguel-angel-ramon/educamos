package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosFormacionProjection", description = "Descripcion para el modelo de listado de alumnos de un tutor")
public interface DatosFormacionProjection {

	@Schema(description = "Id del convenio proyecto o convenio programa")
	Long getId();

	@Schema(description = "Nombre empresa")
	String getNombreEmpresa();

	@Schema(description = "Curso")
	String getCurso();
	
	@Schema(description = "Descripcion programa")
	String getDescripcion();

	@Schema(description = "Unidad")
	String getUnidad();
	
	@Schema(description = "Nombre del archivo Rodal de evaluacion firmado")
	String getTxEvaRodal();

	@Schema(description = "Partes")
	String getPartes();

	@Schema(description = "Identificador del archivo Rodal de evaluacion firmado")
	String getIdEvaRodal();

	@Schema(description = "Fecha evaluacion firmado")
	String getFfirma();

	@Schema(description = "Numero de la Seguridad Social del alumno")
	String getTnuss();

	@Schema(description = "Campo que determina si un alumno cotiza o no en la Seguridad Social")
	Integer getCotiza();

	@Schema(description = "Campo que determina si se pueden borrar los partes mensuales")
	Integer getPuedeBorrar();
	
	@Schema(description = "Email tutor")
	String getEmailTutor();
	
	@Schema(description = "Número de la Seguridad Social actualizado del alumno")
	String getNussActualizado();
	
	@Schema(description = "Teléfono del tutor")
	String getTlfTutor();

	@Schema(description = "Nombre tutor")
	String getNombreTutor();

}

