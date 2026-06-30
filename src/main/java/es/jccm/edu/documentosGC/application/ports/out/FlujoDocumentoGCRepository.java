package es.jccm.edu.documentosGC.application.ports.out;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.QFlujoDocumentoGC;

public interface FlujoDocumentoGCRepository extends AbstractRepository<FlujoDocumentoGC, Long, QFlujoDocumentoGC> {

	 @Query(value ="select * from DGC_ESTADOS_FLUJO "
	 		+ "where nvl(id_estado_ori,-1) = :idEstadoOri "
	 		+ "and id_estado_des = :idEstadoDes "
	 		+ "and x_perfil = :idPerfil "
	 		+ "and id_tipo_documento = :idTipoDoc ", nativeQuery = true)
	Optional<FlujoDocumentoGC> findFlujoSiguiente(Long idEstadoOri, Long idEstadoDes, Long idPerfil,Long idTipoDoc);

	List<FlujoDocumentoGC> findAllByTipoId(Long idTipoDocumento);

	 @Query(value ="select count(*) adjuntos "
	 		+ "from   DGC_DOCUMENTO_HISTORIAL his "
	 		+ "where  his.id_dochis_rodal is null "
	 		+ "and    his.id_flujo = :idFlujo", nativeQuery = true)
	Integer countDocumentosSinAdjuntosByIdFlujo(Long idFlujo);

	 @Query(value ="select count(*) documentosFlujos "
	 		+ "from  (select his.id_documento, "
	 		+ "              his.id_historial, "
	 		+ "              his.id_flujo, "
	 		+ "              decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_documento),'S','N') AS lactual "
	 		+ "       from   DGC_DOCUMENTOS doc, DGC_DOCUMENTO_HISTORIAL his "
	 		+ "       where  doc.id_documento = his.id_documento) a "
	 		+ "where  a.lactual = 'S' "
	 		+ "and    a.id_flujo = :idFlujo", nativeQuery = true)
	Integer countDocumentosAdjuntosByIdFlujo(Long idFlujo);

	List<FlujoDocumentoGC> findAllByTipoIdAndBorrado(Long idTipoDocumento, int i);

}
