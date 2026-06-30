package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Gasto del tutor", description = "Descripcion para el modelo de Gasto tutor")
public class GastoTutorDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gasto del tutor")
	private Long id;
	
	@Schema(description = "Coste de la manutencion")
	private Double costeManutencion;
	
	@Schema(description = "Coste del alojamiento")
	private Double costeAlojamiento;
	
	@Schema(description = "Coste de los billetes")
	private Double costeBilletes;
	
	@Schema(description = "Coste del taxi")
	private Double costeTaxi;

	@Schema(description = "Coste del vehiculo")
	private Double costeKmVehiculo;
	
	@Schema(description = "Coste de los kilometros")
	private Double costeKm;
	
	@Schema(description = "Coste del aparcamiento")
	private Double costeAparcamiento;
	
	@Schema(description = "Coste del peaje")
	private Double costePeaje;
	
	@Schema(description = "Coste total")
	private Double costeTotal;	
	
	@Schema(description = "Fecha de inicio del gasto")
	private Date fechaInicio;
	
	@Schema(description = "Fecha de fin del gasto")
	private Date fechaFin;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Periodo del gasto")
	private PeriodoGastoDto periodoGasto;
	
	@Schema(description = "Centro")
	private CentroDto centro;
	
	@Schema(description = "Tutor fct")
	private TutorFctDualDto tutorFct;
}
