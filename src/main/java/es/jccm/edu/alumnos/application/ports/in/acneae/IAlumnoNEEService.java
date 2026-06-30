package es.jccm.edu.alumnos.application.ports.in.acneae;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.acneae.AlumnoNEE;
import es.jccm.edu.alumnos.application.domain.acneae.DatosAlumnoNEE;

public interface IAlumnoNEEService {

	List<AlumnoNEE> getAlumnosNEE(Long idCentro, int annio,Long idOfertaMatriculacion, Long unidad) ;
	
	DatosAlumnoNEE getDatosAlumnoNEE(Long idMatricula);
}
