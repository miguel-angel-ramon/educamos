package es.jccm.edu.comunicacionesMovil.adapter.out.repository;


import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionDetalles;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesDetallesRepository extends JpaRepository<NotificacionDetalles, Long> {
	
	@Query(value = "SELECT * FROM DELPHOS.NOTIF_FIREBASE WHERE ID_USUARIO = 0", nativeQuery = true)
    List<NotificacionDetalles> findAllPublic();
	
	@Query(value = "SELECT * FROM DELPHOS.NOTIF_FIREBASE WHERE ID_USUARIO = :idUsuario ",
			countQuery = "SELECT count(*) FROM DELPHOS.NOTIF_FIREBASE",  nativeQuery = true)
    Page<NotificacionDetalles> getNotificaciones(@Param("idUsuario") Long idUsuario, Pageable pageable);
}