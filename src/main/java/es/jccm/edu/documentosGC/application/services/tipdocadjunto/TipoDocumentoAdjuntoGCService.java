package es.jccm.edu.documentosGC.application.services.tipdocadjunto;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.application.ports.in.tipdocadjunto.ITipoDocumentoAdjuntoGCService;
import es.jccm.edu.documentosGC.adapter.out.repositories.tipdocadjunto.TipoDocumentoAdjuntoGCRepository;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;

@Service
public class TipoDocumentoAdjuntoGCService implements ITipoDocumentoAdjuntoGCService  {
	
	@Autowired
	private TipoDocumentoAdjuntoGCRepository tipoDocumentoAdjuntoGCRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<TipoDocumentoAdjuntoGC> getTipoDocumentoAdjuntosByIdTipoDocumento(Long idTipoDocumento, Integer cAnno) {
		return tipoDocumentoAdjuntoGCRepository.findByTipoDocumentoId(idTipoDocumento, cAnno);
	}
	
	@Override
	public TipoDocumentoAdjuntoGC getTipoDocumentoAdjuntosGCById(Long idTipoDocumentoAdjunto) {
		return tipoDocumentoAdjuntoGCRepository.findById(idTipoDocumentoAdjunto).orElseThrow();
	}

	@Override
	public TipoDocumentoAdjuntoGC createTipoDocumentoAdjunto(TipoDocumentoAdjuntoGC tipoDocumentoAdjunto) throws Exception {
		Boolean existePrincipal = false;
		Optional<TipoDocumentoAdjuntoGC> tipoDocumentoAdjuntoUpdate = tipoDocumentoAdjuntoGCRepository.findById(tipoDocumentoAdjunto.getId());
		
		if(tipoDocumentoAdjuntoUpdate.isPresent()) {
			tipoDocumentoAdjuntoUpdate.get().setNombre(tipoDocumentoAdjunto.getNombre());
			tipoDocumentoAdjuntoUpdate.get().setDescripcion(tipoDocumentoAdjunto.getDescripcion());
			tipoDocumentoAdjuntoUpdate.get().setOrden(tipoDocumentoAdjunto.getOrden());
			tipoDocumentoAdjuntoUpdate.get().setFirmable(tipoDocumentoAdjunto.getFirmable());
			tipoDocumentoAdjuntoUpdate.get().setPrincipal(tipoDocumentoAdjunto.getPrincipal());
			tipoDocumentoAdjuntoUpdate.get().setAnnoDesde(tipoDocumentoAdjunto.getAnnoDesde());
			tipoDocumentoAdjuntoUpdate.get().setAnnoHasta(tipoDocumentoAdjunto.getAnnoHasta());
			tipoDocumentoAdjuntoUpdate.get().setTamano(tipoDocumentoAdjunto.getTamano());
			
			existePrincipal = tipoDocumentoAdjuntoGCRepository.existePrincipalTipoDocumentoAdjuntosGC(tipoDocumentoAdjuntoUpdate.get().getTipoDocumento().getId(), 
																									  tipoDocumentoAdjuntoUpdate.get().getId(), 
																									  tipoDocumentoAdjunto.getAnnoDesde(), 
																									  tipoDocumentoAdjunto.getAnnoHasta()==null?-1:tipoDocumentoAdjunto.getAnnoHasta(),
					                                                                                  tipoDocumentoAdjunto.getPrincipal()) >= 1;
		}else {
			existePrincipal = tipoDocumentoAdjuntoGCRepository.existePrincipalTipoDocumentoAdjuntosGC(tipoDocumentoAdjunto.getTipoDocumento().getId(), 
					                                                                                  Long.valueOf(0), 
					                                                                                  tipoDocumentoAdjunto.getAnnoDesde(), 
					                                                                                  tipoDocumentoAdjunto.getAnnoHasta()==null?-1:tipoDocumentoAdjunto.getAnnoHasta(),
					                                                                                  tipoDocumentoAdjunto.getPrincipal()) >= 1;
		}
		
		if(Boolean.TRUE.equals(existePrincipal) && tipoDocumentoAdjunto.getPrincipal() == 1)
			throw new Exception("EXISTEPRINCIPAL");
		
		return tipoDocumentoAdjuntoGCRepository.save(tipoDocumentoAdjuntoUpdate.orElse(tipoDocumentoAdjunto));
	}

	@Override
	public void deleteTipoDocumentoAdjunto(Long idTipoDocumentoAdjunto) {
		tipoDocumentoAdjuntoGCRepository.deleteById(idTipoDocumentoAdjunto);
	}

	@Override
	public Integer getSiguienteOrdenTipoDocumentoAdjuntosGC(Long idTipoDocumento) {
		return tipoDocumentoAdjuntoGCRepository.getSiguienteOrdenTipoDocumentoAdjuntosGC(idTipoDocumento);
	}
	

}
