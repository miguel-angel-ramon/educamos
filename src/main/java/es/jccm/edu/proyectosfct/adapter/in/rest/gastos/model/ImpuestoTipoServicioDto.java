package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado Tipo Servicio", description = "Descripcion para el modelo listado gasto alumnado")
public class ImpuestoTipoServicioDto implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id tipo de impuesto servicio")
	private Long id;
	
	@Schema(description = "Maximo alojamiento dia")
	private Double maximoAlojamientoDia;
	
	@Schema(description = "Maximo manutencion dia")
	private Double maximoManutencionDia;
	
	@Schema(description = "Maximo KM Vehiculo dia")
	private Double maximoKmVehiculo;
	
	@Schema(description = "Maximo KM Moto dia")
	private Double maximoKmMoto;
	
	@Schema(description = "Maximo Vehiculo dia")
	private Double maximoVehiculoDia;
	


}
