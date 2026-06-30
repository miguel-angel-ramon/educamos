package es.jccm.edu.comunicaciones.adapter.out.repositories.novedades;

import es.jccm.edu.comunicaciones.application.domain.novedades.Novedad;
import es.jccm.edu.comunicaciones.application.domain.novedades.QNovedad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NovedadRepository extends AbstractRepository<Novedad, Long, QNovedad> {

    @Modifying
    @Query(value = "SELECT mal.* " +
            "FROM tlmensajessal mal, tlmensajesusu msu " +
            "WHERE mal.x_mensajesal = msu.x_mensajesal " +
            "AND msu.x_usuario = (SELECT x_usuario FROM tlusuarios WHERE usuario = :idUsuario) " +
            "ORDER BY mal.f_generacion DESC", nativeQuery = true)
    List<Novedad> findAllNovedades(@Param("idUsuario") String idUsuario);

}
