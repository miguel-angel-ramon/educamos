package es.jccm.edu.proyectosfct.application.domain.extraordinario.projections;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado autorizacion extraordinario", description = "Descripcion para el modelo del listado autorizacion extraordinario")
public interface ListadoAutorizacionExtraordinarioProjection {

	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Nombre Alumno")
	String getNombreAlumno();
	
	@Schema(description = "Finde")
	String getFinde();
	
	@Schema(description = "Vacaciones")
	String getVacaciones();
	
	@Schema(description = "horarioext")
	String getHorarioext();
	
	@Schema(description = "FechaIniExt")
	Date getFiniext();
	
	@Schema(description = "FechaFinExt")
	Date getFfinext();
	
	@Schema(description = "HorarioFuera")
	String getHorariofue();
	
	@Schema(description = "fechaIniFuera")
	Date getFinifue();
	
	@Schema(description = "fechaFinFue")
	Date getFfinfue();
	
	@Schema(description = "Estado")
	String getEstado();
	
	@Schema(description = "IdMatricula")
	Long getIdMatricula();
	
	@Schema(description = "Editar")
	Integer getEditarestado();
	
	@Schema(description = "Curso")
	String getCurso();
	
	@Schema(description = "tutor")
	String getTutor();
	
	@Schema(description = "Calendario Extraordinario")
	String getCalenExt();
	
	@Schema(description = "Calendario Fuera de provincia")
	String getCalenFue();
	
	@Schema(description = "Empresa")
	String getEmpresa();
	
	@Schema(description = "Habilitar boton de borrado")
	String getPuedeBorrar();
	
	@Schema(description = "Fecha de la última creación/modificación solicitud")
	String getFultgen();

	@Schema(description = "Número de petición")
	Integer getNuPeticion();
}
