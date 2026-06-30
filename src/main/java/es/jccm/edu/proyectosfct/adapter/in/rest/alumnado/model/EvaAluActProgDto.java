package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoProgramaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.datosprograma.model.DatosProgramaFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "EvaAluActProgDto", description = "Descripcion para el modelo de evaluacion alumno datos programa")
public class EvaAluActProgDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la tabla FCT_EVAALU_ACTPROG")
	private Long id;
	
	@Schema(description = "Actividad realizada")
	private Long realizada;
	
	@Schema(description = "Criterios adquiridos")
	private Long adquirida;
	
	@Schema(description = "observaciones")
	private String observaciones;

	@Schema(description = "criterios")
	private String criterios;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Convenio alumno programa")
	private AlumnoProgramaDto aluConvProg;
	
	@Schema(description = "Datos programa")
	private DatosProgramaFctDto datosPrograma;

}
