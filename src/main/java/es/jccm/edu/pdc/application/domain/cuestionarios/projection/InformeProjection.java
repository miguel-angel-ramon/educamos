package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "informe", types = {Informe.class})
@Schema(name = "Informe", description = "Proyección para rescatar los datos de un informe")
public interface InformeProjection {

	@Schema(description = "Id del Centro")
	Long getCodCentro();

	@Schema(description = "Id Competencia")
	Long getIdCompetencia();

	@Schema(description = "Nombre del Sector")
	String getSector();

	@Schema(description = "Código del Nivel")
	String getNivel();

	@Schema(description = "Código de la Competencia")
	String getCodCompetencia();

	@Schema(description = "Descripción de la Competencia")
	String getDescCompetencia();

	@Schema(description = "Valor calculado")
	Long getValor();

	@Schema(description = "Número de respuestas")
	Long getRespuestas();

	@Schema(description = "Año")
	Long getAnno();
	
	@Schema(description = "Descripción del objetivo")
	String getDesObjetivo();







	
}

