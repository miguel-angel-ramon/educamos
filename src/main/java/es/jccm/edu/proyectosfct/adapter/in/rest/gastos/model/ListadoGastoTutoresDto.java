package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado gasto tutores", description = "Descripcion para el modelo listado gasto tutores")
public class ListadoGastoTutoresDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del tipo gasto")
	private Long id;
	
	@Schema(description = "Periodo")
	private Integer annoPeriodo;
	
	@Schema(description = "Fecha inicio")
	private Date fInicio;
	
	@Schema(description = "Fecha fin")
	private Date fFin;
	
	@Schema(description = "Nombre completo")
	private String nombreCompleto;

	@Schema(description = "Gasto manutencion")
	private Double manutencion;
	
	@Schema(description = "Gasto alojamiento")
	private Double alojamiento;
	
	@Schema(description = "Gasto billetes")
	private Double billetes;
	
	@Schema(description = "Gasto taxi")
	private Double taxi;
	
	@Schema(description = "Gasto vehiculo")
	private Double vehiculo;
	
	@Schema(description = "Gasto km")
	private Double gastosKm;
	
	@Schema(description = "Gasto aparcamiento")
	private Double aparcamiento;
	
	@Schema(description = "Gasto peaje")
	private Double peaje;
	
	@Schema(description = "Gasto total")
	private Double total;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Fecha de la última creación/modificación gasto")
	private String fultgen;
	
	@Schema(description = "Id del tutor")
	private Long idTutor;
	
	@Schema(description = "Editar estado")
	private Integer editarestado;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio")
	private Date finiciogasto;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha fin")
	private Date ffingasto;

	@Schema(description = "Habilita o deshabilita el boton de borrar")
	private String puedeBorrar;
}
