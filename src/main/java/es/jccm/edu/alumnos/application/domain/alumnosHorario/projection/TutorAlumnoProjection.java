package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TutorAlumno", description = "Tutor y horario rescatados de la BBDD de comunica")
public interface TutorAlumnoProjection {
	
	@Schema(description = "Tutor_Unidad")
    String getTutor_Unidad();
	
	@Schema(description = "Horario_Visita")
    String getHorario_Visita();
}
