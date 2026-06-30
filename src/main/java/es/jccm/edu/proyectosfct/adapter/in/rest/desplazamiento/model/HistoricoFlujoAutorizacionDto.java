package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Historico gastos", description = "Descripcion para el modelo historico gastos")
public class HistoricoFlujoAutorizacionDto  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "Estado")
	private String estado;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha")
	private Date fecha;
	
	@Schema(description = "Estado Actual")
	private String actual;
	
	@Schema(description = "Nombre")
	private String observaciones;
	
	@Schema(description = "Estado Abreviatura")
	private String abreviatura;
	
	
	

}
