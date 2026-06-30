package es.jccm.edu.comunicacionesMovil.application.services;

import com.google.firebase.messaging.*;

import es.jccm.edu.comunicaciones.adapter.in.rest.anuncios.model.AnuncioListDto;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesInvidualesDTO;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesMovilDTO;
import es.jccm.edu.comunicacionesMovil.adapter.out.repository.NotificacionesDetallesRepository;
import es.jccm.edu.comunicacionesMovil.adapter.out.repository.NotificacionesMovilRepository;
import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionDetalles;
import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionMovil;
import es.jccm.edu.comunicacionesMovil.application.ports.in.INotificacionesMovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionesMovilService implements INotificacionesMovilService {

	/*@Autowired
	private FirebaseMessaging firebaseMessaging;*/

	@Autowired
	private NotificacionesMovilRepository notificacionesMovilRepository;
	@Autowired
	private NotificacionesDetallesRepository notificacionesDetallesRepository;

	/**
	 * 
	 * Método para enviar notificaciones masivas a dispositivos móviles mediante
	 * Firebase Cloud Messaging.
	 * 
	 * @param notificacionesDTO objeto DTO que contiene el título y mensaje de la
	 *                          notificación.
	 * 
	 * @return un mensaje de éxito indicando que las notificaciones fueron enviadas
	 *         exitosamente.
	 * 
	 * @throws FirebaseMessagingException si ocurre un error durante el envío de las
	 *                                    notificaciones.
	 */
	@Override
	public String enviarNotificacionesMasivas(NotificacionesMovilDTO notificacionesDTO)
			throws FirebaseMessagingException {

		// Se persiste las notificaciones enviadas a todos los dispositivos.
		// NotificacionesDetalles contiene parte del NotificacionesMovilDTO
		NotificacionDetalles notificacionDetalles = new NotificacionDetalles();
		notificacionDetalles.setTituloNotificacion(notificacionesDTO.getTitulo());
		notificacionDetalles.setDescripcionNotificacion(notificacionesDTO.getMensaje());
		notificacionDetalles.setFotoNotificacion(notificacionesDTO.getFoto());
		notificacionDetalles.setFechaCreacion(new Date());
		notificacionDetalles.setFechaActualizacion(new Date());
		notificacionDetalles.setUsuarioCreacion(1L);
		notificacionDetalles.setUsuarioActualizacion(1L);
		notificacionDetalles.setIdUser(0L);
		notificacionesDetallesRepository.save(notificacionDetalles);

		// Se extraen todos los tokens registrados en BBDD y se envia lo que viene del
		// controller.
		List<NotificacionMovil> notificacionMovil = notificacionesMovilRepository.findAll();
		List<String> tokens = notificacionMovil.stream().map(NotificacionMovil::getToken).collect(Collectors.toList());
		MulticastMessage firebaseMessage = MulticastMessage.builder().setNotification(Notification.builder()
				.setTitle(notificacionesDTO.getTitulo()).setBody(notificacionesDTO.getMensaje()).build())
				.addAllTokens(tokens).build();
		try {
			BatchResponse batchResponse = FirebaseMessaging.getInstance().sendMulticast(firebaseMessage);
			List<SendResponse> responses = batchResponse.getResponses();
			System.out.println("Successfully sent messages: " + responses);
			return "Notificaciones enviadas exitosamente: " + responses;
		} catch (FirebaseMessagingException e) {
			System.err.println("Failed to send messages: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * Método para registrar token en BBDD para uso con Firebase Cloud Messaging.
	 * 
	 * @param String que contiene el token.
	 * 
	 * @return un mensaje de éxito indicando que el token se ha registrado
	 *         exitosamente.
	 * 
	 * @throws FirebaseMessagingException si ocurre un error durante el registro del
	 *                                    token.
	 */
	@Override
	public String registrarToken(String token, Long idUsuComunica) {
		/*
		 * NotificacionMovil notificacionMovil = new NotificacionMovil();
		 * notificacionMovil.setToken(token);
		 * notificacionMovil.setIdUser(idUsuComunica); // Auditoria
		 * notificacionMovil.setFechaCreacion(new Date());
		 * notificacionMovil.setFechaActualizacion(new Date());
		 * notificacionMovil.setUsuarioCreacion(idUsuComunica);
		 * notificacionMovil.setUsuarioActualizacion(idUsuComunica);
		 * notificacionesMovilRepository.save(notificacionMovil); return
		 * "Token registrado exitosamente";
		 */

		// Buscar si el token ya existe en la base de datos
		NotificacionMovil notificacionMovilExistente = notificacionesMovilRepository.findByToken(token);

		if (notificacionMovilExistente != null) {
			// Si el token ya está asociado con otro usuario
			if (!notificacionMovilExistente.getIdUser().equals(idUsuComunica)) {
				// Buscar el registro antiguo del token asociado al usuario actual

				List<NotificacionMovil> notificacionMoviles = notificacionesMovilRepository.findByIdUser(idUsuComunica);
				NotificacionMovil notificacionMovilAntigua = notificacionMoviles.stream()
		                .min(Comparator.comparing(NotificacionMovil::getFechaCreacion))
		                .orElse(null);
				 
				// Eliminar el registro antiguo del token si existe
				if (notificacionMovilAntigua != null) {
					notificacionesMovilRepository.delete(notificacionMovilAntigua);
				}

				// Actualizar el registro existente con el nuevo token y la información del
				// usuario
				notificacionMovilExistente.setIdUser(idUsuComunica);
				notificacionMovilExistente.setFechaActualizacion(new Date());
				notificacionMovilExistente.setUsuarioActualizacion(idUsuComunica);
				notificacionesMovilRepository.save(notificacionMovilExistente);
				return "Token actualizado y antiguo registro eliminado";
			} else {
				// Si el token ya está asociado con el mismo usuario, actualizar la información
				notificacionMovilExistente.setFechaActualizacion(new Date());
				notificacionMovilExistente.setUsuarioActualizacion(idUsuComunica);
				notificacionesMovilRepository.save(notificacionMovilExistente);
				return "Token actualizado exitosamente";
			}
		} else {
			// Registrar el nuevo token para el usuario
			NotificacionMovil notificacionMovil = new NotificacionMovil();
			notificacionMovil.setToken(token);
			notificacionMovil.setIdUser(idUsuComunica);
			// Auditoria
			notificacionMovil.setFechaCreacion(new Date());
			notificacionMovil.setFechaActualizacion(new Date());
			notificacionMovil.setUsuarioCreacion(idUsuComunica);
			notificacionMovil.setUsuarioActualizacion(idUsuComunica);
			notificacionesMovilRepository.save(notificacionMovil);
			return "Token registrado exitosamente";
		}

	}

	@Override
	public String enviarNotificacionesIndividual(Long idUsuario,
			NotificacionesInvidualesDTO notificacionesInvidualesDTO) throws FirebaseMessagingException {
		NotificacionDetalles notificacionDetalles = new NotificacionDetalles();
		notificacionDetalles.setTituloNotificacion(notificacionesInvidualesDTO.getTitulo());
		notificacionDetalles.setDescripcionNotificacion(notificacionesInvidualesDTO.getMensaje().replaceAll("<[^>]*>", ""));
		notificacionDetalles.setFotoNotificacion(notificacionesInvidualesDTO.getFoto());
		notificacionDetalles.setFechaCreacion(new Date());
		notificacionDetalles.setFechaActualizacion(new Date());
		notificacionDetalles.setUsuarioCreacion(idUsuario);
		notificacionDetalles.setUsuarioActualizacion(idUsuario);
		notificacionDetalles.setIdUser(notificacionesInvidualesDTO.getXUsuario());
		notificacionesDetallesRepository.save(notificacionDetalles);

		List<NotificacionMovil> usuarioNotificacion = notificacionesMovilRepository
				.findByIdUser(notificacionesInvidualesDTO.getXUsuario());
		// añadir al controller el metodo con el notificacionesindividualdto
		
		if (usuarioNotificacion == null || usuarioNotificacion.isEmpty()) {
		    return "false";
		}
		List<String> tokens = new ArrayList<String>();
		for (NotificacionMovil user : usuarioNotificacion) {
			//System.out.println(user.getIdUser() +" - "+ user.getToken());
			tokens.add(user.getToken());
		}
		
		if(!tokens.isEmpty()) {
			/*MulticastMessage firebaseMessage = MulticastMessage.builder()
					.setNotification(Notification.builder().setTitle(notificacionesInvidualesDTO.getTitulo())
							.setBody(notificacionesInvidualesDTO.getMensaje().replaceAll("<[^>]*>", "")).build())
					.addAllTokens(tokens).build();*/
			int successCount = 0;         
			int failureCount = 0;         
			for (String token : tokens) {             
				Message message = Message.builder()                     
						.setNotification(Notification.builder()                        
								.setTitle(notificacionesInvidualesDTO.getTitulo())                        
								.setBody(notificacionesInvidualesDTO.getMensaje().replaceAll("<[^>]*>", ""))                         
								.build())                    
						.setToken(token)                    
						.build();             
				try { 
					FirebaseMessaging.getInstance().send(message); successCount++; 
				} catch (FirebaseMessagingException e) { 
					failureCount++; 					
					System.err.println("Error al enviar notificación al token: " + token + " - " + e.getMessage()); 
					} 
				} 
			return "Notificaciones enviadas: " + successCount + ", fallidas: " + failureCount;
		
			/*try {
				BatchResponse batchResponse = FirebaseMessaging.getInstance().sendMulticast(firebaseMessage);
				List<SendResponse> responses = batchResponse.getResponses();
				//System.out.println("Successfully sent messages: " + responses);
				return "Notificaciones enviadas exitosamente: " + responses;
			} catch (FirebaseMessagingException e) {
				//System.err.println("Failed to send messages: " + e.getMessage());
				throw e;
			}*/
		} else {
			return "false";
		}

		
	}

	@Override
	public List<NotificacionDetalles> getNotificaciones() {
		List<NotificacionDetalles> notificacionMovil = notificacionesDetallesRepository.findAllPublic();
		return notificacionMovil;
	}

	@Override
	public Page<NotificacionDetalles> getNotificaciones(Long idUsuario, Pageable p) {
		Page<NotificacionDetalles> notificacionMovil = notificacionesDetallesRepository.getNotificaciones(idUsuario, p);
		return notificacionMovil;
	}

}
