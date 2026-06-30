package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Gasto del tutor", description = "Descripcion para el modelo de Gasto tutor")
public class GastoAnexoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gasto del tutor")
	private Long id;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Periodo del gasto")
	private PeriodoGastoDto periodoGasto;
	
	@Schema(description = "Tipo de gasto")
	private TipoGastoDto tipoGasto;
	
	@Schema(description = "Centro")
	private CentroDto centro;	
	
	@Schema(description = "Tutor de la fct")
	private TutorFctDualDto tutorFct;	
}
