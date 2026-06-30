package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CentroDTO", description = "DTO Centro")
public class CentroDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Centro")
	private Long id;

	@Schema(description = "Código del Centro")
	private Long codigo;

	@Schema(description = "Flag que indica si procede del Registro de centros")
	private String lProRegCen;

	@Schema(description = "Modo de adscripción,exclusiva (E) ,compartida (C)")
	private String lModoAdscripcion;
	
	@Schema(description = "Alternativa entre Si o No")
	private String lExtranjero;
	
	@Schema(description = "Flag que indica si el centro de trabajo corresponde a una Delegación")
	private String lDelegacion;
	
	@Schema(description = "Flag que indicará si la adscripción está activa o no.")
	private String lAdscripcionActiva;
	
	@Schema(description = "Fecha pública de cese del centro")
	private Date fechaPublicaCese;
	
	@Schema(description = "Fecha pública de alta del centro")
	private Date fechaPublicaAlta;
	
	@Schema(description = "Fecha de puesta en funcionamiento del centro")
	private Date fechaInicioFuncionamiento;
	
	@Schema(description = "Fecha fin del funcionamiento del centro")
	private Date fechaFinFuncionamiento;
	
	@Schema(description = "Fecha de creación del centro")
	private Date fechaCreacion;
	
	@Schema(description = "F_BAJA")
	private Date fechaBaja;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Tipo del Centro")
	private TipoCentroDTO tipo;
	
	@Schema(description = "Lista de Ofertas Matrícula del Centro")
	private List<OfertaMatriculaCentroDTO> ofertaMatriculaCentros;

}