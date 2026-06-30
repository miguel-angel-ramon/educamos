package es.jccm.edu.documentosGC.adapter.out.repositories.estadodoc;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.QEstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.EstadoFlujoDocumentoGCProjection;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.LineaEstadoProjection;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.ListadoEstadoDocProjection;
import es.jccm.edu.documentosGC.application.domain.perfiles.projection.PerfilGCProjection;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;

public interface EstadoDocumentoGCRepository extends AbstractRepository<EstadoDocumentoGC, Long, QEstadoDocumentoGC> {
    @Query(value = "select distinct est.* "
    		+ "from   DGC_ESTADOS est, DGC_ESTADOS_FLUJO flu, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPOS_DOCUMENTOS tipp, TLCURSOACA cur "
    		+ "where  est.id_estado = flu.id_estado_des "
    		+ "and    flu.id_tipo_documento = tip.id_tipo_documento "
    		+ "and    tip.id_tipo_padre = tipp.id_tipo_documento "
    		+ "and    (:idPerfil not in (2360,42,1043,2043) OR est.ds_abrev <> 'PR') "
    		+ "and    tipp.ds_abrev = :dsAbrevPadre "
    		+ "and    cur.c_anno = :cAnno "
    		+ "and    tlf_intersecper(est.fh_inicio, est.fh_fin, cur.f_inicio, cur.f_final) = 1 "
    		+ "and    cur.c_anno between tip.c_anno_desde and nvl(tip.c_anno_hasta,2099) "
    		+ "order  by 1 ", nativeQuery = true)
	List<EstadoDocumentoGC> findAllEstadosDocGC(@Param("idPerfil") Long idPerfil,
												@Param("cAnno") Long cAnno,
												@Param("dsAbrevPadre") String dsAbrevPadre);
    
    /* Consulta que precalcula el estado inicial (flujo inicial) al crear un nuevo documento.
     * Comprueba que el estado y el flujo estén vigentes, teniendo en cuenta lo siguiente:
     * 1.- Si el curso académico es el actual, se utiliza la fecha actual para comprobar las vigencias.
     * 2.- Si el curso académico es distinto al actúal, se comprueba que el estado y el flujo estuvieran vigentes en dicho año */
    
    @Query(value = " select flu.id_flujo AS IDFLUJO, est.ds_abrev AS DSABREV , est.ds_nombre AS DSNOMBRE,  "
    		+ "             est.fh_inicio AS FHINICIO, est.fh_fin AS FHFIN, est.lg_final AS LGFINAL, est.tx_aviso AS TXAVISO "
    		+ " from  DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est, TLCURSOACA cur "
    		+ " where flu.id_estado_des = est.id_estado "
    		+ " and flu.id_estado_ori is null "
    		+ " and  flu.x_perfil = :idPerfil "
    		+ " and  flu.id_tipo_documento = :idTipodoc "
    		+ " and  cur.c_anno = :cAnno "
    		+ " and   ((cur.l_actual = 'S' "
    		+ "         and sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')) "
    		+ "         and (flu.lg_borrado = 0 or (lg_borrado = 1 and sysdate < trunc(fh_borrado))) "
    		+ "         ) "
    		+ "         OR "
    		+ "        (cur.l_actual = 'N' "
    		+ "         and tlf_intersecper(cur.f_inicio, cur.f_final,trunc(est.fh_inicio),nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')))=1) "
    		+ "         and (flu.lg_borrado = 0 or (lg_borrado = 1 and cur.f_final < trunc(fh_borrado))) "
    		+ "        ) "
    		+ " order by est.ds_nombre ", nativeQuery = true)
	List<EstadoFlujoDocumentoGCProjection> findEstadoInicialDocGC(@Param("idPerfil") Long idPerfil, @Param("idTipodoc") Long idTipodoc, @Param("cAnno") Long cAnno);

    
    @Query(value = "select his.id_historial AS idHistorial, "
    		+ "est.id_estado AS idEstado, "
    		+ "est.ds_nombre AS nombre, "
    		+ "his.fh_registro AS fhregistro, "
    		+ "decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_documento),'S','N') AS lactual, "
    		+ "0 AS nivel, "
    		+ "0 AS lgfinal, "
    		+ "1 AS lhistorico, "
    		+ "est.ds_abrev AS abrev  "
    		+ "from DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est " 
    		+ "where his.id_flujo = flu.id_flujo "
    		+ "and flu.id_estado_des = est.id_estado "
    		+ "and his.id_documento = :idDocumento "
    		+ "order by his.fh_registro ", nativeQuery = true)
	List<LineaEstadoProjection> getLineaHistoricos(Long idDocumento);

    @Query(value = "select * from ( "
    		+ "select est.*, "
    		+ "decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_documento),'S','N') AS L_ACTUAL  "
    		+ "from DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est "
    		+ "where his.id_flujo = flu.id_flujo "
    		+ "and flu.id_estado_des = est.id_estado "
    		+ "and his.id_documento = :idDocumento) "
    		+ "where l_actual = 'S' ", nativeQuery = true)
    EstadoDocumentoGC getEstadoActual(Long idDocumento);    
    
