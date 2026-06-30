package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EvalProyAnexo;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.QEvalProyAnexo;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EvalProyAnexoRepository extends AbstractRepository<EvalProyAnexo, Long, QEvalProyAnexo> {

    @Query(value = "SELECT * FROM FCT_EVAL_PROY_ANEXO " +
            "WHERE X_MATRICULA = :xMatricula " +
            "AND X_EMPRESA = :xEmpresa", nativeQuery = true)
    Optional<EvalProyAnexo> findAnexoFirmado(Long xMatricula, Long xEmpresa);

    @Query("SELECT e FROM EvalProyAnexo e WHERE e.idEvaFirRodal = :idEvaFirRodal")
    Optional<EvalProyAnexo> findByIdEvaFirRodal(String idEvaFirRodal);
}
