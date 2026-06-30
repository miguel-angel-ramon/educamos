package es.jccm.edu.pdc.application.services.cuestionarios;


import es.jccm.edu.pdc.adapter.out.repositories.cuestionarios.CuestionarioRepository;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.*;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.CargoPDCProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.CuestionarioProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.CursoAcademicoProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.SugerenciasMejorasProjection;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.ICuestionarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CuestionarioService implements ICuestionarioService {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private CuestionarioRepository cuestionarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger LOG = LogManager.getLogger(CuestionarioService.class);

	public Cuestionario getCuestionarioDocenteByCodigo(Long xUsuarioComunica, Long codCentro, String codCuestionario) {
		
		String mensaje = String.format("Obteniendo el cuestionario con código = %s para el usuario = %s para el centro = %s", codCuestionario, xUsuarioComunica, codCentro);
		
		LOG.info(mensaje);
		
		try {
			CuestionarioProjection cues = cuestionarioRepository.getCuestionarioDocenteByCodigo(xUsuarioComunica, codCentro, codCuestionario);
			if(cues == null ) {
				return new Cuestionario();
			}
			Cuestionario cuestionarioOut = modelMapper.map(cues, Cuestionario.class);
			
			return cuestionarioOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}

	}
	public Cuestionario getCuestionarioCentro(Long idCentro, Long anio) {
		
		String mensaje = String.format("Obteniendo el cuestionario  el centro = %s y año = %s",idCentro,anio);
		
		LOG.info(mensaje);
		
		try {
			CuestionarioProjection c = cuestionarioRepository.getCuestionarioCentro(idCentro, anio);
			Cuestionario cuestionarioOut = modelMapper.map(c, Cuestionario.class);
			return cuestionarioOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}

	}

	@Override
	public List<Seccion> getSeccionesByIdCuestionario(Long idCuestionario) {
		String mensaje = String.format("Obteniendo las secciones del cuestionario con id = %s", idCuestionario);

		LOG.info(mensaje);

		try {

			List<Seccion> seccionesOut = cuestionarioRepository.getSeccionesByIdCuestionario(idCuestionario).stream().map(x -> modelMapper.map(x, Seccion.class)).collect(Collectors.toList());

			return seccionesOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}



	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}

	@Override
	public List<Pregunta> getPreguntasByIdSeccion(Long idSeccion) {
		String mensaje = String.format("Obteniendo las preguntas de la seccion con id = %s", idSeccion);

		LOG.info(mensaje);

		try {

			List<Pregunta> preguntasOut = cuestionarioRepository.getPreguntasByIdSeccion(idSeccion).stream().map(x -> modelMapper.map(x, Pregunta.class)).collect(Collectors.toList());

			for (Pregunta pregunta: preguntasOut) {
				List<Respuesta> respuestas = cuestionarioRepository.getRespuestasByIdPregunta(pregunta.getIdPregunta()).stream().map(x -> modelMapper.map(x, Respuesta.class)).collect(Collectors.toList());
				pregunta.setListaRespuesta(respuestas);
			}



			return preguntasOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@Override
	public List<Respuesta> getRespuestasByIdPregunta(Long idPregunta) {
		String mensaje = String.format("Obteniendo las respuestas de la pregunta con id = %s", idPregunta);

		LOG.info(mensaje);

		try {

			List<Respuesta> respuestasOut = cuestionarioRepository.getRespuestasByIdPregunta(idPregunta).stream().map(x -> modelMapper.map(x, Respuesta.class)).collect(Collectors.toList());

			return respuestasOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@Override
	@Transactional
	public void setRespuesta(Long idCuePubUsu, Long idPregunta, Long idRespuesta, String textoRespuesta) {
		String mensaje = String.format("Insertando la respuesta de la pregunta con id = %s", idPregunta);

		LOG.info(mensaje);

		try {

			List<RespuestaSeleccionada> respuestaSeleccionada = cuestionarioRepository.getRespuestaSeleccionada(idCuePubUsu, idPregunta).stream().map(x -> modelMapper.map(x, RespuestaSeleccionada.class)).collect(Collectors.toList());

			if (respuestaSeleccionada.isEmpty()) {
				cuestionarioRepository.setRespuestaUsuario(idCuePubUsu, idPregunta, idRespuesta, textoRespuesta);
			}else {
				cuestionarioRepository.editRespuestaUsuario(idCuePubUsu, idPregunta, idRespuesta, textoRespuesta);
			}

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public void finalizarCuestionario(Long idCuePubUsu,  Long idCuePub) {
		String mensaje = String.format("Llamando al procedimiento encargado de finalizar el cuestionario 'tlpq_finalizar'  = %s", idCuePubUsu);

		Session session = em.unwrap(Session.class);

		LOG.info(mensaje);
		CallableStatement callableStatement =  session.doReturningWork(
				
				new ReturningWork<CallableStatement>() {
			
				    @Override
					public CallableStatement execute(Connection connection) throws SQLException {
							     
					   CallableStatement function = connection.prepareCall("{CALL DELPHOS_SEGEDU.TLPQ_CUESTIONARIO.tlp_finalizar(?)}");
					   					 function.setLong(1, idCuePubUsu);
					   	                 function.execute();
								        return function;
								    }
				});

		try {
			
			callableStatement.getResultSet();
			generarInformeRespuestasCuestionario(idCuePub);
		} catch(SQLException  e) {
			e.printStackTrace();
		}
	}
	
	public void generarInformeRespuestasCuestionario(Long idCuePub) {
		String mensaje = String.format("Llamando al procedimiento encargado de generarInformeRespuestasCuestionario 'tlp_actValCenCuePub'  = %s", idCuePub);

		Session session = em.unwrap(Session.class);

		LOG.info(mensaje);
		CallableStatement callableStatement =  session.doReturningWork(
				
				new ReturningWork<CallableStatement>() {
			
				    @Override
					public CallableStatement execute(Connection connection) throws SQLException {
							     
					   CallableStatement function = connection.prepareCall("{CALL DELPHOS_SEGEDU.TLPQ_CUESTIONARIO.tlp_actValCenCuePub(?)}");
					   					 function.setLong(1, idCuePub);
					   	                 function.execute();					   	               
								        return function;
								    }
				});

		try {

			callableStatement.getResultSet();


		} catch(SQLException  e) {
			e.printStackTrace();
		}
	}

	public List<RespuestaSeleccionada> getRespuestaSeleccionada(Long idCuePubUsu, Long idPregunta) {
		String mensaje = String.format("Obteniendo la respuesta seleccionada por el id pregunta ", idPregunta);

		LOG.info(mensaje);

		try {

			List<RespuestaSeleccionada> respuestaSeleccionada = cuestionarioRepository.getRespuestaSeleccionada(idCuePubUsu, idPregunta).stream().map(x -> modelMapper.map(x, RespuestaSeleccionada.class)).collect(Collectors.toList());

			return respuestaSeleccionada;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<ValoresAmbitoCinco> getAreasAmbitoCinco(Long codCentro, String x_cuepub) {
		String mensaje = String.format("Obteniendo valores");

		LOG.info(mensaje);

		try {
			List<ValoresAmbitoCinco> informeOut = cuestionarioRepository.getAreasAmbitoCinco(codCentro,x_cuepub).stream().map(x -> modelMapper.map(x, ValoresAmbitoCinco.class)).collect(Collectors.toList());

			return  informeOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<ValoresAmbitoCinco> getSumAreasAmbitoCinco(Long codCentro, String x_cuepub) {
		String mensaje = String.format("Obteniendo valores");

		LOG.info(mensaje);

		try {
			List<ValoresAmbitoCinco> informeOut = cuestionarioRepository.getSumAreasAmbitoCinco(codCentro,x_cuepub).stream().map(x -> modelMapper.map(x, ValoresAmbitoCinco.class)).collect(Collectors.toList());

			return  informeOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<SugerenciasMejorasProjection> getSugerenciasMejorasCuestionarioDocente( Long xCentro,  Long xUsuario, Long xCuepub ) {
		String mensaje = String.format("Obteniendo valores");

		LOG.info(mensaje);

		try {
			List<SugerenciasMejorasProjection> sugMejOut = cuestionarioRepository.getSugMejDocente(xCentro, xUsuario, xCuepub );

			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public List<SugerenciasMejorasProjection> getSugerenciasMejorasCuestionarioCentro( Long xCentro, Long xCuepub ) {
		String mensaje = String.format("Obteniendo valores");

		LOG.info(mensaje);

		try {
			List<SugerenciasMejorasProjection> sugMejOut = cuestionarioRepository.getSugMejCentro(xCentro, xCuepub );

			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<HistoricoCuestionario> getHistoricoCuestionarioDocente(Long idCentro) {
		String mensaje = String.format("Obteniendo valores para el centro "+idCentro );
		LOG.info(mensaje);
		try {
			List<HistoricoCuestionario> sugMejOut = cuestionarioRepository.getHistoricoCuestionarioDocente(idCentro).stream().map(x -> modelMapper.map(x, HistoricoCuestionario.class)).collect(Collectors.toList());
			sugMejOut.stream().forEach(o->System.out.println(o.getAnio()+o.getIdCuestionario()));
			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<HistoricoCuestionario> getHistoricoCuestionarioDocenteIndividual(Long xUsuario, Long idCentro) {
		String mensaje = String.format("Obteniendo valores para el centro "+idCentro +" y el usuario "+xUsuario);
		LOG.info(mensaje);
		try {
			List<HistoricoCuestionario> sugMejOut = cuestionarioRepository.getHistoricoCuestionarioDocenteIndividual(xUsuario,idCentro).stream().map(x -> modelMapper.map(x, HistoricoCuestionario.class)).collect(Collectors.toList());
			sugMejOut.stream().forEach(o->System.out.println(o.getAnio()+o.getIdCuestionario()));
			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<HistoricoCuestionario> getHistoricoCuestionarioCentro(Long idCentro) {
		String mensaje = String.format("Obteniendo valores para el centro "+ idCentro);
		LOG.info(mensaje);
		
		try {
			List<HistoricoCuestionario> sugMejOut = cuestionarioRepository.getHistoricoCuestionarioCentro(idCentro).stream().map(x -> modelMapper.map(x, HistoricoCuestionario.class)).collect(Collectors.toList());
			sugMejOut.stream().forEach(o->System.out.println(o.getAnio()+o.getIdCuestionario()));
			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<MediaPorArea> getMediaPorAreaUsuario(String x_cuepubusu) {
		String mensaje = String.format("Obteniendo valores");
		LOG.info(mensaje);
		System.out.println("x_cuepubusu:    "+x_cuepubusu);
		try {
			List<MediaPorArea> sugMejOut = cuestionarioRepository.getMediasPorAreaUsuario(x_cuepubusu).stream().map(x -> modelMapper.map(x, MediaPorArea.class)).collect(Collectors.toList());

			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<MediaPorArea> getMediaPorCastillaLaMancha(String x_cuepub) {
		String mensaje = String.format("Obteniendo valores");
		LOG.info(mensaje);
		System.out.println("x_cuepub:    "+x_cuepub);
		try {
			List<MediaPorArea> sugMejOut = cuestionarioRepository.getMediaPorCastillaLaMancha(x_cuepub).stream().map(x -> modelMapper.map(x, MediaPorArea.class)).collect(Collectors.toList());

			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public MediaPorArea getMediaTotalUsuario(String x_cuepubusu) {
		String mensaje = String.format("Obteniendo valores");
		LOG.info(mensaje);
		System.out.println("x_cuepubusu:    "+x_cuepubusu);
		try {
			MediaPorArea sugMejOut = modelMapper.map(cuestionarioRepository.getMediaTotalUsuario(x_cuepubusu), MediaPorArea.class);

			return  sugMejOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Cuestionario getCuestionarioDocenteByXCUEPUB(Long xUsuarioComunica, String xcentro, String xcues) {
		String mensaje = String.format("Obteniendo el cuestionario con XCUEPUB = %s para el usuario = %s para el Xcentro = %s", xcues, xUsuarioComunica, xcentro);
		
		LOG.info(mensaje);
		
		try {
			
			Cuestionario cuestionarioOut = modelMapper.map(cuestionarioRepository.getCuestionarioDocenteByXCUEPUB(xUsuarioComunica,xcentro,xcues), Cuestionario.class);
			
			return cuestionarioOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}
	public Cuestionario getCuestionarioCentroByXCUEPUB(String xcentro, String xcues) {
		String mensaje = String.format("Obteniendo el cuestionario con XCUEPUB = %s  para el Xcentro = %s", xcues, xcentro);
		
		LOG.info(mensaje);
		
		try {
			
			Cuestionario cuestionarioOut = modelMapper.map(cuestionarioRepository.getCuestionarioCentroByXCUEPUB(xcentro,xcues), Cuestionario.class);
			
			return cuestionarioOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}

	@Override
	public Double getPorcentajeParticipantesDocentes(String x_centro, String x_cuepub) {
		String mensaje = String.format("Obteniendo el porcentaje de participantes con respecto al número de docentes del centro con XCUEPUB = %s y Xcentro = %s", x_cuepub, x_centro);
		
		LOG.info(mensaje);
		
		try {
			
			Double cuestionarioOut = cuestionarioRepository.getPorcentajeParticipantesDocentes(x_centro,x_cuepub);
			
			return cuestionarioOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}

	@Override
	public List<CargoPDC> getCargos(Long x_empleado , Long xCentro) {
		String mensaje = String.format("Obteniendo los cargos para el  x_empleado = %s", x_empleado);
		
		LOG.info(mensaje);
		
		try {
		
			List<CargoPDCProjection> cargos = cuestionarioRepository.getCargos(x_empleado,xCentro);
			if(cargos ==null) {
				return new ArrayList<>();
			}
					
			return cargos.stream().map(x -> modelMapper.map(x, CargoPDC.class)).collect(Collectors.toList());
			
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}
	@Override
	public CursoAcademicoProjection getCursoAcademino(Long anio) {
		String mensaje = String.format("Obteniendo la información sobre el curso %s", anio);
		
		LOG.info(mensaje);
		
		try {
		
			CursoAcademicoProjection curso = cuestionarioRepository.getCursoAcademino(anio);
			
			return curso;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}
	
	@Override
	public List<String> getPerfiles(Long x_empleado) {
		String mensaje = String.format("Obteniendo los cargos para el  x_empleado = %s", x_empleado);
		
		LOG.info(mensaje);
		
		try {
			List<String> perfiles = cuestionarioRepository.getPerfiles(x_empleado);
			if(perfiles == null) {
				return new ArrayList<>();
			}
			return perfiles;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}

}
