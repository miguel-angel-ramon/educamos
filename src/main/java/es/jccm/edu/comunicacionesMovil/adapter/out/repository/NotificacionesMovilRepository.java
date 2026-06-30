package es.jccm.edu.comunicacionesMovil.adapter.out.repository;

import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesMovilDTO;
import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionMovil;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesMovilRepository extends JpaRepository<NotificacionMovil, Long> {
	

    @Query(value = "SELECT * FROM DELPHOS.NOTIF_MOVIL WHERE CD_TOKEN LIKE '%:idToken%'", nativeQuery = true)
    NotificacionMovil findByToken( String idToken);
    
    @Query(value = "SELECT * FROM DELPHOS.NOTIF_MOVIL WHERE ID_USER = :idUser", nativeQuery = true)
    List<NotificacionMovil> findByIdUser( Long idUser);
    
  
}