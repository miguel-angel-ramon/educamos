package es.jccm.edu.evaluacion.application.services.aulaVirtual;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.*;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaValoracionCriterioActividadAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaRelacionPonderacionCriteriosEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaUnidadProgramacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.EvaValoracionTemporalCriterioEvaluacionAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.ValoracionCriteriosRepository;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.ProgramacionAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.*;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.AlumnosPorMateriaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionPonderacionCriteriosEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.AlumnosActividadCalcularAllCriteriosProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.DatosCalcularAllCriteriosProjection;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ActividadAulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AlumnoActividadAulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualListDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.CalificacionActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ContenidoActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ContenidoUsuarioActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.CriterioActividadAulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ModuloActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CriterioAlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository;
import es.jccm.edu.evaluacion.application.domain.Constantes;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.AlumnoMoodle;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaUsuarioAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.UserGradesMoodle;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.AlumnoProjection;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.EvaAulaVirtualProjection;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.RelacionAlumnoOidProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.SistemaCalificacionCua;
import es.jccm.edu.evaluacion.application.domain.programacionAula.AlumnosPorMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionUnidadProgramacionCriteriosEvaluacion;
import es.jccm.edu.evaluacion.application.ports.in.aulaVirtual.IEvaAulaVirtualService;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaValoracionCriterioActividadAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaProgramacionAulaService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionProgramacionAulaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import es.jccm.edu.evaluacion.application.services.calificacionActividades.EvaCalificacionActividadesService;
import es.jccm.edu.evaluacion.application.services.programacionAula.EvaActividadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaAulaVirtualService implements IEvaAulaVirtualService {

	@Autowired
	private EvaAulaVirtualRepository aulaVirtualRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment env;

	@Value("${front.base-path}")
	public String BASE_PATH_FRONT;

	@Value("${entorno}")
	public String ENTORNO;

	// Rutas de los WebServices
	public static final String BASE_TOKEN = "/webservice/rest/server.php?wstoken=";
	public static final String BASE_WEBSERVICE = "&wsfunction=";
	public static final String BASE_USER_IDS = "&userids[0]=";
	public static final String MEDIA_TYPE_JSON = "&moodlewsrestformat=json";
	public static final String READ_USER_IDNUMBER = "core_user_get_users_by_idnumber";
	public static final String COURSE_ID = "&courseid=";
	public static final String CM_ID = "&cmid=";
	public static final String USER_ID = "&userid=";
	public static final String READ_COURSE_GET_CONTENTS = "local_core_course_get_contents";
	public static final String WSTOKEN_COURSE_GET_CONTENTS = "a02960b5362d3af789d1873000842ebb";
	public static final String WSTOKEN_COURSE_GET_CONTENTS_DES = "5064deb4d6aa81e73e55dea27f42ac1e";

	public static final String READ_GRADE_ITEMS = "local_gradereport_user_get_grade_items";
	public static final String WSTOKEN_GRADE_ITEMS = "a02960b5362d3af789d1873000842ebb";
	public static final String WSTOKEN_READ_USER_ID_NUMBER_DES = "ff5515fc704b791df2eb58169e2c862d";
	public static final String WSTOKEN_READ_USER_ID_NUMBER = "ff5515fc704b791df2eb58169e2c862d";
	
	public static final String READ_ACTIVITY_GET_USERS = "local_core_course_get_enrolled_users_by_cmid";
	public static final String WSTOKEN_ACTIVITY_GET_USERS = "a02960b5362d3af789d1873000842ebb";
	public static final String WSTOKEN_ACTIVITY_GET_USERS_DES = "ff5515fc704b791df2eb58169e2c862d";
	
	private static final Map<String, String> servidoresAulasVirtuales = new HashMap<>();

	@Autowired
	private IEvaAlumnoService alumnoService;

	@Autowired
	private IEvaProgramacionAulaService programAulaService;
	
	@Autowired
	private IEvaRelacionProgramacionAulaAlumnoService relacionProgramacionAulaAlumnoService;
	
	@Autowired
	private EvaRelacionActividadCriterioEvaluacionRepository relacionActividadCriterioEvaluacionRepository;

	@Autowired
	private EvaRelacionActividadAlumnoRepository relacionActividadAlumnoRepository;

	@Autowired
	private EvaRelacionProgramacionAulaActividadRepository relacionProgramacionAulaActividadRepository;

	@Autowired
	private EvaActividadService actividadService;

	@Autowired
	private IEvaValoracionCriterioActividadAlumnoService valoracionCriterioActividadAlumnoService;

	@Autowired
	private EvaProgramacionAulaRepository programacionAulaRepository;
	
	@Autowired
    private EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository relacionUnidadProgramacionCriteriosEvaluacionRepository;
	
	@Autowired
    private EvaCalificacionActividadesService califActividadesService;

	@Autowired
	private EvaActividadRepository evaActividadRepository;

	@Autowired
	private EvaMatriculaAlumnoRepository matriculaRepository;

	@Autowired
	private EvaAlumnoRepository alumnoRepository;

	@Autowired
	private EvaRelacionPonderacionCriteriosEvaluacionRepository relacionPonderacionCriteriosEvaluacionRepository;

	@Autowired
	private EvaUnidadProgramacionRepository unidadProgramacionRepository;

	@Autowired
	private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;

	@Autowired
	private ValoracionCriteriosRepository valoracionCriteriosRepository;

	@Autowired
	private EvaValoracionCriterioActividadAlumnoRepository valoracionCriterioActividadAlumnoRepository;

	@Autowired
	private EvaValoracionTemporalCriterioEvaluacionAlumnoRepository criterioAlumnoTempRepository;

	@PostConstruct
	private void postConstruct() {

		String anno = aulaVirtualRepository.getAnnoAcademicoActual().substring(2, 4);

		anno += (Integer.parseInt(anno) + 1);

		servidoresAulasVirtuales.put(Constantes.NUMERO_1, Constantes.AULAS_FP + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_2, Constantes.AULAS_EPAREGESP + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_3, Constantes.AULAS_PRIMARIA + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_4, Constantes.AULAS_ESOABGU + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_5, Constantes.AULAS_ESOCRCU + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_6, Constantes.AULAS_ESOTO + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_7, Constantes.AULAS_BACH + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_8, Constantes.AULAS_CICLOS + anno + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_9, Constantes.AULAS + anno + Constantes.ENTORNO_DES + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
		servidoresAulasVirtuales.put(Constantes.NUMERO_10, Constantes.AULAS_BACH + anno + Constantes.ENTORNO_DES + Constantes.DOMINIO_CASTILLA_LA_MANCHA);
	}

	@Override
	public AulaVirtualDTO getAulaVirtualById(Long idAula) {
		Optional<EvaAulaVirtual> aulaVirtual = aulaVirtualRepository.findById(idAula);
		if (aulaVirtual.isPresent()) {
			return modelMapper.map(aulaVirtual.get(), AulaVirtualDTO.class);
		} else {
			return null;
		}
	}

	@Override
	public List<AulaVirtualListDTO> getAulasVirtuales(List<Long> idEmpleados, Integer anno) throws EvaluacionException {
		
		// Obtengo la información de las Aulas Virtuales del profesor
		List<EvaAulaVirtualProjection> aulasVirtualesProjection = aulaVirtualRepository
				.findAulasVirtualesByEmpleadoAnno(idEmpleados, anno);

		// Transformo la proyección a entidad para poder trabajar con sus datos
		List<AulaVirtualListDTO> aulasVirtuales = aulasVirtualesProjection.stream().map(x -> modelMapper.map(x, AulaVirtualListDTO.class)).collect(Collectors.toList());
		
		aulasVirtuales.stream().forEach(elemento -> elemento.setAlumnos(alumnoService.getAlumnosAulaVirtual(elemento.getId())));
		
		aulasVirtuales.stream().forEach(elemento -> elemento.setIdCurso(generaIdCurso(elemento.getCursoString())));
			
		return aulasVirtuales;

	}

	@Override
	public List<AlumnosPorMateriaDTO> getAlumnosAula(Long idProgramacionAula, Long idAula) {
		List<AlumnosPorMateria> alumnos = aulaVirtualRepository.getAlumnosByAula(idProgramacionAula, idAula).stream()
				.map(x -> modelMapper.map(x, AlumnosPorMateria.class)).collect(Collectors.toList());

		return alumnos.stream().map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public Boolean vincularAulaVirtual(Long idProgramacionAula, Long idAula) throws EvaluacionException {
		Boolean vinculado = Boolean.FALSE;
		try {
			EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
			EvaAulaVirtual aulaVirtual = aulaVirtualRepository.findById(idAula).orElse(null);
			if (programacionAula != null && aulaVirtual != null) {
				AulaVirtualDTO aulaVirt = modelMapper.map(aulaVirtual, AulaVirtualDTO.class);
				// Almacenamos en la relación Prog. aula - Alumno el idUsuarioMoodle
				List<EvaRelacionProgramacionAulaAlumno> relacionesProgramacionAulaAlumno = programacionAula.getRelacionesProgramacionAulaAlumno();
				if(relacionesProgramacionAulaAlumno != null 
						&& !relacionesProgramacionAulaAlumno.isEmpty()) {
					obtenerRelacionUsuarioMoodle(idAula, relacionesProgramacionAulaAlumno);
				}
				
				ProgramacionAulaDTO progAula = modelMapper.map(programacionAula, ProgramacionAulaDTO.class);
				progAula.setAulaVirtual(aulaVirt);
				progAula.setActualizaMoodle(new Date());
				
				programAulaService.updateProgramacionAula(progAula);
				vinculado = Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Se ha producido un error al vincular la programación de aula del aula virtual");
			throw new EvaluacionException(
					"Se ha producido un error al vincular la programación de aula del aula virtual", e);
		}
		return vinculado;
	}

	private void obtenerRelacionUsuarioMoodle(Long idAula, 
			List<EvaRelacionProgramacionAulaAlumno> relacionesProgramacionAulaAlumno) throws EvaluacionException {
		if(relacionesProgramacionAulaAlumno != null 
				&& !relacionesProgramacionAulaAlumno.isEmpty()) {
			
			List<RelacionAlumnoOidProjection> relacionesAlumnoOid = aulaVirtualRepository.getRelacionAlumnoOidByIdAula(idAula);
			if(!relacionesAlumnoOid.isEmpty()) {
				List<RelacionAlumnoOidDTO> relaciones = relacionesAlumnoOid.stream().map(x -> modelMapper.map(x, RelacionAlumnoOidDTO.class)).collect(Collectors.toList());
				for(EvaRelacionProgramacionAulaAlumno relacion: relacionesProgramacionAulaAlumno) {
					RelacionAlumnoOidDTO rel = relaciones.stream()
			                .filter(elemento -> relacion.getMatriculaAlumno().getAlumno().getId().equals(elemento.getIdAlumno()))
			                .findFirst()
			                .orElse(null);
					if(rel != null) {
						EvaUsuarioAulaVirtual usuario = obtenerInfoUsuario(rel.getOid(), idAula);
						if(usuario != null) {
							relacion.setIdUsuarioMoodle(usuario.getId());
							relacionProgramacionAulaAlumnoService.guardar(relacion);
						}
					}
				}
			}
		}
	}

	public Long obtenerIdMoodleAlumno(Long idAula,
									  EvaRelacionProgramacionAulaAlumno relacionAlumno) throws EvaluacionException {
		if (relacionAlumno != null) {

			List<RelacionAlumnoOidProjection> relacionesAlumnoOid = aulaVirtualRepository.getRelacionAlumnoOidByIdAula(idAula);
			if (!relacionesAlumnoOid.isEmpty()) {
				List<RelacionAlumnoOidDTO> relaciones = relacionesAlumnoOid.stream().map(x -> modelMapper.map(x, RelacionAlumnoOidDTO.class)).collect(Collectors.toList());
				RelacionAlumnoOidDTO rel = relaciones.stream()
						.filter(elemento -> relacionAlumno.getMatriculaAlumno().getAlumno().getId().equals(elemento.getIdAlumno()))
						.findFirst()
						.orElse(null);
				if (rel != null) {
					EvaUsuarioAulaVirtual usuario = obtenerInfoUsuario(rel.getOid(), idAula);
					if (usuario != null) {
						return usuario.getId();
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<AlumnoDTO> comprobarAlumnado(Long idProgramacionAula, Long idAula) {
		// Obtenemos lista de alumnos de la prog. aula
		List<AlumnoProjection> alumnosProgAulaProjection = aulaVirtualRepository
				.getAlumnosByProgAulaAndAula(idProgramacionAula, idAula);
		List<AlumnoDTO> alumnosProgAula = alumnosProgAulaProjection.stream()
				.map(alumno -> modelMapper.map(alumno, AlumnoDTO.class)).collect(Collectors.toList());

		// Obtenemos lista de alumnos del aula virtual
		List<AlumnoDTO> alumnosAulaVirtual = alumnoService.getAlumnosAulaVirtual(idAula);

		// Comparamos con la lista de alumnos del aula virtual
		for (AlumnoDTO a : alumnosProgAula) {
			AlumnoDTO alum = alumnosAulaVirtual.stream()
					.filter(alumno -> a.getNumEscolar().equals(alumno.getNumEscolar())).findAny().orElse(null);
			a.setEnAulaVirtual(alum != null ? Boolean.TRUE : Boolean.FALSE);
		}

		return alumnosProgAula;
	}

	@Transactional
	@Override
	public Boolean desvincularAulaVirtual(Long idProgramacionAula, Boolean mantenerDatos) throws EvaluacionException {
		Boolean desvinculado = Boolean.FALSE;
		try {
			EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
			if (programacionAula != null) {
				ProgramacionAulaDTO progAula = modelMapper.map(programacionAula, ProgramacionAulaDTO.class);
				if (mantenerDatos.booleanValue()) {
					desvinculado = desvincularAulaVirtual(progAula);
				} else {
					desvinculado = desvincularAulaVirtualYEliminarDatos(progAula);
				}
			}
		} catch (Exception e) {
			log.error("Se ha producido un error al desvincular la programación de aula del aula virtual");
			throw new EvaluacionException(
					"Se ha producido un error al desvincular la programación de aula del aula virtual", e);
		}
		return desvinculado;
	}

	private Boolean desvincularAulaVirtualYEliminarDatos(ProgramacionAulaDTO progAula) throws EvaluacionException {
		Boolean desvinculado = Boolean.FALSE;
		// Hay que desvincular y eliminar los datos (Actividades que tengan la columna
		// LG_VIENEMOODLE = 1 y todo lo que corresponda a dichas actividades)
		if (progAula.getRelacionesProgramacionAulaActividad() != null) {
			List<ActividadDTO> actividadesAEliminar = new ArrayList<>();
			for (RelacionProgramacionAulaActividadDTO relacionProgramacionAulaActividad : progAula
					.getRelacionesProgramacionAulaActividad()) {
				ActividadDTO actividad = relacionProgramacionAulaActividad.getActividad();
				if (actividad.getLprocedeMoodle() != null && actividad.getLprocedeMoodle().equals(1)) {
					actividadesAEliminar.add(actividad);
				}
			}

			if (!actividadesAEliminar.isEmpty()) {
				// Eliminamos las actividades y los datos correspondientes a dichas actividades
				desvinculado = eliminarActividades(actividadesAEliminar);
				if (desvinculado) {
					progAula.setAulaVirtual(null);
					progAula.setActualizaMoodle(null);
					programAulaService.updateProgramacionAula(progAula);
				}
			} else {
				// No hay actividades vinculadas procedentes del Aula que haya que eliminar
				desvinculado = desvincularAulaVirtual(progAula);
			}
		}
		return desvinculado;
	}

	private Boolean desvincularAulaVirtual(ProgramacionAulaDTO progAula) throws EvaluacionException {
		Boolean desvinculado = Boolean.FALSE;
		try {
			progAula.setAulaVirtual(null);
			progAula.setActualizaMoodle(null);
			programAulaService.updateProgramacionAula(progAula);
			desvinculado = Boolean.TRUE;
		} catch (Exception e) {
			log.error("Se ha producido un error al actualizar la programación de aula");
			throw new EvaluacionException("Se ha producido un error al actualizar la programación de aula", e);
		}

		return desvinculado;
	}

	private Boolean eliminarActividades(List<ActividadDTO> actividadesAEliminar) throws EvaluacionException {
		Boolean desvinculado = Boolean.FALSE;
		try {
			for (ActividadDTO actividad : actividadesAEliminar) {
				EvaActividad actividadABorrar = actividadService.getActividadById(actividad.getId());
				if (actividadABorrar != null) {
					// Eliminamos calificaciones si existen
					eliminarCalificaciones(actividadABorrar.getRelacionesActividadAlumnos(),
							actividadABorrar.getRelacionesActividadCriterios());

					// Se borran todas las relaciones
					relacionActividadCriterioEvaluacionRepository.deleteAllByActividad(actividadABorrar);
					relacionProgramacionAulaActividadRepository.deleteAllByActividad(actividadABorrar);
					relacionActividadAlumnoRepository.deleteAllByActividadId(actividadABorrar.getId());
					// Se borra la actividad
					actividadService.deleteActividadById(actividadABorrar.getId());
					log.info("Se ha eliminado correctamente la actividad con id= " + actividadABorrar.getId());
				}
			}
			desvinculado = Boolean.TRUE;
		} catch (Exception e) {
			log.error("Se ha producido un error al eliminar las actividades de la programación de aula");
			throw new EvaluacionException(
					"Se ha producido un error al eliminar las actividades de la programación de aula", e);
		}
		return desvinculado;
	}

	private void eliminarCalificaciones(List<EvaRelacionActividadAlumno> relacionesActividadAlumnos,
			List<EvaRelacionActividadCriterioEvaluacion> relacionesActividadCriterios) {
		if (relacionesActividadAlumnos != null && !relacionesActividadAlumnos.isEmpty()
				&& relacionesActividadCriterios != null && !relacionesActividadCriterios.isEmpty()) {
			for (EvaRelacionActividadAlumno relacionActividadAlumno : relacionesActividadAlumnos) {
				for (EvaRelacionActividadCriterioEvaluacion relacionActividadCriterio : relacionesActividadCriterios) {
					EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno = valoracionCriterioActividadAlumnoService
							.findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(
									relacionActividadCriterio.getId(), relacionActividadAlumno.getId());
					if (valoracionCriterioActividadAlumno != null) {
						valoracionCriterioActividadAlumnoService.eliminar(valoracionCriterioActividadAlumno);
					}
				}
			}
		}
	}

	@Override
	public List<ActividadDTO> getActividadesAula(ProgramacionAulaDTO programacionAula, Long idAula) throws EvaluacionException {
		
		List<ActividadDTO> actividades = new ArrayList<>();
		
		try {

			// Obtengo la información del Aula Virtual
			EvaAulaVirtualProjection aulaVirtualProjection = aulaVirtualRepository.findAulaById(idAula);

			AulaVirtualListDTO aulaVirtual = modelMapper.map(aulaVirtualProjection, AulaVirtualListDTO.class);
			
			aulaVirtual.setAlumnos(alumnoService.getAlumnosAulaVirtual(aulaVirtual.getId()));
			
			aulaVirtual.setIdCurso(generaIdCurso(aulaVirtual.getCursoString()));
			
			// Creo una instancia de RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Establece las cabeceras de la solicitud
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Crea una entidad de solicitud con las cabeceras
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			// Llamo a los segmentos para rescatar la información del usuario en cada idPlataforma
			// Construyo la url
			String courseId = aulaVirtualRepository.getCourseIdByIdAula(idAula);

			String idPlataforma = aulaVirtual.getIdPlataforma();
			if (ENTORNO.equals("local") || ENTORNO.equals("desa")) {idPlataforma = Constantes.NUMERO_10;}
			String url = servidoresAulasVirtuales.get(idPlataforma) + BASE_TOKEN
					+ aulaVirtual.getTokenPlataforma() + BASE_WEBSERVICE + READ_COURSE_GET_CONTENTS + MEDIA_TYPE_JSON
					+ COURSE_ID + courseId;
			
			// Realiza la llamada
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			String respuesta = response.getBody();

			if(StringUtils.isNotBlank(respuesta) && (!respuesta.contains(Constantes.EXCEPTION) || !respuesta.contains(Constantes.ERROR_NO_DATA))) {
				List<ActividadAulaVirtualDTO> actividadesAulaVirtual = Arrays.asList(objectMapper.readValue(respuesta, ActividadAulaVirtualDTO[].class));

				if(actividadesAulaVirtual != null && !actividadesAulaVirtual.isEmpty()) {
					for(ActividadAulaVirtualDTO act: actividadesAulaVirtual) {
						if(act.getModules() != null && !act.getModules().isEmpty()) {
							actividades.addAll(extraerModulosActividad(act.getModules(), aulaVirtual, programacionAula));
						}
					}
				}
			}

		} catch (JsonProcessingException e) {
			log.error("Se ha producido un error al obtener el listado de actividades del aula con id: " + idAula, e);
			throw new EvaluacionException("Se ha producido un error al obtener el listado de actividades del aula con id: " + idAula, e);
		}
		
		actividades.sort(Comparator.comparing(ActividadDTO::getIdActividadMoodle, Comparator.nullsLast(Comparator.naturalOrder())));
		
		return actividades;
	}
	
	@Override
	public List<ActividadDTO> getCriteriosActividades(AulaVirtualListDTO aulaVirtual, List<ActividadDTO> actividadesDto, Long idUnidadProgramacion) throws EvaluacionException {
		List<ActividadDTO> actividades = new ArrayList<>();
		try {

			// Creo una instancia de RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Establece las cabeceras de la solicitud
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Crea una entidad de solicitud con las cabeceras
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			// Llamo a los segmentos para rescatar la información del usuario en cada
			// idPlataforma
			// Construyo la url
			String courseId = aulaVirtualRepository.getCourseIdByIdAula(aulaVirtual.getId());
			for (ActividadDTO actividadDto : actividadesDto) {

				String idPlataforma = aulaVirtual.getIdPlataforma();
				if (ENTORNO.equals("local") || ENTORNO.equals("desa")) {idPlataforma = Constantes.NUMERO_10;}
				String url = servidoresAulasVirtuales.get(idPlataforma) + BASE_TOKEN
						+ aulaVirtual.getTokenPlataforma() + BASE_WEBSERVICE + READ_GRADE_ITEMS + MEDIA_TYPE_JSON
						+ COURSE_ID + courseId + CM_ID + actividadDto.getIdActividadMoodle();

				//Se le pasa el id del último alumno para agilizar la llamada
				Long userId = null;
				if (actividadDto.getAlumnos() != null && !actividadDto.getAlumnos().isEmpty()) {
					userId = actividadDto.getAlumnos().get(actividadDto.getAlumnos().size() - 1).getIdUsuarioMoodle();
				}

				if (userId != null) {
					url += USER_ID + userId;
				}
				
				// Realiza la llamada
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

				String respuesta = response.getBody();

				if(StringUtils.isNotBlank(respuesta) && (!respuesta.contains(Constantes.EXCEPTION) || !respuesta.contains(Constantes.ERROR_NO_DATA))) {
					ContenidoActividadDTO contenidoActividad = objectMapper.readValue(respuesta, ContenidoActividadDTO.class);

					if(contenidoActividad != null && !contenidoActividad.getUsergrades().isEmpty()) {
						actividades.add(extraerDatos(contenidoActividad.getUsergrades(), actividadDto.getIdActividadMoodle(), idUnidadProgramacion));
					}
				}
			}

		} catch (JsonProcessingException e) {
			log.error("Se ha producido un error al obtener listado de criterios para las actividades del aula virtual con id: " + aulaVirtual.getId(), e);
			throw new EvaluacionException("Se ha producido un error al obtener listado de criterios para las actividades del aula virtual con id: " + aulaVirtual.getId(), e);
		}
		return actividades;
	}
	
	private List<ActividadDTO> extraerModulosActividad(List<ModuloActividadDTO> modulosActividad, AulaVirtualListDTO aulaVirtual, ProgramacionAulaDTO programacionAula) throws EvaluacionException {
		List<ActividadDTO> actividades = new ArrayList<>();
		for(ModuloActividadDTO moduloActividad: modulosActividad) {
			ActividadDTO actividad = new ActividadDTO();
			actividad.setIdActividadMoodle(moduloActividad.getId());
			actividad.setNombre(moduloActividad.getName());
			actividad.setLprocedeMoodle(1);
			List<AlumnoDTO> alumnosActividad = getAlumnosActividad(moduloActividad.getId(), aulaVirtual, programacionAula);
			actividad.setAlumnos(alumnosActividad);
			// Aquí almacemos los id_matrículas, que son los qeu luego se utilizan para guardar la actividad
			actividad.setAlumnosIds(alumnosActividad.stream().map(AlumnoDTO::getIdMatricula).collect(Collectors.toList()));
			actividades.add(actividad);
		}
		
		return actividades;
	}

	private ActividadDTO extraerDatos(List<CalificacionActividadDTO> datosActividad, Long idActividad, Long idUnidadProgramacion) {
		ActividadDTO actividad = new ActividadDTO();
		actividad.setIdActividadMoodle(idActividad);
		actividad.setLprocedeMoodle(1);
		
		// Obtenemos los criterios de la Unidad de Programación para marcar si coinciden con los de la Prog. aula
		List<CriterioEvaluacionDTO> criteriosUP = getCriteriosUnidadProgramacion(idUnidadProgramacion);
		for(CalificacionActividadDTO datoActividad: datosActividad) {
			if(datoActividad.getGradeitems() != null) {
				actividad.setCriteriosEvaluacion(extraerDatosCriterios(datoActividad.getGradeitems(), criteriosUP));
				actividad.setCriteriosEvaluacionIds(actividad.getCriteriosEvaluacion().stream().map(CriterioEvaluacionDTO :: getId).collect(Collectors.toList()));
			}
			if (actividad.getCriteriosEvaluacion() != null && !actividad.getCriteriosEvaluacion().isEmpty()) {
				actividad.setCoincidenciaCriterios(actividad.getCriteriosEvaluacion().stream().anyMatch(c -> c.isEnUnidadProgramacion()));
			}
		}
		
		return actividad;
	}

	private List<CriterioEvaluacionDTO> getCriteriosUnidadProgramacion(Long idUnidadProgramacion) {
		List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> relacionUPcriteriosEvaluacion = 
				relacionUnidadProgramacionCriteriosEvaluacionRepository.findByUnidadProgramacionId(idUnidadProgramacion);
		List<CriterioEvaluacionDTO> criteriosEvaluacion = relacionUPcriteriosEvaluacion.stream().map(x -> modelMapper.map(x.getCriterioEvaluacion(), CriterioEvaluacionDTO.class)).collect(Collectors.toList());
		criteriosEvaluacion.sort(Comparator.comparing(CriterioEvaluacionDTO::getIdCompetenciaEspecifica, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getAbreviatura));
		return criteriosEvaluacion;
	}

	private List<CriterioEvaluacionDTO> extraerDatosCriterios(List<CriterioActividadAulaVirtualDTO> criterios, List<CriterioEvaluacionDTO> criteriosUP) {
		List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
		for(CriterioActividadAulaVirtualDTO criterio: criterios) {
			CriterioEvaluacionDTO crit = criteriosUP.stream().filter(x -> x.getAbreviatura().equals(criterio.getShortname())).findFirst().orElse(null);
			if(crit != null) {
				criteriosActividad.add(crit);
			} else {
				EvaCriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findByAbreviatura(criterio.getShortname()).orElse(null);
				if (criterioEvaluacion != null) {
					CriterioEvaluacionDTO criterioEvaluacionDto = modelMapper.map(criterioEvaluacion, CriterioEvaluacionDTO.class);
					criteriosActividad.add(criterioEvaluacionDto);
				}
			}
		}
		criteriosActividad.sort(Comparator.comparing(CriterioEvaluacionDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getAbreviatura));
		
		for (CriterioEvaluacionDTO crit : criteriosUP) {
			CriterioEvaluacionDTO criterio = criteriosActividad.stream()
					.filter(c -> crit.getAbreviatura().equals(c.getAbreviatura())).findAny().orElse(null);
			crit.setEnUnidadProgramacion(criterio != null ? Boolean.TRUE : Boolean.FALSE);
		}
		
		return criteriosActividad;
	}

	@Override
	public List<AlumnoDTO> getAlumnosActividad(Long idActividad, AulaVirtualListDTO aulaVirtual, ProgramacionAulaDTO programacionAula) throws EvaluacionException {
		List<AlumnoDTO> alumnosActividad = new ArrayList<>();
		
		try {

			// Creo una instancia de RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Establece las cabeceras de la solicitud
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Crea una entidad de solicitud con las cabeceras
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			// Llamo a los segmentos para rescatar la información del usuario en cada
			// idPlataforma
			// Construyo la url

			String idPlataforma = aulaVirtual.getIdPlataforma();
			if (ENTORNO.equals("local") || ENTORNO.equals("desa")) {idPlataforma = Constantes.NUMERO_10;}
			String url = servidoresAulasVirtuales.get(idPlataforma) + BASE_TOKEN
					+ aulaVirtual.getTokenPlataforma() + BASE_WEBSERVICE + READ_ACTIVITY_GET_USERS + MEDIA_TYPE_JSON
					+ CM_ID + idActividad;
			
			// Realiza la llamada
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			String respuesta = response.getBody();
			
			if(StringUtils.isNotBlank(respuesta) && (!respuesta.contains(Constantes.EXCEPTION) || !respuesta.contains(Constantes.ERROR_NO_DATA))) {
				ContenidoUsuarioActividadDTO contenidoAlumnosAct = objectMapper.readValue(respuesta, ContenidoUsuarioActividadDTO.class);

				if(contenidoAlumnosAct != null && contenidoAlumnosAct.getUsers() != null && !contenidoAlumnosAct.getUsers().isEmpty()) {
					alumnosActividad.addAll(extraerDatosAlumno(contenidoAlumnosAct.getUsers(), programacionAula));
				}
			}

		} catch (JsonProcessingException e) {
			log.error("Se ha producido un error al obtener listado de alumnos para la actividad con id: " + idActividad, e);
			throw new EvaluacionException("Se ha producido un error al obtener listado de alumnos para la actividad con id: " + idActividad, e);
		}
		
		return alumnosActividad;
	}

	private List<AlumnoDTO> extraerDatosAlumno(List<AlumnoActividadAulaVirtualDTO> alumnosAct, ProgramacionAulaDTO programacionAula) {
		
		List<AlumnoDTO> alumnosActividad = new ArrayList<>();
		
		// En la tabla EVA_RELPROGAULALUM deben estar todas las relaciones de alumnos con la prog. aula y su id en el Aula virtual
		// Únicamente incluimos esos alumnos
		if (programacionAula != null && programacionAula.getRelacionesProgramacionAulaAlumno() != null && !programacionAula.getRelacionesProgramacionAulaAlumno().isEmpty()) {
			List<RelacionProgramacionAulaAlumnoDTO> relacionesProgramacionAulaAlumno = programacionAula.getRelacionesProgramacionAulaAlumno();
			if(relacionesProgramacionAulaAlumno != null 
					&& !relacionesProgramacionAulaAlumno.isEmpty()) {
				List<Long> idsUsuariosMoodle = relacionesProgramacionAulaAlumno.stream().map(RelacionProgramacionAulaAlumnoDTO::getIdUsuarioMoodle).collect(Collectors.toList());

				List<RelacionProgramacionAulaAlumnoDTO> relacionesProgramacionAulaAlumnoFiltrada = relacionesProgramacionAulaAlumno.stream().filter(x -> x.getIdUsuarioMoodle()!= null).collect(Collectors.toList());

				for(AlumnoActividadAulaVirtualDTO alumno: alumnosAct) {
					if(idsUsuariosMoodle.contains(alumno.getId())) {
						Optional<RelacionProgramacionAulaAlumnoDTO> rel = relacionesProgramacionAulaAlumnoFiltrada.stream()
					            .filter(objeto -> objeto.getIdUsuarioMoodle().equals(alumno.getId()))
					            .findFirst();

					        if (rel.isPresent()) {
					        	AlumnoDTO al = rel.get().getMatriculaAlumno().getAlumno();
					        	al.setIdMatricula(rel.get().getMatriculaAlumno().getId());
					        	al.setEnAulaVirtual(Boolean.TRUE);
								al.setIdUsuarioMoodle(alumno.getId());
					        	alumnosActividad.add(al);
					        }
					}
				}
			}
		}
		
		
		return alumnosActividad;
	}
	
	private EvaUsuarioAulaVirtual obtenerInfoUsuario(Long oid, Long idAula) throws EvaluacionException {
		EvaUsuarioAulaVirtual usuarioMoodle = null;
		try{
			// Obtengo la información del Aula Virtual
			EvaAulaVirtualProjection aulaVirtualProjection = aulaVirtualRepository.findAulaById(idAula);

			AulaVirtualListDTO aulaVirtual = modelMapper.map(aulaVirtualProjection, AulaVirtualListDTO.class);
			
			// Creo una instancia de RestTemplate
	        RestTemplate restTemplate = new RestTemplate();
			
			// Establece las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	
	        // Crea una entidad de solicitud con las cabeceras
	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			//Construyo la url
			String idPlataforma = aulaVirtual.getIdPlataforma();
			if (ENTORNO.equals("local") || ENTORNO.equals("desa")) {idPlataforma = Constantes.NUMERO_10;}
			String url = servidoresAulasVirtuales.get(idPlataforma) + BASE_TOKEN +
					aulaVirtual.getTokenPlataforma() + BASE_WEBSERVICE + READ_USER_IDNUMBER + MEDIA_TYPE_JSON + BASE_USER_IDS + oid;
			
			// Realiza la llamada
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			
	        String respuesta = response.getBody();
	        if(StringUtils.isNotBlank(respuesta) && (!respuesta.contains(Constantes.EXCEPTION) || !respuesta.contains(Constantes.ERROR_NO_DATA))) {
	        	usuarioMoodle = objectMapper.readValue(respuesta, EvaUsuarioAulaVirtual[].class)[0];
	        }
		} catch (JsonProcessingException e) {
			log.error("Se ha producido un error al obtener el usuario de moodle", e);
			throw new EvaluacionException("Se ha producido un error al obtener el usuario de moodle", e);
		}
		return usuarioMoodle;
	}
	
	private Long generaIdCurso(String idCursoString) {
		Long idCurso = null;
		// Hacemos conversión de idCurso
		if(StringUtils.isNotBlank(idCursoString)) {
			String[] partes = idCursoString.split(Constantes.DOS_PUNTOS);
			if (partes.length == 2) {
				try {
		            // 
		            idCurso = Long.parseLong(partes[1].trim());
		        } catch (NumberFormatException e) {
		            log.info("No se puede convertir a Long");
		        }
	        }
		}
		return idCurso;
		
	}
	
	//Nos vemos en la obligacion de devolver el dto para que la fecha tenga el formato correcto
	public ProgramacionAulaDTO actualizaFechaComprobarAlumnado(Long idProgramacionAula) {
		
		//Obtengo el objeto entero de Programacion de aula con id idProg...
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
		//Cambio el valor del campo fecha a new Date()
		programacionAula.setActualizaMoodle(new Date());
		//Guardo la nueva programación en el repository
		programacionAulaRepository.save(programacionAula);
		
		ProgramacionAulaDTO programacionAulaDto = modelMapper.map(programacionAula, ProgramacionAulaDTO.class);
		
		return programacionAulaDto;
	}

	@Transactional
	public List<ActualizarActividadDTO> actualizarActividadesMoodle(Long idPonderacion, Long idAula, Long idUnidadProgramacion, Long anno, BodyActualizarActividadDTO body, Long idProgramacionAula) throws Exception {
		for (ActividadDTO actividad: body.getActividades()) {
			List<AlumnoProjection> listAlumnosActividad = alumnoRepository.getAlumnosAulaVirtualByIdActividad(actividad.getId());
			List<AlumnoDTO> listAlumnosActividadDto = listAlumnosActividad.stream().map(amp -> modelMapper.map(amp, AlumnoDTO.class)).collect(Collectors.toList());
			actividad.setAlumnos(listAlumnosActividadDto);
		}

		List<EvaActividad> actividades = body.getActividades().stream().map(x -> modelMapper.map(x, EvaActividad.class)).collect(Collectors.toList());
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
		List<ActualizarActividadDTO> actualizarActividades = new ArrayList<>();


		actualizarDatosActividadesMoodle(programacionAula, idAula, actividades, actualizarActividades);
		actualizarCriteriosActividadesMoodle(idAula, body.getActividades(), idUnidadProgramacion, actualizarActividades);
		getCalificacionesActividad(idPonderacion, idAula, body.getAlumnos(), anno, body.getActividades(), actualizarActividades,idProgramacionAula);

		for (EvaActividad actividad : actividades) {
			evaActividadRepository.save(actividad);
		}
		return actualizarActividades;
	}
	
	

	@Transactional
	private void actualizarDatosActividadesMoodle(EvaProgramacionAula programacionAula, Long idAula, List<EvaActividad> actividades, List<ActualizarActividadDTO> actualizarActividades) throws EvaluacionException {
		ProgramacionAulaDTO programacionAulaDto = modelMapper.map(programacionAula, ProgramacionAulaDTO.class);
		List<ActividadDTO> listaActividadesMoodle = getActividadesAula(programacionAulaDto, idAula);

		for (EvaActividad actividad : actividades) {
			ActualizarActividadDTO actualizarActividad = new ActualizarActividadDTO();
			ActividadDTO actividadEncontrada = listaActividadesMoodle.stream()
					.filter(a -> a.getIdActividadMoodle().equals(actividad.getIdActividadMoodle()))
					.findFirst().orElse(null);

			if (actividadEncontrada != null) {
				actualizarActividad.setId(actividad.getId());
				actualizarActividad.setNombre(actividad.getNombre());
				actualizarActividad.setIdActividadMoodle(actividad.getIdActividadMoodle());

				if (actividadEncontrada.getNombre() != null && !actividadEncontrada.getNombre().equals(actividad.getNombre())) {
					actividad.setNombre(actividadEncontrada.getNombre());
					actualizarActividad.setCheckTitulo(true);
				}
				if (actividadEncontrada.getDescripcion() != null && !actividadEncontrada.getDescripcion().equals(actividad.getDescripcion())) {
					//actividad.setDescripcion(actividadEncontrada.getDescripcion());
					actualizarActividad.setCheckDescripcion(true);
				}
				if (actividadEncontrada.getFechaInicio() != null && !actividadEncontrada.getFechaInicio().equals(actividad.getFechaInicio())) {
					//actividad.setFechaInicio(actividadEncontrada.getFechaInicio());
					actualizarActividad.setCheckFechaInicio(true);
				}
				if (actividadEncontrada.getFechaFin() != null && !actividadEncontrada.getFechaFin().equals(actividad.getFechaFin())) {
					//actividad.setFechaFin(actividadEncontrada.getFechaFin());
					actualizarActividad.setCheckFechaFin(true);
				}

				actualizarAlumnosActividadesMoodle(actividadEncontrada, actividad, actualizarActividad);

				actualizarActividades.add(actualizarActividad);
			}
		}
	}

	private void actualizarAlumnosActividadesMoodle(ActividadDTO actividadEncontrada, EvaActividad actividad, ActualizarActividadDTO actualizarActividad) {
		List<EvaRelacionActividadAlumno> relacionAlumnos = relacionActividadAlumnoRepository.findAllByActividadId(actividad.getId());
		List<AlumnoDTO> alumnosIguales = new ArrayList<>();
		List<AlumnoDTO> alumnosAdd = new ArrayList<>(actividadEncontrada.getAlumnos());
		List<EvaRelacionActividadAlumno> alumnosBorrar = new ArrayList<>(relacionAlumnos);

		for (AlumnoDTO alumnoMoodle : actividadEncontrada.getAlumnos()) {
			for (EvaRelacionActividadAlumno alumnoEvaluacion : relacionAlumnos) {
				if (alumnoMoodle.getIdMatricula().equals(alumnoEvaluacion.getMatricula().getId())) {
					alumnosIguales.add(alumnoMoodle);
					alumnosBorrar.remove(alumnoEvaluacion);
					alumnosAdd.remove(alumnoMoodle);
					break;
				}
			}
		}

		if (!alumnosBorrar.isEmpty()) {
			for (EvaRelacionActividadAlumno alumno : alumnosBorrar) {
				EvaRelacionActividadAlumno relacionActividadAlumno = relacionActividadAlumnoRepository.findByActividadIdAndMatriculaId(actividad.getId(), alumno.getMatricula().getId());
				if(relacionActividadAlumno != null) {
					relacionActividadAlumnoRepository.delete(relacionActividadAlumno);
				}
			}
			actualizarActividad.setCheckAlumnos(true);
		}

		if(!alumnosAdd.isEmpty()) {
			for (AlumnoDTO alumno : alumnosAdd) {
				EvaRelacionActividadAlumno relacionActividadAlumno = new EvaRelacionActividadAlumno();
				relacionActividadAlumno.setActividad(actividad);
				EvaMatriculaAlumno matricula = matriculaRepository.findById(alumno.getIdMatricula()).orElse(null);
				if (matricula != null) {
					relacionActividadAlumno.setMatricula(matricula);
					relacionActividadAlumnoRepository.save(relacionActividadAlumno);
				}
			}
			actualizarActividad.setCheckAlumnos(true);
		}
	}

	@Transactional
	public void actualizarCriteriosActividadesMoodle(Long idAulavirtual, List<ActividadDTO> actividades, Long idUnidadProgramacion, List<ActualizarActividadDTO> actualizarActividades) throws EvaluacionException {
		EvaAulaVirtualProjection aulaVirtualProjection = aulaVirtualRepository.findAulaById(idAulavirtual);
		AulaVirtualListDTO aulaVirtual = modelMapper.map(aulaVirtualProjection, AulaVirtualListDTO.class);

		if (aulaVirtual != null) {
			List<ActividadDTO> actividadesCriterios = getCriteriosActividades(aulaVirtual, actividades, idUnidadProgramacion);

			for (ActividadDTO actividadDto : actividades) {
				ActividadDTO actividadEncontrada = actividadesCriterios.stream()
						.filter(a -> a.getIdActividadMoodle().equals(actividadDto.getIdActividadMoodle()))
						.findFirst().orElse(null);

				if (actividadEncontrada != null) {
					List<CriterioEvaluacionDTO> criteriosSoloEnUnidad = actividadEncontrada.getCriteriosEvaluacion().stream().filter(CriterioEvaluacionDTO::isEnUnidadProgramacion).collect(Collectors.toList());
					actividadEncontrada.setCriteriosEvaluacion(criteriosSoloEnUnidad);

					ActualizarActividadDTO actualizarActividad = actualizarActividades.stream().filter(x -> x.getIdActividadMoodle().equals(actividadDto.getIdActividadMoodle())).findFirst().orElse(null);
					List<CriterioEvaluacionDTO> criteriosIguales = new ArrayList<>();
					List<CriterioEvaluacionDTO> criteriosAdd = new ArrayList<>(actividadEncontrada.getCriteriosEvaluacion());
					List<CriterioEvaluacionDTO> criteriosBorrar = new ArrayList<>(actividadDto.getCriteriosEvaluacion());


					for (CriterioEvaluacionDTO criterioMoodle : actividadEncontrada.getCriteriosEvaluacion()) {
						for (CriterioEvaluacionDTO criterioEvaluacion : actividadDto.getCriteriosEvaluacion()) {
							if (criterioMoodle.getId().equals(criterioEvaluacion.getId())) {
								criteriosIguales.add(criterioMoodle);
								criteriosBorrar.remove(criterioEvaluacion);
								criteriosAdd.remove(criterioMoodle);
								break;
							}
						}
					}

					if (!criteriosBorrar.isEmpty()) {
						for (CriterioEvaluacionDTO criterio : criteriosBorrar) {
							EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion = relacionActividadCriterioEvaluacionRepository.findByActividadIdAndCriterioEvaluacionId(actividadDto.getId(), criterio.getId());
							// Eliminamos todas las notas del criterio
							valoracionCriterioActividadAlumnoRepository.deleteAllByRelacionActividadCriterioEvaluacionId(relacionActividadCriterioEvaluacion.getId());
							// Eliminamos el criterio
							relacionActividadCriterioEvaluacionRepository.delete(relacionActividadCriterioEvaluacion);
						}
						actualizarActividad.setCheckCriterios(true);
					}

					if (!criteriosAdd.isEmpty()) {
						for (CriterioEvaluacionDTO criterio : criteriosAdd) {
							EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion = new EvaRelacionActividadCriterioEvaluacion();
							EvaActividad actividad = evaActividadRepository.findById(actividadDto.getId()).orElse(null);
							relacionActividadCriterioEvaluacion.setActividad(actividad);
							EvaCriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findById(criterio.getId()).orElse(null);
							if (criterioEvaluacion != null) {
								relacionActividadCriterioEvaluacion.setCriterioEvaluacion(criterioEvaluacion);

								//se añade el peso 1 por defecto al criterio
								EvaUnidadProgramacion unidadProgramacion = unidadProgramacionRepository.findById(idUnidadProgramacion).orElse(null);
								EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(unidadProgramacion, criterioEvaluacion);
								if (relPonCriEva != null && relPonCriEva.getIdOpeCalCriEva() == 2) {
									relacionActividadCriterioEvaluacion.setPeso(1);
								}
							}
							relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacion);
						}
						actualizarActividad.setCheckCriterios(true);
					}

				}
			}

		} else {
			throw new EvaluacionException("Se ha producido un error al obtener el aula virtual");
		}

	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void getCalificacionesActividad(Long idPonderacion, Long idAula, List<AlumnosPorMateriaDTO> alumnos, Long anno, List<ActividadDTO> actividades, List<ActualizarActividadDTO> actualizarActividades, Long idProgramacionAula) throws Exception {

		List<ActividadDTO> list = null;

		// Obtengo la información del Aula Virtual
		EvaAulaVirtualProjection aulaVirtualProjection = aulaVirtualRepository.findAulaById(idAula);

		AulaVirtualListDTO aulaVirtual = modelMapper.map(aulaVirtualProjection, AulaVirtualListDTO.class);

		// Creo una instancia de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Crea una entidad de solicitud con las cabeceras
		HttpEntity<String> entity = new HttpEntity<>(headers);

		String courseId = aulaVirtualRepository.getCourseIdByIdAula(aulaVirtual.getId());

		for (ActividadDTO actividadDto : actividades) {
			if (actividadDto.getIdActividadMoodle() != null) {

				String idPlataforma = aulaVirtual.getIdPlataforma();
				if (ENTORNO.equals("local") || ENTORNO.equals("desa")) {idPlataforma = Constantes.NUMERO_10;}
				String url = servidoresAulasVirtuales.get(idPlataforma) + BASE_TOKEN
						+ aulaVirtual.getTokenPlataforma() + BASE_WEBSERVICE + READ_GRADE_ITEMS + 2 + MEDIA_TYPE_JSON
						+ COURSE_ID + courseId + CM_ID + actividadDto.getIdActividadMoodle();


				// Realiza la llamada

				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				String respuesta = response.getBody();
				try {
					UserGradesMoodle usergrades = objectMapper.readValue(respuesta, UserGradesMoodle.class);

					//si hay respuesta
					if (StringUtils.isNotBlank(respuesta) && (!respuesta.contains(Constantes.EXCEPTION) || !respuesta.contains(Constantes.ERROR_NO_DATA))) {
						List<Boolean> actualizados = new ArrayList<>();

						//por cada alumno RE
						for (AlumnosPorMateriaDTO alumno : alumnos) {
							if (Arrays.stream(usergrades.getUsergrades()).filter(alumn -> alumn.getUserid().equals(alumno.getIdUsuarioMoodle())).count() > 0) {
								//por cada alumno que este en la respuesta
								Arrays.stream(usergrades.getUsergrades()).filter(alumn -> alumn.getUserid().equals(alumno.getIdUsuarioMoodle())).forEach(alumn -> {

									//cogemos los criterios del alumno
									List<CriterioAlumnoDTO> criteriosAlumno = valoracionCriterioActividadAlumnoService.getCriteriosAlumno(alumno.getIdMatricula(), actividadDto.getIdActividadMoodle(), (long) -1, (long) -1)
											.stream().map(criterio -> modelMapper.map(criterio, CriterioAlumnoDTO.class)).collect(Collectors.toList());

									for (CriterioAlumnoDTO criterio : criteriosAlumno) {

										Long count = alumn.getGradeitems().stream().filter(crit -> crit.getItemname().contains(criterio.getAbreviatura())).count();
										if (count > 0) {

											alumn.getGradeitems().stream().filter(crit -> crit.getItemname().contains(criterio.getAbreviatura())).forEach(cri -> {
												if (cri.getGraderaw() != null) {
													//Cogemos el sistema de calificaciones
													Long idEtapa = aulaVirtualRepository.getEtapaByIdProgAula(idProgramacionAula);

													List<SistemaCalificacionCua> calificaciones = valoracionCriteriosRepository.sistemaCalificacion(idEtapa)
															.stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());

													//calificaciones
													calificaciones.stream().filter(c -> Integer.parseInt(c.getNota().toString()) == cri.getGraderaw()).forEach(c -> {
														try {
															criterio.setIdCalifica(c.getIdCalifica());
															boolean actualizado = califActividadesService.guardarCalificacion(alumno.getIdMatricula(), alumno.getIdMatMatricula(), criterio.getIdCriterio(), criterio, 
																	idPonderacion, idProgramacionAula, anno );
															actualizados.add(actualizado);
														} catch (Exception e) {
															throw new RuntimeException(e);
														}
													});
												} else if (cri.getGraderaw() == null && criterio.getIdCalifica() != null) {
													//Si no existe la nota que viene de moodle y existe la nota del criterio la eliminamos
													try {
														boolean actualizado = false;
														criterio.setIdCalifica(null);
														actualizado = califActividadesService.guardarCalificacion(alumno.getIdMatricula(), alumno.getIdMatMatricula(), criterio.getIdCriterio(), criterio,
																idPonderacion, idProgramacionAula, anno );
														actualizados.add(actualizado);
													} catch (EvaluacionException e) {
														throw new RuntimeException(e);
													}
												}
											});
										}
									}
								});
							}
						}
						if (actualizados.stream().anyMatch(x -> x == true) && actualizarActividades != null && !actualizarActividades.isEmpty()) {
							ActualizarActividadDTO actualizarActividad = actualizarActividades.stream().filter(x -> x.getIdActividadMoodle().equals(actividadDto.getIdActividadMoodle())).findFirst().orElse(null);
							if (actualizarActividad != null) {
								actualizarActividad.setCheckCalificaciones(true);
							}
						}

					}
				} catch (Exception e) {
					throw new Exception(e);
				}
			}
		}
	}
	

	public List<ProgramacionAulaVirtualDTO> listDetalleProgramacionAula(Long idPlataforma, Long idCurso) {
		List<ProgramacionAulaVirtual> programacionAulaVirtualProjection = aulaVirtualRepository.listDetalleProgramacionAula(idPlataforma, idCurso);
		List<ProgramacionAulaVirtualDTO> programacionAulaVirtual = programacionAulaVirtualProjection.stream().map(x -> modelMapper.map(x, ProgramacionAulaVirtualDTO.class)).collect(Collectors.toList());

		for (ProgramacionAulaVirtualDTO programacionAula: programacionAulaVirtual) {
			programacionAula.setUrl(BASE_PATH_FRONT + "evaluacion/detalle_programacion_aula/" + programacionAula.getIdProgAula());
		}

		return programacionAulaVirtual;
	}

	
}
