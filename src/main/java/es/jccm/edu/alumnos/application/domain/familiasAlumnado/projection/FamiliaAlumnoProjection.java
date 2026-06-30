package es.jccm.edu.alumnos.application.domain.familiasAlumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FamiliasAlumnado", description = "Proyección para obtener las familias del alumnado de un centro")
public interface FamiliaAlumnoProjection {
	
	@Schema(description = "Id de la familia")
	Long getIdFamilia();
	
	@Schema(description = "Número de identificación del Tutor nº1")
	String getNumideTutor1();
	
	@Schema(description = "Nombre y apellidos del Tutor nº1")
	String getNombreTutor1();
	
	@Schema(description = "Id del Tutor nº1")
	Long getIdTutor1();

	@Schema(description = "Número de identificación del Tutor nº2")
	String getNumideTutor2();
	
	@Schema(description = "Nombre y apellidos del Tutor nº2")
	String getNombreTutor2();
	
	@Schema(description = "Id del Tutor nº2")
	Long getIdTutor2();
	
	@Schema(description = "Dirección del domicilio familiar")
	String getDireccion();
	
	@Schema(description = "Número de teléfono de contacto")
	String getTelefono();
	
	@Schema(description = "Número de teléfono de urgencias")
	String getTfnoUrgencias();
	
	@Schema(description = "Tutor/es que residen en el domicilio")
	String getDomicilioDe();
	
	
	
	

}
