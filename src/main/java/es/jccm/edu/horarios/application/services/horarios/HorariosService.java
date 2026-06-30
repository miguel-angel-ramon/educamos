package es.jccm.edu.horarios.application.services.horarios;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.horarios.adapter.out.repositories.horarios.HorarioRepository;
import es.jccm.edu.horarios.application.domain.horarios.AtributosPlanificacionSemanal;
import es.jccm.edu.horarios.application.domain.horarios.AtributosTotalHoras;
import es.jccm.edu.horarios.application.domain.horarios.GrupoActividad;
import es.jccm.edu.horarios.application.domain.horarios.HorarioDireccion;
import es.jccm.edu.horarios.application.domain.horarios.HorarioGrupoActividadList;
import es.jccm.edu.horarios.application.domain.horarios.HorarioList;
import es.jccm.edu.horarios.application.domain.horarios.HorarioPersonalList;
import es.jccm.edu.horarios.application.domain.horarios.ReunionOrganoCentro;
import es.jccm.edu.horarios.application.domain.horarios.TotalHorasList;
import es.jccm.edu.horarios.application.domain.horarios.VisitasProgramadas;
import es.jccm.edu.horarios.application.domain.horarios.projection.GrupoActividadProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioGrupoActividadProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioPersonalProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.TotalHorasProjection;
import es.jccm.edu.horarios.application.ports.in.horarios.IHorariosService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HorariosService implements IHorariosService {

	private static final Logger LOG = LogManager.getLogger(HorariosService.class);

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<HorarioGrupoActividadList> getHorarios(Long idEmpleado, Integer anno, Long codCentro) {

		LOG.info("Obteniendo horarios del usuario con idEmpleado = {}", idEmpleado);

		List<HorarioGrupoActividadProjection> horarios = horarioRepository.findAllHorariosGrupoActividad(idEmpleado, anno, codCentro);

		return horarios.stream().map(horario -> modelMapper.map(horario, HorarioGrupoActividadList.class))
				.collect(Collectors.toList());
	}

	public List<HorarioList> getHorariosByDia(String idUsuario, Integer anno, Integer diaSemana) {

		LOG.info("Obteniendo horarios del usuario = {} para el día específicado", idUsuario);

		List<HorarioProjection> horarios = horarioRepository.findAllHorariosByDia(idUsuario, anno, diaSemana);

		return horarios.stream().map(horario -> modelMapper.map(horario, HorarioList.class))
				.collect(Collectors.toList());

	}

	public List<TotalHorasList> getTotalHoras(String idUsuario, Integer anno) {

		log.debug("Obteniendo los atributos necesarios para recuperar el total de horas del usuario = " + idUsuario);

		AtributosTotalHoras atributos = horarioRepository.findAtributosTotalHoras(idUsuario, anno).stream()
				.map(x -> modelMapper.map(x, AtributosTotalHoras.class)).collect(Collectors.toList()).get(0);

		log.debug("Obteniendo el total de horas del usuario = " + idUsuario);

		List<TotalHorasProjection> totalHoras = horarioRepository.findTotalHoras(anno, atributos.getIdEmpleado(),
				atributos.getFechaTomaPosesion(), atributos.getFechaInicio(), atributos.getFechaFin(),
				atributos.getFechaActual());

		return totalHoras.stream().map(x -> modelMapper.map(x, TotalHorasList.class)).collect(Collectors.toList());
	}

	public List<AtributosPlanificacionSemanal> getGrupoActividades(Long xEmpleado, String tomaPos) {

		log.debug("Obteniendo los atributos necesarios para recuperar los grupos de actividades = " + xEmpleado);
		List<AtributosPlanificacionSemanal> planificacionSemanal;
		try {
			
			planificacionSemanal = horarioRepository.getPlanificacionSemanal().stream()
					.map(x -> modelMapper.map(x, AtributosPlanificacionSemanal.class)).collect(Collectors.toList());

		} catch (Exception e) {
			LOG.error("error comunicación servicio rest que suministra mensajes recibidos", e);
			e.printStackTrace();
			throw e;
		}

		return planificacionSemanal;
	}

	public List<HorarioPersonalList> getHorarioPersonal(Long idUsuario, String fechaInicio, String fechaFin) {

		log.debug("Obteniendo los atributos necesarios para recuperar el horario personal = " + idUsuario);

		List<HorarioPersonalProjection> horariosPersonales = horarioRepository.getHorarioPersonal(idUsuario, fechaInicio, fechaFin);

		return horariosPersonales.stream().map(horarioPersonal -> modelMapper.map(horarioPersonal, HorarioPersonalList.class)).collect(Collectors.toList());

	}
	
	public List<HorarioDireccion> getHorarioDireccion(Long xEmpleado, String tomaPos) {

		log.debug("Obteniendo los atributos necesarios para recuperar el horario de direccion = " + xEmpleado);
		try {
			return horarioRepository.findActividadesDireccion(xEmpleado, tomaPos).stream()
					.map(x -> modelMapper.map(x, HorarioDireccion.class)).collect(Collectors.toList());


		} catch (Exception e) {
			LOG.error("Error al obtener el horario de direccion", e);
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<ReunionOrganoCentro> getReunionesOrganosCentro(Long codCentro, String anno) {

		log.debug("Obteniendo las reuniones de los órganos del centro = " + codCentro + " para el año = " + anno);
		try {
			return horarioRepository.getReunionesOrganosCentro(codCentro, anno).stream().map(x -> modelMapper.map(x, ReunionOrganoCentro.class)).collect(Collectors.toList());
		} catch (Exception e) {
			LOG.error("Error al obtener las reuniones del centro = {}", codCentro, e);
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<GrupoActividad> getGrupoActividadByEmpleadoAnno(Long idEmpleado, Integer anno, Long idCentro) {

		LOG.info("Obteniendo Grupo Actividad del usuario con idEmpleado = {}", idEmpleado);
		
		List<GrupoActividadProjection> gruposActividad;
		
		try {
			gruposActividad = horarioRepository.findAllGrupoActividadByEmpleadoAnno(idEmpleado, anno, idCentro);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;	
		}
	
		return gruposActividad.stream().map(x -> modelMapper.map(x, GrupoActividad.class))
				.collect(Collectors.toList());
	}
	
	public List<GrupoActividad> gruposActividadByEmpleadoAlumnoAnno(Long idEmpleado, Long idMatricula, Integer anno) {

		LOG.info("Obteniendo Grupo Actividad del usuario con idEmpleado = %d y Alumno con idMatricula = %d", idEmpleado, idMatricula);
		
		try {
			return horarioRepository.gruposActividadByEmpleadoAlumnoAnno(idEmpleado, idMatricula, anno).stream().map(x -> modelMapper.map(x, GrupoActividad.class)).collect(Collectors.toList());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;	
		}
	}
	
	public List<VisitasProgramadas> getVisitasProgramadas(Long idMatricula) {
		LOG.info("Obteniendo visitas programadas del alumno con idMatricula = {}", idMatricula);
		
		List<VisitasProgramadas> visitasProgramadas;
		
		try {
			visitasProgramadas = horarioRepository.getVisitasProgramadasPorMatricula(idMatricula).stream().map(x -> modelMapper.map(x, VisitasProgramadas.class)).collect(Collectors.toList());
		} catch(Exception e) {

			throw e;	
		}
	
		return visitasProgramadas;
	}

}
