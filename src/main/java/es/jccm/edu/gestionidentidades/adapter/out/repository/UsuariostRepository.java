package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.gestionidentidades.application.domain.QUsuariot;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface UsuariostRepository extends AbstractRepository<Usuariot, Long, QUsuariot> {

	@Query(nativeQuery = true, value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T WHERE T_IDENTIFICACION = :identificacion")
    Usuariot findByIdentificacion(@Param("identificacion") String identificacion);

}