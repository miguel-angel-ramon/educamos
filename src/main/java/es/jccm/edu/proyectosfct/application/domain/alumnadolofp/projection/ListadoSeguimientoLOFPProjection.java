package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Listado seguimiento alumnado", description = "Proyección para los datos de la semana")
public interface ListadoSeguimientoLOFPProjection {

	@Schema(description = "Número del mes en formato YYYY-MM")
	String getMesNumero();

	@Schema(description = "Nombre del mes")
	String getMesNombre();

	@Schema(description = "Indica si el mes es visible")
	Integer getMesVisible();

	@Schema(description = "Número de la semana")
	String getSemanaNumero();

	@Schema(description = "Indica si es la semana actual")
	Integer getEsSemanaActual();

	@Schema(description = "Indica si la semana es visible")
	Integer getSemanaVisible();

	@Schema(description = "Fecha de inicio de la semana")
	String getFechaInicioSem();

	@Schema(description = "Fecha de fin de la semana")
	String getFechaFinSem();

	@Schema(description = "Número de periodo")
	Integer getPeriodoNumero();

	@Schema(description = "Fecha de inicio del periodo")
	String getFechaInicioPeriodo();

	@Schema(description = "Fecha de fin del periodo")
	String getFechaFinPeriodo();

	@Schema(description = "Flag que indica si el periodo es el actual o no")
	Integer getEsPeriodoActual();

	@Schema(description = "Flag que indica si el periodo es visible o no")
	Integer getPeriodoVisible();

	@Schema(description = "Número de actividades")
	Integer getNumActividades();

	@Schema(description = "Estado del parte")
	Integer getEstadoParte();
	
	@Schema(description = "Identificador Rodal")
	String getIdRodal();
	
	@Schema(description = "Identificador Rodal")
	String getFichero();

	@Schema(description = "Indica si el parte está actualizado")
	Integer getParteActualizado();

}
