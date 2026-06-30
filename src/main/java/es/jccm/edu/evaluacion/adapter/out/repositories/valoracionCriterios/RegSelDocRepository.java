package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QRegSelDoc;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.RegSelDoc;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RegSelDocRepository extends AbstractRepository<RegSelDoc, Long, QRegSelDoc> {

    @Query(value = "SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
    Long getIdEjecucionRegSelDoc();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TLREGSELDOC(x_identificador, n_clave1) VALUES (:idEjecucion, :idAlumno)", nativeQuery = true)
    void insertRegSelDocAlumno(
            @Param("idEjecucion") Long idEjecucion,
            @Param("idAlumno") Long idAlumno);
}
