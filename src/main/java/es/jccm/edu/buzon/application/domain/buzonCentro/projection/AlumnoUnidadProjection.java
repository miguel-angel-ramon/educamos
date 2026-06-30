package es.jccm.edu.buzon.application.domain.buzonCentro.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoUnidadProjection", description = "Proyección para rescatar los alumnos y sus unidades")

public interface AlumnoUnidadProjection {

	@Schema(description = "Id de la matrícula")
    Long getIdMatricula();

	@Schema(description = "Id del alumno")
    String getIdAlumno();
    
	@Schema(description = "Id de la unidad")
    Long getIdUnidad();

	@Schema(description = "Nombre de la unidad")
    String getNombreUnidad();
    
	@Schema(description = "Curso")
    String getCurso();

	@Schema(description = "Id de la persona")
    Long getIdPersona();
  
	@Schema(description = "Nombre de la persona")
	String getNombre();
	
	@Schema(description = "Primer apellido de la persona")
	String getApellido1();
	
	@Schema(description = "Segundo apellido de la persona")
	String getApellido2();
}
