package es.jccm.edu.comunicacionesMovil.application.ports.in;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.firebase.messaging.FirebaseMessagingException;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesInvidualesDTO;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesMovilDTO;
import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionDetalles;

public interface INotificacionesMovilService {

    String enviarNotificacionesMasivas(NotificacionesMovilDTO notificacionesDTO) throws FirebaseMessagingException;

    String registrarToken (String token, Long idUsuComunica);

     String enviarNotificacionesIndividual(Long idUsuario, NotificacionesInvidualesDTO notificacionesInvidualesDTO) throws FirebaseMessagingException;
     
     List<NotificacionDetalles> getNotificaciones();
     
     Page<NotificacionDetalles> getNotificaciones(Long idUsuario,Pageable p);
}
