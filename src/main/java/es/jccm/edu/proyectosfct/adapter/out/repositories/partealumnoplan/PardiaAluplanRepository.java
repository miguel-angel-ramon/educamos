package es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan;

import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.QPardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoActividadesParteDiaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PardiaAluplanRepository extends AbstractRepository<PardiaAluplan, Long, QPardiaAluplan> {

    @Query(value = "SELECT * FROM FCT_PARDIA_ALUPLAN WHERE ID_CONVPROY_ALU = :idConvProyAlu", nativeQuery = true)
    List<PardiaAluplan> findPartesByIdConvProyAlu(Long idConvProyAlu);

    @Query(value = "SELECT * FROM FCT_PARDIA_ALUPLAN WHERE ID_CONVPROY_ALU = :idConvProyAlu AND F_DIA = :fechaDia", nativeQuery = true)
    PardiaAluplan findByIdConvProyAluAndDia(Long idConvProyAlu, String fechaDia);

    @Query(value = "SELECT  "
    +"        COUNT(paam.ID_PARDIA_ALUPLAN_ACTMOD) AS numeroActividades,    "
    //+"        COALESCE(SUM(pda.NU_HORAS), 0) AS totalHoras, "
    +"        NVL(pda.NU_HORAS,0) AS totalHoras, "
    +"        CASE WHEN COUNT(paam.ID_PARDIA_ALUPLAN_ACTMOD) > 0 THEN 1 ELSE 0 END AS actividadesRelacionadasCheck,"
    +"        max(pda.f_actualiza) AS ultimaActualizacion   "
    +"    FROM      "
    +"        FCT_PARDIA_ALUPLAN pda    "
    +"    LEFT JOIN     "
    +"        FCT_PARDIA_ALUPLAN_ACTMOD paam ON pda.ID_PARDIA_ALUPLAN = paam.ID_PARDIA_ALUPLAN  "
    +"    WHERE     "
    +"        pda.ID_CONVPROY_ALU = :idConvProyAlu  "
    +"        AND pda.F_DIA = TO_DATE(:fecha, 'DD-MM-YYYY') "
    +"    GROUP BY  "
    +"        pda.ID_PARDIA_ALUPLAN, pda.NU_HORAS   ", nativeQuery = true)
    List<InfoActividadesParteDiaProjection> findActividadesPorConvProyYFecha(Long idConvProyAlu, String fecha);

    @Query(value = "SELECT COUNT(*) FROM FCT_PARDIA_ALUPLAN WHERE ID_PARSEM_ALUPLAN = :idParsemAluplan", nativeQuery = true)
    int countPartesDiariosBySemana(Long idParsemAluplan);
}
