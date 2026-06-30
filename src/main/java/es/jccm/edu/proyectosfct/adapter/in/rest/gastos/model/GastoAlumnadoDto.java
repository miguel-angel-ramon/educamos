package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Gasto del alumno", description = "Descripcion para el modelo de Gasto alumno")
public class GastoAlumnadoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gasto del alumno")
	private Long id;
	
	@Schema(description = "Desplazamiento desde centro")
	private Integer desplazaCentro;
	
	@Schema(description = "Desplazamiento desde domicilio")
	private Integer desplazaDomicilio;
	
	@Schema(description = "Coste importe")
	private Double costeImporte;
	
	@Schema(description = "Numero dias transporte colectivo")
	private Integer numDiasTransporteColec;
	
	@Schema(description = "Coste Kilometros")
	private Double costeKm;
	
	@Schema(description = "Numero de dias vehiculo")
	private Integer numDiasVehiculo;
	
	@Schema(description = "Coste importe kilometros")
	private Double costeImporteKm;
	
	@Schema(description = "Coste total transporte")
	private Double costeTotalTransporte;
	
	@Schema(description = "Coste de otros gastos")
	private Double costeOtrosGastos;
	
	@Schema(description = "Coste total")
	private Double costeTotal;

	@Schema(description = "Coste de la manutencion")
	private Double costeManutencion;

	@Schema(description = "Numero de dias de la manutencion")
	private Integer numDiasManu;

	@Schema(description = "Coste de los Epis")
	private Double costeEpis;

	@Schema(description = "Otros costes")
	private Double costeOtros;
	
	@Schema (description = "Localidad durante el curso escolar")
	private String localidadCurso;
	
	@Schema (description = "Localidad de estancia durante la FCT")
	private String localidadEstancia;
	
	@Schema (description = "Localidad del centro educatívo")
	private String  localidadCentroEdu;
	
	@Schema (description = "Localidad del centro de trabajo")
	private String localidadCentroTrabajo;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Periodo del gasto")
	private PeriodoGastoDto periodoGasto;
	
	@Schema(description = "Identificador de la matricula del alumno")
	private Long idMatricula;
	
	@Schema(description = "Centro")
	private CentroDto centro;	
}
