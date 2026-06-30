package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria Unidad", description = "Descripcion para el modelo Convocatoria Unidad")
public class ConvocatoriaUnidadDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de la convocatoria unidad")
	private Long id;
	
	@Schema(description = "fecha estado")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaEstado;
	
	@Schema(description = "fecha sesion")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaSesion;
	
	@Schema(description = "Fecha de la ultima publicacion")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaUltimaPublicacion;
	
	@Schema(description = "Fecha del ultimo cierre definitivo")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaUltimoCierreDefinitivo;
	
	@Schema(description = "Hora del ultimo cierre definitivo")
	private String horaUltimoCierreDefinitivo;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Unidad centro")
	private UnidadesCentroDto unidad;
	
	@Schema(description = "Convocatoria centro oferta matricula centro")
	private ConvocatoriaCentrosOMCDto convocatoriaCentroOmc;
	
	@Schema(description = "Estado convocatoria")
	private Long estadoConvocatoria;
	
}
