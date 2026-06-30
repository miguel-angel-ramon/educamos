package es.jccm.edu.documentosGC.adapter.out.repositories.tipodoc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.QTipoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.projection.TipoDocProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


public interface TipoDocumentoGCRepository extends AbstractRepository<TipoDocumentosGC, Long, QTipoDocumentosGC> {

    @Query(value = "SELECT doc.* " +
            "FROM dgc_tipos_documentos doc " +
            "WHERE doc.ds_abrev != 'DP' order by 2 ", nativeQuery = true)
	List<TipoDocumentosGC> findAllSinDP();

    @Query(value = " SELECT tip.*" +
            " FROM dgc_tipos_documentos tip " +
            " WHERE tip.id_tipo_padre is not null order by 5,2,4 ", nativeQuery = true)
	List<TipoDocumentosGC> findAllTipos();

    @Query(value = "select tip1.* from DGC_TIPOS_DOCUMENTOS tip , DGC_TIPOS_DOCUMENTOS tip1 "
    		+ "where tip.DS_ABREV = :dsAbrev "
    		+ "and tip1.id_tipo_padre = tip.id_tipo_documento "
    		+ "order by tip1.nu_orden asc ", nativeQuery = true)
	List<TipoDocumentosGC> findAllDocDP(@Param("dsAbrev") String dsAbrev);
    
    
    @Query(value="select tip1.* from DGC_TIPOS_DOCUMENTOS tip , DGC_TIPOS_DOCUMENTOS tip1  "
    		+"where tip.DS_ABREV = :dsAbrev "
    		+"and tip1.id_tipo_padre = tip.id_tipo_documento "
    		+"and :cAnno between tip.c_anno_desde and nvl(tip.c_anno_hasta,2099)"
    		+"and :cAnno between tip1.c_anno_desde and nvl(tip1.c_anno_hasta,2099) "
    		+"order by tip1.nu_orden asc ", nativeQuery = true )
    List<TipoDocumentosGC> findAllDocDP(String dsAbrev, Long cAnno);


	List<TipoDocumentosGC> findByIdTipoDocumentoPadreIsNull();

    @Query(value = " select id_tipo_documento AS id , nu_orden AS orden , ds_abrev AS abrev, ds_descripcion AS descripcion "
    		+ "      from DGC_TIPOS_DOCUMENTOS"
    		+ "      where id_tipo_padre is null "
    		+ "      order by 2", nativeQuery = true)
	List<TipoDocProjection> findAllDocPadres();

    @Query(value = "select  tip.id_tipo_documento AS id , tip.nu_orden AS orden ,tip.ds_abrev AS abrev, tip.ds_descripcion AS descripcion, "
    		+ "tip.lg_anual AS anual, tip.c_anno_desde AS annodesde, tip.c_anno_hasta AS annohasta, DECODE(tip.lg_obligatorio,0,'N','S') AS lgobligatorio, "
    		+ "NVL((select 1 from DGC_ESTADOS_FLUJO where id_tipo_documento = tip.id_tipo_documento "
    		+ "UNION "
    		+ "select 1 from DGC_PLAZOS_ENTREGA  where id_tipo_documento = tip.id_tipo_documento),0) AS noborrable, "
    		+ "padres.ds_descripcion AS nombrepadre "
    		+ "from DGC_TIPOS_DOCUMENTOS tip , (select id_tipo_documento, nu_orden, ds_abrev, ds_descripcion from DGC_TIPOS_DOCUMENTOS "
    		+ "where id_tipo_padre is null "
    		+ "and ('-1' = :dsAbrev  or ds_abrev = :dsAbrev)) padres "
    		+ "where tip.id_tipo_padre is not null "
    		+ "and tip.id_tipo_padre = padres.id_tipo_documento "
    		+ "and :cAnno BETWEEN tip.c_anno_desde AND NVL(tip.c_anno_hasta,9999) "
    		+ "order by padres.nu_orden, tip.nu_orden", nativeQuery = true)
    List<TipoDocProjection>  findAllDocSinPadres(Long cAnno, String dsAbrev);

	Optional<TipoDocumentosGC> findByAbrev(String dsAbrev);

}


