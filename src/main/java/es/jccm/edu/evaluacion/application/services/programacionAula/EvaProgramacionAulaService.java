package es.jccm.edu.evaluacion.application.services.programacionAula;


import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualListDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.SaberBasicoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaValoracionCriterioActividadAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.EvaValoracionTemporalCriterioEvaluacionAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.Constantes;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.EvaAulaVirtualProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.CriterioAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.AlumnosPorMateria;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.*;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.*;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.AlumnosActividadCalcularAllCriteriosProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.DatosCalcularAllCriteriosProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaMatriculaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaProgramacionAulaService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import es.jccm.edu.evaluacion.application.services.aulaVirtual.EvaAulaVirtualService;
import es.jccm.edu.evaluacion.application.services.calificacionActividades.EvaCalificacionActividadesService;
import es.jccm.edu.evaluacion.application.services.calificacionActividades.EvaValoracionCriterioActividadAlumnoService;
import es.jccm.edu.evaluacion.application.services.programacionDidactica.EvaMateriaProgramacionDidacticaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualListDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnosPorMateriaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.CriterioActividadPonderacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.CriterioPesoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.CursoProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.MateriaProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.NivelCurricularProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.UnidadPorMateriaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaActividadRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaInstrumentoEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaMatriculaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaProgramacionAulaRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaActividadRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaEmpleadoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaUnidadRepository;
import es.jccm.edu.evaluacion.application.domain.Constantes;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.EvaAulaVirtualProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.AlumnosPorMateria;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaInstrumentoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaMatriculaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaEmpleado;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaUnidad;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaMatriculaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaProgramacionAulaService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import es.jccm.edu.evaluacion.application.services.aulaVirtual.EvaAulaVirtualService;
import es.jccm.edu.evaluacion.application.services.calificacionActividades.EvaCalificacionActividadesService;
import es.jccm.edu.evaluacion.application.services.calificacionActividades.EvaValoracionCriterioActividadAlumnoService;
import es.jccm.edu.evaluacion.application.services.programacionDidactica.EvaMateriaProgramacionDidacticaService;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvaProgramacionAulaService implements IEvaProgramacionAulaService {
	
	@Autowired
	private EvaProgramacionAulaRepository programacionAulaRepository;
	
	@Autowired
	private EvaEmpleadoRepository empleadoAulaRepository;
	
	@Autowired
	private EvaRelacionProgramacionAulaEmpleadoRepository relacionProgramacionAulaEmpleadoRepository;
	
	@Autowired
	private EvaRelacionProgramacionAulaUnidadRepository relacionProgramacionAulaUnidadRepository;
	
	@Autowired
	private EvaRelacionProgramacionAulaAlumnoRepository relacionProgramacionAulaAlumnoRepository;

	@Autowired
	private EvaActividadRepository evaActividadRepository;
	
	@Autowired
    private EvaProgramacionDidacticaRepository programacionDidacticaRepository;

	@Autowired
	EvaUnidadProgramacionRepository unidadProgramacionRepository;

	@Autowired
	EvaInstrumentoEvaluacionRepository instrumentoEvaluacionRepository;

	@Autowired
	EvaConvocatoriaCentrosOMCRepository convocatoriaCentrosOMCRepository;

	@Autowired
	EvaRelacionActividadCriterioEvaluacionRepository relacionActividadCriterioEvaluacionRepository;

	@Autowired
	EvaValoracionCriterioActividadAlumnoRepository valoracionCriterioActividadAlumnoRepository;

	@Autowired
	EvaRelacionActividadAlumnoRepository relacionActividadAlumnoRepository;
	
	@Autowired
	EvaRelacionProgramacionAulaActividadRepository relacionProgramacionAulaActividadRepository;
	
	@Autowired
    private EvaAlumnoRepository alumnoRepository;
	
	@Autowired
    private EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository relacionUnidadProgramacionCriteriosEvaluacionRepository;
	
	@Autowired
    private EvaRelacionProgramacionDidacticaUnidadProgramacionRepository relacionProgramacionDidacticaUnidadProgramacionRepository;
	
	@Autowired
	private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private EvaMatriculaAlumnoRepository matriculaRepository;
	
	@Autowired
	private EvaRelacionPonderacionCriteriosEvaluacionRepository relacionPonderacionCriteriosEvaluacionRepository;
	
	@Autowired
	private EvaCompetenciaEspecificaDidacticaRepository competenciaEspecificaDidacticaRepository;

	@Autowired
	private EvaCalificacionActividadesService calificacionActividadesService;

	@Autowired
	private EvaMateriaProgramacionDidacticaService materiaProgramacionDidacticaService;

	@Autowired
	private EvaValoracionCriterioActividadAlumnoService valoracionCriterioActividadAlumnoService;
	
	@Autowired
	private IEvaAlumnoService alumnoService;
	
	@Autowired
	private IEvaMatriculaAlumnoService matriculaAlumnoService;

	@Autowired
	private EvaActividadService actividadService;
	
	@Autowired
	private EvaAulaVirtualRepository aulaVirtualRepository;

	@Autowired
	private EvaAulaVirtualService aulaVirtualService;

	@Autowired
	private EvaValoracionTemporalCriterioEvaluacionAlumnoRepository criterioAlumnoTempRepository;
	
	@Autowired
	private EvaRelacionUnidadProgramacionSaberBasicoRepository relacionUnidadProgramacionSaberBasicoRepository;

	@Transactional
	public void updateActividad(ActividadDTO actividadDto) throws EvaluacionException {
		EvaActividad actividad = modelMapper.map(actividadDto, EvaActividad.class);

		if(actividad != null) {
			evaActividadRepository.save(actividad);
			
			List<CriterioPesoDTO> criteriosPesos = actividadDto.getCriteriosPesos();
			if(criteriosPesos != null && !criteriosPesos.isEmpty()) {
				List<Long> idsCriterios = criteriosPesos.stream().map(CriterioPesoDTO :: getIdCriterio).collect(Collectors.toList());
				List<EvaRelacionActividadCriterioEvaluacion> relacionesActividadCriterioEvaluacion = relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividad.getId());
				for(EvaRelacionActividadCriterioEvaluacion relActCriEva : relacionesActividadCriterioEvaluacion) {
					int index = idsCriterios.indexOf(relActCriEva.getCriterioEvaluacion().getId());
					if (index == -1) {
						relacionActividadCriterioEvaluacionRepository.delete(relActCriEva);
					} else {
						EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(actividad.getUnidadProgramacion(), relActCriEva.getCriterioEvaluacion());
						if (relPonCriEva != null && relPonCriEva.getIdOpeCalCriEva() == 2) {
							CriterioPesoDTO criterioPesoIn = criteriosPesos.get(index);
							relActCriEva.setLPonderada("1");
							relActCriEva.setPeso(criterioPesoIn.getPeso());
						} else {
							relActCriEva.setLPonderada("0");
						}
						relacionActividadCriterioEvaluacionRepository.save(relActCriEva);
					}
				}
				for(CriterioPesoDTO critPeso : criteriosPesos) {
					EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion = relacionActividadCriterioEvaluacionRepository.findByActividadIdAndCriterioEvaluacionId(actividad.getId(), critPeso.getIdCriterio());
					if(relacionActividadCriterioEvaluacion == null) {
						relacionActividadCriterioEvaluacion = new EvaRelacionActividadCriterioEvaluacion();
						relacionActividadCriterioEvaluacion.setActividad(actividad);
					}

					EvaCriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findById(critPeso.getIdCriterio()).orElse(null);
					if (criterioEvaluacion != null) {
						relacionActividadCriterioEvaluacion.setCriterioEvaluacion(criterioEvaluacion);
						EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(actividad.getUnidadProgramacion(), criterioEvaluacion);
						if (relPonCriEva != null && relPonCriEva.getIdOpeCalCriEva() == 2) {
							relacionActividadCriterioEvaluacion.setLPonderada("1");
							relacionActividadCriterioEvaluacion.setPeso(critPeso.getPeso());
						} else {
							relacionActividadCriterioEvaluacion.setLPonderada("0");
						}
					}

					relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacion);
				}
			} else {
				List<Long> idsCriteriosBis = actividadDto.getCriteriosEvaluacionIds();
				if(idsCriteriosBis == null || idsCriteriosBis.isEmpty()) {
					relacionActividadCriterioEvaluacionRepository.deleteAllByActividad(actividad);
				} else { //FIXME: Esta parte es temporal para comprobar el funcionamiento del update actividad, luego debería eliminarse 
					List<EvaRelacionActividadCriterioEvaluacion> relacionesActividadCriterioEvaluacionBis = relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividad.getId());
					for(EvaRelacionActividadCriterioEvaluacion relActCriEvaBis : relacionesActividadCriterioEvaluacionBis) {
						Long idCriterioAct = relActCriEvaBis.getCriterioEvaluacion().getId();
						if(idsCriteriosBis.contains(idCriterioAct)) {
							idsCriteriosBis.remove(idCriterioAct);
						} else {
							relacionActividadCriterioEvaluacionRepository.delete(relActCriEvaBis);
						}
					}
					for(Long idCriterio : idsCriteriosBis) {
						EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacionNuevo = new EvaRelacionActividadCriterioEvaluacion();
						relacionActividadCriterioEvaluacionNuevo.setActividad(actividad);
						EvaCriterioEvaluacion criterioEvaluacionIns = criterioEvaluacionRepository.findById(idCriterio).orElse(null);
						if (criterioEvaluacionIns != null) {
							relacionActividadCriterioEvaluacionNuevo.setCriterioEvaluacion(criterioEvaluacionIns);
						}
						relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacionNuevo);
					}
				}
			}

			//Recalculo los criterios, competencias y nota de la materia temporal en caso de que la actividad tenga un criterio
			//con tipo de cálculo 'Último valor'
			calcularAllCriteriosOnUpdate(actividad);
			
			List<Long> idsAlumnos = actividadDto.getAlumnosIds();
			if(idsAlumnos == null || idsAlumnos.isEmpty()) {
				relacionActividadAlumnoRepository.deleteAllByActividadId(actividad.getId());
			} else {
				List<EvaRelacionActividadAlumno> relacionesActividadAlumno = relacionActividadAlumnoRepository.findAllByActividadId(actividad.getId());
				for(EvaRelacionActividadAlumno relActAlu : relacionesActividadAlumno) {
					if(idsAlumnos.indexOf(relActAlu.getMatricula().getId()) == -1) {
						relacionActividadAlumnoRepository.delete(relActAlu);
					} else {
						idsAlumnos.remove(relActAlu.getMatricula().getId());
					}
				}
				for(Long idMatricula : idsAlumnos) {
					EvaRelacionActividadAlumno relacionActividadAlumno = new EvaRelacionActividadAlumno();
					relacionActividadAlumno.setActividad(actividad);
					EvaMatriculaAlumno matricula = matriculaRepository.findById(idMatricula).orElse(null);
					if(matricula != null) {
						relacionActividadAlumno.setMatricula(matricula);
					}

					relacionActividadAlumnoRepository.save(relacionActividadAlumno);
				}
			}
		} else {
			throw new EvaluacionException("No se ha podido actualizar la actividad");
		}
	}

	@Transactional
	public void calcularAllCriteriosOnUpdate(EvaActividad actividad) {

		List<Long> listadoCriterios = evaActividadRepository.getAllCriteriosUltimoValorAndMediaPonderadaByIdActividad(actividad.getId());

		if(!listadoCriterios.isEmpty()) {
			//Rescato los datos necesarios para posteriormente calcular todos los criterios de la programación de Aula a los alumnos
			DatosCalcularAllCriteriosProjection datos = criterioAlumnoTempRepository.getDatosParaCalculoAllCriterios(actividad.getId());

			//Rescato los alumnos que tenian asociada la actividad para volver a calcularles los criterios, competencias y nota global
			List<AlumnosActividadCalcularAllCriteriosProjection> listadoAlumnos = criterioAlumnoTempRepository.getAlumnosActividad(actividad.getId());

			//Vuelvo a calcularles los criterios, competencias y nota globala los alumnos asociados a la actividad
			for(AlumnosActividadCalcularAllCriteriosProjection alumno : listadoAlumnos) {
				calificacionActividadesService.calculoNotaTodosLosCriteriosParaAlumno(alumno.getIdMatricula(), datos.getIdPonderacion(),
						alumno.getIdMatMatriAlu(), datos.getIdProgramacionAula(), datos.getAnno(), datos.getIdSistemaCalifica());
			}
		}

	}
	

	public List<ProgramacionAulaDTO> getPlantillasProgramacionAulaByEmpleado(Long idEmpleado) {
		List<EvaProgramacionAula> programacionesAulaIn = programacionAulaRepository.findByEmpleadoNotUnidad(idEmpleado);
		return programacionesAulaIn.stream().map(x -> modelMapper.map(x, ProgramacionAulaDTO.class)).collect(Collectors.toList());
	}
	
	@Override
	public ProgramacionAulaDTO getProgramacionAulaById(Long idProgAula) {
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgAula).orElse(null);
		if (programacionAula != null) {
			return obtenerDatosProgramacionAula(programacionAula);
		} else {
			return null;
		}
	}
	
	/*
	@Transactional
	public void insertPlantillaProgramacionAula(Long idEmpleado, Long idMateriaOmg, Long idCentro, Long anno, Long idOfertamatrig, Long idnivelCurricular, ProgramacionAulaDTO programacionAulaDTO) {
		EvaEmpleado empleado = empleadoAulaRepository.findById(idEmpleado).orElse(null);
		ProgramacionDidacticaDTO programacionDidacticaDTO = programacionDidacticaAulas(idMateriaOmg, idCentro, anno, idOfertamatrig, idnivelCurricular);

		if (programacionDidacticaDTO != null && empleado != null) {
			programacionAulaDTO.setProgramacionDidactica(programacionDidacticaDTO);
			EvaProgramacionAula programacionAula = modelMapper.map(programacionAulaDTO, EvaProgramacionAula.class);
			programacionAula = programacionAulaRepository.save(programacionAula);
			if(programacionAula != null) {
				EvaRelacionProgramacionAulaEmpleado relProgAulaEmp = new EvaRelacionProgramacionAulaEmpleado();
				relProgAulaEmp.setProgramacionAula(programacionAula);
				relProgAulaEmp.setEmpleado(empleado);
				relacionProgramacionAulaEmpleadoRepository.save(relProgAulaEmp);
			}
		}
	}*/
	
	@Transactional
	public void updateProgramacionAula(ProgramacionAulaDTO programacionAulaDTO) {
		EvaProgramacionAula programacionAula = modelMapper.map(programacionAulaDTO, EvaProgramacionAula.class);
		if (programacionAula != null && programacionAulaRepository.existsById(programacionAula.getId())) {
			programacionAulaRepository.save(programacionAula);
		}
	}

	@Override
	@Transactional
	public Boolean deleteProgramacionAula(Long idProgramacionAula) {
		// Se busca si existe la programación de aula
		EvaProgramacionAula programacionAulaABorrar = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
		if (programacionAulaABorrar != null) {
			// Se borran todas las relaciones
			relacionProgramacionAulaAlumnoRepository.deleteAllByProgramacionAula(idProgramacionAula);
			relacionProgramacionAulaUnidadRepository.deleteAllByProgramacionAula(idProgramacionAula);
			relacionProgramacionAulaEmpleadoRepository.deleteAllByProgramacionAula(programacionAulaABorrar);

			// Se borran las actividades
			List<EvaRelacionProgramacionAulaActividad> relActividades = relacionProgramacionAulaActividadRepository.getAllByProgramacionAula(programacionAulaABorrar);
			for (EvaRelacionProgramacionAulaActividad relActividad: relActividades) {
				deleteActividad(relActividad.getActividad().getId());
			}

			relacionProgramacionAulaActividadRepository.deleteAllByProgramacionAula(programacionAulaABorrar);

			// Se borra la programación de aula
			programacionAulaRepository.deleteById(idProgramacionAula);

			log.info("Se ha borrado exitosamente la programación didáctica con id= " + idProgramacionAula);
			return true;
		} else {
			log.error("Error al borrar la programación de aula con id= " + idProgramacionAula);
			return false;
		}
	}
	
	@Override
	public List<CursoProgramacionAulaDTO> getCursosProgramacionAulaByCentroAndAnno(Long idEmpleado, Long idCentro, Integer anno, Boolean direccion) {

		List<CursoProgramacionAulaProjection> ofertas = null;

		if(!direccion){
			ofertas = programacionAulaRepository.getCursoProgramacionAulaByCentroAndAnno(idEmpleado, idCentro, anno);
		} else {
			ofertas = programacionAulaRepository.getCursoProgramacionAula_Dir(idCentro, anno);
		}


		return ofertas.stream().map(entity -> modelMapper.map(entity, CursoProgramacionAulaDTO.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<MateriaProgramacionAulaDTO> getMateriasProgramacionAula(Long idEmpleado, Long idCentro, Long idOfermatrig, Integer anno, List<Long> idsDocenteSust ) throws EvaluacionException  {
		List<MateriaProgramacionAulaProjection> ofertas = programacionAulaRepository.getMateriasProgramacionAula(idEmpleado, idCentro, idOfermatrig, anno);
		List<MateriaProgramacionAulaDTO> materiaProgramacionAula = ofertas.stream().map(entity -> modelMapper.map(entity, MateriaProgramacionAulaDTO.class)).collect(Collectors.toList());

		for(MateriaProgramacionAulaDTO materia :materiaProgramacionAula) {
			List<NivelCurricularProgramacionAulaDTO> nivelCurricular = materiaProgramacionDidacticaService.getNivelCurricular(anno, materia.getIdMateriaOmg(), idCentro,idOfermatrig);
			if(nivelCurricular.isEmpty()) {
				materia.setTieneNivelCurricular(false);
			} else {
				materia.setTieneNivelCurricular(true);
			}
			// se pone esta lista por calificacion de actividades y los docentes sustitutos
			idsDocenteSust.add(idEmpleado);
			//
			List<ProgramacionAulaDTO> programacionesAula = calificacionActividadesService.getProgramacionesAulaByDidactica(idOfermatrig, materia.getIdMateriaOmg(), idCentro, anno, idsDocenteSust, false);
			if(programacionesAula.isEmpty()) {
				materia.setTieneProgramaciones(false);
			} else {
				materia.setTieneProgramaciones(true);
			}
		}


		return materiaProgramacionAula;
	}

	public List<MateriaProgramacionAulaDTO> getMateriasProgramacionAula_dir(Long idCentro, Long idOfermatrig, Integer anno) throws EvaluacionException  {
		List<MateriaProgramacionAulaProjection> ofertas = programacionAulaRepository.getMateriasProgramacionAula_Dir( idCentro, idOfermatrig, anno);
		List<MateriaProgramacionAulaDTO> materiaProgramacionAula = ofertas.stream().map(entity -> modelMapper.map(entity, MateriaProgramacionAulaDTO.class)).collect(Collectors.toList());

		for(MateriaProgramacionAulaDTO materia :materiaProgramacionAula) {
			List<NivelCurricularProgramacionAulaDTO> nivelCurricular = materiaProgramacionDidacticaService.getNivelCurricular(anno, materia.getIdMateriaOmg(), idCentro,idOfermatrig);
			if(nivelCurricular.isEmpty()) {
				materia.setTieneNivelCurricular(false);
			} else {
				materia.setTieneNivelCurricular(true);
			}

			List<ProgramacionAulaDTO> programacionesAula = calificacionActividadesService.getProgramacionesAulaByDidactica(idOfermatrig, materia.getIdMateriaOmg(), idCentro, anno,null, true);
			if(programacionesAula.isEmpty()) {
				materia.setTieneProgramaciones(false);
			} else {
				materia.setTieneProgramaciones(true);
			}
		}

		return materiaProgramacionAula;
	}

	public ProgramacionDidacticaDTO programacionDidacticaAulas(Long idDidactica) {
		EvaProgramacionDidacticaAulaProjection programacionDidacticaAulaProjection = programacionAulaRepository.programacionDidacticaAulas(idDidactica);
		EvaProgramacionDidactica programacionDidactica = modelMapper.map(programacionDidacticaAulaProjection, EvaProgramacionDidactica.class);
		ProgramacionDidacticaDTO programacionDidacticaOut = modelMapper.map(programacionDidactica, ProgramacionDidacticaDTO.class);
		programacionDidacticaOut.setNombreMateria(programacionDidacticaAulaProjection.getNombreMateria());
		programacionDidacticaOut.setNombreCurso(programacionDidacticaAulaProjection.getNombreCurso());
		return programacionDidacticaOut;
	}
	
	@Override
	public List<CursoProgramacionAulaDTO> getCursosPlantilla(Long idCentro, Integer anno) {
		List<CursoProgramacionAulaProjection> ofertas = programacionAulaRepository.getCursoPlantilla(anno, idCentro);
		return ofertas.stream().map(entity -> modelMapper.map(entity, CursoProgramacionAulaDTO.class)).collect(Collectors.toList());
	}
    
	@Transactional
	@Override
    public Long insertProgramacionAula(Long idEmpleado, Long idDidac, DataProgramacionAulaInsertDTO data, Long idProAula) throws EvaluacionException {

		EvaProgramacionDidactica programacionDidactica =  programacionDidacticaRepository.findById(idDidac).orElse(null);
		EvaEmpleado empleado = empleadoAulaRepository.findById(idEmpleado).orElse(null);
		EvaProgramacionAula progAula = null;

		Long idAula;
		if (idProAula != null) {

			/*En esta parte borramos tanto las unidades como los alumnos relacionados con una programación de aula */
			idAula = idProAula;

			/*Borramos unidades */
			relacionProgramacionAulaUnidadRepository.deleteAllByProgramacionAula(idAula);

			/*Borramos alumnos */
			relacionProgramacionAulaAlumnoRepository.deleteAllByProgramacionAula(idAula);

			progAula = programacionAulaRepository.findById(idProAula).orElse(null);

		} else {
			/*En esta creamos la programación de aula ya que no existe */
			EvaProgramacionAula programacionAulaSaved = new EvaProgramacionAula();
			programacionAulaSaved.setNombre(data.getName());
			programacionAulaSaved.setProgramacionDidactica(programacionDidactica);
			idAula = programacionAulaRepository.save(programacionAulaSaved).getId();

			if (empleado != null && programacionAulaSaved != null) {
				EvaRelacionProgramacionAulaEmpleado relProgAulaEmp = new EvaRelacionProgramacionAulaEmpleado();
				relProgAulaEmp.setProgramacionAula(programacionAulaSaved);
				relProgAulaEmp.setEmpleado(empleado);
				relacionProgramacionAulaEmpleadoRepository.save(relProgAulaEmp);
			}
		}


		for (UnidadPorMateriaDTO unidad : data.getUnidades()) {
			EvaRelacionProgramacionAulaUnidad relacionUnidad = new EvaRelacionProgramacionAulaUnidad();
			relacionUnidad.setLAfectaTodos(unidad.getAfectaTodos());
			relacionUnidad.setUnidadCentro(unidad.getIdUnidad());
			relacionUnidad.setProgramacionAula(idAula);
			relacionProgramacionAulaUnidadRepository.save(relacionUnidad);
		}

		if (!data.getAlumnos().isEmpty()) {
			for (AlumnosPorMateriaDTO alumn : data.getAlumnos()) {
				EvaRelacionProgramacionAulaAlumno alumno = new EvaRelacionProgramacionAulaAlumno();
				EvaMatriculaAlumno matriculaAlumno = matriculaAlumnoService.findById(alumn.getIdMatricula());
				if(matriculaAlumno != null) {
					alumno.setMatriculaAlumno(matriculaAlumno);
				}
				alumno.setUnidadCentro(alumn.getIdUnidad());
				alumno.setProgramacionAula(idAula);

				relacionProgramacionAulaAlumnoRepository.save(alumno);

				// Añadimos el id de moodle si la programación tiene asociada el aula virtual
				if(progAula != null && progAula.getAulaVirtual() != null) {
					Long idAulaVirtual = aulaVirtualService.obtenerIdMoodleAlumno(progAula.getAulaVirtual().getId(), alumno);
					if(idAulaVirtual != null) {
						alumno.setIdUsuarioMoodle(idAulaVirtual);
						relacionProgramacionAulaAlumnoRepository.save(alumno);
					}
				}
			}
		}
		return idAula;
     
    }

	@Override
	public List<ActividadDTO> getActividades(Long idUnidadProgramacion, Long idProgramacionAula) {

		List<ActividadProjection> actividades = evaActividadRepository.findAllActividades(idUnidadProgramacion, idProgramacionAula);
		List<ActividadDTO> actividadesDto = actividades.stream().map(actividad -> modelMapper.map(actividad, ActividadDTO.class)).collect(Collectors.toList());

			if(idUnidadProgramacion != 0) {

				for (ActividadDTO actividad : actividadesDto) {
					List<Long> idCriterios = new ArrayList<>();
					List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
					List<CriterioPesoDTO> criteriosPesos = new ArrayList<>();
					List<Long> idMatriculas = new ArrayList<>();

					for (EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion : relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividad.getId())) {
						idCriterios.add(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
						criteriosActividad.add(modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(), CriterioEvaluacionDTO.class));
						CriterioPesoDTO critPeso = new CriterioPesoDTO();
						critPeso.setIdCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
						critPeso.setAbrevCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getAbreviatura());
						critPeso.setPeso(relacionActividadCriterioEvaluacion.getPeso());
						criteriosPesos.add(critPeso);

					}
					actividad.setCriteriosEvaluacionIds(idCriterios);
					actividad.setCriteriosEvaluacion(criteriosActividad);
					actividad.setCriteriosPesos(criteriosPesos);

					for (EvaRelacionActividadAlumno relacionActividadAlumno : relacionActividadAlumnoRepository.findAllByActividadId(actividad.getId())) {
						idMatriculas.add(relacionActividadAlumno.getMatricula().getId());
					}
					actividad.setAlumnosIds(idMatriculas);

					//ESTADO CALIFICADA
					List<CriterioAlumno> criteriosEvaluados = valoracionCriterioActividadAlumnoService.getCriteriosActividad(actividad.getId());
					boolean todosNulos = criteriosEvaluados.stream().allMatch(x -> x.getIdCalifica() == null);
					boolean alMenosUnNulo = criteriosEvaluados.stream().anyMatch(x -> x.getIdCalifica() == null);
					boolean todosNoNulos = criteriosEvaluados.stream().allMatch(x -> x.getIdCalifica() != null);

					if (todosNulos) {
						actividad.setCalificada("Sin calificar");
					} else if (alMenosUnNulo) {
						actividad.setCalificada("Parcialmente");
					} else if (todosNoNulos) {
						actividad.setCalificada("Calificada");
					}
				}
			}
			return actividadesDto;

	}
	
	@Transactional
	@Override
	public Boolean insertActividades(List<ActividadDTO> actividades) {
		Boolean res = Boolean.TRUE;
		try {
			for(ActividadDTO actividad: actividades) {
				insertActividad(actividad);
			}
		} catch (Exception e) {
			res = Boolean.FALSE;
		}
		return res;
	}

	@Transactional
	@Override
	public void insertActividad(ActividadDTO actividadDto) {
		EvaActividad actividad = modelMapper.map(actividadDto, EvaActividad.class);

		EvaUnidadProgramacion unidadProgramacion = unidadProgramacionRepository.findById(actividadDto.getIdUnidadProgramacion()).orElse(null);
		actividad.setUnidadProgramacion(unidadProgramacion);

		EvaConvocatoriaCentrosOMC convocatoriaCentroOMC = convocatoriaCentrosOMCRepository.findById(actividadDto.getIdConvCentroOmc()).orElse(null);
		actividad.setConvCentroOmc(convocatoriaCentroOMC);

		EvaInstrumentoEvaluacion instrumentoEvaluacion = instrumentoEvaluacionRepository.findById(actividadDto.getIdInstrumentoEvaluacion()).orElse(null);
		actividad.setInstrumentoEvaluacion(instrumentoEvaluacion);

		evaActividadRepository.save(actividad);
		
		if (actividadDto.getCriteriosEvaluacionIds() != null && actividadDto.getCriteriosPesos() == null) {
			for (Long idCriterio : actividadDto.getCriteriosEvaluacionIds()) {
				EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion = new EvaRelacionActividadCriterioEvaluacion();
				relacionActividadCriterioEvaluacion.setActividad(actividad);
				relacionActividadCriterioEvaluacion.setPeso(1);
				relacionActividadCriterioEvaluacion.setLPonderada("0");
				EvaCriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findById(idCriterio).orElse(null);
				if (criterioEvaluacion != null) {
					relacionActividadCriterioEvaluacion.setCriterioEvaluacion(criterioEvaluacion);
					EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(unidadProgramacion, criterioEvaluacion);
					if (relPonCriEva != null && relPonCriEva.getIdOpeCalCriEva() == 2) {
						relacionActividadCriterioEvaluacion.setLPonderada("1");
					}
				}
				relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacion);
			}
		}
		
		if (actividadDto.getCriteriosPesos() != null) {
			for (CriterioPesoDTO criterioPesoIn : actividadDto.getCriteriosPesos()) {
				EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion = new EvaRelacionActividadCriterioEvaluacion();
				relacionActividadCriterioEvaluacion.setActividad(actividad);
				EvaCriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findById(criterioPesoIn.getIdCriterio()).orElse(null);
				if (criterioEvaluacion != null) {
					relacionActividadCriterioEvaluacion.setCriterioEvaluacion(criterioEvaluacion);
					EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(unidadProgramacion, criterioEvaluacion);
					if (relPonCriEva != null && relPonCriEva.getIdOpeCalCriEva() == 2) {
						relacionActividadCriterioEvaluacion.setLPonderada("1");
						relacionActividadCriterioEvaluacion.setPeso(criterioPesoIn.getPeso());
					} else {
						relacionActividadCriterioEvaluacion.setLPonderada("0");
					}
				}
	
				relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacion);
			}
		}

		for (Long idMatricula : actividadDto.getAlumnosIds()) {
			EvaRelacionActividadAlumno relacionActividadAlumno = new EvaRelacionActividadAlumno();
			relacionActividadAlumno.setActividad(actividad);
			EvaMatriculaAlumno matricula = matriculaRepository.findById(idMatricula).orElse(null);
			if(matricula != null) {
				relacionActividadAlumno.setMatricula(matricula);
				relacionActividadAlumnoRepository.save(relacionActividadAlumno);
			}
		}
		
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(actividadDto.getIdProgramacionAula()).orElse(null);
		if(programacionAula != null) {
			EvaRelacionProgramacionAulaActividad relacionProgramacionAulaActividad = new EvaRelacionProgramacionAulaActividad();
			relacionProgramacionAulaActividad.setActividad(actividad);
			relacionProgramacionAulaActividad.setProgramacionAula(programacionAula);
			
			relacionProgramacionAulaActividadRepository.save(relacionProgramacionAulaActividad);
		}
	}

	@Override
	public Boolean isDocenteValidoProgramacionAula(Long idEmpleado, Integer anno, Long idCentro) {
		return programacionAulaRepository.isDocenteValidoProgramacionAula(idEmpleado, anno, idCentro);
	}
	
	@Transactional
	@Override
	public Boolean deleteActividad(Long idActividad) {
		EvaActividad actividadABorrar = evaActividadRepository.findById(idActividad).orElse(null);
		if(actividadABorrar != null) {

			//Rescato los datos necesarios para posteriormente calcular todos los criterios de la programación de Aula a los alumnos
			DatosCalcularAllCriteriosProjection datos = criterioAlumnoTempRepository.getDatosParaCalculoAllCriterios(idActividad);

			//Rescato los alumnos que tenian asociada la actividad para volver a calcularles los criterios, competencias y nota global
			List<AlumnosActividadCalcularAllCriteriosProjection> listadoAlumnos = criterioAlumnoTempRepository.getAlumnosActividad(idActividad);

			// Se borran todas las relaciones
			List<EvaRelacionActividadCriterioEvaluacion> relacionActividadCriterios = relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividadABorrar.getId());
			if (relacionActividadCriterios != null && !relacionActividadCriterios.isEmpty()) {
				for (EvaRelacionActividadCriterioEvaluacion relacionActividadCriterio : relacionActividadCriterios) {
					valoracionCriterioActividadAlumnoRepository.deleteAllByRelacionActividadCriterioEvaluacionId(relacionActividadCriterio.getId());
				}
			}

			relacionActividadCriterioEvaluacionRepository.deleteAllByActividad(actividadABorrar);
			relacionProgramacionAulaActividadRepository.deleteAllByActividad(actividadABorrar);
			relacionActividadAlumnoRepository.deleteAllByActividadId(actividadABorrar.getId());
			
			// Se borra la programación de aula
			evaActividadRepository.deleteById(idActividad);

			//Vuelvo a calcularles los criterios, competencias y nota global a los alumnos asociados a la actividad
			for(AlumnosActividadCalcularAllCriteriosProjection alumno : listadoAlumnos) {
				calificacionActividadesService.calculoNotaTodosLosCriteriosParaAlumno(alumno.getIdMatricula(), datos.getIdPonderacion(),
						alumno.getIdMatMatriAlu(), datos.getIdProgramacionAula(), datos.getAnno(), datos.getIdSistemaCalifica());
			}

			log.info("Se ha borrado exitosamente la actividad con id= " + idActividad);
			return true;
		} else {
			log.error("Error al borrar la actividad con id= " + idActividad);
			return false;
		}
	}

	@Transactional
	@Override
	public void editNombreProgramacionAula(Long idProgramacionAula, String nombre) throws Exception {
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);

		if(programacionAula != null) {
			programacionAula.setNombre(nombre);
			programacionAulaRepository.save(programacionAula);
		} else {
			throw new EvaluacionException("No se ha podido cambiar el nombre");
		}
	}
	
	private ProgramacionAulaDTO obtenerDatosProgramacionAula(EvaProgramacionAula programacionAula) {
		EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(programacionAula.getProgramacionDidactica().getId()).orElse(null);
		List<AlumnosPorMateriaProjection> listAlumnosProgAulaProjection = alumnoRepository.getAlumnosProgramacionAula(programacionAula.getId());
		List<AlumnosPorMateria> listAlumnosProgAula = listAlumnosProgAulaProjection.stream().map(amp -> modelMapper.map(amp, AlumnosPorMateria.class)).collect(Collectors.toList());
		ProgramacionAulaDTO progAulaOut = modelMapper.map(programacionAula, ProgramacionAulaDTO.class);
		EvaEmpleado empleado = programacionAula.getRelacionesProgramacionAulaEmpleado().get(0).getEmpleado();
		progAulaOut.setIdEmpleado(empleado.getId());
		progAulaOut.setNombreEmpleado(empleado.getApellido1() + " " + empleado.getApellido2() + ", " + empleado.getNombre());
		ProgramacionDidacticaDTO progDidacOut = modelMapper.map(programacionDidactica, ProgramacionDidacticaDTO.class);
		EvaProgramacionDidacticaAulaProjection progDidacProjection = programacionAulaRepository.programacionDidacticaAulaById(progDidacOut.getId());
		progDidacOut.setNombreMateria(progDidacProjection.getNombreMateria());
		progDidacOut.setNombreCurso(progDidacProjection.getNombreCurso());
		progAulaOut.setAlumnosSeleccionados(listAlumnosProgAula.stream().map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList()));
		List<EvaRelacionProgramacionDidacticaUnidadProgramacion> relProgDidacUnidadProg = relacionProgramacionDidacticaUnidadProgramacionRepository.findByProgramacionDidactica(programacionDidactica);
    	List<EvaUnidadProgramacion> unidadesProgramacion = relProgDidacUnidadProg.stream()
    			.map(x -> x.getUnidadProgramacion())
    			.sorted(Comparator.comparing(EvaUnidadProgramacion::getOrden)
                .thenComparing(EvaUnidadProgramacion::getAbreviatura)
                .thenComparing(EvaUnidadProgramacion::getNombre))
    			.collect(Collectors.toList());
    	
    	List<UnidadProgramacionDTO> unidadesProgramacionOut = unidadesProgramacion.stream()
    			.map(x -> {
					List<EvaActividad> actividades = x.getActividades().stream().filter(y -> y.getRelacionesProgramacionAulaActividad().stream()
									.anyMatch(z -> programacionAula.equals(z.getProgramacionAula()))).collect(Collectors.toList());
					Collections.sort(actividades, Comparator.comparing(EvaActividad::getOrden));
    				x.setActividades(actividades);
    				return modelMapper.map(x, UnidadProgramacionDTO.class);
    				}
    			).collect(Collectors.toList());
    	
    	for (UnidadProgramacionDTO unidadProgramacion : unidadesProgramacionOut) {
    		List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> criteriosEvaluacion =
    				relacionUnidadProgramacionCriteriosEvaluacionRepository.findByUnidadProgramacionId(unidadProgramacion.getId());
			List<CriterioEvaluacionDTO> criteriosEvaluacionDto = criteriosEvaluacion.stream().map(x -> modelMapper.map(x.getCriterioEvaluacion(), CriterioEvaluacionDTO.class)).collect(Collectors.toList());
			criteriosEvaluacionDto.sort(Comparator.comparing(CriterioEvaluacionDTO::getIdCompetenciaEspecifica, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getAbreviatura));
			unidadProgramacion.setCriteriosEvaluacion(criteriosEvaluacionDto);
			
			List<EvaRelacionUnidadProgramacionSaberBasico> saberesBasicos = 
					relacionUnidadProgramacionSaberBasicoRepository.findAllByUnidadProgramacionId(unidadProgramacion.getId());
			List<SaberBasicoDTO> saberesBasicosDto = saberesBasicos.stream().map(x -> modelMapper.map(x.getSaberBasico(), SaberBasicoDTO.class)).collect(Collectors.toList());
			saberesBasicosDto.sort(Comparator.comparing(SaberBasicoDTO::getIdBloque, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(SaberBasicoDTO::getIdSaberBasicoDepende, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(SaberBasicoDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(SaberBasicoDTO::getAbreviatura));
			unidadProgramacion.setSaberesBasicos(saberesBasicosDto);
    	}
		progDidacOut.setUnidadesProgramacion(unidadesProgramacionOut);
		progAulaOut.setProgramacionDidactica(progDidacOut);

		progAulaOut.setNivelCurricular(programacionAulaRepository.getNivelCurricularDeProgramacionDidactica(progDidacOut.getId()));
		
		// Si está vinculada a un aula virtual, obtenemos los alumnos del aula virtual
		AulaVirtualDTO aulaVirtual = progAulaOut.getAulaVirtual();
		if(aulaVirtual != null) {
			List<AlumnoDTO> alumnosAulaVirtual = alumnoService.getAlumnosAulaVirtual(aulaVirtual.getId());
			progAulaOut.getAulaVirtual().setAlumnos(alumnosAulaVirtual);
			// Eliminamos del idCurso todo lo que no es id
			String idCurso = aulaVirtual.getIdCurso(); 
			if(StringUtils.isNotBlank(idCurso)) {
				String[] partes = idCurso.split(Constantes.DOS_PUNTOS);
				if (partes.length == 2) {
					idCurso = partes[1];
		        }
				aulaVirtual.setIdCurso(idCurso); 
			}
			
			// Obtenemos la información necesaria del aula que no tenemos
			EvaAulaVirtualProjection aulaVirtualProjection = aulaVirtualRepository.findAulaById(aulaVirtual.getId());
			if(aulaVirtualProjection != null) {
				AulaVirtualListDTO aulaVirtualList = modelMapper.map(aulaVirtualProjection, AulaVirtualListDTO.class);
				aulaVirtual.setIdPlataforma(aulaVirtualList.getIdPlataforma());
				aulaVirtual.setTokenPlataforma(aulaVirtualList.getTokenPlataforma());
				
				}
		}
		
		return progAulaOut;
	}
	
	@Override
	public List<ActividadDTO> getActividadesCriteriosPonderacion(Long idUnidadProgramacion, Long idProgramacionAula) {
		List<ActividadProjection> actividades = evaActividadRepository.findAllActividades(idUnidadProgramacion, idProgramacionAula);
		List<ActividadDTO> actividadesDto = actividades.stream().map(actividad -> modelMapper.map(actividad, ActividadDTO.class)).collect(Collectors.toList());

		for(ActividadDTO actividad: actividadesDto) {
			List<Long> idCriterios = new ArrayList<>();
			List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
			List<CriterioPesoDTO> criteriosPesos = new ArrayList<>();
			List<Long> idMatriculas = new ArrayList<>();

			for(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion: relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividad.getId())) {
				idCriterios.add(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
				criteriosActividad.add(modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(), CriterioEvaluacionDTO.class));
				CriterioPesoDTO critPeso = new CriterioPesoDTO();
				critPeso.setIdCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
				critPeso.setAbrevCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getAbreviatura());
				critPeso.setPeso(relacionActividadCriterioEvaluacion.getPeso());
				critPeso.setLPonderada(relacionActividadCriterioEvaluacion.getLPonderada());
				criteriosPesos.add(critPeso);
				
			}
			actividad.setCriteriosEvaluacionIds(idCriterios);
			actividad.setCriteriosEvaluacion(criteriosActividad);
			actividad.setCriteriosPesos(criteriosPesos);

			for(EvaRelacionActividadAlumno relacionActividadAlumno: relacionActividadAlumnoRepository.findAllByActividadId(actividad.getId())) {
				idMatriculas.add(relacionActividadAlumno.getMatricula().getId());
			}
			actividad.setAlumnosIds(idMatriculas);
		}

		return actividadesDto;
	}

	@Override
	public Boolean isProgramacionAulaEliminable(Long idProgramacionAula) {
		Boolean eliminable = Boolean.TRUE;
		EvaProgramacionAula programacionAula = programacionAulaRepository.findById(idProgramacionAula).orElse(null);
		if(programacionAula != null) {
			for(EvaRelacionProgramacionAulaActividad relacionProgramacionAulaActividad: programacionAula.getRelacionesProgramacionAulaActividad()){
				EvaActividad actividad = relacionProgramacionAulaActividad.getActividad();
				log.info(relacionProgramacionAulaActividad.getActividad().getNombre());
				// No se pueden eliminar programaciones de aula con al menos una calificación en cualquier actividad
				List<EvaRelacionActividadAlumno> relacionesActAlu = actividad.getRelacionesActividadAlumnos();
				List<EvaRelacionActividadCriterioEvaluacion> relacionesActCriEva = actividad.getRelacionesActividadCriterios();
				
				if (relacionesActAlu != null && relacionesActCriEva != null) {
					outerLoop: for(EvaRelacionActividadAlumno relActAlu: relacionesActAlu) {
						for(EvaRelacionActividadCriterioEvaluacion relActCriEva: relacionesActCriEva) {
							EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno = valoracionCriterioActividadAlumnoService.findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(relActCriEva.getId(), relActAlu.getId());
							if(valoracionCriterioActividadAlumno != null) {
								// Existe al menos una calificación guardada
								eliminable = Boolean.FALSE;
								break outerLoop;
							}
						}
					}
				}
			}
		}
		return eliminable;
	}

	@Override
	public List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificasByProgramacionAula(Long idProgramacionAula) {
		return competenciaEspecificaDidacticaRepository.findAllByIdProgramacionAula(idProgramacionAula).stream().map(competenciaEspecifica -> modelMapper.map(competenciaEspecifica, CompetenciaEspecificaDidacticaDTO.class)).collect(Collectors.toList());
	}

	@Override
	public Page<CriterioActividadPonderacionDTO> getCriteriosActividadesPonderaciones(Long idProgramacionAula, Long idCompetenciaEspecifica, Long idCriterioEvaluacion, int page, int numItems) {
		Integer sumPesos = programacionAulaRepository.getSumaPesosCriteriosActividadesProgramacionAula(idProgramacionAula, idCompetenciaEspecifica, idCriterioEvaluacion);
		Pageable paging = PageRequest.of(page, numItems);
		Page<CriterioActividadProjection> criteriosActividadesProjection = programacionAulaRepository.getCriteriosActividades(idProgramacionAula, idCompetenciaEspecifica, idCriterioEvaluacion, paging);
		List<CriterioActividadPonderacionDTO> criteriosActividades = criteriosActividadesProjection.getContent().stream().map(criActPond -> modelMapper.map(criActPond, CriterioActividadPonderacionDTO.class)).collect(Collectors.toList());
		
		for(CriterioActividadPonderacionDTO criActPondOut : criteriosActividades) {
			
			//Se rellena la actividad asociada
			ActividadDTO actividadOut = modelMapper.map(actividadService.getActividadById(criActPondOut.getIdActividad()), ActividadDTO.class);
			
			Optional<EvaConvocatoriaCentrosOMC> convCentroOmcOpt = convocatoriaCentrosOMCRepository.findById(actividadOut.getIdConvCentroOmc());
			Optional<EvaUnidadProgramacion> uniProgOpt = unidadProgramacionRepository.findById(actividadOut.getIdUnidadProgramacion());
			
			if (convCentroOmcOpt.isPresent()) {
				actividadOut.setDescripcionConvocatoria(convCentroOmcOpt.get().getConvocatoriaCentro().getDescripcionConvocatoria());
				if (uniProgOpt.isPresent()) {
					actividadOut.setConvocatoriaUnidad(uniProgOpt.get().getConvCentroOmc().equals(convCentroOmcOpt.get()));
				}
			}
			
			actividadOut.setIdProgramacionAula(idProgramacionAula);
			
			List<Long> idCriterios = new ArrayList<>();
			List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
			List<CriterioPesoDTO> criteriosPesos = new ArrayList<>();
			List<Long> idMatriculas = new ArrayList<>();

			for(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion: relacionActividadCriterioEvaluacionRepository.findAllByActividadId(actividadOut.getId())) {
				idCriterios.add(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
				criteriosActividad.add(modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(), CriterioEvaluacionDTO.class));
				CriterioPesoDTO critPeso = new CriterioPesoDTO();
				critPeso.setIdCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
				critPeso.setAbrevCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getAbreviatura());
				critPeso.setPeso(relacionActividadCriterioEvaluacion.getPeso());
				criteriosPesos.add(critPeso);
				
			}
			actividadOut.setCriteriosEvaluacionIds(idCriterios);
			actividadOut.setCriteriosEvaluacion(criteriosActividad);
			actividadOut.setCriteriosPesos(criteriosPesos);

			for(EvaRelacionActividadAlumno relacionActividadAlumno: relacionActividadAlumnoRepository.findAllByActividadId(actividadOut.getId())) {
				idMatriculas.add(relacionActividadAlumno.getMatricula().getId());
			}
			actividadOut.setAlumnosIds(idMatriculas);

			//ESTADO CALIFICADA
			List<CriterioAlumno> criteriosEvaluados = valoracionCriterioActividadAlumnoService.getCriteriosActividad(actividadOut.getId());
			boolean todosNulos = criteriosEvaluados.stream().allMatch(x -> x.getIdCalifica() == null);
			boolean alMenosUnNulo = criteriosEvaluados.stream().anyMatch(x -> x.getIdCalifica() == null);
			boolean todosNoNulos = criteriosEvaluados.stream().allMatch(x -> x.getIdCalifica() != null);

			if (todosNulos) {
				actividadOut.setCalificada("Sin calificar");
			} else if (alMenosUnNulo) {
				actividadOut.setCalificada("Parcialmente");
			} else if(todosNoNulos) {
				actividadOut.setCalificada("Calificada");
			}
			
			criActPondOut.setActividadAsociada(actividadOut);
			
			//Porcentaje ponderación
			if (criActPondOut.getEsPonderada()) {				
				if (sumPesos != null) {
					criActPondOut.setPorcentaje(100F * criActPondOut.getPeso()/sumPesos);
				}
			}
		}
		
		return new PageImpl<>(criteriosActividades, criteriosActividadesProjection.getPageable(), criteriosActividadesProjection.getTotalElements());
	}

	@Override
	public List<CriterioEvaluacionDTO> getCriteriosEvaluacionByProgramacionAulaAndCompetenciaEspecifica(Long idProgramacionAula, Long idCompetenciaEspecifica) {
		return criterioEvaluacionRepository.findAllByIdProgramacionAulaAndIdCompetenciaEspecifica(idProgramacionAula, (idCompetenciaEspecifica != -1) ? idCompetenciaEspecifica : null).stream().map(criterioEvaluacion -> modelMapper.map(criterioEvaluacion, CriterioEvaluacionDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProgramacionAulaProjection> findProgramacionesAulaByEmpleadoAndAnyo(Long idEmpleado, Long anno, Long idCentro) {
		return programacionAulaRepository.findByEmpleado(idEmpleado, anno, idCentro);
	}

	@Override
	public List<ProgramacionAulaProjection> findProgramacionesAulaByEmpleadosAndAnyo(Long idEmpleado, Long anno, Long idCentro, String idsEmpleadosCompartidas) {
		return programacionAulaRepository.findByEmpleados(((Long[]) ConvertUtils.convert((idEmpleado + "," + idsEmpleadosCompartidas).split(","), Long[].class)), anno, idCentro);
	}

	public List<ProgramacionAulaProjection> findProgramacionesAulaByCursoMateriaUnidad(Long anno, Long idCentro, Long idOfertaMatrig, Long idMateriaOMG, Long idUnidad) {
		return programacionAulaRepository.findProgByCursoMateriaUnidad(anno, idCentro, idOfertaMatrig, idMateriaOMG, idUnidad);
	}

	@Override
	public Boolean isDocenteValidoProgramacionAula(Long idEmpleado, Integer anno, Long idCentro, String idsEmpleadosCompartidas) {
		return programacionAulaRepository.isDocenteValidoProgramacionAula(((Long[]) ConvertUtils.convert((idEmpleado + "," + idsEmpleadosCompartidas).split(","), Long[].class)), anno, idCentro);
	}

	@Override
	public List<AlumnosPorGrupoProjection> findAlumnosByEmpleadoAndAnyo(Long idEmpleado, Long anno, Long idCentro) {

		return programacionAulaRepository.findAlumnosByEmpleadoAndAnyo(idEmpleado, anno, idCentro);
	}

	@Override
	public List<AlumnosPorGrupoProjection> findAlumnosByAnyoAndEmpleados(Long idEmpleado, Long anno, Long idCentro, String idsEmpleadosCompartidas) {
		return programacionAulaRepository.findAlumnosByAnyoAndEmpleados(anno, idCentro, ((Long[]) ConvertUtils.convert((idEmpleado + "," + idsEmpleadosCompartidas).split(","), Long[].class)));
	}

	@Override
	public List<DocenteProgAulaProjection> getDocentesProgAula(Long anno, Long idCentro) {
		return programacionAulaRepository.getDocentesProgAula(anno, idCentro);
	}

	@Transactional
	public void insertAlumnosActividades(List<Long> idsMatricula, List<Long> idsActividad) {
		for(Long idMatricula : idsMatricula) {
			
			EvaMatriculaAlumno matricula = matriculaRepository.findById(idMatricula).orElse(null);
			
			for(Long idActividad : idsActividad) {
				
				EvaActividad actividad = actividadService.getActividadById(idActividad);
				
				if (matricula != null && actividad != null) { //Si la matrícula o la actividad no existen, no se crea relación
				
					EvaRelacionActividadAlumno relacionActividadAlumno = relacionActividadAlumnoRepository.findByActividadIdAndMatriculaId(idActividad, idMatricula);
					
					if (relacionActividadAlumno == null) { //Si la relación no existe se inserta una nueva con la matrícula y la actividad especificadas
						relacionActividadAlumno = new EvaRelacionActividadAlumno();
						
						relacionActividadAlumno.setMatricula(matricula);
						relacionActividadAlumno.setActividad(actividad);
						
						relacionActividadAlumnoRepository.save(relacionActividadAlumno);
					}
				}
			}
		}
		
	}



}
