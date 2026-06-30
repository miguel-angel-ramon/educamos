package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoFlujo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QGastoFlujo;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.GastoEstadoFlujoSiguienteProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface GastoFlujoRepository extends AbstractRepository<GastoFlujo, Long, QGastoFlujo> {

	GastoFlujo findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(Long idPerfil, Long id);

	 @Query(value = "select flu.id_gastos_flujo AS id,  "
	 		+ "     est.ds_abrev AS DSABREV ,  "
	 		+ "     est.ds_nombre AS DSNOMBRE,   "
	 		+ "     est.fh_inicio AS FHINICIO, "
	 		+ "     est.fh_fin AS FHFIN,  "
	 		+ "     est.tx_aviso AS TXAVISO,  "
	 		+ "     flu.lg_reqadjunto AS ADJUNTO,  "
	 		+ "     flu.x_perfil AS idPerfil "
	 		+ "             from FCT_GASTOS_FLUJO flu,  "
	 		+ "                  FCT_ESTADOS_GASTOS est,  "
	 		+ "                  (select per.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_GASTOS_TUTOR) AS fh_registromax  "
	 		+ "                   from   FCT_PERIODOS_GASTOS per, FCT_GASTOS_TUTORES gas, FCT_GASTOS_TUTOR_HISTORIAL his, FCT_GASTOS_FLUJO flu  "
	 		+ "                   where  gas.ID_GASTOS_TUTOR = his.ID_GASTOS_TUTOR  "
	 		+ "                   and    his.ID_GASTOS_FLUJO = flu.ID_GASTOS_FLUJO  "
	 		+ "                   and    per.ID_PERIODO_GASTO = gas.ID_PERIODO_GASTO  "
	 		+ "                   and    his.ID_GASTOS_TUTOR = :idGasto "
	 		+ "                   and    flu.id_tipos_gastos = :idTipoGasto "
	 		+ "                   and    1 = :idTipoGasto  "
	 		+ "                   UNION  "
	 		+ "                   select per.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_GASTOS_ALUMNADO) AS fh_registromax  "
	 		+ "                   from   FCT_PERIODOS_GASTOS per, FCT_GASTOS_ALUMNADO gas, FCT_GASTOS_ALUMNADO_HISTORIAL his, FCT_GASTOS_FLUJO flu  "
	 		+ "                   where  gas.ID_GASTOS_ALUMNADO = his.ID_GASTOS_ALUMNADO  "
	 		+ "                   and    his.ID_GASTOS_FLUJO = flu.ID_GASTOS_FLUJO  "
	 		+ "                   and    per.ID_PERIODO_GASTO = gas.ID_PERIODO_GASTO  "
	 		+ "                   and    his.ID_GASTOS_ALUMNADO = :idGasto "
	 		+ "                   and    flu.id_tipos_gastos = :idTipoGasto "
	 		+ "                   and    2 = :idTipoGasto "
	 		+ "                   UNION "
	 		+ "                   select per.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_GASTOS_ANEXOS) AS fh_registromax  "
	 		+ "                   from   FCT_PERIODOS_GASTOS per, FCT_GASTOS_ANEXOS gas, FCT_ANEXOS_HISTORIAL his, FCT_GASTOS_FLUJO flu  "
	 		+ "                   where  gas.ID_GASTOS_ANEXOS = his.ID_GASTOS_ANEXOS  "
	 		+ "                   and    his.ID_GASTOS_FLUJO = flu.ID_GASTOS_FLUJO  "
	 		+ "                   and    per.ID_PERIODO_GASTO = gas.ID_PERIODO_GASTO  "
	 		+ "                   and    his.ID_GASTOS_ANEXOS = :idGasto "
	 		+ "                   and    gas.ID_TIPOS_GASTOS = :idTipoGasto "
	 		+ "                   and    flu.id_tipos_gastos = gas.ID_TIPOS_GASTOS "
	 		+ "                   ) fluori,  "
	 		+ "                  TLCURSOACA cur  "
	 		+ "             where flu.id_estado_des = est.id_estado_gasto "
	 		+ "             and   flu.id_estado_ori = fluori.id_estado_des  "
	 		+ "             and   flu.x_perfil = :idPerfil "
	 		+ "             and   fluori.fh_registro = fluori.fh_registromax  "
	 		+ "             and   fluori.c_anno = cur.c_anno  "
	 		+ "             and   flu.id_tipos_gastos = :idTipoGasto  "
	 		+ "             and   ((cur.l_actual = 'S'  "
	 		+ "                      and sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY'))  "
	 		+ "                      and (flu.lg_borrado = 0 or (lg_borrado = 1 and sysdate < trunc(fh_borrado)))  "
	 		+ "                      )  "
	 		+ "                      OR  "
	 		+ "                     (cur.l_actual = 'N'  "
	 		+ "                      and tlf_intersecper(cur.f_inicio, cur.f_final,trunc(est.fh_inicio),nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')))=1)  "
	 		+ "                      and (flu.lg_borrado = 0 or (lg_borrado = 1 and cur.f_final < trunc(fh_borrado)))  "
	 		+ "                     )    "
	 		+ "             order by est.ds_nombre", nativeQuery = true)
	List<GastoEstadoFlujoSiguienteProjection> findSiguienteGastoFlujo(Long idPerfil, Long idGasto, Long idTipoGasto);
 
    GastoFlujo findByIdPerfilAndTipoGastoIdAndEstadoOrigenIdAndEstadoDestinoId(Long idPerfil, Long idTipo, Long idEstadoOri, Long IdEstadoFin);
    
	 @Query(value = "select ds_abrev from (  "
	 		+ "select est.ds_abrev from fct_gastos_anexos gas, "
	 		+ "                         fct_anexos_historial his, "
	 		+ "                         fct_gastos_flujo flu, "
	 		+ "                         fct_estados_gastos est "
	 		+ "where gas.id_gastos_anexos = :idGastoAnexo "
	 		+ "and gas.id_gastos_anexos = his.id_gastos_anexos "
	 		+ "and flu.id_gastos_flujo = his.id_gastos_flujo "
	 		+ "and est.id_estado_gasto = id_estado_des "
	 		+ "order by his.fh_registro desc) WHERE ROWNUM =1", nativeQuery = true)	 
    String getEstadoAnexo(Long idGastoAnexo);
	 GastoFlujo findAllById(Long idFlujoValProf);
}
