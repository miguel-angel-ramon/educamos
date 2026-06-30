	package es.jccm.edu.alumnos.application.services.faltasAsistenciaAlumno;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import es.jccm.edu.alumnos.adapter.out.repository.faltasAsistenciaAlumno.FaltaAsistenciaAlumnoRepository;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.ConvAlumnCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAlumnoCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAsistenciaAlumno;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.MotivoJustificacion;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.ConvAlumnCentroProjection;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.FaltaAlumnoCentroProjection;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.MotivoJustificacionProjection;
import es.jccm.edu.alumnos.application.ports.in.faltasAsistenciaAlumno.IFaltaAsistenciaAlumnoService;
import es.jccm.edu.shared.application.ports.out.resttemplate.comunica.ClientComunicaRestTemplatePortOut;

@Service
public class FaltaAsistenciaAlumnoService implements IFaltaAsistenciaAlumnoService {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private FaltaAsistenciaAlumnoRepository faltaAsistenciaRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	private static final Logger LOG = LogManager.getLogger(FaltaAsistenciaAlumnoService.class);

	@Autowired
	@Qualifier("clientComunicaRestTemplate")
	private ClientComunicaRestTemplatePortOut clientComunicaRestTemplatePortOut;

	@Value("${comunica.api.path}")
	private String comunicaApiUrl;

	@Override
	public void setFaltaAsistenciaAlumno(String idUsuario, Long idCentro,
			List<FaltaAsistenciaAlumno> faltasAsistenciaAlumno) {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.comunicaApiUrl + "faltaAsistencia/crearFaltas/");

		if (idUsuario != null && idCentro != null) {
			builder.pathSegment(idUsuario);
			builder.pathSegment(Long.toString(idCentro));
		}

		try {
			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), faltasAsistenciaAlumno, FaltaAsistenciaAlumno.class);

		} catch (RestClientException e) {
			LOG.error("error comunicación servicio rest que setea las faltas de asistencia de los alumnos", e);
			e.printStackTrace();
			throw e;
		}

	}
	
	
	
	@Override
	public List<ConvAlumnCentro> getConvAlumn (Long idMatricula){
		List<ConvAlumnCentroProjection> Convlist = faltaAsistenciaRepository.getConvCentro(idMatricula);
		return Convlist.stream().map(x -> modelMapper.map(x, ConvAlumnCentro.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<FaltaAlumnoCentro> getFaltasAlumnoByFilter(Long idMatricula, String fecIni, String fecFin, String type, String idGroup){
		
		try {
			List<FaltaAlumnoCentroProjection> listFaltas;
			
			if(idGroup.equals("-1")) {
				listFaltas = faltaAsistenciaRepository.getFaltasByAlumnSinGruposAct(idMatricula, fecIni, fecFin, type);
			} else {
				Long[] idsGruposAct = (Long[]) ConvertUtils.convert(idGroup.split(","), Long[].class);
				
				listFaltas = faltaAsistenciaRepository.getFaltasByAlumn(idMatricula, fecIni, fecFin, type, idsGruposAct);
			}
			
			List<FaltaAlumnoCentro>faltasOut = listFaltas.stream().map(falta -> modelMapper.map(falta, FaltaAlumnoCentro.class)).collect(Collectors.toList());
					
			return faltasOut;
		} catch (Exception e) {
            e.printStackTrace();
            throw e;
       }
	}
	
	@Override
	public void setJustificacionFaltaAlumno(Long idFaltaAsistencia, Long idMotivo, String observacion) throws SQLException {

		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement = null;
		try {
			
			callableStatement =  session.doReturningWork(
			
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{call ? := DELPHOS_SEGEDU.tlpq_actJusfalasialu.Insertar(?,?,?)}");
				   					 function.setLong(2, idFaltaAsistencia);
						             function.setLong(3, idMotivo);
						             function.setString(4, observacion);
							         function.registerOutParameter(1, Types.INTEGER);
				   	                 function.execute();
				   	               
							        return function;
							    }
			});

    	   Integer resultado = callableStatement.getInt(1);
    	   
    	   String mensaje = String.format("Resultado de insertar la justificación a la falta de asistencia con idFaltaAsistencia: %d es %d", 
					idFaltaAsistencia, resultado);
			LOG.info(mensaje);
           } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	       } finally {
	    	   if (callableStatement != null) {
						callableStatement.close();	
					}
	       }
		
	}
	
	@Override
	public void updateJustificacionFaltaAlumno(Long idJustificacion, Long idMotivo, String observacion) throws SQLException {

		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement = null;
		try {
			
			callableStatement =  session.doReturningWork(
			
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{call ? := DELPHOS_SEGEDU.tlpq_actJusfalasialu.Modificar(?,?,?)}");
				   					 function.setLong(2, idJustificacion);
						             function.setLong(3, idMotivo);
						             function.setString(4, observacion);
							         function.registerOutParameter(1, Types.INTEGER);
				   	                 function.execute();
				   	               
							        return function;
							    }
			});

    	   Integer resultado = callableStatement.getInt(1);
    	   
    	   String mensaje = String.format("Resultado de modificar la justificación de la falta de asistencia con idJustificacion: %d es %d", 
    			   idJustificacion, resultado);
			LOG.info(mensaje);
			
           } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	       } finally {
	    	   if (callableStatement != null) {
						callableStatement.close();	
					}
	       }
		
	}
	
	@Override
	public void deleteJustificacionFaltaAlumno(Long idFaltaAsistencia) throws SQLException {

		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement = null;
		try {
			
			callableStatement =  session.doReturningWork(
			
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{call ? := DELPHOS_SEGEDU.tlpq_actJusfalasialu.Borrar_Justificacion_Falta(?)}");
				   					 function.setLong(2, idFaltaAsistencia);
							         function.registerOutParameter(1, Types.INTEGER);
				   	                 function.execute();
				   	               
							        return function;
							    }
			});

    	   Integer resultado = callableStatement.getInt(1);
    	   
    	   String mensaje = String.format("Resultado de boorar la justificación de la falta de asistencia con idFaltaAsistencia: %d es %d", 
    			   idFaltaAsistencia, resultado);
			LOG.info(mensaje);
			
           } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	       } finally {
	    	   if (callableStatement != null) {
						callableStatement.close();	
					}
	       }
		
	}



	@Override
	public List<MotivoJustificacion> getMotivosJustificacionFaltaAlumno() {
		
		List<MotivoJustificacionProjection> motivosList = faltaAsistenciaRepository.getMotivosJustificacionFaltaAlumno();
		
		return motivosList.stream().map(x -> modelMapper.map(x, MotivoJustificacion.class)).collect(Collectors.toList());
	}
	
    @Transactional
    public void borrarNotificacionCompleta(Long idNotificacion) {
        try {
            faltaAsistenciaRepository.deleteTramosNotificacion(idNotificacion);

            faltaAsistenciaRepository.deleteNotificacion(idNotificacion);
            
            faltaAsistenciaRepository.deleteRelacionNotificacion(idNotificacion);

            LOG.info("Notificación y tramos borrados correctamente para idNotificacion: {}", idNotificacion);
        } catch (Exception e) {
            LOG.error("Error borrando notificación y tramos para idNotificacion: {}", idNotificacion, e);
            throw e;
        }
    }

}
