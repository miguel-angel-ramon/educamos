package es.jccm.edu.movil.adapter.in.rest.calendario.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Fiesta", description = "Descripcion para el modelo de fiesta local, provincial o comarcal")
public class CalendarioPublicoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador localidad, provincia, comunidad")
	private Long id;

	@Schema(description = "Año del festivo")
	private Long anyo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha firma del festivo")
	private Date fechaFiesta;

	@Schema(description = "Tipo de festivo")
	private String tipoFiesta;
	
	@Schema(description = "Ámbito del festivo")
	private String ambito;

	@Schema(description = "Usuario creador")
	private Long usuarioCreacion;
	
	@Schema(description = "Usuario que actualiza")
	private Long usuarioActualiza;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha actualización")
	private Date fechaActualizacion;

}
