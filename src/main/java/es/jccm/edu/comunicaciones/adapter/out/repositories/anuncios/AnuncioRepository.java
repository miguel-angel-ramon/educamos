package es.jccm.edu.comunicaciones.adapter.out.repositories.anuncios;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.comunicaciones.application.domain.anuncios.Anuncio;
import es.jccm.edu.comunicaciones.application.domain.anuncios.QAnuncio;
import es.jccm.edu.comunicaciones.application.domain.anuncios.projection.AnuncioProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AnuncioRepository extends AbstractRepository<Anuncio, Long, QAnuncio> {

    @Modifying
    @Query(value = "SELECT nsa.x_notsectabanucen idAnuncio, tba.x_sectabanucen idSeccionAnuncio, nsa.f_publicacion fechaPublicacion, " +
            "nsa.f_fin_vigencia fechaFinVigencia, nsa.d_titulo titulo, nsa.t_cuerpo cuerpo, nsa.l_notenvweb noEnviadaWeb " +
            "FROM delphos_segedu.tlnotsectabanucen nsa, delphos_segedu.tltabanucen tba " +
            "WHERE tba.x_sectabanucen = nsa.x_sectabanucen " +
            "AND f_publicacion <= sysdate " +
            "AND f_fin_vigencia >= sysdate " +
            "AND tba.x_centro = :idCentro " +
            "ORDER BY f_publicacion DESC, f_fin_vigencia DESC, d_titulo", nativeQuery = true)
    List<AnuncioProjection> findAllAnuncios(@Param("idCentro") Long idCentro);

}
