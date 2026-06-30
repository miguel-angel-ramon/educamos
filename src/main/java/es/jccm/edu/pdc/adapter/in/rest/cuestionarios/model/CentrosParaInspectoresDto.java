package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Información para un inspectos", description = "Proyección para rescatar los datos de los centros, zonas, etc., de un inspector")
public class CentrosParaInspectoresDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del empleado")
	Long idEmpleado;
	
	@Schema(description = "Id del centro")
	Long idCentro;
	
	@Schema(description = "Id de la zona")
	Long idZona;
	
	@Schema(description = "Descripción de la zona")
	String desZona;
	
	@Schema(description = "Titulo del centro")
	String TituloCentro;
	
	@Schema(description = "Código centro")
	Long CodCentro;
	
	@Schema(description = "Código provincia")
	Long idProvincia;
	
	@Schema(description = "Descripción provincia")
	String desProvincia;
	
	@Schema(description = "Curso académico")
	Long anio;
	
	@Schema(description = "Id del cuestionario")
	Long idCuestionario;

	@Schema(description = "Denominación del centro")
	String denomCentro;
	
}