package es.jccm.edu.documentosGC.application.services.centrodoc;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.centrodoc.CentroDocRepository;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocumentosGC;
import es.jccm.edu.documentosGC.application.ports.in.centrodoc.ICentroDocGCService;

@Service
public class CentroDocGCService implements ICentroDocGCService {
	
	@Autowired
	private CentroDocRepository centroDocRepository;
	
	@Override
	public CentroDocumentosGC findById(Long idCentro) {
		Optional<CentroDocumentosGC> centro =  centroDocRepository.findById(idCentro) ;
		
		return centro.orElse(null);
	}

}
