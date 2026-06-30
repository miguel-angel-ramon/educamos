package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection.ListadoResultadosAsociadosPlanProjection;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection.ListadoResultadosAsociadosPlanRelacionadosProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.QResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultadosAsociadosPlanRepository extends AbstractRepository<ResultadosAsociadosPlan, Long, QResultadosAsociadosPlan> {
    @Query(value = "select com.x_comesp x_comesp," +
            "           com.t_abrev as abreviatura, " +
            "           com.d_comesp descripcion," +
            "           NVL((select LG_CENTRO from FCT_RESULTADOSA_MODULOS res where res.id_modulo_curso = modu.id_modulo_curso and res.x_comesp = com.x_comesp),0)  as lg_centro,  " +
            "           NVL((select LG_EMPRESA from FCT_RESULTADOSA_MODULOS res where res.id_modulo_curso = modu.id_modulo_curso and res.x_comesp = com.x_comesp),0) as lg_empresa," +
   "   (select count(*) from fct_actividades_modulos act,  fct_resultadosa_modulos rem, fct_resultadosa_actividades rea  " +
   "                        where act.id_modulo_curso = modu.id_modulo_curso  " +
   "                        and rea.id_actividad_modulo = act.id_actividad_modulo " +
   "                        and rea.id_resultadoa_modulo = rem.id_resultadoa_modulo" +
   "                        and rem.id_modulo_curso = act.id_modulo_curso" +
   "                        and rem.x_comesp = com.x_comesp) AS actividades " +
            "    from tlrelcompesmat rel, tlcomesp com, fct_modulos_cursos modu " +
            "    where modu.id_modulo_curso = :idModulo" +
            "    and rel.x_materiaomg = modu.x_materiaomg" +
            "    and com.x_comesp = rel.x_comesp" +
            "    order by n_ordenpres", nativeQuery = true)
    List<ListadoResultadosAsociadosPlanProjection> findResultadosAsociadosPlanByModulo(Long idModulo);

    @Query(value = "SELECT res.id_resultadoa_modulo AS id_resultadoa_modulo,  "
      + "       com.x_comesp AS x_comesp,  "
      + "       com.t_abrev AS abreviatura,  "
      + "       com.d_comesp AS descripcion, "
      + "       NVL(CASE WHEN act.id_actividad_modulo IS NOT NULL THEN 1 ELSE 0 END, 0) AS lgres  "
      + "FROM fct_modulos_cursos modu "
      + "JOIN tlrelcompesmat rel ON rel.x_materiaomg = modu.x_materiaomg "
      + "JOIN tlcomesp com ON com.x_comesp = rel.x_comesp "
      + "LEFT JOIN fct_resultadosa_modulos res ON res.id_modulo_curso = modu.id_modulo_curso  "
      + "                                       AND res.x_comesp = com.x_comesp AND res.lg_empresa = 1 "
      + "LEFT JOIN fct_resultadosa_actividades act ON act.id_actividad_modulo = :idActividad "
      + "                                           AND act.id_resultadoa_modulo = res.id_resultadoa_modulo "
      + "WHERE res.id_modulo_curso = :idModulo  "
      + "ORDER BY com.n_ordenpres", nativeQuery = true)
    List<ListadoResultadosAsociadosPlanRelacionadosProjection> findResultadosAsociadosPlanByModuloEnRelacion(Long idModulo, Long idActividad);

    @Query(value = "select id_resultadoa_modulo from FCT_RESULTADOSA_MODULOS where id_modulo_curso = :idModuloCurso and x_comesp = :xComEsp", nativeQuery = true)
    Long findIdByModulosCursoIdModuloCursoAndXComesp(Long idModuloCurso, Long xComEsp);
    
    List<ResultadosAsociadosPlan> findAllByModulosCursoId(Long id);
    
}
