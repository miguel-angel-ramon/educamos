package es.jccm.edu.documentosGC.adapter.out.repositories.bandejafirmas;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.BandejaFirmasListProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.EstadosBandejaProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.TipoAdjuntoBandejaProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.TipoDocumentoBandejaProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.QDocumentosGC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface BandejaFirmasRepository extends AbstractRepository<DocumentosGC, Long, QDocumentosGC>  {

	@Query(value =" select distinct tdoc.id_tipo_documento AS id, "
			+ "                     tdoc.ds_abrev AS abreviatura,"
			+ "                     decode(tdoc2.ds_abrev,'AE',tdoc2.ds_descripcion|| '  - ','') || tdoc.ds_descripcion AS descripcion, "
			+ "                     tdoc2.nu_orden AS  ordenpadre, "
			+ "                     tdoc.nu_orden AS orden "
			+ "from   DGC_TIPOS_DOCUMENTOS tdoc, DGC_TIPDOC_ADJUNTOS tadj, DGC_TIPOS_DOCUMENTOS tdoc2 "
			+ "where  tdoc.id_tipo_documento = tadj.id_tipo_documento "
			+ "and    tdoc.id_tipo_padre = tdoc2.id_tipo_documento "
			+ "and    tadj.lg_firmable = 1 "
			+ "and    :cAnno between tdoc.c_anno_desde and nvl(tdoc.c_anno_hasta,2099) "
			+ "and    :cAnno between tadj.c_anno_desde and nvl(tadj.c_anno_hasta,2099) "
			+ "order  by tdoc2.nu_orden, tdoc.nu_orden  ", nativeQuery = true)
	List<TipoDocumentoBandejaProjection> getTiposDocumentoBandeja(Long cAnno);

	@Query(value =" select distinct tadj.id_tipo_adjunto AS id, "
			+ "                     tadj.ds_nombre AS nombre, "
			+ "                     tdoc.nu_orden AS ordendocumento, "
			+ "                     tadj.nu_orden AS ordenadjunto "
			+ "from   DGC_TIPOS_DOCUMENTOS tdoc, DGC_TIPDOC_ADJUNTOS tadj "
			+ "where  tdoc.id_tipo_documento = tadj.id_tipo_documento "
			+ "and    tadj.lg_firmable = 1 "
			+ "and    :cAnno between tdoc.c_anno_desde and nvl(tdoc.c_anno_hasta,2099) "
			+ "and    :cAnno between tadj.c_anno_desde and nvl(tadj.c_anno_hasta,2099) "
			+ "and    (:idTipoDocumento = -1 or tdoc.id_tipo_documento = :idTipoDocumento) "
			+ "order  by tdoc.nu_orden, tadj.nu_orden ", nativeQuery = true)
	List<TipoAdjuntoBandejaProjection> getTiposAdjuntosBandeja(Long cAnno, Long idTipoDocumento);
	
	@Query(value =" select distinct est.id_estado AS id, "
			+ "                     est.ds_nombre AS nombre "
			+ "from   DGC_TIPOS_DOCUMENTOS tip, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est, TLCURSOACA cur "
			+ "where  tip.id_tipo_documento = flu.id_tipo_documento "
			+ "and    flu.id_estado_des = est.id_estado "
			+ "and    tlf_intersecper(est.fh_inicio, est.fh_fin, cur.f_inicio, cur.f_final) = 1 "
			+ "and    cur.c_anno between tip.c_anno_desde and nvl(tip.c_anno_hasta,2099) "
			+ "and    cur.c_anno = :cAnno "
			+ "and    (:idTipoDocumento = -1 or tip.id_tipo_documento = :idTipoDocumento) "
			+ "order  by est.ds_nombre ", nativeQuery = true)
	List<EstadosBandejaProjection> getEstadosDocumentoBandeja(Long cAnno, Long idTipoDocumento);
	
	@Query(value =" select doc.ID_DOCUMENTO AS id, "
			+ "       decode(tadj.lg_principal,1,'Pr.','Adj.') AS principal, "
			+ "       tdoc.ds_descripcion As tipodocumento, "
			+ "       tadj.ds_nombre AS tipoadjunto, "
			+ "       doc.ds_descripcion AS nombre, "
			+ "       his.fh_registro AS fechageneracion, "
			+ "       fir.LG_FIRMADO AS lgfirmado, "
			+ "       fir.FH_FIRMA AS fechafirma, "
			+ "       est.ds_nombre AS estado, "
			+ "       adj.ID_DOCHIS_RODAL AS idrodal, "
			+ "       adj.TX_DOCHIS_FICHERO As fichero, "
			+ "       adj.id_adjunto AS idAdjunto, "
			+ "       decode(est.ds_abrev,'PF',nvl((select 1 from dgc_adjunto_firmantes fir "
			+ "              where  fir.id_adjunto = adj.id_adjunto "
			+ "              and    fir.x_empleado = :idEmpleado "
			+ "              and    lg_firmado = 0 "
			+ "              and    not exists (select distinct 0 total "
			+ "                                 from   dgc_adjunto_firmantes fir2 "
			+ "                                 where  fir2.id_adjunto = fir.id_adjunto "
			+ "                                 and    fir2.nu_orden < fir.nu_orden "
			+ "                                 and    fir2.lg_firmado = 0)),0),0) as PERMITEFIRMAR "
			+ "from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_HISTORIAL_ADJUNTOS adj, DGC_TIPDOC_ADJUNTOS tadj, DGC_ADJUNTO_FIRMANTES fir, DGC_TIPOS_DOCUMENTOS tdoc, "
			+ "       (select doc.id_documento, est.id_estado, est.ds_nombre, est.ds_abrev, his.fh_registro, "
			+ "        MAX(his.fh_registro) OVER (PARTITION BY his.id_documento) AS fh_registromax "
			+ "        from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est "
			+ "        where  doc.id_documento = his.id_documento "
			+ "        and    his.id_flujo = flu.id_flujo "
			+ "        and    flu.id_estado_des = est.id_estado "
			+ "        and    doc.c_anno = :cAnno "
			+ "        and    exists (select 1 "
			+ "                       from   DGC_DOCUMENTO_HISTORIAL his2, DGC_HISTORIAL_ADJUNTOS adj2, DGC_ADJUNTO_FIRMANTES fir2 "
			+ "                       where  his2.id_historial = adj2.id_historial "
			+ "                       and    adj2.id_adjunto = fir2.id_adjunto "
			+ "                       and    fir2.x_empleado = :idEmpleado "
			+ "                       and    his2.id_documento = doc.id_documento)) est "
			+ "where  doc.id_documento = his.id_documento "
			+ "and    his.id_historial = adj.id_historial "
			+ "and    adj.id_tipo_adjunto = tadj.id_tipo_adjunto "
			+ "and    adj.id_adjunto = fir.id_adjunto "
			+ "and    doc.id_tipo_documento = tdoc.id_tipo_documento "
			+ "and    doc.id_documento = est.id_documento "
			+ "and    est.fh_registro = est.fh_registromax "
			+ "and    fir.x_empleado = :idEmpleado "
			+ "and    tadj.lg_firmable = 1 "
			+ "and    doc.c_anno = :cAnno "
			+ "and    (:idTipoDocumento = -1 or tdoc.id_tipo_documento = :idTipoDocumento) "
			+ "and    (:idTipoAdjunto = -1 or tadj.id_tipo_adjunto = :idTipoAdjunto) "
			+ "and    (:idEstado = -1 or est.id_estado = :idEstado ) "
			+ "and    (:fechaInicioGeneracion IS NULL or :fechaFinGeneracion IS NULL "
			+ "OR his.fh_registro BETWEEN :fechaInicioGeneracion AND :fechaFinGeneracion) "
			+ "and    (:fechaInicioFirma IS NULL or :fechaFinFirma IS NULL "
			+ "OR fir.fh_firma BETWEEN :fechaInicioFirma AND :fechaFinFirma) "
			+ "and    (:miFirma = 0 "
			+ "        or "
	        + "        (:miFirma = 1 and (decode(fir.lg_firmado,1,0,nvl((select distinct 0 total "
            + "                                                          from   dgc_adjunto_firmantes fir2 "
            + "                                                          where  fir2.id_adjunto = adj.id_adjunto "
            + "                                                          and    fir2.nu_orden < fir.nu_orden "
            + "                                                          and    fir2.lg_firmado = 0),1)) = 1)) "
			+ "       ) "
			+ "and ('-1' IN (:perfiles) OR fir.DS_DESCRIPCION IN (:perfiles)) "
			+ "order  by fechageneracion desc, tdoc.nu_orden, doc.id_documento, tdoc.nu_orden ", nativeQuery = true)
	List<BandejaFirmasListProjection> getAllDocumentosBandeja(Long cAnno, 
														      Long idTipoDocumento, 
														      Long idTipoAdjunto,
														      Long idEstado,
														      Integer miFirma, 
														      Long idEmpleado,
														      @Temporal Date fechaInicioGeneracion,
														      @Temporal Date fechaFinGeneracion,
														      @Temporal Date fechaInicioFirma,
														      @Temporal Date fechaFinFirma,
														      List<String> perfiles);
	
	
	
	
	
}
