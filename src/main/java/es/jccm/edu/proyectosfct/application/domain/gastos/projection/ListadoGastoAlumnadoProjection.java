package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado gastos alumno", description = "Descripcion para el modelo de Listado gastos alumno")
public interface ListadoGastoAlumnadoProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Nombre completo")
	String getNombreAlumno();
	
	@Schema(description = "Id matricula del alumno")
	Long getIdMatricula();
	
	@Schema(description = "Nombre completo")
	String getNombreTutor();
	
	@Schema(description = "Anno periodo")
	Integer getAnnoPeriodo();
	
	@Schema(description = "Fecha inicio periodo")
	Date getFInicio();
	
	@Schema(description = "Fecha fin periodo")
	Date getFFin();
	
	@Schema(description = "Manutencion")
	Integer getDesplazaCentro();
	
	@Schema(description = "Alojamiento")
	Integer getDesplazaDomicilio();
	
	@Schema(description = "Billetes")
	Double getImporte();	
	
	@Schema(description = "Taxi")
	Double getDiasColectivo();	
	
	@Schema(description = "Vehiculo")
	Double getKm();	
	
	@Schema(description = "Gastos")
	Double getDiasVehiculo();	
	
	@Schema(description = "Coste importe km")
	Double getCosteImporteKm();
	
	@Schema(description = "Aparcamiento")
	Double getTotalTransporte();	
	
	@Schema(description = "Peaje")
	Double getOtrosGastos();	
	
	@Schema(description = "Total")
	Double getTotal();	
	
	@Schema(description = "Fecha de la última creación/modificación gasto")
	String getFultgen();
	
	@Schema(description = "Descripcion del estado del flujo")
	String getEstado();	
	
	@Schema(description = "Editar estado")
	Integer getEditarEstado();	
	
	@Schema(description = "Id tutor")
	Long getIdTutor();
	
	@Schema(description = "Fecha inicio gasto")
	Date getFInicioGasto();
	
	@Schema(description = "Fecha fin gasto")
	Date getFFinGasto();

	@Schema(description = "Habilita o deshabilita el boton de borrar")
	String getPuedeBorrar();	
	
}

