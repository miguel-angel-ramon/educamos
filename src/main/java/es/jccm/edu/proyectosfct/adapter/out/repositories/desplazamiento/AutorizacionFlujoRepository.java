package es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QAutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.AutorizacionFlujoSiguienteProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AutorizacionFlujoRepository extends AbstractRepository<AutorizacionFlujo, Long, QAutorizacionFlujo> {

 AutorizacionFlujo findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(Long idPerfil,
   Long idTipoAutorizacion);

 AutorizacionFlujo findByIdEstadoOrigenAndIdPerfilAndTipoAutorizacionId(Long idEstadoOrigen, Long idPerfil,
   Long idTipoAutorizacion);

 
    @Query(value = "select flu.id_autorizacion_flujo AS id,  "
    + "    est.ds_abrev AS DSABREV ,  "
    + "    est.ds_nombre AS DSNOMBRE,   "
    + "    est.fh_inicio AS FHINICIO, "
    + "    est.fh_fin AS FHFIN,  "
    + "    est.tx_aviso AS TXAVISO,  "
    + "    flu.lg_reqadjunto AS ADJUNTO,  "
    + "    flu.x_perfil AS idPerfil "
    + "       from FCT_AUTORIZACIONES_FLUJO flu,  "
    + "       FCT_ESTADOS_AUTORIZACIONES est,  "
    + "       (select per.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_AUT_DES) AS fh_registromax  "
    + "        from   FCT_PERIODOS_GASTOS per, FCT_AUT_DES aut, FCT_AUTDES_HISTORIAL his, fct_autorizaciones_flujo flu  "
    + "        where  aut.ID_AUT_DES = his.ID_AUT_DES  "
    + "        and    his.ID_AUTORIZACION_FLUJO = flu.ID_AUTORIZACION_FLUJO  "
    + "        and    per.ID_PERIODO_GASTO = aut.ID_PERIODO_GASTO  "
    + "        and    his.ID_AUT_DES = :idAut "
    + "        and    flu.id_tipo_autorizacion = :idTipo      "
    + "        and    flu.id_tipo_autorizacion = 1 "
    + "        UNION  "
    + "        select (select c_anno from tlmatalu where x_matricula = aut.x_matricula) c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_AUT_EXTPRO) AS fh_registromax  "
    + "       from   FCT_AUT_EXTPRO aut, fct_autextfue_historial his, fct_autorizaciones_flujo flu  "
    + "       where  aut.ID_AUT_EXTPRO = his.ID_AUT_EXTPRO  "
    + "       and    his.ID_AUTORIZACION_FLUJO = flu.ID_AUTORIZACION_FLUJO      "
    + "       and    his.ID_AUT_EXTPRO = :idAut "
    + "       and    flu.id_tipo_autorizacion = :idTipo      "
    + "       and    flu.id_tipo_autorizacion = 2  "
    + "       UNION "
    + "       select per.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_AUTORIZACION_ANEXO) AS fh_registromax " 
    + "       from   FCT_PERIODOS_GASTOS per, fct_autorizaciones_anexos aut, FCT_ANEAUT_HISTORIAL his, fct_autorizaciones_flujo flu  "
    + "       where  aut.ID_AUTORIZACION_ANEXO = his.ID_AUTORIZACION_ANEXO  "
    + "       and    his.ID_AUTORIZACION_FLUJO = flu.ID_AUTORIZACION_FLUJO  "
    + "       and    per.ID_PERIODO_GASTO = aut.ID_PERIODO_GASTO  "
    + "       and    his.ID_AUTORIZACION_ANEXO = :idAut "
    + "       and    flu.id_tipo_autorizacion = :idTipo "
    + "       and    flu.id_tipo_autorizacion = 3 " 
    + "      UNION "
    + "       select aut.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_AUTORIZACION_ANEXO) AS fh_registromax "  
     + "       from   fct_autorizaciones_anexos aut, FCT_ANEAUT_HISTORIAL his, fct_autorizaciones_flujo flu "  
     + "       where  aut.ID_AUTORIZACION_ANEXO = his.ID_AUTORIZACION_ANEXO "  
     + "       and    his.ID_AUTORIZACION_FLUJO = flu.ID_AUTORIZACION_FLUJO  " 
     + "       and    his.ID_AUTORIZACION_ANEXO = :idAut  "
     + "       and    flu.id_tipo_autorizacion = :idTipo " 
     + "       and    flu.id_tipo_autorizacion = 4 "
    + "       ) fluori,  "
    + "       TLCURSOACA cur  "
    + "       where flu.id_estado_des = est.id_estado_autorizacion "
    + "       and   flu.id_estado_ori = fluori.id_estado_des  "
    + "       and   flu.x_perfil = :idPerfil "
    + "       and   fluori.fh_registro = fluori.fh_registromax  "
    + "       and   fluori.c_anno = cur.c_anno  "
    + "       and   flu.id_tipo_autorizacion = :idTipo  "
    + "       and   ((cur.l_actual = 'S'  "
    + "      and sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY'))  "
    + "      and (flu.lg_borrado = 0 or (lg_borrado = 1 and sysdate < trunc(fh_borrado)))  "
    + "      )  "
    + "      OR  "
    + "     (cur.l_actual = 'N'  "
    + "      and tlf_intersecper(cur.f_inicio, cur.f_final,trunc(est.fh_inicio),nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')))=1)  "
    + "      and (flu.lg_borrado = 0 or (lg_borrado = 1 and cur.f_final < trunc(fh_borrado)))  "
    + "     )    "
    + "       order by est.ds_nombre", nativeQuery = true)
 List<AutorizacionFlujoSiguienteProjection> findSiguienteAutorizacionFlujo(Long idPerfil, Long idAut, Long idTipo);

    
    
    @Query(value = "select flu.id_autorizacion_flujo AS id,  "
      + "       est.ds_abrev AS DSABREV ,  "
      + "       est.ds_nombre AS DSNOMBRE,   "
      + "       est.fh_inicio AS FHINICIO, "
      + "       est.fh_fin AS FHFIN,  "
      + "       est.tx_aviso AS TXAVISO,  "
      + "       flu.lg_reqadjunto AS ADJUNTO,  "
      + "       flu.x_perfil AS idPerfil "
      + "     from FCT_AUTORIZACIONES_FLUJO flu,  "
      + "     FCT_ESTADOS_AUTORIZACIONES est,  "
      + "     (select  pro.c_anno ,flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.ID_PROGPERFOR) AS fh_registromax  "
      + "      from   fct_progperfor pro, fct_progperfor_historial his, fct_autorizaciones_flujo flu  "
      + "      where  pro.ID_PROGPERFOR = his.ID_PROGPERFOR  "
      + "      and    his.ID_AUTORIZACION_FLUJO = flu.ID_AUTORIZACION_FLUJO        "
      + "      and    his.ID_PROGPERFOR = :idPrograma "
      + "      and    flu.id_tipo_autorizacion = 5          "
      + "     ) fluori,  "
      + "     TLCURSOACA cur,  "
      + "     (select * from fct_progperfor where id_progperfor = :idPrograma) programa "  
      + "     where flu.id_estado_des = est.id_estado_autorizacion "
      + "     and   flu.id_estado_ori = fluori.id_estado_des  "
      + "     and   flu.x_perfil = :idPerfil "
      + "     and   fluori.fh_registro = fluori.fh_registromax  "
      + "     and   fluori.c_anno = cur.c_anno  "
      + "     and   flu.id_tipo_autorizacion = 5  "
      + "     and   ((cur.l_actual = 'S'  "
      + "         and sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY'))  "
      + "         and (flu.lg_borrado = 0 or (lg_borrado = 1 and sysdate < trunc(fh_borrado)))  "
      + "         )  "
      + "         OR  "
      + "        (cur.l_actual = 'N'  "
      + "         and tlf_intersecper(cur.f_inicio, cur.f_final,trunc(est.fh_inicio),nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')))=1)  "
      + "         and (flu.lg_borrado = 0 or (lg_borrado = 1 and cur.f_final < trunc(fh_borrado)))  "
      + "        )  "
      + "        and  ((:idPerfil != 2 AND :idPerfil != 161) "
      + "                           OR (:idPerfil = 161 AND :esDirector = 0 AND ds_abrev != 'VBD') "
      + "                           OR (:idPerfil = 161 AND :esDirector = 1 AND :esMasivo = 1 AND ds_abrev = 'VBD')  "
      + "                           OR (:idPerfil = 161 AND :esDirector = 1 AND :esMasivo = 0) "
      + "                        OR (:esJefe = 0 AND :idPerfil = 2 AND :cUsuario = programa.c_usu_prog  AND ds_abrev != 'PBD')  "
      + "                           OR (:esJefe = 1 AND :idPerfil = 2 AND ds_abrev != 'PVJ')) "
      + "     order by est.ds_nombre", nativeQuery = true)   
 List<AutorizacionFlujoSiguienteProjection> findSiguienteAutorizacionFlujoPFE(Long idPerfil, Long idPrograma,
   Integer esJefe, Long cUsuario, Integer esDirector, Integer esMasivo);

    @Query(value = "SELECT flu.id_autorizacion_flujo AS id, "
            + "       est.ds_abrev AS DSABREV, "
            + "       est.ds_nombre AS DSNOMBRE, "
            + "       est.fh_inicio AS FHINICIO, "
            + "       est.fh_fin AS FHFIN, "
            + "       est.tx_aviso AS TXAVISO, "
            + "       flu.lg_reqadjunto AS ADJUNTO, "
            + "       flu.x_perfil AS idPerfil "
            + "FROM FCT_AUTORIZACIONES_FLUJO flu, FCT_ESTADOS_AUTORIZACIONES est "
            + "WHERE flu.id_tipo_autorizacion = 5 AND x_perfil = 11207 "
            + "  AND flu.id_estado_ori = 13 "
            + "  AND flu.id_estado_des = 9 "
            + "  AND flu.id_estado_des = est.id_estado_autorizacion", nativeQuery = true)
    List<AutorizacionFlujoSiguienteProjection>  getSiguienteEstadoFlujoPFEDelegacion();

}
