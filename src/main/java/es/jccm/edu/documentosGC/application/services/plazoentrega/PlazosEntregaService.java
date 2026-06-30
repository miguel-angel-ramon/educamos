package es.jccm.edu.documentosGC.application.services.plazoentrega;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.application.ports.in.plazoentrega.IPlazosEntregaService;
import es.jccm.edu.documentosGC.adapter.out.repositories.plazosentrega.PlazosEntregaRepository;
import es.jccm.edu.documentosGC.application.domain.plazoentrega.entities.PlazosEntrega;

@Service
public class PlazosEntregaService implements IPlazosEntregaService  {
	
	@Autowired
	private PlazosEntregaRepository plazosEntregaRepository;

	@Override
	public PlazosEntrega findById (Long idPlazo) {
		
		Optional<PlazosEntrega> plazoEntrega =  plazosEntregaRepository.findById(idPlazo) ;
		
		return plazoEntrega.orElse(null);
	}
	
	@Override
	public PlazosEntrega findByCAnnoAndTipoId (Long idTipoDocumento, Integer cAnno) {
		
		Optional<PlazosEntrega> plazoEntrega =  plazosEntregaRepository.findBycAnnoAndTipoId(cAnno, idTipoDocumento);
		
		return plazoEntrega.orElse(null);
	}
	
	@Override
	public PlazosEntrega createPlazosEntrega(PlazosEntrega plazoEntrega) {
		
		Optional<PlazosEntrega> plazoEntregaUpdate = plazosEntregaRepository.findById(plazoEntrega.getId());
			
		if(plazoEntregaUpdate.isPresent()) {
			plazoEntregaUpdate.get().setCAnno(plazoEntrega.getCAnno());
			plazoEntregaUpdate.get().setFechaInicio(plazoEntrega.getFechaInicio());
			plazoEntregaUpdate.get().setFechaFin(plazoEntrega.getFechaFin());
		}else			
			plazoEntregaUpdate =  Optional.of(plazoEntrega);
		
		
		return plazosEntregaRepository.save(plazoEntregaUpdate.orElseThrow());
	}


}
