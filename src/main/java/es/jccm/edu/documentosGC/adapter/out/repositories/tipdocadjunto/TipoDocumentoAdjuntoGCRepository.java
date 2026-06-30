package es.jccm.edu.documentosGC.adapter.out.repositories.tipdocadjunto;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.QTipoDocumentoAdjuntoGC;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface TipoDocumentoAdjuntoGCRepository extends AbstractRepository<TipoDocumentoAdjuntoGC, Long, QTipoDocumentoAdjuntoGC> {

	List<TipoDocumentoAdjuntoGC> findByTipoDocumentoId(Long idTipoDocumento);

	@Query(value = "SELECT adj.*  "
			+ "  FROM DGC_TIPDOC_ADJUNTOS adj "
			+ " WHERE ID_TIPO_DOCUMENTO  = :idTipoDocumento "
			+ "   AND :cAnno BETWEEN C_ANNO_DESDE AND NVL(C_ANNO_HASTA, 9999) "
			+ " ORDER BY adj.NU_ORDEN ASC", nativeQuery = true)
	List<TipoDocumentoAdjuntoGC> findByTipoDocumentoId(Long idTipoDocumento, Integer cAnno);

	@Query(value = "SELECT SIGORDEN "
			+ "  FROM (SELECT (NU_ORDEN+1) AS SIGORDEN "
			+ "          FROM DGC_TIPDOC_ADJUNTOS "
			+ "         WHERE ID_TIPO_DOCUMENTO = :idTipoDocumento "
			+ "      ORDER BY NU_ORDEN DESC) "
			+ " WHERE ROWNUM = 1", nativeQuery = true)
	Integer getSiguienteOrdenTipoDocumentoAdjuntosGC(Long idTipoDocumento);
	
	@Query(value = "SELECT COUNT(*) AS EXISTEPRINCIPAL "
			+ "      FROM DGC_TIPDOC_ADJUNTOS "
			+ "     WHERE ID_TIPO_DOCUMENTO = :idTipoDocumento "
			+ "       AND LG_PRINCIPAL = 1 "
			+ "       AND tlf_intersecper(TO_DATE('01/01/'||C_ANNO_DESDE, 'DD/MM/YYYY'), TO_DATE('31/12/'||NVL(C_ANNO_HASTA, 9999), 'DD/MM/YYYY'), "
			+ "                           TO_DATE('01/01/'||:cAnnoDesde, 'DD/MM/YYYY'), TO_DATE('31/12/'||DECODE(:cAnnoHasta,-1, 9999,:cAnnoHasta), 'DD/MM/YYYY')) = 1 "
			+ "       AND (SELECT DECODE(COUNT(*), 1, 'N', 'S') "
			+ "                 FROM DGC_TIPDOC_ADJUNTOS  "  
		    + "                WHERE ID_TIPO_ADJUNTO = :idTipoDocumentoAdjunto " 
		    + "                  AND :principal = LG_PRINCIPAL) = 'S'  "
			+ "  ORDER BY NU_ORDEN DESC", nativeQuery = true)
	Integer existePrincipalTipoDocumentoAdjuntosGC(Long idTipoDocumento, Long idTipoDocumentoAdjunto, Integer cAnnoDesde, Integer cAnnoHasta, Integer principal);
	
	@Query(value = "SELECT COUNT(NU_ORDEN) EXISTEORDEN "
			+ "      FROM DGC_TIPDOC_ADJUNTOS "
			+ "     WHERE ID_TIPO_DOCUMENTO = :idTipoDocumento  "
			+ "       AND :orden <= (SELECT SIGORDEN  "
			+ "                       FROM (SELECT NU_ORDEN AS SIGORDEN  "
			+ "                               FROM DGC_TIPDOC_ADJUNTOS "
			+ "                              WHERE ID_TIPO_DOCUMENTO = :idTipoDocumento  "
			+ "                           ORDER BY NU_ORDEN DESC) "
			+ "                      WHERE ROWNUM = 1) "
			+ "       AND (SELECT DECODE(COUNT(*), 1, 'N', 'S') "
			+ "              FROM DGC_TIPDOC_ADJUNTOS  "
			+ "             WHERE ID_TIPO_ADJUNTO = :idTipoDocumentoAdjunto "
			+ "               AND :orden = NU_ORDEN) = 'S' "
			+ "       AND :cAnno BETWEEN C_ANNO_DESDE AND NVL(C_ANNO_HASTA, 9999) "
			+ "  ORDER BY NU_ORDEN DESC", nativeQuery = true)
	Integer existeOrdenTipoDocumentoAdjuntosGC(Long idTipoDocumento, Long idTipoDocumentoAdjunto , Integer orden, Integer cAnno);
}


