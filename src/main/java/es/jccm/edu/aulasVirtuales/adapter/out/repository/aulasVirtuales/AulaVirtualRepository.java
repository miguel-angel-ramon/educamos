package es.jccm.edu.aulasVirtuales.adapter.out.repository.aulasVirtuales;

import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.AulaVirtual;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.QAulaVirtual;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.projection.AulaVirtualProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaVirtualRepository extends AbstractRepository<AulaVirtual, Long, QAulaVirtual> {

	@Query(value = "SELECT TLF_ANNOACTUAL FROM DUAL", nativeQuery = true)
    String getAnnoAcademicoActual();
	
    @Query(value = "SELECT AUL.X_AULA idAula, AUL.D_AULA aula, (SELECT T.D_VALOR  FROM DELPHOS_SEGEDU.TLAULASINFO T WHERE T.X_AULA = AUL.X_AULA AND T.C_INFO = 'MATERIA_NOMBRE') AS nombreMateria, (SELECT T.D_VALOR  FROM DELPHOS_SEGEDU.TLAULASINFO T WHERE T.X_AULA = AUL.X_AULA AND T.C_INFO = 'CICLO') AS ciclo, AUL.ID_PLATAFORMA idPlataforma, INF.D_VALOR idMoodle " +
            "FROM DELPHOS_SEGEDU.TLAULAS AUL, DELPHOS_SEGEDU.TLAULASUSUARIOS USU, DELPHOS_SEGEDU.TLAULASINFO INF " +
            "WHERE AUL.C_ANNO = :anno " +
            "AND AUL.X_AULA = USU.X_AULA " +
            "AND AUL.X_AULA = INF.X_AULA " +
            "AND USU.X_EMPLEADO = :idEmpleado " +
            "AND INF.C_INFO = 'MOODLE_ID'", nativeQuery = true)
    List<AulaVirtualProjection> findAulasVirtualesByEmpleado(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);
    
    @Query(value = "SELECT AUL.X_AULA idAula, AUL.D_AULA aula, INF.D_VALOR, SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1) idPlataforma, PARGEN.T_VALPAR tokenPlataforma, "
    		+ "AUL.X_MATERIAOMG idMateriaOMG, AUL.X_OFERTAMATRIG idOfertaMatrig "
    		+ "FROM DELPHOS_SEGEDU.TLAULAS AUL, "
    		+ "DELPHOS_SEGEDU.TLAULASUSUARIOS AUSU, "
    		+ "DELPHOS_SEGEDU.TLPARGEN PARGEN, "
    		+ "DELPHOS_SEGEDU.TLAULASINFO INF "
    		+ "WHERE AUL.C_ANNO = :anno "
    		+ "AND INF.X_AULA = AUSU.X_AULA "
    		+ "AND INF.C_INFO = 'MOODLE_ID' "
    		+ "AND AUL.X_AULA = AUSU.X_AULA "
    		+ "AND PARGEN.T_NOMPAR LIKE TRIM('URL_SW_MOODLE_API_TOKEN_'||SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1)||'_'||TLF_ANNOACTUAL) "
    		+ "AND aul.L_ACTIVA = 'S' "
    		+ "AND AUSU.X_EMPLEADO = :idEmpleado "
    		+ "ORDER BY AUL.D_AULA", nativeQuery = true)
    List<AulaVirtualProjection> findAulasVirtualesByEmpleadoAnno(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);

}
