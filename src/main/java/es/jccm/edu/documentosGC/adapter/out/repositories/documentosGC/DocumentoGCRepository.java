package es.jccm.edu.documentosGC.adapter.out.repositories.documentosGC;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.CursoProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.QDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.AdjuntosListDetalleProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ContadoresInicioProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ConvocatoriaReunionesProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.DocumentosGCListProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.HistDocumentosGCListProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.InformacionEstadoPojection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ParteGeneradoDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.PartesMensualesDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.PlazosEntregaDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.TipoAdjuntosListProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface DocumentoGCRepository extends AbstractRepository<DocumentosGC, Long, QDocumentosGC> {
 
  @Query(value ="select DS_ABREV AS idTipo, "
   + "       total AS nuTotal, "
   + "       (SELECT count(*) "
   + "        FROM   DGC_TIPOS_DOCUMENTOS tip, DGC_PLAZOS_ENTREGA pla "
   + "        WHERE  tip.id_tipo_padre = tot.id_tipo_documento "
   + "        AND    tip.id_tipo_documento = pla.id_tipo_documento  "
   + "        AND    tip.lg_obligatorio = 1  "
   + "        AND    pla.c_anno = :anno  "
   + "        AND    pla.c_anno BETWEEN tip.c_anno_desde and nvl(tip.c_anno_hasta,2099)  "
   + "        AND SYSDATE >= pla.fh_inicio  "
   + "        AND not exists (SELECT 1 from DGC_DOCUMENTOS doc  "
   + "                        WHERE  doc.id_tipo_documento = tip.id_tipo_documento  "
   + "                        AND    doc.x_centro = :idCentro  "
   + "                        AND    doc.c_anno = pla.c_anno)) AS nuObl, "
   + "       totalPF AS nuPF, "
   + "       totalMiFir AS nuMiFir "
   + "from  "
   + "(select tpad.id_tipo_documento, "
   + "        tpad.ds_abrev,  "
   + "       count (distinct doc.id_documento) total, "
   + "       sum (decode(his.ds_abrev,'PF',1,0)) totalPF, "
   + "       sum( decode(his.ds_abrev,'PF',nvl((select 1 from DGC_DOCUMENTO_HISTORIAL his2, DGC_HISTORIAL_ADJUNTOS adj2, DGC_TIPDOC_ADJUNTOS tadj2, DGC_ADJUNTO_FIRMANTES fir2  "
   + "                                          where  his2.id_historial = adj2.id_historial  "
   + "                                          and    adj2.id_tipo_adjunto = tadj2.id_tipo_adjunto  "
   + "                                          and    adj2.id_adjunto = fir2.id_adjunto  "
   + "                                          and    tadj2.lg_firmable = 1  "
   + "                                          and    tadj2.lg_principal = 1  "
   + "                                          and    his2.id_documento = doc.id_documento  "
   + "                                          and    fir2.x_empleado = :xEmpleado  "
   + "                                          and    fir2.lg_firmado = 0  "
   + "                                          and    not exists (select * from dgc_adjunto_firmantes fir3  "
   + "                                                             where  fir3.id_adjunto = adj2.id_adjunto  "
   + "                                                             and    fir3.nu_orden < fir2.nu_orden  "
   + "                                                             and    fir3.lg_firmado = 0)),0),0)) totalMiFir "
   + "from DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip,  DGC_TIPOS_DOCUMENTOS tpad, "
   + "(select his.id_documento,est.id_estado, est.ds_nombre, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax, est.ds_abrev "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est  "
   + "where his.id_flujo = flu.id_flujo   "
   + "and flu.id_estado_des = est.id_estado   "
   + "and doc.id_documento = his.id_documento   "
   + "and doc.x_centro = :idCentro  "
   + "and (:idPerfil <> 2360 OR (:idPerfil = 2360 and est.ds_abrev <> 'PR'))  "
   + "and doc.c_anno = :anno   "
   + " ) his  "
   + "where doc.id_tipo_documento = tip.id_tipo_documento "
   + "and   tip.id_tipo_padre = tpad.id_tipo_documento "
   + "and doc.id_documento = his.id_documento "
   + "and his.fh_registro = his.fh_registromax   "
   + "and doc.c_anno = :anno  "
   + "and doc.x_centro = :idCentro  "
   + "group by tpad.id_tipo_documento, tpad.ds_abrev) tot ", nativeQuery = true)
 List<ContadoresInicioProjection> getContadoresDirectores(@Param("idCentro") Long idCentro, 
							    	                      @Param("anno") Integer anno,
							    	                      @Param("idPerfil") Long idPerfil,
							    	                      @Param("xEmpleado") Long xEmpleado);
  
  
  @Query(value ="select tpad.ds_abrev  AS idTipo,  "
   + "       count (distinct doc.id_documento) AS nuTotal, "
   + "       0  AS nuObl, "
   + "       sum (decode(his.ds_abrev,'PF',1,0)) AS nuPF, "
   + "       sum( decode(his.ds_abrev,'PF',nvl((select 1 from DGC_DOCUMENTO_HISTORIAL his2, DGC_HISTORIAL_ADJUNTOS adj2, DGC_TIPDOC_ADJUNTOS tadj2, DGC_ADJUNTO_FIRMANTES fir2  "
   + "                                          where  his2.id_historial = adj2.id_historial  "
   + "                                          and    adj2.id_tipo_adjunto = tadj2.id_tipo_adjunto  "
   + "                                          and    adj2.id_adjunto = fir2.id_adjunto  "
   + "                                          and    tadj2.lg_firmable = 1  "
   + "                                          and    tadj2.lg_principal = 1  "
   + "                                          and    his2.id_documento = doc.id_documento  "
   + "                                          and    fir2.x_empleado = :xEmpleado  "
   + "                                          and    fir2.lg_firmado = 0  "
   + "                                          and    not exists (select * from dgc_adjunto_firmantes fir3  "
   + "                                                             where  fir3.id_adjunto = adj2.id_adjunto  "
   + "                                                             and    fir3.nu_orden < fir2.nu_orden  "
   + "                                                             and    fir3.lg_firmado = 0)),0),0)) AS nuMiFir "
   + "from DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip,  DGC_TIPOS_DOCUMENTOS tpad, "
   + "(select his.id_documento,est.id_estado, est.ds_nombre, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax, est.ds_abrev "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est  "
   + "where his.id_flujo = flu.id_flujo   "
   + "and flu.id_estado_des = est.id_estado   "
   + "and doc.id_documento = his.id_documento   "
   + "and (:idPerfil = 161 OR (est.ds_abrev <> 'PR'))  "
   + "and ( "
   + "    (:idPerfil = 2043 and doc.x_centro in (select distinct dcen.x_centro  "
   + "                                          from TLINSPECTORESCEN insc, TLDATOSCEN dcen  "
   + "                                          where insc.x_centro = dcen.x_centro  "
   + "                                          and insc.x_empleado = :xEmpleado  "
   + "                                          and insc.f_tomapos = TO_DATE(:fTomapos,'dd-MM-yyyy')  "
   + "                                          and dcen.l_vigente = 'S')) "
   + "    or "
   + "    (:idPerfil = 1043 and doc.x_centro in (select dcen.x_centro  "
   + "                                           from  TLUSUARIOS u, TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen  "
   + "                                           where u.x_usuario = usu.x_usuario "
   + "                                           and   zon.x_zona = usu.x_zona  "
   + "                                           and   zon.x_centro = dcen.x_centro  "
   + "                                           and   usu.x_perfil = :idPerfil  "
   + "                                           and   u.x_empleado = :xEmpleado "
   + "                                           and   dcen.l_vigente = 'S')) "
   + "    or "
   + "    (:idPerfil = 42 and doc.x_centro in (select distinct dcen2.x_centro "
   + "                                         from   TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLMUNICIPIOS mun, TLDATOSCEN dcen2 "
   + "                                         where  pop.x_empleado = pto.x_empleado  "
   + "                                         and    pop.f_tomapos = pto.f_tomapos  "
   + "                                         and    pto.x_centro = dcen.x_centro  "
   + "                                         and    dcen.c_provincia = prv.c_provincia  "
   + "                                         and    dcen.c_provincia = mun.c_provincia  "
   + "                                         and    (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
   + "                                         and    dcen.c_provincia = dcen2.c_provincia "
   + "                                         and    dcen2.l_vigente = 'S' "
   + "                                         and    pop.x_perfil = :idPerfil  "
   + "                                         and    pto.x_empleado = :xEmpleado  "
   + "                                         and    pto.f_tomapos = TO_DATE(:fTomapos,'dd-MM-yyyy')))  "
   + "    or "
   + "    (:idPerfil = 2360) "
   + "    ) "
   + "and doc.c_anno = :anno   "
   + " ) his  "
   + "where doc.id_tipo_documento = tip.id_tipo_documento "
   + "and   tip.id_tipo_padre = tpad.id_tipo_documento "
   + "and doc.id_documento = his.id_documento "
   + "and his.fh_registro = his.fh_registromax   "
   + "and doc.c_anno = :anno  "
   + "and ( "
   + "    (:idPerfil = 2043 and doc.x_centro in (select distinct dcen.x_centro  "
   + "                                          from TLINSPECTORESCEN insc, TLDATOSCEN dcen  "
   + "                                          where insc.x_centro = dcen.x_centro  "
   + "                                          and insc.x_empleado = :xEmpleado  "
   + "                                          and insc.f_tomapos = TO_DATE(:fTomapos,'dd-MM-yyyy')  "
   + "                                          and dcen.l_vigente = 'S')) "
   + "    or "
   + "    (:idPerfil = 1043 and doc.x_centro in (select dcen.x_centro  "
   + "                                           from  TLUSUARIOS u, TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen  "
   + "                                           where u.x_usuario = usu.x_usuario "
   + "                                           and   zon.x_zona = usu.x_zona  "
   + "                                           and   zon.x_centro = dcen.x_centro  "
   + "                                           and   usu.x_perfil = :idPerfil  "
   + "                                           and   u.x_empleado = :xEmpleado "
   + "                                           and   dcen.l_vigente = 'S')) "
   + "    or "
   + "    (:idPerfil = 42 and doc.x_centro in (select distinct dcen2.x_centro "
   + "                                         from   TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLMUNICIPIOS mun, TLDATOSCEN dcen2 "
   + "                                         where  pop.x_empleado = pto.x_empleado  "
   + "                                         and    pop.f_tomapos = pto.f_tomapos  "
   + "                                         and    pto.x_centro = dcen.x_centro  "
   + "                                         and    dcen.c_provincia = prv.c_provincia  "
   + "                                         and    dcen.c_provincia = mun.c_provincia  "
   + "                                         and    (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
   + "                                         and    dcen.c_provincia = dcen2.c_provincia "
   + "                                         and    dcen2.l_vigente = 'S' "
   + "                                         and    pop.x_perfil = :idPerfil  "
   + "                                         and    pto.x_empleado = :xEmpleado  "
   + "                                         and    pto.f_tomapos = TO_DATE(:fTomapos,'dd-MM-yyyy')))  "
   + "    or "
   + "    (:idPerfil = 2360) "
   + "    ) "
   + "group by tpad.id_tipo_documento, tpad.ds_abrev ", nativeQuery = true)
  List<ContadoresInicioProjection> getContadoresInspectores(@Param("anno") Integer anno,
											      	        @Param("idPerfil") Long idPerfil,
											      	        @Param("xEmpleado") Long xEmpleado,
											      	        @Param("fTomapos") String fTomapos);

  
 @Query(value ="select doc.id_documento AS ID, "
		 +" prov.d_provincia AS PROVINCIA, "
		 +" mun.d_municipio AS MUNICIPIO, "
		 +" tlf_datoscentro(dat.x_centro) AS CENTRO, "
		 +" tip.ds_descripcion AS TIPO,  "
		 +" his.ds_nombre AS ESTADO,  "
		 +" doc.ds_descripcion AS DESCRIPCION,  "
		 +" his.fh_registro AS FHREGISTRO,  "
		 +" rodal.ID_DOCHIS_RODAL AS IDRODAL,  "
		 +" rodal.TX_DOCHIS_FICHERO AS FICHERO,   "
		 +" doc.ds_paraus AS DSPARAUS, "
		 +" his.tx_aviso AS AVISO,  "
		 +" rodal.id_adjunto AS idAdjunto, "
		 +" (select count(*) total from DGC_TIPDOC_ADJUNTOS where lg_principal = 0 and id_tipo_documento = doc.id_tipo_documento) AS PERMITEADJUNTOS, "
		 +" decode(his.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir "
		 +"         where  fir.id_adjunto = rodal.id_adjunto "
		 +"         and    fir.x_empleado = :xEmpleado "
		 +"         and    lg_firmado = 0 "
		 +"         and    not exists (select distinct 0 total "
		 +"                            from   dgc_adjunto_firmantes fir2 "
		 +"                            where  fir2.id_adjunto = fir.id_adjunto "
		 +"                            and    fir2.nu_orden < fir.nu_orden  "
		 +"                            and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR, "
		 +" nvl(adj.totalAdjuntos,0) AS totalAdjuntos, "
		 +" nvl(adj.pendienteFirma,0) || ' Pendiente de firma' AS aviso2, "
		 + "nvl(adj.pendienteMiFirma,0) || ' MI FIRMA' AS aviso3 "
		 +" from DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPOS_DOCUMENTOS tpad, TLCENTROS cen, TLDATOSCEN dat, TLPROVINCIAS prov, TLMUNICIPIOS mun, "
		 +" (select his.id_documento,est.id_estado, est.ds_nombre, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax, est.tx_aviso, est.ds_abrev "
		 +" from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPOS_DOCUMENTOS tpad "
		 +" where his.id_flujo = flu.id_flujo  "
		 +" and flu.id_estado_des = est.id_estado  "
		 +" and doc.id_documento = his.id_documento "
		 +" and doc.id_tipo_documento = tip.id_tipo_documento "
		 +" and tip.id_tipo_padre = tpad.id_tipo_documento "
		 +" and tpad.ds_abrev = :abrev "
		 +" and (-1 = :idCentro OR :idCentro = doc.x_centro)  "
		 +" and (:idPerfil <> 2360 OR (:idPerfil = 2360 and est.ds_abrev <> 'PR')) "
		 +" and doc.c_anno = :anno  "
		 +" ) his,  "
		 +" (select doc.ID_DOCUMENTO, adj.ID_DOCHIS_RODAL, adj.TX_DOCHIS_FICHERO, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax, adj.id_adjunto "
		 +" from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tadj, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPOS_DOCUMENTOS tpad "
		 +" where doc.id_documento = his.id_documento  "
		 +" and his.id_historial = adj.id_historial "
		 +" and adj.id_tipo_adjunto = tadj.id_tipo_adjunto "
		 +" and doc.id_tipo_documento = tip.id_tipo_documento "
		 +" and tip.id_tipo_padre = tpad.id_tipo_documento "
		 +" and tpad.ds_abrev = :abrev "
		 +" and tadj.lg_principal = 1 "
		 +" and (-1 = :idCentro OR :idCentro = doc.x_centro) "
		 +" and doc.c_anno = :anno "
		 +" ) rodal, "
		 +" (select doc.ID_DOCUMENTO, "
		 +"        count(his.id_documento) AS totalAdjuntos, "
		 +"        sum(tadj.lg_firmable - adj.lg_firmado) AS pendienteFirma, "
		 +"        sum(decode(fir.x_empleado,:xEmpleado,decode(fir.lg_firmado,0,1,0),0)) AS pendienteMiFirma "
		 +" from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tadj, DGC_ADJUNTO_FIRMANTES fir, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPOS_DOCUMENTOS tpad "
		 +" where doc.id_documento = his.id_documento "
		 +" and his.id_historial = adj.id_historial "
		 +" and adj.id_tipo_adjunto = tadj.id_tipo_adjunto "
		 +" and adj.id_adjunto = fir.id_adjunto (+) "
		 +" and doc.id_tipo_documento = tip.id_tipo_documento "
		 +" and tip.id_tipo_padre = tpad.id_tipo_documento "
		 +" and tpad.ds_abrev = :abrev "
		 +" and tadj.lg_principal = 0 "
		 +" and (-1 = :idCentro OR :idCentro = doc.x_centro) "
		 +" and doc.c_anno = :anno "
		 +" group by doc.id_documento "
		 +" ) adj "
		 +" where doc.id_tipo_documento = tip.id_tipo_documento "
		 +" and doc.id_documento = his.id_documento " 
		 +" and his.fh_registro = his.fh_registromax "
		 +" and doc.id_documento = rodal.id_documento (+) "
		 +" and (rodal.id_documento is null or rodal.fh_registro = rodal.fh_registromax) "
		 +" and doc.id_documento = adj.id_documento (+) "
		 +" and tip.id_tipo_padre = tpad.id_tipo_documento "
		 +" and tpad.ds_abrev = :abrev "
		 +" and doc.c_anno = :anno "
		 +" and cen.x_centro = doc.x_centro "
		 +" and dat.x_centro = doc.x_centro "
		 +" and prov.c_provincia = dat.c_provincia " 
		 +" and mun.c_provincia = prov.c_provincia "
		 +" and mun.c_municipio = dat.c_municipio "
		 +" and (-1 = :idEstado OR :idEstado = his.ID_ESTADO) "
		 +" and (-1 = :idTipo OR :idTipo = tip.ID_TIPO_DOCUMENTO) " 
		 +" and (-1 = :idCentro OR doc.x_centro = :idCentro) "  
		 +" and (-1 = :idProvincia OR dat.c_provincia = :idProvincia) "
		 +" and (-1 = :idMunicipio OR dat.c_municipio = :idMunicipio) "
		 +" order by FHREGISTRO desc ", nativeQuery = true)
 List<DocumentosGCListProjection> getAllDocumentosGC(@Param("idCentro") Long idCentro, 
              @Param("anno") Integer anno, 
              @Param("idTipo") Long idTipo,
              @Param("idEstado") Long idEstado,
              @Param("idProvincia") Long idProvincia,
              @Param("idMunicipio") Long idMunicipio,
              @Param("abrev") String abrev,
              @Param("idPerfil") Long idPerfil,
              @Param("xEmpleado") Long xEmpleado);
 
 
 @Query(value = " select doc.id_documento AS ID, "
   +"prov.d_provincia AS PROVINCIA, "
   +"mun.d_municipio AS MUNICIPIO, "
   +"tlf_datoscentro(dat.x_centro) AS CENTRO, " 
   +"tip.ds_descripcion AS TIPO,  "
   +"his.ds_nombre AS ESTADO,  "
   +"doc.ds_descripcion AS DESCRIPCION,  "
   +"his.fh_registro AS FHREGISTRO,  "
   +"rodal.ID_DOCHIS_RODAL AS IDRODAL,  "
   +"rodal.TX_DOCHIS_FICHERO AS FICHERO,   "
   +"doc.ds_paraus AS DSPARAUS, "
   +"(select count(*) total from DGC_TIPDOC_ADJUNTOS where lg_principal = 0 and id_tipo_documento = doc.id_tipo_documento) AS PERMITEADJUNTOS, "
   +"rodal.id_adjunto AS idAdjunto,  "
   +"decode(his.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir " 
   +"        where  fir.id_adjunto = rodal.id_adjunto  "
   +"        and    fir.x_empleado = :xEmpleado  "
   +"        and    lg_firmado = 0  "
   +"        and    not exists (select distinct 0 total " 
   +"                           from   dgc_adjunto_firmantes fir2 " 
   +"                           where  fir2.id_adjunto = fir.id_adjunto " 
   +"                           and    fir2.nu_orden < fir.nu_orden  "
   +"                           and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR "
   +"from   DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip, TLCENTROS cen, TLDATOSCEN dat, TLPROVINCIAS prov, TLMUNICIPIOS mun,   "
   +"(select his.id_documento,est.id_estado, est.ds_nombre, est.ds_abrev, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax  "
   +"from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est  "
   +"where  his.id_flujo = flu.id_flujo  "
   +"and flu.id_estado_des = est.id_estado  "
   +"and doc.id_documento = his.id_documento  "
   +"and (-1 = :idCentro OR :idCentro = doc.x_centro) "
   +"and est.ds_abrev <> 'PR' "
   +"and doc.c_anno = :anno  "
   +") his,  "
   +"(select doc.ID_DOCUMENTO, adj.id_adjunto, adj.ID_DOCHIS_RODAL, adj.TX_DOCHIS_FICHERO, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "where doc.id_documento = his.id_documento  "
   + "and his.id_historial = adj.id_historial "
   + "and adj.id_tipo_adjunto = tip.id_tipo_adjunto "
   + "and tip.lg_principal = 1 "
   + "and (-1 = :idCentro OR :idCentro = doc.x_centro) "
   + "and doc.c_anno = :anno "
   + ") rodal   "
   +"where  doc.id_tipo_documento = tip.id_tipo_documento  "
   +"and  doc.id_documento = his.id_documento  "
   +"and  his.fh_registro = his.fh_registromax  "
   +"and  doc.id_documento = rodal.id_documento (+)  "
   +"and (rodal.id_documento is null or rodal.fh_registro = rodal.fh_registromax)  "
   +"and tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :abrev)   "
   +"and doc.c_anno = :anno  "
   +"and cen.x_centro = doc.x_centro "
   +"and dat.x_centro = doc.x_centro "
   +"and prov.c_provincia = dat.c_provincia   "
   +"and mun.c_provincia = prov.c_provincia  "
            +"and mun.c_municipio = dat.c_municipio "
   +"and dat.x_centro in (select dcen.x_centro "
   +"from   TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen "
   +"where zon.x_zona = usu.x_zona "
   +"and zon.x_centro = dcen.x_centro "
   +"and usu.x_perfil = :idPerfil "
   +"and usu.x_usuario = :idUsuario "
   +"and dcen.l_vigente = 'S') "   
   +"and (-1 = :idEstado OR :idEstado = his.ID_ESTADO)  "
   +"and (-1 = :idTipo OR :idTipo = tip.ID_TIPO_DOCUMENTO)  "
   +"and (-1 = :idCentro OR doc.x_centro = :idCentro)             " 
   +"and (-1 = :idProvincia OR dat.c_provincia = :idProvincia)  "
   +"and (-1 = :idMunicipio OR dat.c_municipio = :idMunicipio)   "           
   +"order  by FHREGISTRO desc ", nativeQuery = true)
 List<DocumentosGCListProjection> getAllDocumentosGCZona(@Param("idCentro") Long idCentro, 
                  @Param("anno") Integer anno, 
                  @Param("idTipo") Long idTipo,
                  @Param("idEstado") Long idEstado,
                  @Param("idProvincia") Long idProvincia,
                  @Param("idMunicipio") Long idMunicipio,
                  @Param("idPerfil") Long idPerfil,
                  @Param("idUsuario") Long idUsuario,
                  @Param("abrev") String abrev,
                  @Param("xEmpleado") Long xEmpleado);  
 
 
 @Query(value = "select doc.id_documento AS ID, "
   +"prov.d_provincia AS PROVINCIA, "
   +"mun.d_municipio AS MUNICIPIO, "
   +"tlf_datoscentro(dat.x_centro) AS CENTRO, " 
   +"tip.ds_descripcion AS TIPO,  "
   +"his.ds_nombre AS ESTADO,  "
   +"doc.ds_descripcion AS DESCRIPCION,  "
   +"his.fh_registro AS FHREGISTRO,  "
   +"rodal.ID_DOCHIS_RODAL AS IDRODAL, "
   +"rodal.TX_DOCHIS_FICHERO AS FICHERO,  "
   +"doc.ds_paraus AS DSPARAUS, "
   +"(select count(*) total from DGC_TIPDOC_ADJUNTOS where lg_principal = 0 and id_tipo_documento = doc.id_tipo_documento) AS PERMITEADJUNTOS, "
   +"rodal.id_adjunto AS idAdjunto,  "
   +"decode(his.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir " 
   +"        where  fir.id_adjunto = rodal.id_adjunto  "
   +"        and    fir.x_empleado = :xEmpleado  "
   +"        and    lg_firmado = 0  "
   +"        and    not exists (select distinct 0 total " 
   +"                           from   dgc_adjunto_firmantes fir2 " 
   +"                           where  fir2.id_adjunto = fir.id_adjunto " 
   +"                           and    fir2.nu_orden < fir.nu_orden  "
   +"                           and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR "
   +"from   DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip, TLCENTROS cen, TLDATOSCEN dat, TLPROVINCIAS prov, TLMUNICIPIOS mun,   "
   +"(select his.id_documento,est.id_estado, est.ds_nombre, est.ds_abrev, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax  "
   +"from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est  "
   +"where his.id_flujo = flu.id_flujo  "
   +"and flu.id_estado_des = est.id_estado  "
   +"and doc.id_documento = his.id_documento  "
   +"and (-1 = :idCentro OR :idCentro = doc.x_centro) "
   +"and est.ds_abrev <> 'PR' "
   +"and doc.c_anno = :anno) his,  "
   +"(select doc.ID_DOCUMENTO, adj.id_adjunto, adj.ID_DOCHIS_RODAL, adj.TX_DOCHIS_FICHERO, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "where doc.id_documento = his.id_documento  "
   + "and his.id_historial = adj.id_historial "
   + "and adj.id_tipo_adjunto = tip.id_tipo_adjunto "
   + "and tip.lg_principal = 1 "
   + "and (-1 = :idCentro OR :idCentro = doc.x_centro) "
   + "and doc.c_anno = :anno "
   + ") rodal "
   +"where doc.id_tipo_documento = tip.id_tipo_documento  "
   +"and doc.id_documento = his.id_documento  "
   +"and his.fh_registro = his.fh_registromax  "
   +"and doc.id_documento = rodal.id_documento (+)  "
   +"and (rodal.id_documento is null or rodal.fh_registro = rodal.fh_registromax)  "
   +"and tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :abrev)   "
   +"and doc.c_anno = :anno  "
   +"and cen.x_centro = doc.x_centro "
   +"and dat.x_centro = doc.x_centro "
   +"and prov.c_provincia = dat.c_provincia   "
   +"and mun.c_provincia = prov.c_provincia  "
            +"and mun.c_municipio = dat.c_municipio "
   +"and dat.x_centro in (select distinct dcen.x_centro "
   + "from   TLINSPECTORESCEN insc, TLDATOSCEN dcen "
   + "where  insc.x_centro = dcen.x_centro "
   + "and    insc.x_empleado = :xEmpleado "
   + "and    insc.f_tomapos = :fTomapos "
   + "and    dcen.l_vigente = 'S')   "
   + "and (-1 = :idEstado OR :idEstado = his.ID_ESTADO)  "
   + "and (-1 = :idTipo OR :idTipo = tip.ID_TIPO_DOCUMENTO) "
   + "and (-1 = :idCentro OR doc.x_centro = :idCentro)           "
   + "and (-1 = :idProvincia OR dat.c_provincia = :idProvincia) "
   + "and (-1 = :idMunicipio OR dat.c_municipio = :idMunicipio)  "
   + "order  by FHREGISTRO desc ", nativeQuery = true)
 List<DocumentosGCListProjection> getAllDocumentosGCInspectorCentro(@Param("idCentro") Long idCentro, 
                    @Param("anno") Integer anno, 
                    @Param("idTipo") Long idTipo,
                    @Param("idEstado") Long idEstado,
                    @Param("idProvincia") Long idProvincia,
                    @Param("idMunicipio") Long idMunicipio,
                    @Param("xEmpleado") Long xEmpleado,
                    @Param("fTomapos") Date fTomapos,
                    @Param("abrev") String abrev);
 
 @Query(value = "select doc.id_documento AS ID, "   
   +"prov.d_provincia AS PROVINCIA, "
   +"mun.d_municipio AS MUNICIPIO, "
   +"tlf_datoscentro(dat.x_centro) AS CENTRO, "   
   +"tip.ds_descripcion AS TIPO,  "
   +"his.ds_nombre AS ESTADO,  "
   +"doc.ds_descripcion AS DESCRIPCION,  "
   +"his.fh_registro AS FHREGISTRO,  "
   +"rodal.ID_DOCHIS_RODAL AS IDRODAL,  "
   +"rodal.TX_DOCHIS_FICHERO AS FICHERO,   "
   +"doc.ds_paraus AS DSPARAUS, "
   +"(select count(*) total from DGC_TIPDOC_ADJUNTOS where lg_principal = 0 and id_tipo_documento = doc.id_tipo_documento) AS PERMITEADJUNTOS, "
   +"rodal.id_adjunto AS idAdjunto,  "
   +"decode(his.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir " 
   +"        where  fir.id_adjunto = rodal.id_adjunto  "
   +"        and    fir.x_empleado = :xEmpleado  "
   +"        and    lg_firmado = 0  "
   +"        and    not exists (select distinct 0 total " 
   +"                           from   dgc_adjunto_firmantes fir2 " 
   +"                           where  fir2.id_adjunto = fir.id_adjunto " 
   +"                           and    fir2.nu_orden < fir.nu_orden  "
   +"                           and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR "
   +"from DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip, TLCENTROS cen, TLDATOSCEN dat, TLPROVINCIAS prov, TLMUNICIPIOS mun, "
   +"(select his.id_documento,est.id_estado, est.ds_nombre, est.ds_abrev, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax  "
   +"from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est  "
   +"where  his.id_flujo = flu.id_flujo  "
   +"and flu.id_estado_des = est.id_estado  "
   +"and doc.id_documento = his.id_documento  "
   +"and (-1 = :idCentro OR :idCentro = doc.x_centro)  "
   +"and est.ds_abrev <> 'PR' "
   +"and doc.c_anno = :anno  "
   +" ) his,  "
   +" (select doc.ID_DOCUMENTO, adj.id_adjunto, adj.ID_DOCHIS_RODAL, adj.TX_DOCHIS_FICHERO, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "where doc.id_documento = his.id_documento "
   + "and his.id_historial = adj.id_historial "
   + "and adj.id_tipo_adjunto = tip.id_tipo_adjunto "
   + "and tip.lg_principal = 1 "
   + "and (-1 = :idCentro OR :idCentro = doc.x_centro)  "
   + "and doc.c_anno = :anno "
   + ") rodal  "
   +"where doc.id_tipo_documento = tip.id_tipo_documento  "
   +"and doc.id_documento = his.id_documento  "
   +"and his.fh_registro = his.fh_registromax  "
   +"and doc.id_documento = rodal.id_documento (+)  "
   +"and (rodal.id_documento is null or rodal.fh_registro = rodal.fh_registromax)  "
   +"and tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :abrev)   "
   +"and doc.c_anno = :anno  "
   +"and cen.x_centro = doc.x_centro "
   +"and dat.x_centro = doc.x_centro "
   +"and prov.c_provincia = dat.c_provincia   "
   +"and mun.c_provincia = prov.c_provincia  "
            +"and mun.c_municipio = dat.c_municipio "
   +"and dat.c_provincia in (select distinct prv.c_provincia "
   +"from   TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLMUNICIPIOS mun "
            +"where  u.x_usuario = :idUsuario "
            +"and pto.x_empleado=u.x_empleado "
            +"and pop.x_empleado=pto.x_empleado "
            +"and pop.f_tomapos = pto.f_tomapos "
            +"and pto.x_centro = dcen.x_centro "
            +"and dcen.c_provincia = prv.c_provincia "
            +"and pop.x_perfil = :idPerfil "
            +"and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
            +"and  dcen.c_provincia = mun.c_provincia "
            +"and  pto.x_centro = :idCentroProvincia)  "
   +"and (-1 = :idEstado OR :idEstado = his.ID_ESTADO)  "
   +"and (-1 = :idTipo OR :idTipo = tip.ID_TIPO_DOCUMENTO)  "
   +"and (-1 = :idCentro OR doc.x_centro = :idCentro)             " 
   +"and  (-1 = :idProvincia OR dat.c_provincia = :idProvincia)  "
   +"and   (-1 = :idMunicipio OR dat.c_municipio = :idMunicipio)   "           
   +"order  by FHREGISTRO desc ", nativeQuery = true)
 List<DocumentosGCListProjection> getAllDocumentosGCProvincial(@Param("idCentro") Long idCentro, 
                  @Param("anno") Integer anno, 
                  @Param("idTipo") Long idTipo,
                  @Param("idEstado") Long idEstado,
                  @Param("idProvincia") Long idProvincia,
                  @Param("idMunicipio") Long idMunicipio,
                  @Param("idPerfil") Long idPerfil,
                  @Param("idUsuario") Long idUsuario,
                  @Param("abrev") String abrev,
                  @Param("idCentroProvincia") Long idCentroProvincia,
                  @Param("xEmpleado") Long xEmpleado); 
 
 
 @Query(value = "select doc.id_documento AS ID, "
   + "tip.ds_descripcion AS TIPO, "
   + "his.ds_nombre AS ESTADO, "
   + "doc.ds_descripcion AS DESCRIPCION, "
   + "his.fh_registro AS FHREGISTRO, "
   + "rodal.ID_DOCHIS_RODAL AS IDRODAL, "
   + "rodal.TX_DOCHIS_FICHERO AS FICHERO, "
   +"doc.ds_paraus AS DSPARAUS, "
   +"(select count(*) total from DGC_TIPDOC_ADJUNTOS where lg_principal = 0 and id_tipo_documento = doc.id_tipo_documento) AS PERMITEADJUNTOS, "
   +"rodal.id_adjunto AS idAdjunto,  "
   +"decode(his.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir " 
   +"        where  fir.id_adjunto = rodal.id_adjunto  "
   +"        and    fir.x_empleado = :xEmpleado  "
   +"        and    lg_firmado = 0  "
   +"        and    not exists (select distinct 0 total " 
   +"                           from   dgc_adjunto_firmantes fir2 " 
   +"                           where  fir2.id_adjunto = fir.id_adjunto " 
   +"                           and    fir2.nu_orden < fir.nu_orden  "
   +"                           and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR "
   + "from   DGC_DOCUMENTOS doc, DGC_TIPOS_DOCUMENTOS tip, "
   + "(select his.id_documento,est.id_estado, est.ds_nombre, est.ds_abrev, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
   + "from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est "
   + "where  his.id_flujo = flu.id_flujo "
   + "and flu.id_estado_des = est.id_estado "
   + "and doc.id_documento = his.id_documento "
   + "and (-1 = :idCentro OR :idCentro = doc.x_centro) "
   + "and doc.c_anno = :anno "
   + ") his, "
   + "(select doc.ID_DOCUMENTO, adj.id_adjunto, adj.ID_DOCHIS_RODAL, adj.TX_DOCHIS_FICHERO, his.fh_registro, MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "where doc.id_documento = his.id_documento "
   + "and his.id_historial = adj.id_historial "
   + "and adj.id_tipo_adjunto = tip.id_tipo_adjunto "
   + "and tip.lg_principal = 1 "
   + "and (-1 = :idCentro OR :idCentro = doc.x_centro)  "
   + "and doc.c_anno = :anno "
   + ") rodal "
   + " where doc.id_tipo_documento = tip.id_tipo_documento "
   + " and doc.id_documento = his.id_documento "
   + " and his.fh_registro = his.fh_registromax "
   + " and doc.id_documento = rodal.id_documento (+) "
   + " and (rodal.id_documento is null or rodal.fh_registro = rodal.fh_registromax) "
   + " and tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = 'AR') "
   + " and doc.x_centro = :idCentro "
   + " and doc.c_anno = :anno "
   + " and (-1 = :idEstado OR :idEstado = his.ID_ESTADO) "
   + " and (-1 = :idTipo OR :idTipo = tip.ID_TIPO_DOCUMENTO) "
   + " order  by FHREGISTRO desc ", nativeQuery = true)
 List<DocumentosGCListProjection> getAllDocumentosAR(@Param("idCentro") Long idCentro, 
                       @Param("anno") Integer anno, 
                       @Param("idTipo") Long idTipo,
                       @Param("idEstado") Long idEstado,
                       @Param("xEmpleado") Long xEmpleado);


 @Query(value = "select emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS usuario, "
   + "est.ds_nombre AS estado, "
   + "his.fh_registro AS FHREGISTRO, "
   + "adj.id_dochis_rodal AS IDFICHERO, "
   + "adj.tx_dochis_fichero AS NOMBREFICHERO, "
   + "his.tx_observaciones AS OBSERVACIONES, "
   + "est.tx_aviso AS AVISO  "
   + "from DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est, TLUSUARIOS usu, TLEMPLEADOS emp, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "where his.id_flujo = flu.id_flujo "
   + "and flu.id_estado_des = est.id_estado "
   + "and his.x_usuario = usu.x_usuario (+) "
   + "and usu.x_empleado = emp.x_empleado (+) "
   + "and his.id_documento = :idDocumento "
   //+ "and flu.id_estado_des = :idEstado "
   + "and his.id_historial = :idHistorial "
   + "AND his.id_historial = adj.id_historial (+) "
   + "AND adj.id_tipo_adjunto = tip.id_tipo_adjunto (+) "
   + "ORDER  BY his.fh_registro desc, tip.nu_orden ", nativeQuery = true)
 List<InformacionEstadoPojection> getInformacionEstadoDocumento(@Param("idDocumento") Long idDocumento, 
                         @Param("idHistorial") Long idHistorial);
 
 
 @Query(value="SELECT dogc.id_documento as ID, "
   + "dogc.fh_registro AS FECHA, "
   + "emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS USUARIO, "
   + "est.ds_nombre AS  ESTADO, "
   + "adj.id_dochis_rodal AS  IDRODAL, "
   + "adj.TX_DOCHIS_FICHERO AS DOCUMENTO, "
   + "dogc.TX_OBSERVACIONES AS COMENTARIOS, "
   + "adj.id_adjunto AS IDADJUNTO, "
   + "tip.lg_firmable AS LGFIRMABLE, "
   + "decode(tip.lg_firmable,0,null,decode(adj.lg_firmado,1,'Sí',0,'No',null)) AS LGFIRMADO "
   + "FROM   dgc_documento_historial dogc, tlusuarios usu,dgc_estados_flujo estf,dgc_estados est,tlempleados emp, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tip "
   + "WHERE  dogc.ID_DOCUMENTO = :id_documento "
   + "AND    dogc.x_usuario = usu.x_usuario "
   + "AND    usu.x_empleado = emp.x_empleado "
   + "AND    estf.id_flujo = dogc.id_flujo "
   + "AND    estf.id_estado_des = est.id_estado "
   + "AND    dogc.id_historial = adj.id_historial (+) "
   + "AND    adj.id_tipo_adjunto = tip.id_tipo_adjunto (+) "
   + "ORDER  BY fh_registro desc, tip.nu_orden ",nativeQuery = true)
 List<HistDocumentosGCListProjection>getHistDocumentosGC(@Param("id_documento")Long idDocumento);
 
 @Query(value ="SELECT conv.x_convreunion as id, "
     + "'Convocatoria ' || DECODE(conv.c_tipo, 'O', 'Ordinaria', 'E', 'Extraordinaria')|| ' (' || convierte_hora(conv.n_hora)|| ' del ' || f_reunion || ')' as descripcionConvocatoriaReunion "
     + "FROM tlorganoscen org, "
     + "tlconvreuniones conv, "
     + "tlcursoaca cur, "
     + "dgc_tipos_documentos tip "
     + " WHERE org.x_organo = conv.x_organo "
     + "AND org.c_anno = cur.c_anno "
     + "AND conv.f_reunion BETWEEN cur.f_inicio AND cur.f_final "
     + "AND conv.c_estado = 'R' "
     + "AND org.x_centro = :idCentro "
     + "AND org.c_anno = :anno "
     + "AND org.x_tiporgano = tip.x_tiporgano (+) "
     + "AND tip.id_tipo_documento = :idTipo "
     + "ORDER BY f_reunion DESC ", nativeQuery = true)
 List<ConvocatoriaReunionesProjection> getConvocatorias(@Param("idCentro") Long idCentro, 
                    @Param("anno") Long anno, 
                    @Param("idTipo") Long idTipo);
 
 @Query(value ="select  conv.x_convreunion as id, "
	 + "     'Convocatoria ' || DECODE(conv.c_tipo, 'O', 'Ordinaria', 'E', 'Extraordinaria')|| ' (' || convierte_hora(conv.n_hora)|| ' del ' || f_reunion || ')' as descripcionConvocatoriaReunion "
	 + "  from tlorganoscen org, "
	 + "       dgc_tipos_documentos tip, "
	 + "       tlconvreuniones conv, "
	 + "       tlcursoaca cur "
	 + "  where org.x_centro =:idCentro "
	 + "  AND org.x_tiporgano = tip.x_tiporgano (+) "
	 + "  AND org.x_organo = conv.x_organo "
	 + "  AND tip.id_tipo_documento = :idTipo "
	 + "  AND conv.f_reunion BETWEEN cur.f_inicio AND cur.f_final "
	 + "  AND ((cur.c_anno = :anno AND cur.l_actual = 'S') OR "
	 + "       (org.c_anno = cur.c_anno AND cur.c_anno = :anno)) "
	 + "  AND conv.c_estado = 'R'  "
	 + "  ORDER BY f_reunion DESC ", nativeQuery = true)
 List<ConvocatoriaReunionesProjection> getConvocatoriasConsejoEscolar(@Param("idCentro") Long idCentro, 
															          @Param("anno") Long anno, 
															          @Param("idTipo") Long idTipo);
 
 @Query(value =    "SELECT count(tip.id_tipo_documento) "
      + "FROM DGC_TIPOS_DOCUMENTOS tip, DGC_PLAZOS_ENTREGA pla "
      + "WHERE tip.id_tipo_documento = pla.id_tipo_documento "
      + "AND tip.lg_obligatorio = 1 "
      + "AND pla.c_anno = :anno "
      + "AND pla.c_anno BETWEEN tip.c_anno_desde and nvl(tip.c_anno_hasta,2099) "
      + "AND SYSDATE >= pla.fh_inicio "
      + "AND tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :pdsAbrev) "
      + "AND not exists (SELECT 1 from DGC_DOCUMENTOS doc "
      + "WHERE  doc.id_tipo_documento = tip.id_tipo_documento "
      + "AND    doc.x_centro = :idCentro "
      + "AND    doc.c_anno = pla.c_anno) ", nativeQuery = true)
 Integer getNumeroDocumentosSinCrear(@Param("idCentro") Long idCentro, 
             @Param("anno") Long anno, 
             @Param("pdsAbrev") String pdsAbrev);
 
 @Query(value ="SELECT tip.id_tipo_documento id, "
   + "tip.ds_descripcion || ' (' || to_char(pla.fh_inicio,'DD/MM/YYYY') || '-' || to_char(pla.fh_fin,'DD/MM/YYYY') || ')' descripcion "
   + "FROM   DGC_TIPOS_DOCUMENTOS tip, DGC_PLAZOS_ENTREGA pla "
   + "WHERE tip.id_tipo_documento = pla.id_tipo_documento "
   + "AND tip.lg_obligatorio = 1 "
   + "AND pla.c_anno = :anno  "
   + "AND pla.c_anno BETWEEN tip.c_anno_desde and nvl(tip.c_anno_hasta,2099) "
   + "AND SYSDATE >= pla.fh_inicio "
   + "AND tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :pdsAbrev) "
   + "AND not exists (SELECT 1 from DGC_DOCUMENTOS doc "
   + "WHERE doc.id_tipo_documento = tip.id_tipo_documento "
   + "AND doc.x_centro = :idCentro "
   + "AND doc.c_anno = pla.c_anno) "
   + "order by tip.nu_orden ", nativeQuery = true)
 List<PlazosEntregaDocumentosProjection> getDocumentosNoCreados(@Param("idCentro") Long idCentro, 
                             @Param("anno") Long anno, 
                             @Param("pdsAbrev") String pdsAbrev);

 
 @Query(value ="SELECT pla.id_plazo id, "
   + "tip.ds_descripcion || ' (' || to_char(pla.fh_inicio,'DD/MM/YYYY') || '-' || to_char(pla.fh_fin,'DD/MM/YYYY') || ')' descripcion "
   + "FROM DGC_TIPOS_DOCUMENTOS tip, DGC_PLAZOS_ENTREGA pla "
   + "WHERE tip.id_tipo_documento = pla.id_tipo_documento "
   + "AND tip.lg_obligatorio = 1 "
   + "AND pla.c_anno = :anno  "
   + "AND pla.c_anno BETWEEN tip.c_anno_desde and nvl(tip.c_anno_hasta,2099) "
   + "AND trunc(SYSDATE) > pla.fh_fin "
   + "AND tip.id_tipo_padre = (select id_tipo_documento from DGC_TIPOS_DOCUMENTOS where ds_abrev = :pdsAbrev) "
   + "AND not exists (select 1 from DGC_DOCUMENTOS doc "
   + "where  doc.id_tipo_documento = tip.id_tipo_documento "
   + "and    doc.x_centro = :idCentro "
   + "and    doc.c_anno = pla.c_anno) "
   + "order by tip.nu_orden ", nativeQuery = true)
 List<PlazosEntregaDocumentosProjection> getMensajesPlazosEntregaFinalizado(@Param("idCentro") Long idCentro, 
                                      @Param("anno") Long anno, 
                                   @Param("pdsAbrev") String pdsAbrev);

 @Query(value ="SELECT RTRIM(NLS_INITCAP(TO_CHAR(TO_DATE(PAU.N_MES,'MM'),'MONTH'))) || ' de ' || PAU.N_ANNONATURAL || '/' || PAU.N_ORDEN AS ID, "
   + "RTRIM(NLS_INITCAP(TO_CHAR(TO_DATE(PAU.N_MES,'MM'),'MONTH'))) || ' de ' || PAU.N_ANNONATURAL || '/' || PAU.N_ORDEN AS DESCRIPCION "
   + "FROM TLPARAUS PAU "
   + "WHERE  PAU.X_CENTRO = :idCentro "
   + "AND PAU.C_ANNO = :anno "
   + "AND NOT EXISTS (SELECT 1 FROM DGC_DOCUMENTOS doc "
   + "WHERE doc.x_centro = pau.x_centro "
   + "and   doc.c_anno = pau.c_anno "
   + "and   doc.ds_paraus = RTRIM(NLS_INITCAP(TO_CHAR(TO_DATE(PAU.N_MES,'MM'),'MONTH'))) || ' de ' || PAU.N_ANNONATURAL || '/' || PAU.N_ORDEN ) "
   + "ORDER  BY PAU.N_ANNONATURAL, PAU.N_MES, PAU.N_ORDEN ", nativeQuery = true)
 List<PartesMensualesDocumentosProjection> getPartesMensuales(Long idCentro, Long anno);

 @Query(value= "SELECT RTRIM(NLS_INITCAP(TO_CHAR(TO_DATE(PAU.N_MES,'MM'),'MONTH'))) || ' de ' || PAU.N_ANNONATURAL || '/' || PAU.N_ORDEN AS ID, " + 
   "       PAU.F_REMISION AS FREMISION, " + 
   "       PAU.F_GENERA AS FGENERA, " +
   "       PAU.N_ORDEN AS NORDEN, "  +
            "       PAU.N_MES AS NMES,  " +
            "       PAU.C_ANNO AS CANNO,  " +
            "       nvl((select 1 from DGC_DOCUMENTOS doc " +
            "        where  doc.x_centro = :xCentro " +
            "        and    doc.c_anno = :cAnno " +
            "        and    doc.ds_paraus = RTRIM(NLS_INITCAP(TO_CHAR(TO_DATE(PAU.N_MES,'MM'),'MONTH'))) || ' de ' || PAU.N_ANNONATURAL || '/' || PAU.N_ORDEN and ROWNUM = 1),0) AS LGENERADO " +
   " FROM   TLPARAUS PAU " + 
   " WHERE  PAU.X_CENTRO = :xCentro " + 
   " AND    PAU.C_ANNO = :cAnno " + 
   " ORDER  BY PAU.N_ANNONATURAL, PAU.N_MES, PAU.N_ORDEN", nativeQuery = true)

 List<ParteGeneradoDocumentosProjection> getParteGenerados(@Param("cAnno") Long cAnno, @Param("xCentro") Long xCentro);
 
 @Query(value= "select trim(substr(to_char(ADD_MONTHS(cur.f_inicio,level-1),'MONTH'),1,1)||lower(substr(to_char(ADD_MONTHS(cur.f_inicio,level-1),'MONTH'),2))) || ' - ' || to_char(ADD_MONTHS(cur.f_inicio,level-1),'YYYY') AS parte, "
   + "to_number(to_char(ADD_MONTHS(cur.f_inicio,level-1),'MM')) AS mes, "
   + "to_char(ADD_MONTHS(cur.f_inicio,level-1),'YYYY') AS annonatural  "
   + "from (select cur.f_inicio, cur.f_final "
   + "      from TLCURSOACA cur "
   + "      where cur.c_anno = :cAnno) cur "
   + "CONNECT BY LEVEL <= (round(months_between(cur.f_inicio,cur.f_final)*-1)) ", nativeQuery = true)

 List<CursoProjection> getMesCurso(@Param("cAnno") Long cAnno);
 
 @Query(value = "select NVL(MAX(N_ORDEN)+1, 1) AS orden "
   + "from   tlparaus par "
   + "where  par.x_centro = :idCentro "
   + "and    par.c_anno   = :anno "
   + "and    par.n_mes    = :mes ", nativeQuery = true)
 Integer getNumeroPartesCreados(@Param("idCentro") Long idCentro, 
             @Param("anno") Long anno, 
             @Param("mes") Long mes);

 
 
 @Query(value = " select adj.id_tipo_documento AS ID , "
   + " adj.id_tipo_adjunto AS IDTIPO, "
   + " adj.nu_orden AS ORDEN, "
   + " adj.lg_principal AS PRINCIPAL, "
   + " adj.ds_nombre AS NOMBRE, "
   + " adj.ds_descripcion AS DESCRIPCION, "
   + " adj.lg_firmable AS FIRMABLE, "
   + " adj.nu_tamano AS TAMANO, "
   + " adj.c_anno_desde AS ANNODESDE, "
   + " adj.c_anno_hasta AS  ANNOHASTA"
   + " from   DGC_TIPOS_DOCUMENTOS tip, DGC_TIPDOC_ADJUNTOS adj "
   + " where  tip.id_tipo_documento = adj.id_tipo_documento "
   + " and tip.id_tipo_documento = :idTipDoc "
   + " and :anno between adj.c_anno_desde and nvl(adj.c_anno_hasta,2099) "
   + " and ((adj.lg_principal = 0 and (adj.x_perfil is null or adj.x_perfil = :idPerfil)) "
   + "      or "
   + "      (adj.lg_principal =  1 and (select lg_reqadjunto from DGC_ESTADOS_FLUJO where id_flujo = :idFlujo) = 1))"
   + " order  by adj.lg_principal desc, adj.nu_orden asc ", nativeQuery = true)
 List<TipoAdjuntosListProjection> getInformacionTiposAdjuntosDoc(@Param("anno") Long anno, 
                    @Param("idTipDoc") Long idTipDoc, 
                    @Param("idPerfil") Long idPerfil,
                    @Param("idFlujo") Long idFlujo);


 @Query(value = " select id_adjunto AS IDADJUNTO, "
   + "id_tipo_documento AS IDTIPODOC, "
   + "id_tipo_adjunto AS IDTIPOADJUNTO, "
   + "nu_orden AS ORDEN, "
   + "lg_principal AS PRINCIPAL, "
   + "ds_nombre AS NOMBRE, "
   + "ds_descripcion AS DESCRIPCION, "
   + "lg_firmable AS FIRMABLE, "
   + "nu_tamano AS TAMANO, "
   + "c_anno_desde AS ANNODESDE, "
   + "c_anno_hasta AS  ANNOHASTA, "
   + "id_historial AS IDHISTORIAL, "
   + "lg_firmado AS LGFIRMADO, "
   + "id_dochis_rodal AS IDDOCHISRODAL, "
   + "tx_dochis_fichero AS TXDOCHISFICHERO, "
   + "TO_CHAR(fecha,'dd/MM/yyyy') || ', '|| TO_CHAR(fecha,'hh24:mm:ss') AS FECHAPANTALLA "
   + "from (select * "
   + " from  (select doc.c_anno, doc.id_tipo_documento, his.id_documento, his.fh_registro,  "
   + "  MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax, his.id_historial, adj.id_tipo_adjunto, "
   + "  adj.nu_orden, adj.lg_principal, "
   + "  adj.ds_nombre, adj.ds_descripcion, "
   + "  adj.lg_firmable, adj.nu_tamano, "
   + "  adj.c_anno_desde, adj.c_anno_hasta, "
   + "  hadj.id_adjunto, hadj.lg_firmado, hadj.id_dochis_rodal, hadj.tx_dochis_fichero, nvl(hadj.f_actualiza,hadj.f_creacion) AS fecha  "
   + "from DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_TIPOS_DOCUMENTOS tip, DGC_TIPDOC_ADJUNTOS adj, DGC_HISTORIAL_ADJUNTOS hadj  "
   + "where doc.id_documento = his.id_documento "
   + "and his.id_documento = :idDocumento "
   + "and tip.id_tipo_documento = adj.id_tipo_documento "
   + "and tip.id_tipo_documento = doc.id_tipo_documento "
   + "and his.id_historial = hadj.id_historial (+) "
   + "and adj.id_tipo_adjunto = hadj.id_tipo_adjunto (+) "
   + "and doc.c_anno between adj.c_anno_desde and nvl(adj.c_anno_hasta,2099) "
   + "and adj.lg_principal = 0 "
   + "and (adj.x_perfil is null or adj.x_perfil = :idPerfil) "
   + ") "
   + "where  fh_registro = fh_registromax) ", nativeQuery = true)
 List<AdjuntosListDetalleProjection> getInformacionTiposAdjuntosDoc(@Param("idDocumento") Long idDocumento, 
                    @Param("idPerfil")    Long idPerfil);

 @Query(value = " select decode(fir.lg_firmado,1,0,decode((select count(lg_firmado) total "
   + "                                        from   dgc_adjunto_firmantes fir2 "
   + "                                        where  fir2.id_adjunto = fir.id_adjunto "
   + "                                        and    fir2.nu_orden < fir.nu_orden "
   + "                                        and    fir2.lg_firmado = 0),0,1,0)) AS PERMITEFIRMAR "
   + "from   dgc_adjunto_firmantes fir, dgc_historial_adjuntos adj, dgc_tipdoc_adjuntos tip "
   + "where  fir.id_adjunto = adj.id_adjunto "
   + "and    adj.id_tipo_adjunto = tip.id_tipo_adjunto "
   + "and    tip.lg_firmable = 1 "
   + "and    adj.id_adjunto = :idAjunto "
   + "and    fir.x_empleado = :idEmpleado ", nativeQuery = true)
 String getAdjuntoFirmable(Long idAjunto, Long idEmpleado);


 @Query(value = " select count(*) "
 		+ " from dgc_documentos "
 		+ " where id_tipo_documento = :idTipoDocumento "
 		+ " and x_centro = :idCentro "
 		+ " and ds_descripcion = :name ", nativeQuery = true)
 Integer getNombreDocumentoEnUso(Long idTipoDocumento, Long idCentro, String name);


}
