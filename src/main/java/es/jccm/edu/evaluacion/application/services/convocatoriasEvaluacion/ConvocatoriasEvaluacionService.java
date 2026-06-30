package es.jccm.edu.evaluacion.application.services.convocatoriasEvaluacion;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.jccm.edu.evaluacion.adapter.out.repositories.convocatoriasEvaluacion.ConvocatoriasEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoConvocatorias;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoEvalConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.CalificacionMateriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.CompetenciasConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.ConvocatoriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.CriterioConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.DatosAlumnoConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.MateriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.PonderacionConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.RelacionCalificacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.AlumnoConvocatoriasProjection;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.AlumnoEvalConvProjection;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.DatosAlumnoConvProjection;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.ObservacionConvProjection;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.PonderacionConvProjection;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.ValoracionConvProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.PonderacionProjection;
import es.jccm.edu.evaluacion.application.ports.in.convocatoriasEvaluacion.IConvocatoriasEvaluacionService;


@Service
public class ConvocatoriasEvaluacionService implements IConvocatoriasEvaluacionService {
	
	private static final Logger LOG = LogManager.getLogger(ConvocatoriasEvaluacionService.class);
	
	@Autowired
	private ConvocatoriasEvaluacionRepository convocatoriasRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private PonderacionRepository ponderacionRepository;


	

	public DatosAlumnoConv datosAlumnoConvocatoria(Long idMatmatrialu, Long idConvCentroOmc) {
		LOG.info("Obteniendo los datos del alumno con idMatmatrialu = ", idMatmatrialu);

		DatosAlumnoConvProjection alumnoProjection = convocatoriasRepository.datosAlumnoConvocatoria(idMatmatrialu, idConvCentroOmc);
		DatosAlumnoConv alumno = modelMapper.map(alumnoProjection, DatosAlumnoConv.class);

		Blob img = convocatoriasRepository.getFotoAlumno(alumno.getNumEscolar());
		setFotoAlumno(img, alumno);

		return alumno;
	}
	
	public List<AlumnoConvocatorias> alumnosEvaluacion(String idMatmatrialu) {
		LOG.info("Obteniendo los alumnos con idMatmatrialu: ", idMatmatrialu);
		
		Long[] idMatmatrialus = (Long[]) ConvertUtils.convert(idMatmatrialu.split(","), Long[].class);
		
		List<AlumnoConvocatoriasProjection> alumnosProjection = convocatoriasRepository.alumnosEvaluacion(idMatmatrialus);
		List<AlumnoConvocatorias> alumnos = alumnosProjection.stream().map(alumno -> modelMapper.map(alumno, AlumnoConvocatorias.class)).collect(Collectors.toList());

		return alumnos;
	}

