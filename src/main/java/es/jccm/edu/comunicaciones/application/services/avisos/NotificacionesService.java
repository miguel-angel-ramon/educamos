package es.jccm.edu.comunicaciones.application.services.avisos;



import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import es.jccm.edu.comunicaciones.adapter.out.repositories.avisos.AvisoRepository;
import es.jccm.edu.comunicaciones.application.domain.avisos.Notificacion;
import es.jccm.edu.comunicaciones.application.domain.avisos.projection.NotificacionProjection;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.INotificacionesService;

@Service
public class NotificacionesService implements INotificacionesService {
	
	private static final Logger LOG = LogManager.getLogger(NotificacionesService.class);

	@Autowired
	private AvisoRepository avisosRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Notificacion> getNotificaciones(Long idUsuario, Long codCentro, Long idEmpleado, Long Anno, String tipo, String text, String perfil, int page, int size) {
		
		Page<Notificacion> notificaciones;
		Pageable paging = PageRequest.of(page, size);
		try {		
			List<String> perfiles = Arrays.asList(perfil.split(","));
			
			switch (tipo){
	            case "aviso":{
	            	notificaciones = avisosRepository.getNotificaciones(idUsuario, codCentro, text, paging).map(aviso -> modelMapper.map(aviso, Notificacion.class));
	                break;
	            }
	            case "firma":{
	            	notificaciones = avisosRepository.getAllDocumentosBandeja(Anno, 0, idEmpleado, text, perfiles, paging).map(aviso -> modelMapper.map(aviso, Notificacion.class));
	                break;
	            }
	            default: {
	            	Page<NotificacionProjection> notP = avisosRepository.getAllNotifications(idUsuario, codCentro, Anno, 0, idEmpleado, text, perfiles, paging);
	            	
	            	notificaciones = notP.map(aviso -> modelMapper.map(aviso, Notificacion.class));
	            	}
			}
			return notificaciones;
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public Long getNoFirmadosCount(Long idUsuario, Long idEmpleado, Long Anno){
		try {
			return avisosRepository.getAllDocumentosCount(Anno, 0, idEmpleado);
		}
		catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
