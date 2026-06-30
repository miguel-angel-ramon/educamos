package es.jccm.edu.documentosGC.application.ports.in.tipodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.ListadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;

public interface ITipoDocumentosGCService {
	
	List<TipoDocumentosGC> tiposDocumentosGC();
	
	TipoDocumentosGC findById(Long idTipo);

	List<TipoDocumentosGC> tiposDocumentosProgramativos(String dsAbrev);
	
	List<TipoDocumentosGC> tiposDocumentosProgramativos(String dsAbrev,Long cAnno);

	List<TipoDocumentosGC> tiposDocumentosProgramaticosPadre();

	TipoDocumentosGC createTipoDocumento(TipoDocumentosGC tipoDocumento);
	
	List<TipoDocumentosGC> tiposDocumentosProgramativosPadres();
	
	List<ListadoTipoDocumentoGC> tiposDocumentosSinPadre(Long cAnno, String dsAbrev);

	void deleteTipoDocumentoGC(Long idTipoDocumento);

	Long getCountTiposDocumentos();

	TipoDocumentosGC tipoDocumentoByAbrev(String dsAbrev);

}
