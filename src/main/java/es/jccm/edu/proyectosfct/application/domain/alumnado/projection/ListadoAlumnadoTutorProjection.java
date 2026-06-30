package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListaAlumnoTutor", description = "Descripcion para el modelo de listado de alumnos de un tutor")
public interface ListadoAlumnadoTutorProjection {
	
	@Schema(description = "Id del convenio proyecto o convenio programa")
	Long getId();
	
	@Schema(description = "Nombre alumno")
	String getNombreAlumno();
	
	@Schema(description = "Nombre empresa")
	String getNombreEmpresa();
	
	@Schema(description = "Tipo empresa")
	String getTipoEmpresa();
	
	@Schema(description = "Curso")
	String getCurso();
	
	@Schema(description = "Unidad")
	String getUnidad();
	
	@Schema(description = "Descripcion programa")
	String getDescripcion();
	
	@Schema(description = "Partes")
	String getPartes();
	
	@Schema(description = "Identificador del archivo Rodal de evaluacion firmado")
	String getIdEvaRodal();
	
	@Schema(description = "Nombre del archivo Rodal de evaluacion firmado")
	String getTxEvaRodal();
	
	@Schema(description = "Fecha evaluacion firmado")
	String getFfirma();

	@Schema(description = "Numero de la Seguridad Social del alumno")
	String getTnuss();

	@Schema(description = "Número de la Seguridad Social actualizado del alumno")
	String getNussActualizado();

	@Schema(description = "Campo que determina si un alumno cotiza o no en la Seguridad Social")
	Integer getCotiza();

	@Schema(description = "Campo que determina si se pueden borrar los partes mensuales")
	Integer getPuedeBorrar();
	
	@Schema(description = "Nombre tutor")
	String getNombreTutor();
	
	@Schema(description = "Descripcion familia")
	String getFamilia();
	
	@Schema(description = "Descripcion orden")
	String getOrden();
	
	@Schema(description = "Descripcion seguridad social")
	String getSeguridad();

	@Schema(description = "Matricula del alumno")
	Long getXMatricula();

	@Schema(description = "Estado del plan")
	String getEstado();

	@Schema(description = "ID del centro del alumno")
	Long getIdCentro();
	
	@Schema(description = "Puede Evaluar")
	Integer getPuedeEvaluar();	

	@Schema(description = "Campo que indica si tiene o no cotizaciones mensuales del mes anterior al actual por enviar")
	Integer getAvisoMes();

	@Schema(description = "Campo que indica si el número de la SS es provisional ")
	Integer getNussProvisional();

	@Schema(description = "Campo que indica las advertencias en partes")
	String getAdvertenciaPartes();

	@Schema(description = "Campo que indica si el alumno esta excluido de las prácticas ")
	Integer getLgExcluir();

}

