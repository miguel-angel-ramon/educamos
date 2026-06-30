package es.jccm.edu.buzon.application.ports.in.SolicitudCorreoAlumno;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoDetalle;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.*;
import es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno.SolicitudActivacionCorreoDto;
import java.util.List;
import java.util.Optional;

public interface ISolicitudCorreoAlumnoService {

	Long guardarSolicitud(SolicitudCorreoUsuarioDTO solicitud);
	void guardarSolicitudes(List<SolicitudCorreoUsuarioDTO> solicitudes);
	List<SolicitudCorreoUsuarioDTO> findAll();
	List<CursoSolicitudDatosDTO> getCursoSolicitudDatos(Long centro, Long anno);
	List<SolicitudCorreoUsuarioDTO> findAllByCentro(Long idCentro);
	List<SolicitudActivacionCorreoDto> buscarSolicitudes();
	List<SolicitudActivacionCorreoDto> buscarSolicitudes(Long idOfertamatricCurso);
	List<UsuariotDto> getAlumnosFromCursoCentro(
		Long idCentro
		,Long idOfertaMatrig
		,Long anno
		,Optional<Long> crtPt
	);
	SolicitudCorreoUsuarioDTO findByIdCentroAndIdOfertamatricCursoAndEstadoSolicitud(
		Long idCentro
		,Long idOfertamatricCurso
		,Long estadoSolicitud
	);
	void updateCrtptFromUsuariost();
	void guardarAlumno(UsuariotDto alumno);

	List<UnidadDto> getUnidadesCorreoAlumnado(Long idCentro, Integer anno, Long idCurso);

	List<PermisoDto> getPermisosCorreoAlumnado();

	List<AlumnoDetalleDto> getAlumnosDetalleCorreoAlumnado(Long idCentro, Integer anno, Long idCurso);
	List<SolicitudActivacionCorreoDto> getSolicitudesByCursoCentro(Long idOfertamatricCurso, Long idCentro);
	void actualizarPermisoUsuariosT(Long idUsuarioT, String idPermiso);

}
