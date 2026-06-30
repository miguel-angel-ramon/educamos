package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Informe rodal", description = "Informe rodal")
public interface EvaInformeRodalProjection {

	@Schema(description = "Id rodal")
	String getIdRodal();
	
	@Schema(description = "Nombre del fichero")
	String getNombreFichero();
	
}
