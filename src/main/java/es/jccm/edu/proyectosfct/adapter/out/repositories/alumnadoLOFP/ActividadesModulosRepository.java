package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;

import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.QActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.projection.ListadoActividadesModulosProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadesModulosRepository extends AbstractRepository<ActividadesModulos, Long, QActividadesModulos> {

    @Query(value = "SELECT act.id_actividad_modulo AS id," +
            "       act.tx_nombre AS nombre," +
            "       (    SELECT LISTAGG(com.t_abrev, '/c') WITHIN GROUP (ORDER BY com.n_ordenpres) AS lsResula" +
            "           FROM fct_resultadosa_actividades react" +
            "           JOIN fct_resultadosa_modulos remod ON remod.id_resultadoa_modulo = react.id_resultadoa_modulo" +
            "           JOIN tlcomesp com ON com.x_comesp = remod.x_comesp" +
            "           WHERE react.id_actividad_modulo = act.id_actividad_modulo" +
            "       ) AS lsResula," +
            " (    SELECT LISTAGG(com.d_comesp, '/c') WITHIN GROUP (ORDER BY com.n_ordenpres) AS dsDescripcion " +  
            "        FROM fct_resultadosa_actividades react   " +
            "        JOIN fct_resultadosa_modulos remod ON remod.id_resultadoa_modulo = react.id_resultadoa_modulo  " + 
            "        JOIN tlcomesp com ON com.x_comesp = remod.x_comesp  " +  
            "        WHERE react.id_actividad_modulo = act.id_actividad_modulo " +   
            "    ) AS dsDescripcion, " +
            "   act.tx_abrev AS txAbrev, " +
            "   act.nu_orden as nuOrden " +
            "   FROM fct_actividades_modulos act" +
            "   WHERE act.id_modulo_curso = :idModulo order by nu_orden", nativeQuery = true)
    List<ListadoActividadesModulosProjection> findActividadesPlanByModulo(Long idModulo);

    boolean existsByTxNombreAndIdModuloCursoAndIdActividadModuloNot(String txNombre, Long idModuloCurso, Long idActividad);
    boolean existsByTxAbrevAndIdModuloCursoAndIdActividadModuloNot(String txAbrev, Long idModuloCurso, Long idActividad);
    boolean existsByNuOrdenAndIdModuloCursoAndIdActividadModuloNot(Integer nuOrden, Long idModuloCurso, Long idActividad);


    List<ActividadesModulos> findAllByIdModuloCurso(Long idModuloCurso);
    ActividadesModulos findFirstByIdModuloCursoOrderByNuOrdenDesc(Long idModuloCurso);

    @Query(value = "select am.id_actividad_modulo AS id, " +
            "       am.tx_nombre AS nombre, " +
            "       (    SELECT LISTAGG(com.t_abrev, '/c') WITHIN GROUP (ORDER BY com.n_ordenpres) AS lsResula " +
            "            FROM fct_resultadosa_actividades react " +
            "            JOIN fct_resultadosa_modulos remod ON remod.id_resultadoa_modulo = react.id_resultadoa_modulo " +
            "            JOIN tlcomesp com ON com.x_comesp = remod.x_comesp " +
            "            WHERE react.id_actividad_modulo = am.id_actividad_modulo " +
            "       ) AS lsResula, " +
            "       (    SELECT LISTAGG(com.d_comesp, '/c') WITHIN GROUP (ORDER BY com.n_ordenpres) AS dsDescripcion  " +
            "            FROM fct_resultadosa_actividades react    " +
            "            JOIN fct_resultadosa_modulos remod ON remod.id_resultadoa_modulo = react.id_resultadoa_modulo   " +
            "            JOIN tlcomesp com ON com.x_comesp = remod.x_comesp   " +
            "            WHERE react.id_actividad_modulo = am.id_actividad_modulo  " +
            "       ) AS dsDescripcion,  " +
            "       am.tx_abrev AS txAbrev,  " +
            "       am.nu_orden as nuOrden, " +
            "       CASE  " +
            "           WHEN EXISTS ( " +
            "               SELECT 1 FROM FCT_PARDIA_ALUPLAN_ACTMOD apm " +
            "               WHERE apm.id_pardia_aluplan = ( " +
            "                   SELECT par.id_pardia_aluplan " +
            "                   FROM fct_pardia_aluplan par " +
            "                   WHERE par.id_convproy_alu = ca.id_convproy_alu " +
            "                   AND par.f_dia = :fechaDia " +
            "               ) " +
            "               AND apm.id_actividad_modulo = am.id_actividad_modulo " +
            "           ) THEN 1 " +
            "           ELSE 0 " +
            "       END AS actividadRegistrada " +
            " from fct_convproy_alu ca " +
            " join fct_conv_proy cp on ca.id_conv_proy = cp.id_conv_proy " +
            " join fct_cursos_proyectos cpr on cp.id_proyecto = cpr.id_proyecto " +
            " join fct_modulos_cursos mc on cpr.id_curso_proyecto = mc.id_curso_proyecto " +
            " join fct_actividades_modulos am on mc.id_modulo_curso = am.id_modulo_curso " +
            " join fct_modulos_empresas emp on emp.id_modulo_curso = mc.id_modulo_curso and emp.id_conv_proy = cp.id_conv_proy  " +
            " where ca.id_convproy_alu = :idConvProyAlu", nativeQuery = true)
    List<ListadoActividadesModulosProjection> findActividadesPlanByIdConvProyAluAndDia(Long idConvProyAlu, String fechaDia);

}
