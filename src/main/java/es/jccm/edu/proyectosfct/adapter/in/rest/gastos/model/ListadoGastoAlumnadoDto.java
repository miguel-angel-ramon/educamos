package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado gasto alumnado", description = "Descripcion para el modelo listado gasto alumnado")
public class ListadoGastoAlumnadoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del tipo gasto")
	private Long id;
	
	@Schema(description = "Nombre del alumno")
	private String nombreAlumno;
	
	@Schema(description = "Id matricula del alumno")
	private Long idMatricula;
	
	@Schema(description = "Nombre del tutor del alumno")
	private String nombreTutor;
	
	@Schema(description = "Periodo")
	private Integer annoPeriodo;
	
	@Schema(description = "Fecha inicio")
	private Date fInicio;
	
	@Schema(description = "Fecha fin")
	private Date fFin;

	@Schema(description = "Se desplaza desde el centro")
	private Integer desplazaCentro;
	
	@Schema(description = "Se desplaza desde el domicilio")
	private Integer desplazaDomicilio;
	
	@Schema(description = "Gasto importe")
	private Double importe;
	
	@Schema(description = "Numero de dias colectivo")
	private Double diasColectivo;
	
	@Schema(description = "Gasto KM")
	private Double km;	
	
	@Schema(description = "Habilita o deshabilita el boton de borrar")
	private String fultgen;
	
	@Schema(description = "Numero de dias")
	private Double diasVehiculo;
	
	@Schema(description = "Coste importe kilometros")
	private Double costeImporteKm;
	
	@Schema(description = "Gasto transporte")
	private Double totalTransporte;
	
	@Schema(description = "Otros gastos")
	private Double otrosGastos;
	
	@Schema(description = "Gasto total")
	private Double total;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Editar estado")
	private Integer editarestado;
	
	@Schema(description = "Id tutor")
	private Long idTutor;
	
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
