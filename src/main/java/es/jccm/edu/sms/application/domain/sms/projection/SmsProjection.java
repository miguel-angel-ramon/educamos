package es.jccm.edu.sms.application.domain.sms.projection;


import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Date;

@Projection(name = "Verificación código sms", types = {Informe.class})
@Schema(name = "Sms", description = "Proyección para rescatar el código de verificación")
public interface SmsProjection {


	@Schema(name = "codigoVerificacion", description = "Código de verificación")
	public String getCodigoVerificacion();

	@Schema(name = "lActivo", description = "Indicador de activo")
	public Long getlActivo();

	@Schema(name = "fechaCreacion", description = "Fecha de creación")
	public LocalDateTime getFechaCreacion();








	
}

