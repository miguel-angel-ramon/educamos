package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.PeriodoGastoDto;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Autorizacion desplazamiento", description = "Descripcion para el modelo de autorizacion desplazamiento")
public class AutorizacionDesplazamientoDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador de la autorizacion del desplazamiento")
	private Long id;
	
	@Schema(description = "Numero de dias de la solicitud")
	private Integer numeroDias;
	
	@Schema(description = "Texto de la matricula del alumno/a")
	private String txtMatricula;
	
	@Schema(description = "Texto del itinerario del alumno/a")
	private String txtItinerario;
	
	@Schema(description = "Porcentaje de kilometros al dia")
	private Double porcetanjeKmDia;
	
	@Schema(description = "Porcentaje de kilometros totales")
	private Double porcetanjeTotalKm;

	@Schema(description = "Numero de dias de la solicitud de la segunda autorización")
	private Integer numeroDias2;

	@Schema(description = "Texto de la matrícula del alumno/a de la segunda autorización")
	private String txtMatricula2;

	@Schema(description = "Texto del itinerario del alumno/a de la segunda autorización")
	private String txtItinerario2;

	@Schema(description = "Porcentaje de kilometros al dia de la segunda autorización")
	private Double porcetanjeKmDia2;

	@Schema(description = "Porcentaje de kilometros totales de la segunda autorización")
	private Double porcetanjeTotalKm2;

	@Schema(description = "Numero de dias de la solicitud de la tercera autorización")
	private Integer numeroDias3;

	@Schema(description = "Texto de la matrícula del alumno/a de la tercera autorización")
	private String txtMatricula3;

	@Schema(description = "Texto del itinerario del alumno/a de la tercera autorización")
	private String txtItinerario3;

	@Schema(description = "Porcentaje de kilometros al dia de la tercera autorización")
	private Double porcetanjeKmDia3;

	@Schema(description = "Porcentaje de kilometros totales de la tercera autorización")
	private Double porcetanjeTotalKm3;

	@Schema(description = "Identificador del fichero autorizacion tutor/a")
	private String idAutTutRodal;
	
	@Schema(description = "Texto del fichero autorizacion tutor/a")
	private String nombreFichero;

	@Schema(description = "Cantidad de autorizaciones rellenas")
	private Integer nuAut;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Identificador del periodo gasto")
	private PeriodoGastoDto periodoGasto;
	
	@Schema(description = "Idenficador del tutor que registra el gasto")
	private Long idTutorFct;
	
	@Schema(description = "Identificador de la matrícula del alumno")
	private Long idMatricula;
	
	@Schema(description = "Idenficador del centro")
	private Long idCentro;
}
