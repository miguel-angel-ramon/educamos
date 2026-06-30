package es.jccm.edu.alumnos.application.ports.in.faltasAsistenciaAlumno;

import java.sql.SQLException;
import java.util.List;

import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.ConvAlumnCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAlumnoCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAsistenciaAlumno;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.MotivoJustificacion;

public interface IFaltaAsistenciaAlumnoService {

	void setFaltaAsistenciaAlumno(String idUsuario, Long idCentro, List<FaltaAsistenciaAlumno> faltasAsistenciaAlumno);
	
	List<ConvAlumnCentro> getConvAlumn(Long idMatricula);

	List<FaltaAlumnoCentro> getFaltasAlumnoByFilter(Long idMatricula, String fecIni, String fecFin, String type, String idGroup);

	void setJustificacionFaltaAlumno(Long idFaltaAsistencia, Long idMotivo, String observacion) throws SQLException;
	
	void updateJustificacionFaltaAlumno(Long idJustificacion, Long idMotivo, String observacion) throws SQLException;
	
	void deleteJustificacionFaltaAlumno(Long idFaltaAsistencia) throws SQLException;
	
	List<MotivoJustificacion> getMotivosJustificacionFaltaAlumno();
	
	void borrarNotificacionCompleta(Long idNotificacion);

}
