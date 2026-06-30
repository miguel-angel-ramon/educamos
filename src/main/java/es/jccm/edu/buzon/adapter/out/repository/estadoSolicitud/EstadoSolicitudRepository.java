package es.jccm.edu.buzon.adapter.out.repository.estadoSolicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.jccm.edu.buzon.application.domain.estadoSolicitud.EstadoSolicitud;

@Repository
public interface EstadoSolicitudRepository  extends JpaRepository<EstadoSolicitud, Long>{

	

}
