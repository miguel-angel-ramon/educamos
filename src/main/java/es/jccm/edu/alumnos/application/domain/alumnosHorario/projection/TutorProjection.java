package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Tutor", description = "Tutor rescatados de la BBDD de comunica")
public interface TutorProjection {
	
	@Schema(description = "Id del Tutor")
	Long getidTutor();

	@Schema(description = "Nombre")
	String getNombre();
	
    @Schema(description = "Primer apellido")
    String getApellido1();

    @Schema(description = "Segundo apellido")
	String getApellido2();
    
    @Schema(description = "Nº identificacion")
	String getNumide();
    
    @Schema(description = "Teléfono")
    String getTelefono();
    
    @Schema(description = "Tlefnourg")
    String getTlefnourg();
    
    @Schema(description = "Id comunica")
	Long getidUsuarioComunica();


}
