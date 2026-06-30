package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DocumentoGCNEW nuevo", description = "Descripcion para el modelo de nuevo documento request")
public class ConvocatoriaReunionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de la convocatoria reunion")
	private Long id;
	
	@Schema(description = "Tipo convocatoria reunion")
	private String tipo;
	
	@Schema(description = "Fecha convocatoria")
	private Date fechaConvocatoria;
	
	@Schema(description = "Fecha reunion")
	private Date fechaReunion;
	
	@Schema(description = "Numero de horas")
	private Integer numHoras;
	
	@Schema(description = "Fecha toma posesion del empleado")
	private Date fechaTomaPosesion;
	
	@Schema(description = "Estado convocatoria reunion")
	private String estado;	
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Empleado")
	private DgcEmpleado empleado;
	
	@Schema(description = "Identificador organo")
	private Long idOrgano;
	
	@Schema(description = "Identificador dependencia")
	private Long idDependencia;

}
