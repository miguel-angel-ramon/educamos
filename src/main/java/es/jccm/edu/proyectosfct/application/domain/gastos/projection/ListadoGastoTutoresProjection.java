package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado gastos tutores", description = "Descripcion para el modelo de Listado gastos tutores")
public interface ListadoGastoTutoresProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Anno periodo")
	Integer getAnnoPeriodo();
	
	@Schema(description = "Fecha inicio periodo")
	Date getFInicio();
	
	@Schema(description = "Fecha fin periodo")
	Date getFFin();
	
	@Schema(description = "Nombre completo")
	String getNombreCompleto();
	
	@Schema(description = "Manutencion")
	Double getManutencion();
	
	@Schema(description = "Alojamiento")
	Double getAlojamiento();
	
	@Schema(description = "Billetes")
	Double getBilletes();	
	
	@Schema(description = "Taxi")
	Double getTaxi();	
	
	@Schema(description = "Vehiculo")
	Double getVehiculo();	
	
	@Schema(description = "Gastos")
	Double getGastosKm();	
	
	@Schema(description = "Aparcamiento")
	Double getAparcamiento();	
	
	@Schema(description = "Peaje")
	Double getPeaje();	
	
	@Schema(description = "Total")
	Double getTotal();	
	
	@Schema(description = "Descripcion del estado del flujo")
	String getEstado();	
	
	@Schema(description = "Fecha de la última creación/modificación gasto")
	String getFultgen();
	
	@Schema(description = "Id tutor")
	Long getIdTutor();
	
	@Schema(description = "Editar estado")
	Integer getEditarestado();
	
	@Schema(description = "Fecha inicio gasto")
	Date getFInicioGasto();
	
	@Schema(description = "Fecha fin gasto")
	Date getFFinGasto();

	@Schema(description = "Habilita o deshabilita el boton de borrar")
	String getPuedeBorrar();
}

