package es.jccm.edu.evaluacion.application.services.programacionAula;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnosPorMateriaDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaValoracionCriterioActividadAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.AlumnoValoracionActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.AlumnosPorMateria;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaAlumno;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;

@Service
public class EvaAlumnoService implements IEvaAlumnoService {

    @Autowired
    private EvaAlumnoRepository alumnoRepository;

    @Autowired
    private EvaRelacionActividadAlumnoRepository relacionActividadAlumnoRepository;

    @Autowired
    private EvaValoracionCriterioActividadAlumnoRepository valoracionCriterioActividadAlumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AlumnosPorMateria> findAlumnosByMateria(Long idEmpleado, String fechaTomaPosesion, Long idDidac, Long nivelCurricular){
		
		return alumnoRepository.findAllAlumnosByMateria(idEmpleado, fechaTomaPosesion, idDidac, nivelCurricular)
				.stream().map(x -> modelMapper.map(x, AlumnosPorMateria.class)).collect(Collectors.toList());
	}

    @Override
    public List<AlumnosPorMateria> getGestionarAlumnadoProgramacionAula(Long idEmpleado, Long idProgramacionAula, Long nivelCurricular,Long idCentro){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date fechaTomaPosesion = alumnoRepository.getFechaTomaPosesionByIdEmpleadoAndIdCentro(idEmpleado,idCentro);
        String ftomapos = formatter.format(fechaTomaPosesion);

        return alumnoRepository.getGestionarAlumnadoProgramacionAula(idEmpleado, ftomapos, idProgramacionAula, nivelCurricular)
                .stream().map(x -> modelMapper.map(x, AlumnosPorMateria.class)).collect(Collectors.toList());
    }



    @Override
    public List<AlumnosPorMateriaDTO> getAlumnosProgramacionAula(Long idProgramacionAula, Long idActividad){

        List<AlumnosPorMateria> alumnos = alumnoRepository.getAlumnosProgramacionAula(idProgramacionAula)
                .stream().map(x -> modelMapper.map(x, AlumnosPorMateria.class)).collect(Collectors.toList());

        List<AlumnosPorMateriaDTO> alumnosDto = alumnos.stream()
                .map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList());

        if (idActividad != -1) {
            for (AlumnosPorMateriaDTO alumno :alumnosDto) {
                EvaRelacionActividadAlumno relacionActividadAlumno = relacionActividadAlumnoRepository.findByActividadIdAndMatriculaId(idActividad, alumno.getIdMatricula());
                if (relacionActividadAlumno != null) {
                    Long numeroCalificaciones = valoracionCriterioActividadAlumnoRepository.countByRelacionActividadAlumnoId(relacionActividadAlumno.getId());
                    if (numeroCalificaciones != null && numeroCalificaciones > 0) {
                        alumno.setTieneValoraciones(true);
                    }
                }
            }
        }

        return alumnosDto;
    }
    
    public List<AlumnoValoracionActividadDTO> getAlumnosProgramacionAulaConvocatoria(Long idProgramacionAula, Long idConvCentroOmc, Long idUnidadProgramacion, Long idMateriaOmg, Long idUnidadCentro) {
        if (idUnidadProgramacion != null && idUnidadProgramacion > 0L) {
            return alumnoRepository.findAllAlumnosValoracionActividadByProgAulaConvocatoriaUnidad(idProgramacionAula, idConvCentroOmc, idUnidadProgramacion, idMateriaOmg, idUnidadCentro)
                .stream().map(x -> modelMapper.map(x, AlumnoValoracionActividadDTO.class)).collect(Collectors.toList());
        } else {
            return alumnoRepository.findAllAlumnosValoracionActividadByProgAulaConvocatoria(idProgramacionAula, idConvCentroOmc, idMateriaOmg, idUnidadCentro)
                    .stream().map(x -> modelMapper.map(x, AlumnoValoracionActividadDTO.class)).collect(Collectors.toList());
        }
    }
    
    public List<AlumnoValoracionActividadDTO> getAlumnosActividad(Long idActividad, Long idMateriaOmg, Long idUnidadCentro) {
    	return alumnoRepository.findAllAlumnosValoracionActividadByIdActividad(idActividad, idMateriaOmg, idUnidadCentro)
    			.stream().map(x -> modelMapper.map(x, AlumnoValoracionActividadDTO.class)).collect(Collectors.toList());
    }
    
    public List<AlumnoDTO> getAlumnosAulaVirtual(Long idAulaVirtual) {
    	List<AlumnoDTO> alumnosAula = alumnoRepository.findAllAlumnosByAulaVirtual(idAulaVirtual)
    			.stream().map(x -> modelMapper.map(x, AlumnoDTO.class)).collect(Collectors.toList());
    	alumnosAula.forEach(aula -> aula.setEnAulaVirtual(Boolean.TRUE));
    	return alumnosAula;
    }

	@Override
	public String getNumEscolar(Long idAlumno) {
		String numEscolar = null;
		Optional<EvaAlumno> alumno = alumnoRepository.findById(idAlumno);
		if(alumno.isPresent()) {
			numEscolar = alumno.get().getNumEscolar();
		}
		return numEscolar;
	}
	
	public List<AlumnosPorMateria> findAllAlumnosACNEESinProgramacionAula(List<Long> idsEmpleado, List<String> fechasTomaPosesion, Long idCentro, Integer anno, Long direccion) {
		return alumnoRepository.findAllAlumnosACNEESinProgramacionAula(idsEmpleado, fechasTomaPosesion, idCentro, anno, direccion)
				.stream().map(x -> modelMapper.map(x, AlumnosPorMateria.class)).collect(Collectors.toList());
	}
    
}