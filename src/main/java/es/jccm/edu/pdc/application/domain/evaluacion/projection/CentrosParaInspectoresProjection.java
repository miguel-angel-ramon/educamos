package es.jccm.edu.pdc.application.domain.evaluacion.projection;


import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.data.rest.core.config.Projection;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;

@Projection(name = "Información para un inspectos", types = {AmbitoAsociado.class})
@Schema(name = "Centro-inspector", description = "Proyección para rescatar los datos de los centros, zonas, etc., de un inspector")
public interface CentrosParaInspectoresProjection {

	@Schema(description = "Id del empleado")
	Long getIdEmpleado();
	
	@Schema(description = "Id del centro")
	Long getIdCentro();
	
	@Schema(description = "Id de la zona")
	Long getIdZona();
	
	@Schema(description = "Descripción de la zona")
	String getDesZona();
	
	@Schema(description = "Titulo del centro")
	String getTituloCentro();
	
	@Schema(description = "Código centro")
	Long getCodCentro();
	
	@Schema(description = "Código provincia")
	Long getIdProvincia();
	
	@Schema(description = "Descripción provincia")
	String getDesProvincia();
	
	@Schema(description = "Curso académico")
	Long getAnio();
	
	@Schema(description = "Id del cuestionario")
	Long getIdCuestionario();
}