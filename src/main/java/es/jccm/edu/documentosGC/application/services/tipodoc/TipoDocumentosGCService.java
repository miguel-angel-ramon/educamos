package es.jccm.edu.documentosGC.application.services.tipodoc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.application.ports.in.tipodoc.ITipoDocumentosGCService;
import es.jccm.edu.documentosGC.adapter.out.repositories.tipodoc.TipoDocumentoGCRepository;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.ListadoTipoDocumentoGC;

@Service
public class TipoDocumentosGCService implements ITipoDocumentosGCService  {
	
	@Autowired
	private TipoDocumentoGCRepository tiposDocumentosGCRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<TipoDocumentosGC> tiposDocumentosGC() {
		return tiposDocumentosGCRepository.findAllTipos();
	}
	
	@Override
	public Long getCountTiposDocumentos() {
		return tiposDocumentosGCRepository.count();
	}


	@Override
	public TipoDocumentosGC findById (Long idTipo) {
		
		Optional<TipoDocumentosGC> tipo =  tiposDocumentosGCRepository.findById(idTipo) ;
		
		return tipo.orElse(null);

	}

	@Override
	public List<TipoDocumentosGC> tiposDocumentosProgramativos(String dsAbrev) {
		return tiposDocumentosGCRepository.findAllDocDP(dsAbrev);
	}
	
	@Override
	public List<TipoDocumentosGC> tiposDocumentosProgramativos(String dsAbrev, Long cAnno) {
		return tiposDocumentosGCRepository.findAllDocDP(dsAbrev, cAnno);
	}
	
	
	public List<TipoDocumentosGC> tiposDocumentosProgramaticosPadre() {
		return tiposDocumentosGCRepository.findByIdTipoDocumentoPadreIsNull();
	}

	@Override
	public TipoDocumentosGC createTipoDocumento(TipoDocumentosGC tipoDocumento) {
		
		Optional<TipoDocumentosGC> tipoDocumentoUpdate = tiposDocumentosGCRepository.findById(tipoDocumento.getId());
			
		if(tipoDocumentoUpdate.isPresent()) {
			tipoDocumentoUpdate.get().setOrden(tipoDocumento.getOrden());
			tipoDocumentoUpdate.get().setAbrev(tipoDocumento.getAbrev());
			tipoDocumentoUpdate.get().setDescripcion(tipoDocumento.getDescripcion());
			tipoDocumentoUpdate.get().setAnnodesde(tipoDocumento.getAnnodesde());
			tipoDocumentoUpdate.get().setAnnohasta(tipoDocumento.getAnnohasta());
			tipoDocumentoUpdate.get().setAnual(tipoDocumento.getAnual());
			tipoDocumentoUpdate.get().setLgObligatorio(tipoDocumento.getLgObligatorio());
			tipoDocumentoUpdate.get().setIdTipoDocumentoPadre(tipoDocumento.getIdTipoDocumentoPadre());	
		}else			
			tipoDocumentoUpdate =  Optional.of(tipoDocumento);
		
		return tiposDocumentosGCRepository.save(tipoDocumentoUpdate.orElseThrow());
	}
	
	public List<TipoDocumentosGC> tiposDocumentosProgramativosPadres() {		
		
		return tiposDocumentosGCRepository.findAllDocPadres().stream()
				.map(entity -> modelMapper.map(entity, TipoDocumentosGC.class)).collect(Collectors.toList());	
		
		
	}

	@Override
	public List<ListadoTipoDocumentoGC> tiposDocumentosSinPadre(Long cAnno, String dsAbrev) {
		
		return tiposDocumentosGCRepository.findAllDocSinPadres(cAnno,dsAbrev).stream()
				.map(entity -> modelMapper.map(entity, ListadoTipoDocumentoGC.class)).collect(Collectors.toList());	
	}

	@Override
	public void deleteTipoDocumentoGC(Long idTipoDocumento) {
		tiposDocumentosGCRepository.deleteById(idTipoDocumento);		
	}

	@Override
	public TipoDocumentosGC tipoDocumentoByAbrev(String dsAbrev) {
		
		Optional<TipoDocumentosGC> tipo = tiposDocumentosGCRepository.findByAbrev(dsAbrev);
		
		return tipo.orElse(null);
		
	}

	

}
