package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EstadoPlanLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.QEstadoPlanLOFP;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoPlanLOFPRepository extends AbstractRepository<EstadoPlanLOFP, Long, QEstadoPlanLOFP> {

    @Query(value = "select max(nu_orden) from FCT_VALIDACIONES_ALUPLAN where x_matricula = :xMatricula", nativeQuery = true)
    Integer findLastOrdenByMatricula(Long xMatricula);
    Optional<EstadoPlanLOFP> findFirstByMatriculaAndEstadoOrderByFechaRegistroDesc(Long idMatricula, String estado);
}
