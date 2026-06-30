package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema (name = "ALUMNADO_ALTAS_BAJAS", description = "Descripcion para el modelo de Alumnado Altas y Bajas")
public interface AlumnadoAltasBajasProjection {

	@Schema (name =  "Código cuenta de cotización")
	String getCcc();
	
	@Schema (name = "Número de la Seguridad Social")
	String getNuss();
	
	@Schema (name = "Fecha Real de Alta")	
	Date getFechaRealAlta();
	
	
}
