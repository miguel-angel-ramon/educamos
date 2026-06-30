package es.jccm.edu.alumnos.application.ports.in.notificacionesFalta;

import es.jccm.edu.alumnos.application.domain.notificacionesFalta.NotificacionFalta;

public interface INotificacionesFaltasService {

    Long countNotificacionFaltaByAsignatura(Long idUnidad, Long idMateria);
    
    NotificacionFalta getDetailNotification(Long idNotificacion);

}
