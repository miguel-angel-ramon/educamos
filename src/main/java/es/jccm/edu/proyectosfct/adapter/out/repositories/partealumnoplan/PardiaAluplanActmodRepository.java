package es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan;

import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplanActmod;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.QPardiaAluplanActmod;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PardiaAluplanActmodRepository extends AbstractRepository<PardiaAluplanActmod, Long, QPardiaAluplanActmod> {

    @Query(value = "SELECT *  FROM FCT_PARDIA_ALUPLAN_ACTMOD WHERE ID_PARDIA_ALUPLAN = :idPardiaAluplan AND ID_ACTIVIDAD_MODULO = :idActividadModulo", nativeQuery = true)
    Optional<PardiaAluplanActmod> findByPardiaAluplanIdAndActividadModuloId(Long idPardiaAluplan, Long idActividadModulo);

    @Query(value = "SELECT * FROM FCT_PARDIA_ALUPLAN_ACTMOD WHERE ID_PARDIA_ALUPLAN = :idPardiaAluplan", nativeQuery = true)
    List<PardiaAluplanActmod> findByPardiaAluplanId(Long idPardiaAluplan);

    @Query(value = " SELECT * FROM FCT_PARDIA_ALUPLAN_ACTMOD apm " +
            "        WHERE apm.id_pardia_aluplan IN ( " +
            "           SELECT par.id_pardia_aluplan " +
            "           FROM fct_pardia_aluplan par " +
            "           WHERE par.id_convproy_alu = :idConvProyAlu " +
            "           AND par.f_dia = :fechaDia" +
            "        )", nativeQuery = true)
    List<PardiaAluplanActmod> findPartesByIdConvProyAluAndDia(Long idConvProyAlu, Date fechaDia);

}
