package es.jccm.edu.documentosGC.application.services.historicodoc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.historicodoc.HistoricoDocumentoGCRepository;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;
import es.jccm.edu.documentosGC.application.ports.in.historicodoc.IHistoricoDocumentoGCService;

@Service
public class HistoricoDocumentoGCService implements IHistoricoDocumentoGCService {

	@Autowired
	private HistoricoDocumentoGCRepository historicoDocRepository;
	
	@Override
	public void save(HistoricoDocumentoGC historico) {

		historicoDocRepository.save(historico);
		
	}

	@Override
	public void delete(HistoricoDocumentoGC historico) {

		historicoDocRepository.delete(historico);
		
	}

	@Override
	public List<HistoricoDocumentoGC> getHistoricoDocumentoId(DocumentosGC documento) {
		return  historicoDocRepository.findAllByDocumento(documento);
	}

	@Override
	public HistoricoDocumentoGC findById(Long id) {
		Optional<HistoricoDocumentoGC> historico =  historicoDocRepository.findById(id);
		
		return historico.orElse(null);
	}
	

}