	private DatosAlumnoConv setFotoAlumno(Blob img, DatosAlumnoConv alumn) {
		try {
			if (img != null) {
				alumn.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alumn;
	}

	public PonderacionConv ponderacionConvocatoria(Long idEmpleado, Long idMatMatriAlu) {
		LOG.info("Obteniendo las ponderaciones del empleado: ", idEmpleado);

		PonderacionConvProjection ponderacionProjection = convocatoriasRepository.getPonderacionConvocatoria(idMatMatriAlu, idEmpleado);
		PonderacionConv ponderacion = modelMapper.map(ponderacionProjection, PonderacionConv.class);

		ponderacion = getCompetenciasConv(ponderacion, idMatMatriAlu);

		return ponderacion;
	}

	public PonderacionConv getCompetenciasConv(PonderacionConv ponderacion, Long idMatMatriAlu) {
		List<CompetenciasConv> competencias = convocatoriasRepository.getCompetenciasConvocatoria(ponderacion.getIdPonderacion()).stream().map(competencia -> modelMapper.map(competencia, CompetenciasConv.class)).collect(Collectors.toList());

		for (CompetenciasConv competencia : competencias) {
			ValoracionConvProjection valComp = convocatoriasRepository.valoracionCompetencia(ponderacion.getIdPonderacion(), competencia.getIdCompetencia(), idMatMatriAlu);
			if (valComp != null) {
				competencia.setValoracion(valComp.getValoracion());
				competencia.setAprueba(valComp.getAprueba());
			}

			List<CriterioConv> criterios = convocatoriasRepository.getCriteriosConvocatoria(ponderacion.getIdPonderacion(), competencia.getIdCompetencia()).stream().map(criterio -> modelMapper.map(criterio, CriterioConv.class)).collect(Collectors.toList());
			for (CriterioConv criterio : criterios) {
				ValoracionConvProjection valCri = convocatoriasRepository.valoracionCriterios(ponderacion.getIdPonderacion(), competencia.getIdCompetencia(), criterio.getIdCriterio(), idMatMatriAlu);
				if (valCri != null) {
					criterio.setValoracion(valCri.getValoracion());
					criterio.setAprueba(valCri.getAprueba());
				}
			}


			competencia.setCriterios(criterios);
		}
		ponderacion.setCompetencias(competencias);

		return ponderacion;
	}
	
	public List<AlumnoEvalConv> getAlumnosEvaluacion(Long idCurso, Long idUnidad) {
		LOG.info("Obteniendo los alumnos de ", idUnidad);
		
		List<AlumnoEvalConvProjection> alumnosProjection = convocatoriasRepository.getAlumnosEvaluacion(idCurso, idUnidad);
		List<AlumnoEvalConv> alumnos = alumnosProjection.stream().map(alumno -> modelMapper.map(alumno, AlumnoEvalConv.class)).collect(Collectors.toList());
		
		for(AlumnoEvalConv alum : alumnos) {
			Blob img = convocatoriasRepository.getFotoAlumno(alum.getNumEscolar());
			setFotoAlumno(img, alum);
			Integer numMaterias = convocatoriasRepository.countMateriasAlumno(alum.getIdMatricula());
			alum.setNumMaterias(numMaterias);
		}

		return alumnos;
	}
	
	private AlumnoEvalConv setFotoAlumno(Blob img, AlumnoEvalConv alum) {
		try {
			if (img != null) {
				alum.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alum;
	}
	
	public List<MateriaAlumnoEvaluacion> getMateriasEvaluacionAlumno(Long idMatricula, Long idEmpleado) {
		LOG.info("Obteniendo las materias de ", idMatricula);
		
		List<MateriaAlumnoEvaluacion> materias = convocatoriasRepository.getMateriasAlumno(idMatricula).stream().map(materia -> modelMapper.map(materia, MateriaAlumnoEvaluacion.class)).collect(Collectors.toList());
		
		for (MateriaAlumnoEvaluacion materia : materias) {
			
			List<CalificacionMateriaAlumnoEvaluacion> calificaciones = convocatoriasRepository.getCalificacionesMateriaAlumno(materia.getIdMatMatriAlu()).stream().map(convocatoria -> modelMapper.map(convocatoria, CalificacionMateriaAlumnoEvaluacion.class)).collect(Collectors.toList());
			if(calificaciones != null) {
				materia.setCalificaciones(calificaciones);
			}
			
			//COMPROBACION PARA VER SI LA MATERIA ESTA PONDERADA O NO
			PonderacionConvProjection ponderacionProjection = convocatoriasRepository.getPonderacionConvocatoria(materia.getIdMatMatriAlu(), idEmpleado);
			materia.setEstaPonderada(ponderacionProjection != null);			
		}
		
		return materias;
	}
	
	public List<ConvocatoriaAlumnoEvaluacion> getConvocatoriasEvaluacionAlumno(Long idMatricula) {
		LOG.info("Obteniendo las convocatorias de ", idMatricula);
		
		List<ConvocatoriaAlumnoEvaluacion> convocatorias = convocatoriasRepository.getConvocatoriasAlumno(idMatricula).stream().map(convocatoria -> modelMapper.map(convocatoria, ConvocatoriaAlumnoEvaluacion.class)).collect(Collectors.toList());
		
		return convocatorias;
	}

	@Transactional
	public void setObservacionesEvaluacionAlumno(Long idMatricula, Long idConvCentroOmc, String observaciones) {
		
		ObservacionConvProjection observacionProjection = convocatoriasRepository.getObservacion(idMatricula, idConvCentroOmc);
		
		try {
			if (observacionProjection == null) {
				Integer resultado = convocatoriasRepository.insertarObservacionesEvaluacionAlumno(idMatricula, idConvCentroOmc, observaciones);
				String mensaje = String.format("Resultado de insertar observaciones con idMatricula: %d, idConvCentroOmc: %d, observaciones: %s es %d", 
						idMatricula, idConvCentroOmc, observaciones, resultado);
				LOG.info(mensaje);
			} else {
				convocatoriasRepository.actualizarObservacionesEvaluacionAlumno(idMatricula, idConvCentroOmc, observaciones);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public List<RelacionCalificacion> getRelacionCalificaciones(Long idSistCal) {
		LOG.info("Obteniendo la relación de calificaciones de ", idSistCal);
		
		List<RelacionCalificacion> relacion = convocatoriasRepository.getRelacionCalificaciones(idSistCal).stream().map(nota -> modelMapper.map(nota, RelacionCalificacion.class)).collect(Collectors.toList());
		
		return relacion;
	}
	
private Boolean existenCompetencias(Long idMateria) {
    	
    	//Intento rescatar las competencias de una materia, si existen entonces devolveré un true, en caso contrario un false
    	Boolean existenCompetencias = ponderacionRepository.getCompetenciasByMateria(idMateria).isEmpty() ? false : true;
    	
    	return existenCompetencias; 
    }

}
