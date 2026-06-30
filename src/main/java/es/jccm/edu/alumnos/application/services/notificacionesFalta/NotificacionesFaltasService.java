package es.jccm.edu.alumnos.application.services.notificacionesFalta;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.adapter.out.repository.notificacionesFalta.NotificacionFaltaRepository;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.NotificacionFaltaProjection;
import es.jccm.edu.alumnos.application.domain.notificacionesFalta.NotificacionFalta;
import es.jccm.edu.alumnos.application.ports.in.notificacionesFalta.INotificacionesFaltasService;

@Service
public class NotificacionesFaltasService implements INotificacionesFaltasService {

	@Autowired
	private NotificacionFaltaRepository notificacionFaltaRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public Long countNotificacionFaltaByAsignatura(Long idUnidad, Long idMateria) {
		return notificacionFaltaRepository.countByAsignatura(idUnidad, idMateria);
	}
	
	
	@Override
	public NotificacionFalta getDetailNotification(Long idNotificacion) {
		NotificacionFaltaProjection not_p = notificacionFaltaRepository.getNotification(idNotificacion);
		return modelMapper.map(not_p, NotificacionFalta.class);
	}

}
