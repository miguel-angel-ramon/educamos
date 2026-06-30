package es.jccm.edu.evaluacion.application.services.calificacionActividades;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.*;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.UnidadesValoracionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.PonderacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaCalificacionActividadesRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaCalificacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaProgramacionAulaRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaActividadRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaRelacionProgramacionDidacticaPonderacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.EvaNotaGlobalCalculadaAlumnoMateriaTemporalRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.EvaValoracionTemporalCompetenciaEspecificaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.EvaValoracionTemporalCriterioEvaluacionAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.Constantes;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.CompentenciasEspecificasConPorcentajeYPeso;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.CriterioEvaluacionConPorcentajeYPeso;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CalificacionCalculoTemporalMateriaProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.UnidadProgramacionProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.ValCriActAluProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.MateriasValoracion;
import es.jccm.edu.evaluacion.application.domain.evaluacion.SistemaCalificacionCua;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.ConvocatoriaProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.MateriasValoracionProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.CompetenciasEspecificas;
import es.jccm.edu.evaluacion.application.domain.ponderacion.CriterioList;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaEmpleado;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionProgramacionDidacticaPonderacion;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaNotaGlobalCalculadaAlumnoMateriaTemporal;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaCalificacionActividadesService;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaCalificacionService;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaValoracionCriterioActividadAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaActividadService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionActividadAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionActividadCriterioEvaluacionService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvaCalificacionActividadesService implements IEvaCalificacionActividadesService {
	
	@Autowired
	private EvaCalificacionActividadesRepository calificacionActividadesRepository;
	
	@Autowired
	private EvaProgramacionAulaRepository proAulaRepository;
	
	@Autowired
    private EvaRelacionProgramacionDidacticaPonderacionRepository progPondRepository;
	
	@Autowired
    private PonderacionRepository ponderacionRepository;
	
	@Autowired
    private IEvaValoracionCriterioActividadAlumnoService valoracionCriterioActividadAlumnoService;
	
	@Autowired
    private IEvaActividadService actividadService;
	
	@Autowired
    private IEvaRelacionActividadAlumnoService relacionActividadAlumnoService;
	
	@Autowired
    private IEvaRelacionActividadCriterioEvaluacionService relacionActividadCriterioEvaluacionService;
	
	@Autowired
    private IEvaCalificacionService calificacionService;
	
	@Autowired
	private IEvaAlumnoService alumnoService;

	@Autowired
	private EvaRelacionProgramacionAulaActividadRepository relacionProgramacionAulaActividadRepository;
	
	@Autowired
	private EvaValoracionTemporalCriterioEvaluacionAlumnoRepository criterioAlumnoTempRepository;
	
	@Autowired
	private EvaValoracionTemporalCompetenciaEspecificaAlumnoRepository valoracionCompetenciaTempRepository;
	
	@Autowired
	private EvaAulaVirtualRepository aulaVirtualRepository;
	
	@Autowired
	private EvaNotaGlobalCalculadaAlumnoMateriaTemporalRepository notaGlobalCaluladaAlumnoMateriaRepository;
	
	@Autowired 
	private EvaCalificacionRepository califRepository;

	@Autowired
	private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;

	@Autowired
    private ModelMapper modelMapper;

	
	@Override
	public List<ConvocatoriasDto> getConvocatorias(Long idProgramacionAula){
		   List<ConvocatoriaProjection> projections = calificacionActividadesRepository.getConvocatorias(idProgramacionAula);
		   return projections.stream().map(convocatoria -> modelMapper.map(convocatoria, ConvocatoriasDto.class)).collect(Collectors.toList());
	}
	
	
	@Override
	public List<UnidadProgramacionDTO> getUnidadesProgramacionProgAula(Long idConvCentroOmc, Long idProgramacionAula, Long idUnidadProgramacion){
		List<UnidadProgramacionProjection> projections = calificacionActividadesRepository.getUnidadesProgramacion(idConvCentroOmc, idProgramacionAula);
		List<UnidadProgramacionDTO> unidadesProgramacionDTO = projections.stream().map(convocatoria -> modelMapper.map(convocatoria, UnidadProgramacionDTO.class)).collect(Collectors.toList());
		List<UnidadProgramacionDTO> unidadesProgramacionEliminar = new ArrayList<>();
		// Obtenemos las actividades de las unidades, filtradas por la convocatoria. Si una unidad no tiene actividades no la incluimos
		for (UnidadProgramacionDTO unidadProgramacion : unidadesProgramacionDTO) {
			// Si una actividad no tiene criterios asociados (que no debería pasar) no la incluimos
			
			List<ActividadDTO> actividades = actividadService.findActividadesByUnidadProgramacionAndConvocatoriaAndProgramacionAula(unidadProgramacion.getId(), idConvCentroOmc, idProgramacionAula);
			List<ActividadDTO> actividadesAEliminar = actividades.stream().filter(act -> act.getCriteriosEvaluacion().isEmpty()).collect(Collectors.toList());
			actividades.removeAll(actividadesAEliminar);
			if (!actividades.isEmpty()) {
				Long numCriteriosTotal = 0L;
				actividades.sort(Comparator.comparing(ActividadDTO::getOrden));
				unidadProgramacion.setActividades(actividades);
				for (ActividadDTO actividad : actividades) {
					numCriteriosTotal += actividad.getCriteriosEvaluacion().size();
				}
				unidadProgramacion.setNumCriterios(numCriteriosTotal);
			} else {
				unidadesProgramacionEliminar.add(unidadProgramacion);
			}
		}

		unidadesProgramacionDTO.removeAll(unidadesProgramacionEliminar);

		return unidadesProgramacionDTO;
	}

	@Override
	public List<ProgramacionAulaDTO> getProgramacionesAulaByDidactica(Long idOfermatrig, Long idMateriaOmg, Long idCentro, Integer anno, List<Long> idsEmpleadosCompartidas, Boolean director) {
		
		List<Long> ids = director? proAulaRepository.findAllDirector(idMateriaOmg, idOfermatrig, idCentro, anno):
			proAulaRepository.findAllByDidac(idMateriaOmg, idOfermatrig, idCentro, anno, idsEmpleadosCompartidas);
		

		List<EvaProgramacionAula> programacionAulas = (List<EvaProgramacionAula>) proAulaRepository.findAllById(ids);
		
		if(director) {
			programacionAulas.stream().forEach(programacion -> {
				if(director) {
					
					EvaEmpleado empleado = programacion.getRelacionesProgramacionAulaEmpleado().get(0).getEmpleado();
					programacion.setNombreEmpleado(String.format("%s %s %s", empleado.getNombre(), empleado.getApellido1(), empleado.getApellido2()));
					programacion.setNombreDePila(empleado.getNombre());
					programacion.setApellido1(empleado.getApellido1());
					programacion.setApellido2(empleado.getApellido2());
				}
			}
			);
		}

		List<ProgramacionAulaDTO> programacionAulasDto = programacionAulas.stream().map(programacion -> modelMapper.map(programacion, ProgramacionAulaDTO.class)).collect(Collectors.toList());
		
				
		for (ProgramacionAulaDTO programacionAulaDto : programacionAulasDto) {
			EvaProgramacionAula programacionAula = proAulaRepository.findById(programacionAulaDto.getId()).orElse(null);
			if(relacionProgramacionAulaActividadRepository.countByProgramacionAula(programacionAula) == 0) {
				programacionAulaDto.setTieneActividades(false);
			} else {
				programacionAulaDto.setTieneActividades(true);
			}

			Long niveAdap = programacionAulaDto.getProgramacionDidactica().getNiveadap();

			if(niveAdap != null) {

				String nombreAdapt = proAulaRepository.getNombreNivelAdaptacion(niveAdap);

				programacionAulaDto.getProgramacionDidactica().setNombreAdapt(nombreAdapt);
			}




		}
		return programacionAulasDto;
	}

	@Override
	public PonderacionDTO getPonderacionByProgramacionDidactica(Long idProgramacionDidactica) throws EvaluacionException {
		EvaRelacionProgramacionDidacticaPonderacion relacionProgramacionDidacticaPonderacion = progPondRepository.findByProgramacionDidacticaId(idProgramacionDidactica);
		
		if(relacionProgramacionDidacticaPonderacion != null) {
			EvaPonderacion ponderacion = relacionProgramacionDidacticaPonderacion.getPonderacion();
			PonderacionDTO ponderacionDTO = modelMapper.map(ponderacion, PonderacionDTO.class);
			List<CompetenciasEspecificasDTO> competenciasDTO = getCompetencias(ponderacion.getId());
			ponderacionDTO.setCompetencias(competenciasDTO);
			return ponderacionDTO;
		} else {
			log.error("No se ha encontrado ponderación para programación didáctica con id: " + idProgramacionDidactica);
			throw new EvaluacionException("No se ha encontrado ponderación para programación didáctica con id: " + idProgramacionDidactica);
		}
	}
	
	private List<CompetenciasEspecificasDTO> getCompetencias(Long idPonderacion) {
        List<CompetenciasEspecificas> competencias = ponderacionRepository.getCompetenciasEspecificas(idPonderacion).stream().map(competencia -> modelMapper.map(competencia, CompetenciasEspecificas.class)).collect(Collectors.toList());
        List<CompetenciasEspecificasDTO> competenciasDTO = competencias.stream().map(competencia -> modelMapper.map(competencia, CompetenciasEspecificasDTO.class)).collect(Collectors.toList());
        for (CompetenciasEspecificasDTO competencia : competenciasDTO) {
            List<CriterioList> criterios = ponderacionRepository.getCriteriosEvaluacion(idPonderacion, competencia.getIdCompetencia()).stream().map(criterio -> modelMapper.map(criterio, CriterioList.class)).collect(Collectors.toList());
            List<CriterioListDTO> criteriosDTO = criterios.stream().map(criterio -> modelMapper.map(criterio, CriterioListDTO.class)).collect(Collectors.toList());
            competencia.setCriterios(criteriosDTO);
        }

        return competenciasDTO;
    }
	
	public List<AlumnoValoracionActividadDTO> getAlumnosCalificacionesUnidad(Long idProgramacionAula, Long idConvCentroOmc, Long idUnidadProgramacion, Long idMateriaOmg, Long idUnidadCentro) throws EvaluacionException  {
		//Se rescatan los alumnos
		List<AlumnoValoracionActividadDTO> alumnos = alumnoService.getAlumnosProgramacionAulaConvocatoria(idProgramacionAula, idConvCentroOmc, idUnidadProgramacion, idMateriaOmg, idUnidadCentro);
		 try {
			 for (AlumnoValoracionActividadDTO alumno : alumnos) {
				//Foto
				 if(idUnidadProgramacion == -1) {
					 String numEscolar = alumnoService.getNumEscolar(alumno.getIdAlumno());
					 alumno.setNumEscolar(numEscolar);
				 }
				 Blob img = calificacionActividadesRepository.getAlumnoFoto(alumno.getNumEscolar());
				 setFotoAlumno(img, alumno);
				 
				//Se rescatan los criterios
				 alumno.setCriterios(valoracionCriterioActividadAlumnoService.getCriteriosAlumno(alumno.getIdMatricula(), idConvCentroOmc, idUnidadProgramacion)
						 .stream().map(criterio -> modelMapper.map(criterio, CriterioAlumnoDTO.class)).collect(Collectors.toList()));
			 }
		 }catch (Exception e){
			 log.error("Se ha producido un error al obtener las calificaciones de los alumnos");
				throw new EvaluacionException("Se ha producido un error al obtener las calificaciones de los alumnos", e);
		 }
		
		return alumnos;
	}
	
	public List<AlumnoValoracionActividadDTO> getAlumnosCalificacionesActividad(Long idActividad, Long idMateriaOmg, Long idUnidadCentro) throws EvaluacionException {
		//Se rescatan los alumnos
		List<AlumnoValoracionActividadDTO> alumnos = alumnoService.getAlumnosActividad(idActividad, idMateriaOmg, idUnidadCentro);
		try {
			for (AlumnoValoracionActividadDTO alumno : alumnos) {
				//Foto
				Blob img = calificacionActividadesRepository.getAlumnoFoto(alumno.getNumEscolar());
				setFotoAlumno(img, alumno);
				
				//Se rescatan los criterios
				alumno.setCriterios(valoracionCriterioActividadAlumnoService.getCriteriosAlumnoActividad(alumno.getIdMatricula(), idActividad)
						.stream().map(criterio -> modelMapper.map(criterio, CriterioAlumnoDTO.class)).collect(Collectors.toList()));
				for (CriterioAlumnoDTO critAlum : alumno.getCriterios()) {
					EvaRelacionActividadAlumno relActAlu = relacionActividadAlumnoService.findByActividadIdAndMatriculaId(idActividad, alumno.getIdMatricula());
					EvaRelacionActividadCriterioEvaluacion relActCriEva = relacionActividadCriterioEvaluacionService.findByActividadIdAndCriterioEvaluacionId(idActividad, critAlum.getIdCriterio());
					
					if (relActAlu != null && relActCriEva != null) {
						EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno = valoracionCriterioActividadAlumnoService.findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(relActCriEva.getId(), relActAlu.getId());
						if (valoracionCriterioActividadAlumno != null) {
							critAlum.setValoracion(modelMapper.map(valoracionCriterioActividadAlumno, ValoracionCriterioActividadAlumnoDTO.class));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Se ha producido un error al obtener las calificaciones de los alumnos");
			throw new EvaluacionException("Se ha producido un error al obtener las calificaciones de los alumnos", e);
		}
		
		return alumnos;
	}

	public List<AlumnoValoracionActividadDTO> getAlumnosActividadReport(Long idActividad, Long idMateriaOmg, Long idUnidadCentro) {
		List<AlumnoValoracionActividadDTO> alumnos = alumnoService.getAlumnosActividad(idActividad, idMateriaOmg, idUnidadCentro);

		return alumnos;
	}
	
	@Transactional
	@Override
	public void guardarCalificacionCriteriosActividadAlumno(AlumnoValoracionActividadDTO alumno, Long idCriterio, Long idActividad, Long idPonderacion, Long idProgramacionAula, Long anno) throws EvaluacionException {
		if(alumno != null 
				&& alumno.getCriterios() != null 
				&& !alumno.getCriterios().isEmpty()) {
			Optional<CriterioAlumnoDTO> optCriterioAlumno = alumno.getCriterios().stream().filter(s -> idCriterio.equals(s.getIdCriterio()) && idActividad.equals(s.getIdActividad())).findFirst();
			if(optCriterioAlumno.isPresent()) {
				guardarCalificacion(alumno.getIdMatricula(), alumno.getIdMatMatriAlu(), idCriterio, optCriterioAlumno.get(), idPonderacion, idProgramacionAula, anno);
			}
		}
	}

	public boolean guardarCalificacion(Long  idMatricula, Long idMatMatriAlu, Long idCriterio, CriterioAlumnoDTO criterioAlumno, Long idPonderacion, Long idProgramacionAula, Long anno) throws EvaluacionException {
		CriterioAlumnoDTO criterio = criterioAlumno;
		EvaRelacionActividadAlumno relActAlu = relacionActividadAlumnoService.findByActividadIdAndMatriculaId(criterio.getIdActividad(), idMatricula);
		EvaRelacionActividadCriterioEvaluacion relActCriEva = relacionActividadCriterioEvaluacionService.findByActividadIdAndCriterioEvaluacionId(criterio.getIdActividad(), idCriterio);
		
		
		if (relActAlu != null && relActCriEva != null) {
			EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno = valoracionCriterioActividadAlumnoService.findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(relActCriEva.getId(), relActAlu.getId());
			if (valoracionCriterioActividadAlumno != null) {
				boolean actualizado = editarCalificacionAlumno(valoracionCriterioActividadAlumno, criterio.getIdCalifica());
				calculoNotaCriterioParaAlumno(criterio, idMatricula, criterioAlumno.getIdActividad(), idPonderacion, idMatMatriAlu, idProgramacionAula, anno, valoracionCriterioActividadAlumno.getCalificacion().getSistema());
				return actualizado;
			} else {
				if (criterio.getIdCalifica() != null) {
					EvaCalificacion calificacion = calificacionService.getCalificacionById(criterio.getIdCalifica());
			    	
			    		valoracionCriterioActividadAlumno = new EvaValoracionCriterioActividadAlumno();
						valoracionCriterioActividadAlumno.setRelacionActividadAlumno(relActAlu);
						valoracionCriterioActividadAlumno.setRelacionActividadCriterioEvaluacion(relActCriEva);
						valoracionCriterioActividadAlumno.setCalificacion(calificacion);
						
						valoracionCriterioActividadAlumnoService.guardar(valoracionCriterioActividadAlumno);
						
						calculoNotaCriterioParaAlumno(criterio, idMatricula, criterioAlumno.getIdActividad(), idPonderacion, idMatMatriAlu, idProgramacionAula, anno, calificacion.getSistema());

						return true;
			    	}
	
			}
		} else {
			log.error("No se ha podido guardar la calificación. No se encuentran los datos requeridos.");
			throw new EvaluacionException("No se ha podido guardar la calificación. No se encuentran los datos requeridos.");
		}

		return false;
	}
	
	private boolean editarCalificacionAlumno(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno, Long idCalifica) {
    	if(idCalifica != null) {
    		EvaCalificacion calificacion = calificacionService.getCalificacionById(idCalifica);
        	if(calificacion != null && !calificacion.equals(valoracionCriterioActividadAlumno.getCalificacion())) {
        		valoracionCriterioActividadAlumno.setCalificacion(calificacion);
        		valoracionCriterioActividadAlumnoService.guardar(valoracionCriterioActividadAlumno);
				return true;
        	} else {
				return false;
			}
    	} else {
    		eliminarCalificacionAlumno(valoracionCriterioActividadAlumno);
			return true;
    	}
    }

	@Transactional
    public void bloquearPonderacion(Long idPonderacion) throws EvaluacionException {
		Optional<EvaPonderacion> poderacion = ponderacionRepository.findById(idPonderacion);
		if(poderacion.isPresent()) {
			EvaPonderacion pond = poderacion.get();
			pond.setEditable(Constantes.LETRA_N);
			ponderacionRepository.save(pond);
		} else {
			log.error("No se ha encontrado ponderación con id: " + idPonderacion);
			throw new EvaluacionException("No se ha encontrado ponderación con id: " + idPonderacion);	
		}
    }
	
    private void eliminarCalificacionAlumno(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno) {
    	valoracionCriterioActividadAlumnoService.eliminar(valoracionCriterioActividadAlumno);
    }
    
    public List<CriterioEvaluacionDTO> getCriteriosByActividad(Long idActividad) throws EvaluacionException {
    	EvaActividad actividad = actividadService.getActividadById(idActividad);
    	
    	if (actividad != null) {
    		if(actividad.getRelacionesActividadCriterios() != null && !actividad.getRelacionesActividadCriterios().isEmpty()) {
    			List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
				for(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion : actividad.getRelacionesActividadCriterios()) {
					CriterioEvaluacionDTO criterio = modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(),CriterioEvaluacionDTO.class);
					criterio.setPeso(relacionActividadCriterioEvaluacion.getPeso());
					criteriosActividad.add(criterio);
				}
				criteriosActividad.sort(Comparator.comparing(CriterioEvaluacionDTO::getOrden));
				return criteriosActividad;
    		} else {
    			log.error("No se han encontrado criterios para la actividad con id: " + idActividad);
    			throw new EvaluacionException("No se han encontrado criterios para la actividad con id: " + idActividad);
    		}
    	} else {
    		log.error("No se ha encontrado actividad con id: " + idActividad);
			throw new EvaluacionException("No se ha encontrado actividad con id: " + idActividad);
    	}
    }
    
    private AlumnoValoracionActividadDTO setFotoAlumno(Blob img, AlumnoValoracionActividadDTO alumn) {
        try {
            if (img != null) {
                alumn.setFoto(img.getBytes(1, (int) img.length()));
            }
        } catch (SQLException e) {
        	log.error("Se ha producido un error al obtener la foto del alumno con id: " + alumn.getIdAlumno());
        }
        return alumn;
    }

	public List<MateriasValoracion> getMateriasProgAula(Long idEmpleado, Long anno, Long codigoCentro, Long idOfertaMatrig) {

		List<MateriasValoracionProjection> materiasValoracionProjection = null;

		if(idOfertaMatrig == -1){ //Este valor quiere decir que el usuario es profesor, obtiene sus materias
			materiasValoracionProjection = calificacionActividadesRepository.getMateriasProgAula(idEmpleado, anno, codigoCentro);
		} else { //Si el usuario es director, obtiene todas las materias dado el idOfertaMatrig para poder cambiar calificaciones
			materiasValoracionProjection = calificacionActividadesRepository.getMateriasProgAula_Dir(idOfertaMatrig, anno, codigoCentro);
		}



		return materiasValoracionProjection.stream().map(materia -> modelMapper.map(materia, MateriasValoracion.class)).collect(Collectors.toList());
	}
	
	private void calculoNotaCriterioParaAlumno(CriterioAlumnoDTO Criterio, Long idMatricula, Long idActividad, Long idPonderacion, Long idMatMatriAlu, Long idProgramacionAula, Long anno, Long idSistemaCalifica) {
		
		try {
			
			EvaValoracionTemporalCriterioEvaluacionAlumno criterioAlumnoTemp = criterioAlumnoTempRepository.findByIdPonderacionAndCriEvaAndMatMatricula(idPonderacion, Criterio.getIdCriterio(), idMatMatriAlu);
			
			if(criterioAlumnoTemp == null) {
				criterioAlumnoTemp = new EvaValoracionTemporalCriterioEvaluacionAlumno();
			}
			
			List<ValCriActAluProjection> criterios = calificacionActividadesRepository.getNotasPorCriterio(Criterio.getIdCriterio(), idMatricula);
			
			
			if(!criterios.isEmpty() || Criterio.getIdCalifica() != null) {

				List<SistemaCalificacionCua> calificaciones = aulaVirtualRepository.getSistemaCalifActividadCalculoCriterio(idSistemaCalifica)
						.stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());
				
				List<Integer> notas = criterios.stream().map(x -> x.getNumero()).collect(Collectors.toList());
				
				int tipo = calificacionActividadesRepository.getTipoCalculoCriterio(idProgramacionAula, Criterio.getIdCriterio());
				Long calculo, calculoCopia = null;

				
				switch (tipo) {
				//Media Aritmetica
				case 1:
					
					Double total = (double) notas.stream().mapToInt(Integer::intValue).sum() / notas.stream().count();
					
					calculoCopia = (long) Math.round(total);
					
					break;
				//Media Ponderada: Sumamos todas las notas multiplicados por sus pesos y dividimos entre 100
		        case 2:
		        	int sumaPeso = 0;

					sumaPeso = criterios.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

		        	for (int i=0;i< criterios.size();i++) {
		        		int n = notas.get(i);
		        		int peso = criterios.get(i).getPeso() != null && criterios.get(i).getPeso() != 0 ? criterios.get(i).getPeso() : 1;
						DecimalFormat df = new DecimalFormat("#.##");
						float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
		        		notas.set((i), (int) (n * porcentaje));
		        	}
		        	
		        	Double division = (double) notas.stream().mapToInt(Integer::intValue).sum() / 100;
		        	
		        	calculoCopia = (long) Math.round(division);
		        	break;
		        //Moda: devolvemos el valor que más se repita (en caso de ser todos iguales, devolvemos el más alto)
		        case 3:
		        	calculoCopia = getCalculoModa(notas);          
		        	break;
		        //Valor más Alto: devolvemos el valor más alto
		        case 4:
		        	calculoCopia = (long) Collections.max(notas);
		        	break;
		        //Último Valor: comprobamos la fecha de actualización y devolvemos el que tenga la fecha más reciente 
		        case 5:
		        	calculoCopia = criterioAlumnoTempRepository.getNotaUltimoValor(idMatricula, idPonderacion, Criterio.getIdCriterio());
		            break;
				}
				
				calculo = calculoCopia;
				//comparar con el id de 
				for(SistemaCalificacionCua cali: calificaciones.stream().filter(c -> c.getNota().equals(calculo)).collect(Collectors.toList())) {
					criterioAlumnoTemp.setCalificacion(califRepository.findById(cali.getIdCalifica()).orElse(null));
				}
				
				criterioAlumnoTemp.setIdPonderacion(idPonderacion);
				criterioAlumnoTemp.setMatMatricula(idMatMatriAlu);
				criterioAlumnoTemp.setCriEva(Criterio.getIdCriterio());
				
				criterioAlumnoTempRepository.save(criterioAlumnoTemp);

				this.calcularYAlmacenarNotaCompetenciaEspecifica(criterioAlumnoTemp, calificaciones);

				if (idSistemaCalifica != 747) {
					this.calcularYAlmacenarNotaMateria(criterioAlumnoTemp, calificaciones);
				}

				}else{
					if(criterioAlumnoTemp.getId() != null) {
						criterioAlumnoTempRepository.deleteById(criterioAlumnoTemp.getId());
						calcularYAlmacenarNotaCompetenciaEspecifica(criterioAlumnoTemp, null);
						calcularYAlmacenarNotaMateria(criterioAlumnoTemp, null);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw e;
			}
	}

	public void calculoNotaTodosLosCriteriosParaAlumno(Long idMatricula, Long idPonderacion, Long idMatMatriAlu, Long idProgramacionAula, Long anno, Long idSistemaCalifica) {

		try {

			List<Long> listadoIdsCriteriosAlumno = criterioAlumnoTempRepository.getAllCriteriosAlumnoByIdProgramacionAula(idProgramacionAula, idMatricula);

			for(Long idCriterio: listadoIdsCriteriosAlumno) {

				EvaValoracionTemporalCriterioEvaluacionAlumno criterioAlumnoTemp = criterioAlumnoTempRepository.findByIdPonderacionAndCriEvaAndMatMatricula(idPonderacion, idCriterio, idMatMatriAlu);

				if(criterioAlumnoTemp == null) {
					criterioAlumnoTemp = new EvaValoracionTemporalCriterioEvaluacionAlumno();
				}

				List<ValCriActAluProjection> criterios = calificacionActividadesRepository.getNotasPorCriterio(idCriterio, idMatricula);


				if(!criterios.isEmpty()) {

					List<SistemaCalificacionCua> calificaciones = aulaVirtualRepository.getSistemaCalifActividadCalculoCriterio(idSistemaCalifica)
							.stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());

					List<Integer> notas = criterios.stream().map(x -> x.getNumero()).collect(Collectors.toList());

					int tipo = calificacionActividadesRepository.getTipoCalculoCriterio(idProgramacionAula, idCriterio);
					Long calculo, calculoCopia = null;


					switch (tipo) {
						//Media Aritmetica
						case 1:

							Double total = (double) notas.stream().mapToInt(Integer::intValue).sum() / notas.stream().count();

							calculoCopia = (long) Math.round(total);

							break;
						//Media Ponderada: Sumamos todas las notas multiplicados por sus pesos y dividimos entre lo que me diga Jose
						case 2:
							int sumaPeso = 0;

							sumaPeso = criterios.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

							for (int i=0;i< criterios.size();i++) {
								int n = notas.get(i);
								int peso = criterios.get(i).getPeso() != null && criterios.get(i).getPeso() != 0 ? criterios.get(i).getPeso() : 1;
								DecimalFormat df = new DecimalFormat("#.##");
								float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
								notas.set((i), (int) (n * porcentaje));
							}

							Double division = (double) notas.stream().mapToInt(Integer::intValue).sum() / 100;

							calculoCopia = (long) Math.round(division);
							break;
						//Moda: devolvemos el valor que más se repita (en caso de ser todos iguales, devolvemos el más alto)
						case 3:
							calculoCopia = getCalculoModa(notas);
							break;
						//Valor más Alto: devolvemos el valor más alto
						case 4:
							calculoCopia = (long) Collections.max(notas);
							break;
						//Último Valor: comprobamos la fecha de actualización y devolvemos el que tenga la fecha más reciente
						case 5:
							calculoCopia = criterioAlumnoTempRepository.getNotaUltimoValor(idMatricula, idPonderacion, idCriterio);
							break;
					}

					calculo = calculoCopia;
					//comparar con el id de
					for(SistemaCalificacionCua cali: calificaciones.stream().filter(c -> c.getNota().equals(calculo)).collect(Collectors.toList())) {
						criterioAlumnoTemp.setCalificacion(califRepository.findById(cali.getIdCalifica()).orElse(null));
					}

					criterioAlumnoTemp.setIdPonderacion(idPonderacion);
					criterioAlumnoTemp.setMatMatricula(idMatMatriAlu);
					criterioAlumnoTemp.setCriEva(idCriterio);

					criterioAlumnoTempRepository.save(criterioAlumnoTemp);

					this.calcularYAlmacenarNotaCompetenciaEspecifica(criterioAlumnoTemp, calificaciones);

					if (idSistemaCalifica != 747) {
						this.calcularYAlmacenarNotaMateria(criterioAlumnoTemp, calificaciones);
					}

				}else{
					if(criterioAlumnoTemp.getId() != null) {
						criterioAlumnoTempRepository.deleteById(criterioAlumnoTemp.getId());
						calcularYAlmacenarNotaCompetenciaEspecifica(criterioAlumnoTemp, null);
						calcularYAlmacenarNotaMateria(criterioAlumnoTemp, null);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
	}

	private void calcularYAlmacenarNotaCompetenciaEspecifica(EvaValoracionTemporalCriterioEvaluacionAlumno criterioAlumnoTemp, List<SistemaCalificacionCua> calificaciones) {

		//Busco el criterio de evaluación
		Optional<EvaCriterioEvaluacion> criterioEvaluacion = criterioEvaluacionRepository.findById(criterioAlumnoTemp.getCriEva());

		//Intento rescatar si ya existe la competencia especifica temporal
		EvaValoracionTemporalCompetenciaEspecificaAlumno competenciaEspecificaTemporal = valoracionCompetenciaTempRepository
				.findByIdPonderacionAndComEspAndMatMatricula(
						criterioAlumnoTemp.getIdPonderacion(),
						criterioEvaluacion.get().getCompetenciaEspecifica().getId(),
						criterioAlumnoTemp.getMatMatricula());

		//Si no existe la creo y completo los datos de la competencia especifica temporal si no existen
		if (competenciaEspecificaTemporal == null) {
			competenciaEspecificaTemporal = new EvaValoracionTemporalCompetenciaEspecificaAlumno();

			competenciaEspecificaTemporal.setIdPonderacion(criterioAlumnoTemp.getIdPonderacion());
			competenciaEspecificaTemporal.setMatMatricula(criterioAlumnoTemp.getMatMatricula());
			competenciaEspecificaTemporal.setComEsp(criterioEvaluacion.get().getCompetenciaEspecifica().getId());
		}

		//Rescato el listado de criterios temporales evaluados del alumno para la competencia específica y ponderación
		List<CriterioEvaluacionConPorcentajeYPeso> listadoCriteriosEvaluacion = valoracionCompetenciaTempRepository
				.findAllNotasCriterioByIdCompetenciaEspecificaAndIdPonderacionAndIdMatMatricula(
						criterioEvaluacion.get().getCompetenciaEspecifica().getId(),
						criterioAlumnoTemp.getIdPonderacion(),
						criterioAlumnoTemp.getMatMatricula())
				.stream().map(x -> modelMapper.map(x, CriterioEvaluacionConPorcentajeYPeso.class)).collect(Collectors.toList());

		if (calificaciones != null && !listadoCriteriosEvaluacion.isEmpty()) {
			//Por cada criterio que haya rescatado le calculo y aplico los porcentajes a la nota del cálculo del criterio temporal
			List<Integer> notas = listadoCriteriosEvaluacion.stream().map(x -> x.getNumero()).collect(Collectors.toList());
			int sumaPeso = listadoCriteriosEvaluacion.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

			for(int i=0;i< listadoCriteriosEvaluacion.size();i++){
				int n = notas.get(i);
				int peso = listadoCriteriosEvaluacion.get(i).getPeso() != null && listadoCriteriosEvaluacion.get(i).getPeso() != 0 ? listadoCriteriosEvaluacion.get(i).getPeso() : 1;
				DecimalFormat df = new DecimalFormat("#.##");
				float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
				notas.set((i), Math.round((n * porcentaje)));
			}

			//Calculo la nota total de la competencia específica
			float calculo = (float) notas.stream().mapToInt(Integer::intValue).sum() / 100;

			//Busco la calificación en el sistema de calificación que corresponda al resultado del cálculo y se lo seteo a la entidad
			float calculoCopia = calculo;

			List<SistemaCalificacionCua> calificacion = calificaciones.stream()
					.filter(c -> Math.abs(c.getNota() - Math.round(calculoCopia)) < 0.0001)
					.collect(Collectors.toList());

			if (!calificacion.isEmpty()) {
				competenciaEspecificaTemporal.setCalificacion(califRepository.findById(calificacion.get(0).getIdCalifica()).orElse(null));
			}

			//Guardo el resultado de la competencia especifica temporal
			valoracionCompetenciaTempRepository.save(competenciaEspecificaTemporal);
		}else {
			//Si se elimina
			if(competenciaEspecificaTemporal != null) {
				valoracionCompetenciaTempRepository.deleteById(competenciaEspecificaTemporal.getIdCompetencia());
			}
		}

		
	}

	private void calcularYAlmacenarNotaMateria(EvaValoracionTemporalCriterioEvaluacionAlumno criterioAlumnoTemp, List<SistemaCalificacionCua> calificaciones) {

		//Intento rescatar si ya existe la materia temporal
		EvaNotaGlobalCalculadaAlumnoMateriaTemporal materiaTemporal = notaGlobalCaluladaAlumnoMateriaRepository
				.findByMatMatricula(criterioAlumnoTemp.getMatMatricula());


		//Si no existe la creo y completo los datos de la competencia especifica temporal si no existen
		if (materiaTemporal == null) {
			materiaTemporal = new EvaNotaGlobalCalculadaAlumnoMateriaTemporal();
			materiaTemporal.setMatMatricula(criterioAlumnoTemp.getMatMatricula());
		}

		//Rescato el listado de competencias específicas temporales evaluados por ponderación y matMatricula
		List<CompentenciasEspecificasConPorcentajeYPeso> listadoCompentenciasEspecificas = notaGlobalCaluladaAlumnoMateriaRepository
				.findAllNotasCompentenciasEspecificasIdPonderacionAndIdMatMatricula(
						criterioAlumnoTemp.getIdPonderacion(),
						criterioAlumnoTemp.getMatMatricula())
				.stream().map(x -> modelMapper.map(x, CompentenciasEspecificasConPorcentajeYPeso.class)).collect(Collectors.toList());

		//Si no se elimina la nota de la materia
		if (calificaciones != null && !listadoCompentenciasEspecificas.isEmpty()) {
			//Por cada criterio que haya rescatado le calculo y aplico los porcentajes a la nota del cálculo del criterio temporal
			List<Integer> notas = listadoCompentenciasEspecificas.stream().map(x -> x.getNumero()).collect(Collectors.toList());
			int sumaPeso = listadoCompentenciasEspecificas.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

			for(int i=0;i< listadoCompentenciasEspecificas.size();i++){
				int n = notas.get(i);
				int peso = listadoCompentenciasEspecificas.get(i).getPeso() != null && listadoCompentenciasEspecificas.get(i).getPeso() != 0 ? listadoCompentenciasEspecificas.get(i).getPeso() : 1;
				DecimalFormat df = new DecimalFormat("#.##");
				float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
				notas.set(i, Math.round(n * porcentaje));
			}

			//Calculo la nota total de la competencia específica
			float calculo = notas.stream().mapToInt(Integer::intValue).sum() / 100F;

			//Busco la calificación en el sistema de calificación que corresponda al resultado del cálculo y se lo seteo a la entidad

			//Compruebo si el sistema de calificación pertenece a primaria o secundaria para aplicar los cálculos pertinentes en función a la tabla TLRELNOTSIST
			if(calificaciones.get(0).getIdSistCal() == 749 || calificaciones.get(0).getIdSistCal() == 743) {

				//Rescato el sistema de calificación especial
				List<CalificacionCalculoTemporalMateriaProjection> listadoCalificacionesCalculoMateria =  notaGlobalCaluladaAlumnoMateriaRepository
						.getSistemaCalificacion(calificaciones.get(0).getIdSistCal());

				//Busco entre que valor mínimo y máximo está mi cálculo y rescato el idCalifica que corresponde
				calculo = (float) (1.5 + (calculo - 1) * 2);
				for (CalificacionCalculoTemporalMateriaProjection calificacion : listadoCalificacionesCalculoMateria) {
					if (calculo >= calificacion.getValorMinimo() && calculo <= calificacion.getValorMaximo()) {
						materiaTemporal.setCalificacion(califRepository.findById(calificacion.getIdCalifica()).orElse(null));
					}
				}
			} else {

				List<SistemaCalificacionCua> listadoCalificacionesCalculoMateria = califRepository.getSistemaCalificacionNotaMateriaByIdMatMatricula(criterioAlumnoTemp.getMatMatricula())
						.stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());

				float calculoCopia = calculo;
				List<SistemaCalificacionCua> calificacion = listadoCalificacionesCalculoMateria.stream()
						.filter(c -> Math.abs(c.getNota() - Math.round(calculoCopia)) < 0.0001)
						.collect(Collectors.toList());
				//Si es un sistema de calificación de infantil o bachillerato busco la calificación de manera normal en el sistema de calificaciones
				if (!calificacion.isEmpty()) {
					materiaTemporal.setCalificacion(califRepository.findById(calificacion.get(0).getIdCalifica()).orElse(null));
				}
			}
			

			materiaTemporal.setNota((double) calculo);

			//Guardo el resultado de la competencia especifica temporal
			notaGlobalCaluladaAlumnoMateriaRepository.save(materiaTemporal);
		}else {
			if(materiaTemporal.getId() != null) {
				notaGlobalCaluladaAlumnoMateriaRepository.deleteById(materiaTemporal.getId());
			}
		}
		
	}

	private Long getCalculoModa(List<Integer> notas) {

		Long calculo = null;
		int number = 0;
		int repeticiones = 0;
		
		//si hay repetidos
		Set<Integer> repetidos = notas.stream().distinct().filter(i -> Collections.frequency(notas, i) > 1).collect(Collectors.toSet());
	
		if(repetidos.stream().count() > 0) {
			if(repetidos.stream().count() == 1) {
				
				calculo = (long) repetidos.stream().findFirst().orElse(null);
			} else {
				
				for(int repeditdo: repetidos) {
					if(Collections.frequency(notas, repeditdo) > repeticiones || (Collections.frequency(notas, repeditdo) == repeticiones && repeditdo > number)) {
						repeticiones = Collections.frequency(notas, repeditdo); 
						number = repeditdo;
					}
				}
			
				calculo = (long) number;
			}
			
		} else {
			
			calculo = (long) Collections.max(notas);
		}
		
		return calculo;
	}


	public List<UnidadesValoracionDto> getUnidadesCentro(Long idProgramacionAula, Long idActividad, Long idUnidadProg) {
		return calificacionActividadesRepository.getUnidadesCentro(idProgramacionAula, idActividad, idUnidadProg).stream().map(unidad -> modelMapper.map(unidad, UnidadesValoracionDto.class)).collect(Collectors.toList());
	}
	

}
