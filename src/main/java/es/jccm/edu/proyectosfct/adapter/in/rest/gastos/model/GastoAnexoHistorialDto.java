package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Historial gasto anexo", description = "Descripcion para el modelo historial gasto anexo")
public class GastoAnexoHistorialDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del historial gasto anexo")
	private Long id;
	
	@Schema(description = "Fecha del registro del usuario")
	private Date fechaRegistro;
	
	@Schema(description = "Observaciones cambio estado")
	private String observaciones;
	
	@Schema(description = "Nombre del fichero")
	private String nombreFichero;
	
	@Schema(description = "XUsuario primera firma")
	private Long idUsuario1_firma;
	
	@Schema(description = "Fecha primera firma")
	private Date fhRegistro1;
	
	@Schema(description = "XUsuario segunda firma")
	private Long idUsuario2_firma;
	
	@Schema(description = "Fecha segunda firma")
	private Date fhRegistro2;
	
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Idenficador del anexo en Rodal")
	private String idAneHisRodal;
	
	@Schema(description = "Idenficador del usuario que registra el historico")
	private Long idUsuario;
	
	@Schema(description = "Gasto del anexo")
	private GastoAnexoDto gastoAnexo;
	
	@Schema(description = "Flujo del gasto")
	private GastoFlujoDto gastoFlujo;
}
