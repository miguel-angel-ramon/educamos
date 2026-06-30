package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "TutorAlumno", description = "Tutor y horario rescatados de la BBDD de comunica")
public class TutorAlumnoDTO {
	@Schema(description = "Tutor_Unidad")
    private String Tutor_Unidad;
	
	@Schema(description = "Horario_Visita")
    private String Horario_Visita;
}


