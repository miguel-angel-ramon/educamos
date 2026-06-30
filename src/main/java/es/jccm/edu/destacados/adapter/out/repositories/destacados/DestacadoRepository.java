package es.jccm.edu.destacados.adapter.out.repositories.destacados;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.destacados.application.domain.destacados.Destacado;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuario;
import es.jccm.edu.destacados.application.domain.destacados.QDestacado;
import es.jccm.edu.destacados.application.domain.destacados.QDestacadoUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface DestacadoRepository extends AbstractRepository<Destacado, Long, QDestacado> {

    @Query(value = "SELECT * FROM DELPHOS_MODACC.ESC_DESTACADOS WHERE ID_DESTACADO = :idDestacado", nativeQuery = true)
    Destacado findByIdDestacado(@Param("idDestacado") Long idDestacado);


}