    @Query(value = "select distinct null AS idHistorial, "
    		+ "estdes.id_estado AS idEstado, "
    		+ "estdes.ds_nombre AS nombre, "
    		+ "null AS fhregistro, "
    		+ "'N' AS lactual, "
    		+ "level AS nivel, "
    		+ "estdes.lg_final AS lgfinal, "
    		+ "0 AS lhistorico,  "
    		+ "estdes.ds_abrev AS abrev  "
    		+ "from   DGC_ESTADOS_FLUJO flu, DGC_ESTADOS estori, DGC_ESTADOS estdes "
    		+ "where  flu.id_estado_ori = estori.id_estado (+) "
    		+ "and    flu.id_estado_des = estdes.id_estado "
    		+ "and    flu.id_tipo_documento = :idTipo "
    		+ "start  with flu.id_estado_ori = :idEstado "
    		+ "connect by NOCYCLE prior flu.id_estado_des = flu.id_estado_ori and estori.lg_final = 0 "
    		+ "order  by level, lgfinal desc ", nativeQuery = true)
	List<LineaEstadoProjection> getLineaDirecta(Long idEstado, Long idTipo);

    
    /* Consulta que precalcula los posibles estados a los que puede pasar un documento ya creado.
     * Comprueba que el estado y el flujo estén vigentes, teniendo en cuenta lo siguiente:
     * 1.- Si el curso académico es el actual, se utiliza la fecha actual para comprobar las vigencias.
     * 2.- Si el curso académico es distinto al actúal, se comprueba que el estado y el flujo estuvieran vigentes en dicho año */
    
    @Query(value = 
    		" select flu.id_flujo AS IDFLUJO, est.ds_abrev AS DSABREV , est.ds_nombre AS DSNOMBRE,  "
    		+ "      est.fh_inicio AS FHINICIO, est.fh_fin AS FHFIN, est.lg_final AS LGFINAL, est.tx_aviso AS TXAVISO, flu.lg_reqadjunto AS ADJUNTO  "
    		+ " from DGC_ESTADOS_FLUJO flu, "
    		+ "      DGC_ESTADOS est, "
    		+ "      (select doc.c_anno, flu.id_estado_des,his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
    		+ "       from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu "
    		+ "       where  doc.id_documento = his.id_documento "
    		+ "       and    his.id_flujo = flu.id_flujo "
    		+ "       and    his.id_documento = :idDocumento) fluori, "
    		+ "      TLCURSOACA cur "
    		+ " where flu.id_estado_des = est.id_estado "
    		+ " and   flu.id_estado_ori = fluori.id_estado_des "
    		+ " and   fluori.fh_registro = fluori.fh_registromax "
    		+ " and   fluori.c_anno = cur.c_anno "
    		+ " and   (:sFirma = 'S' OR (:sFirma = 'N' AND est.ds_abrev <> 'FIRM')) "
    		+ " and   flu.x_perfil = :idPerfil "
    		+ " and   flu.id_tipo_documento = :idTipodoc "
    		+ " and   ((cur.l_actual = 'S' "
    		+ "          and sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')) "
    		+ "          and (flu.lg_borrado = 0 or (lg_borrado = 1 and sysdate < trunc(fh_borrado))) "
    		+ "          ) "
    		+ "          OR "
    		+ "         (cur.l_actual = 'N' "
    		+ "          and tlf_intersecper(cur.f_inicio, cur.f_final,trunc(est.fh_inicio),nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')))=1) "
    		+ "          and (flu.lg_borrado = 0 or (lg_borrado = 1 and cur.f_final < trunc(fh_borrado))) "
    		+ "         ) "
    		+ " order by est.ds_nombre ", nativeQuery = true)
	List<EstadoFlujoDocumentoGCProjection> estadosPosiblesDocumentosGC(Long idPerfil, Long idTipodoc, Long idDocumento, String sFirma);
    
    @Query(value = "select ID_ESTADO AS id, DS_ABREV AS abreviatura, DS_NOMBRE AS nombre, FH_INICIO AS fInicio, FH_FIN AS fFin, decode(LG_FINAL,1,'S','N') AS esfinal, "
    		+ "NVL((select distinct 1 from DGC_ESTADOS_FLUJO "
    		+ "where (id_estado_ori = est.id_estado or id_estado_des = est.id_estado)),0) AS noborrable "
    		+ "from   DGC_ESTADOS est, TLCURSOACA cur "
    		+ "where  tlf_intersecper(cur.f_inicio, cur.f_final, est.fh_inicio, est.fh_fin) = 1 "
    		+ "and    cur.c_anno = :cAnno "
    		+ "order  by est.ds_nombre ", nativeQuery = true)
	List<ListadoEstadoDocProjection> listadoEstadosDocumentosGC(Long cAnno);
    
    @Query(value ="select id_estado from DGC_ESTADOS where ds_abrev = :abrevEstado ", nativeQuery = true)
	Long getEstadoId(@Param("abrevEstado") String abrevEstado);	
	
    

    @Query(value = "select est.id_estado as id, est.ds_nombre as nombre "
    		+ " from DGC_ESTADOS est "
    		+ " where sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')) "
    		+ " and est.lg_final = 0 "
    		+ " order by 2", nativeQuery = true)
	List<ListadoEstadoDocProjection> findEstadosOrigen();

    @Query(value = "select est.id_estado as id, est.ds_nombre as nombre "
    		+ "from   DGC_ESTADOS est "
    		+ "where  sysdate between trunc(est.fh_inicio) and nvl(trunc(est.fh_fin),to_date('01/01/2099','DD/MM/YYYY')) "
    		+ "and    est.id_estado <> :idEstadoOrigen "
    		+ "order  by 2", nativeQuery = true)
    List<ListadoEstadoDocProjection> findEstadosDestino(Long idEstadoOrigen);

    @Query(value = "select per.x_perfil AS id, "
    		+ "     per.d_perfil AS descripcion, "
    		+ "     per.C_CODIGO AS codigo, "
    		+ "     per.C_AMBVISCEN AS ambito "
    		+ "from   tlperfiles per "
    		+ "where  c_codigo in ('C','INC','ICO','I','INZ') "
    		+ "order  by per.d_perfil", nativeQuery = true)
	List<PerfilGCProjection> findAllPerfilesEstadosFlujo();	 

	
}
