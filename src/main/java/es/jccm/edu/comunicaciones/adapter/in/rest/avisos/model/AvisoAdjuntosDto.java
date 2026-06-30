package es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AvisoAdjunto", description = "Archivo adjunto a un aviso rescatado para el módulo de escritorio")
public class AvisoAdjuntosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del fichero adjunto")
	private int id;
	
	@Schema(description = "nombre del fichero adjunto")
	private String nombre;
	
	@Schema(description = "Tamaño del fichero adjunto")
	private String tamano;
	
	@Schema(description = "Posición que ocupa el adjunto en la lista de archivos enviados")
	private String orden;

}
