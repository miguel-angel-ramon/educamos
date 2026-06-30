package es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "Autorizacion Extraordinario", description = "Descripcion para el modelo de autorización extraordinario")
public class AutorizacionExtraordinarioDto extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador de la autorizacion extraordinaria")
	private Long id;
	
	@Schema(description = "Indica si hace FCT en fin de semana")
	private Integer lgfindeext;
	
	@Schema(description = "Indica si hace FCT en periodo de vacaciones")
	private Integer lgvacacext;
	
	@Schema(description = "Descripcion horario extraordinario")
	private String txhorarext;
	
	@Schema(description = "Descripcion horario fuera de provincia")
	private String txhorarfue;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio calendario extraordinario")
	private Date fhIniCalenExt;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Europe/Madrid")
	@Schema(description = "Fecha fin calendario extraordinario")
	private Date fhFinCalenExt;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio calendario fuera de provincia")
	private Date fhIniCalenFue;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Europe/Madrid")
	@Schema(description = "Fecha fin calendario fuera de provincia")
	private Date fhFinCalenFue;

	@Schema(description = "Número de petición asociado a la solicitud")
	private Integer nuPeticion;


	// ---------- Relationships -----------
	
	@Schema(description = "Identificador del tutor que registra el gasto")
	private Long idTutorFct;
	
	@Schema(description = "Indentificador de la matrícula del alumno")
	private Long idMatricula;
	
	@Schema(description = "Identificador del centro")
	private Long idCentro;
}
