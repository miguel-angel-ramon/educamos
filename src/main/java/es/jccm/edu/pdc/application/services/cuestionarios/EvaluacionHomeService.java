package es.jccm.edu.pdc.application.services.cuestionarios;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import es.jccm.edu.pdc.application.domain.evaluacion.projection.CentrosParaInspectoresProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionCompletoProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionHomeProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionVisionGlobalProjection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.pdc.adapter.out.repositories.evaluacion.EvaluacionHomeRepository;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.ObjetivoEspecificoEva;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaSeguimiento;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.IEvaluacionHomeService;

@Service
public class EvaluacionHomeService implements IEvaluacionHomeService {
	
	@Autowired
	private EvaluacionHomeRepository evaluacionHomeRepository;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger LOG = LogManager.getLogger(EvaluacionHomeService.class);
	
	public List<EvaluacionHome> getEvaluacionHomeAll() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
		
		LOG.info(mensaje);
		
		try {
			
			List<EvaluacionHome> evaluacionOut = evaluacionHomeRepository.getEvaluacionHomeAll().stream().map(x -> modelMapper.map(x, EvaluacionHome.class))
					.collect(Collectors.toList());
			
			return evaluacionOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}

	}
	public List<EvaluacionHome> getPorcentajes() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<EvaluacionHome> evaluacionOut = evaluacionHomeRepository.getPorcentajes().stream().map(x -> modelMapper.map(x, EvaluacionHome.class))
					.collect(Collectors.toList());
				
			return evaluacionOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public List<EvaluacionHome> getEstadoPorcentajes() {
			
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<EvaluacionHome> evaluacionOut = evaluacionHomeRepository.getEstadoPorcentajes().stream().map(x -> modelMapper.map(x, EvaluacionHome.class))
					.collect(Collectors.toList());
				
			return evaluacionOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public Double getMediaPorcentajes() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			Double evaluacionOut = evaluacionHomeRepository.getMediaPorcentajes();
				
			return evaluacionOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public List<EvaluacionHome> getFechasActualizacion() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);

		try {
			List<EvaluacionHomeProjection> evaluacionTmp = evaluacionHomeRepository.getFechasActualizacion();
			if (evaluacionTmp == null || evaluacionTmp.isEmpty() || evaluacionTmp.stream().anyMatch(Objects::isNull)) {
				return null;
			}

			List<EvaluacionHome> evaluacionOut = evaluacionTmp.stream()
					.map(x -> modelMapper.map(x, EvaluacionHome.class))
					.collect(Collectors.toList());

			return evaluacionOut;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	
	public List<AmbitoAsociado> getAmbitoAsociado() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<AmbitoAsociado> ambitoCompletoOut = evaluacionHomeRepository.getAmbitoAsociado().stream().map(x -> modelMapper.map(x, AmbitoAsociado.class))
					.collect(Collectors.toList());
				
			return ambitoCompletoOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public List<ObjetivoEspecificoEva> getObjetivoEspecificoEva() {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<ObjetivoEspecificoEva> objetivoEspecificoOut = evaluacionHomeRepository.getObjetivoEspecificoEva().stream().map(x -> modelMapper.map(x, ObjetivoEspecificoEva.class))
					.collect(Collectors.toList());
				
			return objetivoEspecificoOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public List<EvaluacionCompletoProjection> getEvaluacionCompleto(Long x_centro, Integer anno) {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<EvaluacionCompletoProjection> evaluacionCompletoOut = evaluacionHomeRepository.getEvaluacionCompleto(x_centro, anno);
				
			return evaluacionCompletoOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	public List<EvaluacionVisionGlobalProjection> getEvaluacionVisionGlobal(Long codCentro, Integer anno) {
		
		String mensaje = String.format("Obteniendo datos de evaluacion");
			
		LOG.info(mensaje);
			
		try {
				
			List<EvaluacionVisionGlobalProjection> evaluacionVisionGlobalOut = evaluacionHomeRepository.getEvaluacionVisionGlobal(codCentro, anno);
				
			return evaluacionVisionGlobalOut;
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	
	}
	
	@Transactional //TODO: Un modelo para poder pasarlo más fácil
	public void setLineasDeSeguimiento(Date fechaInicioEjecucion, Date fechaFinEjecucion, Integer porcentaje,
			String tareas, String valoracion, String dificultades_acciones, String comentarios, Long idLinAct,
			Long idUsuComunica) {
		String mensaje = String.format("Insertando un nuevo seguimiento");
		
		LOG.info(mensaje);
		
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicioFormat = null;
		String fechaFinFormat = null;
		String fechaActual = dt1.format(new Date());

		if(fechaInicioEjecucion != null) {
			fechaInicioFormat = dt1.format(fechaInicioEjecucion);
		}
		if (fechaFinEjecucion != null) {
			fechaFinFormat = dt1.format(fechaFinEjecucion);
		}
		try {
			evaluacionHomeRepository.setLineasDeSeguimiento(
					porcentaje,
					tareas,
					valoracion,
					dificultades_acciones,
					comentarios,
					idLinAct,
					idUsuComunica, 
					fechaActual
			);
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	



	@Transactional
	public void editLineasDeSeguimiento(Long idSeguiLinAct, List<LineaSeguimiento> lineasSeguimiento, Long idUsuComunica) {
		String mensaje = String.format("Editando las lineas de seguimiento %d", idSeguiLinAct);
		LOG.info(mensaje);

		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDeLaLineaSeguimiento = evaluacionHomeRepository.getFactualizaLinActById(idSeguiLinAct);
		String fechaLineaSeguiFormat = dt1.format(fechaDeLaLineaSeguimiento);
		try {
			for (LineaSeguimiento lineaSeguimiento : lineasSeguimiento) {
				Long idLinAct = lineaSeguimiento.getIdLinAct();
				String fechaInicioFormat = null;
				String fechaFinFormat = null;
				String fechaActual = dt1.format(new Date());
				Long idUsuario = idUsuComunica;
				Long idSegui = idSeguiLinAct;

				fechaInicioFormat = lineaSeguimiento.getFechaInicioEjecucion() != null ? dt1.format(lineaSeguimiento.getFechaInicioEjecucion()) : null;
				fechaFinFormat = lineaSeguimiento.getFechaFinEjecucion() != null ? dt1.format(lineaSeguimiento.getFechaFinEjecucion()) : null;


					if (fechaLineaSeguiFormat.equals(fechaActual)) {
						System.out.println("La fecha es igual a hoy");
						evaluacionHomeRepository.editLineasDeSeguimiento(
								idSegui,
								fechaInicioFormat,
								fechaFinFormat,
								lineaSeguimiento.getPorcentaje(),
								lineaSeguimiento.getTareas(),
								lineaSeguimiento.getValoracion(),
								lineaSeguimiento.getDificultades_acciones(),
								lineaSeguimiento.getComentarios(),
								idUsuario,
								fechaActual
						);
						break;
					} else {
						System.out.println("La fecha no es igual a hoy");
						evaluacionHomeRepository.setLineasDeSeguimiento(

								lineaSeguimiento.getPorcentaje(),
								lineaSeguimiento.getTareas(),
								lineaSeguimiento.getValoracion(),
								lineaSeguimiento.getDificultades_acciones(),
								lineaSeguimiento.getComentarios(),
								lineaSeguimiento.getIdLinAct(),
								idUsuario,
								fechaActual
						);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private List<CentrosParaInspectoresProjection> obtenerInformesConserjeria(Long tipoInforme) {
		if(tipoInforme == 0) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaConserjeria();
			return out;
		}
		else if(tipoInforme == 1) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaConserjeriaInformeEvaFinal();
			return out;
		}
		else if(tipoInforme == 2) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaConserjeriaInformeDocente();
			return out;
		}
		return new ArrayList<>();
	}
	
	private List<CentrosParaInspectoresProjection> obtenerInformesInspectores(Long tipoInforme, Long idEmpleado) {
		if(tipoInforme == 0) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaInspectores(idEmpleado);
			return out;
		}
		else if(tipoInforme == 1) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaInspectoresInformeEvaFinal(idEmpleado);
			return out;
		}
		else if(tipoInforme == 2) {
			List<CentrosParaInspectoresProjection> out = evaluacionHomeRepository.getCentrosParaInspectoresInformeDocente(idEmpleado);
			return out;
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<CentrosParaInspectoresProjection> getCentrosInspector(Long idEmpleado, Long tipoInforme, Long tipoEmpleado) {
		String mensaje = String.format("Obteniendo información para inspector");
		
		LOG.info(mensaje);
			
		try {
			
			if(tipoEmpleado == 0) {
				return obtenerInformesConserjeria(tipoInforme);
			}
			else {
				return obtenerInformesInspectores(tipoInforme,idEmpleado);
			}
				
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
}


