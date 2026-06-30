package es.jccm.edu.buzon.application.services.solicitudCorreoAlumno;

import es.jccm.edu.alumnos.application.services.evaluacion.EvaluacionService;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.*;
import es.jccm.edu.buzon.adapter.out.repository.solicitudCorreoAlumno.SolicitudCorreoAlumnoRepository;
import es.jccm.edu.buzon.application.domain.buzon.projection.CursoSolicitudDatosProjection;
import es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno.*;
import es.jccm.edu.buzon.application.ports.in.SolicitudCorreoAlumno.ISolicitudCorreoAlumnoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SolicitudCorreoAlumnoService implements ISolicitudCorreoAlumnoService {

	private static final Logger LOG = LogManager.getLogger(EvaluacionService.class);

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	SolicitudCorreoAlumnoRepository solicitudCorreoAlumnoRepository;

  public SolicitudCorreoAlumnoService(SolicitudCorreoAlumnoRepository solicitudCorreoAlumnoRepository) {
    this.solicitudCorreoAlumnoRepository = solicitudCorreoAlumnoRepository;
  }

	@Override
	@Transactional
	public Long guardarSolicitud(SolicitudCorreoUsuarioDTO solicitud){
		// Guarda la nueva Solicitud.
		SolicitudCorreoAlumno modelo = new SolicitudCorreoAlumno();
		try{
			modelo.setEstadoSolicitud(solicitud.getEstadoSolicitud());
			modelo.setFechaSolicitud(solicitud.getFechaSolicitud());
			modelo.setIdCentro(solicitud.getIdCentro());
			modelo.setIdSolicitud(solicitud.getIdSolicitud());
			modelo.setIdOfertamatricCurso(solicitud.getIdOfertamatricCurso());
			solicitudCorreoAlumnoRepository.save(modelo);
			return modelo.getIdSolicitud();
		}catch(Exception ex){
			LOG.error(ex);
		}
		return null;
	}

	@Override
	@Transactional
	public void guardarAlumno(UsuariotDto alumno){
		// Actualiza USUARIOS_T.CRT_PT con el ID Solicitud.
		try{
			alumno.setCRT_PT(alumno.getCRT_PT());
			solicitudCorreoAlumnoRepository.saveAlumno(alumno.getOID(), alumno.getCRT_PT());
		}catch(Exception ex){ LOG.error(ex); }
	}

	@Override
	public void updateCrtptFromUsuariost(){
		/** 
		 * Llama a un procedimiento en BD que se encarga de crear las actualizaciones.
		 * Cuando se añade un alumno sin correo a una clase donde ya había correo.
		 * Y la Petición está en estado [Procesando o Procesada].
		 * Igualmente si es la actualización la que está en estado [Procesando o Procesada].
		 */
		try{
			solicitudCorreoAlumnoRepository.updateCrtptFromUsuariost(checkAnno(null));
		}catch(Exception ex){ LOG.error(ex); }
	}

	// @Override
	// public void getDatosAlumnosDetalle(idCentro, idOfertaMatrig){
	// 	/** 
	// 	 * Llama a un procedimiento en BD que se encarga de crear las actualizaciones.
	// 	 * Cuando se añade un alumno sin correo a una clase donde ya había correo.
	// 	 * Y la Petición está en estado [Procesando o Procesada].
	// 	 * Igualmente si es la actualización la que está en estado [Procesando o Procesada].
	// 	 */
	// 	try{
	// 		solicitudCorreoAlumnoRepository.getDatosAlumnosDetalle(
	// 			idCentro
	// 			,idOfertaMatrig
	// 			,checkAnno(null)
	// 		);
	// 	}catch(Exception ex){ LOG.error(ex); }
	// }

	@Override
	@Transactional
	public void guardarSolicitudes(List<SolicitudCorreoUsuarioDTO> solicitudes) {
		solicitudes.stream().forEach(x -> guardarSolicitud(x));
	}

	@Override
	public List<SolicitudCorreoUsuarioDTO> findAll() {
		List<SolicitudCorreoAlumno> solicitudes = new ArrayList<>();
		solicitudCorreoAlumnoRepository.findAll().forEach(solicitudes::add);
		return solicitudes.stream().map(x -> modelMapper.map(x, SolicitudCorreoUsuarioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<SolicitudCorreoUsuarioDTO> findAllByCentro(Long idCentro) {
		List<SolicitudCorreoAlumno> solicitudes = new ArrayList<>();
		solicitudes = solicitudCorreoAlumnoRepository.findAllByIdCentro(idCentro);
		return solicitudes.stream().map(x -> modelMapper.map(x, SolicitudCorreoUsuarioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public SolicitudCorreoUsuarioDTO findByIdCentroAndIdOfertamatricCursoAndEstadoSolicitud(
		Long idCentro
		,Long idOfertamatricCurso
		,Long estadoSolicitud
	){
		SolicitudCorreoAlumno rtn = solicitudCorreoAlumnoRepository.findByIdCentroAndIdOfertamatricCursoAndEstadoSolicitud(
			idCentro
			,idOfertamatricCurso
			,estadoSolicitud
		);
		return modelMapper.map(rtn, SolicitudCorreoUsuarioDTO.class);
	}

	@Override
	public List<CursoSolicitudDatosDTO> getCursoSolicitudDatos(Long idCentro, Long anno) {
		List<CursoSolicitudDatosProjection> datos = new ArrayList<>();
		datos = solicitudCorreoAlumnoRepository.getCursoSolicitudDatosByCentro(idCentro, anno);
		return datos.stream().map(x -> modelMapper.map(x, CursoSolicitudDatosDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<UsuariotDto> getAlumnosFromCursoCentro(Long idCentro,Long idOfertaMatrig,Long anno,Optional<Long> idSolicitud) {
		  List<UsuariotProjection> a;
		  if (!idSolicitud.isEmpty()) {
			  a = solicitudCorreoAlumnoRepository
					  .getDatosAlumnosFromCursoCentro(idCentro, idOfertaMatrig, checkAnno(null), idSolicitud.orElse(0L));
		  } else {
			  a = solicitudCorreoAlumnoRepository
					  .getDatosAlumnosFromCursoCentro(idCentro, idOfertaMatrig, checkAnno(null));
		  }

		  List<UsuariotDto> usuariosDto = a.stream().map(x -> modelMapper.map(x, UsuariotDto.class)).collect(Collectors.toList());
		  String finalPermiso = getPermiso(idOfertaMatrig);
		  usuariosDto.forEach(user -> user.setCD_PERMISO_CORREO_ALUMNADO(finalPermiso));
		  return usuariosDto;

	}

	private String getPermiso(Long idOfertaMatrig) {
		String permiso = "0"; // default: Permiso deshabilitado
		Integer i = solicitudCorreoAlumnoRepository.esCursoPrimariaESO(idOfertaMatrig);
		if(solicitudCorreoAlumnoRepository.esCursoPrimariaESO(idOfertaMatrig)==1){
			permiso = "A01"; // Correo abierto primaria/ESO
		} else{
			permiso = "A15"; // Correo abierto bach/fp
		}
		return permiso;
	}

	private Long checkAnno(Long anno) {
		if(anno == null){
			/**
			 * El 1 de Octubre se incrementa el año que se guarda en anno si no se pasa un valor por parámetro
			 * ya que se supone que en esa fecha ya se habrán registrado las nuevas matrículas.
			 */
			LocalDate aux = LocalDate.now();
			return (aux.getMonthValue() > 9)
				?Long.valueOf(aux.getYear())
				:Long.valueOf((long) aux.getYear() - 1)
				;
			}
		return anno;
	}

	@Override
	public List<SolicitudActivacionCorreoDto> buscarSolicitudes() {
		List<SolicitudActivacionCorreoProjection> solicitudes = solicitudCorreoAlumnoRepository.getSolicitudesAdministrar(checkAnno(null));
		return solicitudes.stream().map(
			x -> modelMapper.map(x, SolicitudActivacionCorreoDto.class)
		).collect(Collectors.toList());
	}

	@Override
	public List<SolicitudActivacionCorreoDto> buscarSolicitudes(Long idOfertamatricCurso) {
		List<SolicitudActivacionCorreoProjection> solicitudes = solicitudCorreoAlumnoRepository.getSolicitudesAdministrar(
			checkAnno(null),
			idOfertamatricCurso
		);
		return solicitudes.stream().map(
			x -> modelMapper.map(x, SolicitudActivacionCorreoDto.class)
		).collect(Collectors.toList());
	}

	@Override
	public List<SolicitudActivacionCorreoDto> getSolicitudesByCursoCentro(Long idCurso, Long idCentro) {
		List<SolicitudActivacionCorreoProjection> solicitudes = solicitudCorreoAlumnoRepository.getSolicitudesByCursoCentro(
			idCurso
			,idCentro
			// ,checkAnno(null)
		);
		return solicitudes.stream().map(
			x -> modelMapper.map(x, SolicitudActivacionCorreoDto.class)
		).collect(Collectors.toList());
	}

	@Override
	public List<UnidadDto> getUnidadesCorreoAlumnado(Long idCentro, Integer anno, Long idCurso) {

		List<UnidadProjection> listadoUnidades = solicitudCorreoAlumnoRepository.getUnidadesCorreoAlumnado(idCentro, anno, idCurso);

		return listadoUnidades.stream().map(x -> modelMapper.map(x, UnidadDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PermisoDto> getPermisosCorreoAlumnado() {
		List<PermisoProjection> listadoPermisos = solicitudCorreoAlumnoRepository.getPermisosCorreoAlumnado();

		return listadoPermisos.stream().map(x -> modelMapper.map(x, PermisoDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<AlumnoDetalleDto> getAlumnosDetalleCorreoAlumnado(Long idCentro, Integer anno, Long idCurso) {
		List<AlumnoDetalleProjection> listadoAlumnos = solicitudCorreoAlumnoRepository.getAlumnosDetalleCorreoAlumnado(idCentro, anno, idCurso);

		return listadoAlumnos.stream().map(x -> modelMapper.map(x, AlumnoDetalleDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void actualizarPermisoUsuariosT(Long idUsuarioT, String idPermiso){
		// Actualizar el permiso [USUARIOS_T.CD_PERMISO_CORREO_ALUMNADO]
		try{
			solicitudCorreoAlumnoRepository.actualizarPermisoUsuariosT(idUsuarioT,idPermiso);
		}catch(Exception ex){
			LOG.error(ex);
		}
	}

}
