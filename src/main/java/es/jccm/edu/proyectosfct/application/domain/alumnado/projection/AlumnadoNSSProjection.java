package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ALUMNADO_NSS", description = "Descripcion para el modelo de alumnado NSS")
public interface AlumnadoNSSProjection {
	
	@Schema(description = "Documentacion")
	String getIpf();
	
	@Schema(description = "Provincia")
	String getProv();
	
	@Schema(description = "Primer apellido")
	String getAp1();
	
	@Schema(description = "Segundo apellido")
	String getAp2();
	
	@Schema(description = "Nombre")
	String getNom();
	
	@Schema(description = "Fecha nacimiento")
	String getFnac();
	
	@Schema(description = "Nacionalidad")
	String getNacionalidad();
	
	@Schema(description = "Nombre padre")
	String getNombrepadre();
	
	@Schema(description = "Nombre madre")
	String getNombremadre();
	
	@Schema(description = "Prefijo teléfono móvil")
	String getMovilpre();
	
	@Schema(description = "Teléfono móvil")
	String getMovil();
	

}
