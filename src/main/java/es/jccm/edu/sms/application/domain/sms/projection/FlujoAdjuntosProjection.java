package es.jccm.edu.sms.application.domain.sms.projection;


import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Date;


@Schema(name = "FlujoDocumentoAprobacion", description = "Proyección para rescatar el código del flujo")
public interface FlujoAdjuntosProjection {


	@Schema(name = "nombre", description = "Nombre")
	public String getNombre();

	@Schema(name = "apellido1", description = "Apellido 1")
	public String getApellido1();
	
	@Schema(name = "apellido2", description = "Apellido 2")
	public String getApellido2();

	@Schema(name = "orden", description = "Orden")
	public Long getOrden();
	
	@Schema(name = "dni", description = "Dni")
	public String getDni();
	
	@Schema(name = "firmado", description = "Firmado")
	public Integer getFirmado();
	

	@Schema(name = "fechafirma", description = "Fecha Firma")
	public Date getFechaFirma();



	
}

