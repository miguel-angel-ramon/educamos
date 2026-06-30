package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EstadoPlanLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.QEstadoPlanLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.EstadoValidacionProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidacionAlumnoPlanRepository extends AbstractRepository<EstadoPlanLOFP, Long, QEstadoPlanLOFP> {

    @Query(value = " SELECT fvp.CD_VISTA AS usuario,       "
    		+ "                    t.APELLIDO1 || ' ' || t.APELLIDO2 || ', ' || t.NOMBRE as nombre,    "
    		+ "                    TO_CHAR(fvp.F_REGISTRO, 'DD/MM/YYYY HH24:MI:SS') AS fecha,       "
    		+ "                    fvp.DS_ESTADO AS estado, "
    		+ "                    FVP.NU_ORDEN "
    		+ "             FROM fct_validaciones_aluplan fvp        "
    		+ "             JOIN TLUSUARIOS u ON u.X_USUARIO = fvp.X_USUARIO     "
    		+ "             JOIN TLEMPLEADOS t ON t.X_EMPLEADO = u.X_EMPLEADO    "
    		+ "             WHERE fvp.X_MATRICULA = :matricula   "
    		+ "UNION  "
    		+ "SELECT fvp.CD_VISTA AS usuario,       "
    		+ "                    t.APELLIDO1 || ' ' || t.APELLIDO2 || ', ' || t.NOMBRE as nombre,    "
    		+ "                    TO_CHAR(fvp.F_REGISTRO, 'DD/MM/YYYY HH24:MI:SS') AS fecha,       "
    		+ "                    fvp.DS_ESTADO AS estado, "
    		+ "                    FVP.NU_ORDEN "
    		+ "             FROM fct_validaciones_aluplan fvp        "
    		+ "             JOIN delphos_segedu.TLUSUARIOS u ON u.X_USUARIO = fvp.X_USUARIO     "
    		+ "             JOIN delphos_segedu.TLEMPLEADOS t ON t.X_EMPLEADO = u.X_EMPLEADO    "
    		+ "             WHERE fvp.X_MATRICULA = :matricula  "
    		+ "ORDER BY NU_ORDEN ASC ", nativeQuery = true)
    List<EstadoValidacionProjection> findValidacionesByMatricula(Long matricula);
}
