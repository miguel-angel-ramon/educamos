package es.jccm.edu.documentosGC.application.services.flujodoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.estadodoc.EstadoDocumentoGCRepository;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.ListadoEstadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.perfiles.entities.PerfilGC;
import es.jccm.edu.documentosGC.application.domain.perfiles.projection.PerfilGCProjection;
import es.jccm.edu.documentosGC.application.ports.in.flujodoc.IFlujoDocumentosGCService;
import es.jccm.edu.documentosGC.application.ports.out.FlujoDocumentoGCRepository;

@Service
public class FlujoDocumentosGCService implements IFlujoDocumentosGCService {
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private FlujoDocumentoGCRepository flujoRepository;
	
	@Autowired
	private EstadoDocumentoGCRepository estadoDocumentoGCRepository;
	
	@Override
	public FlujoDocumentoGC flujoSiguienteDocumentosGC(Long idEstadoOri, Long idEstadoDes, Long idPerfil,
													   Long idTipoDoc) {
		Optional<FlujoDocumentoGC> flujo = flujoRepository.findFlujoSiguiente(idEstadoOri,idEstadoDes,idPerfil,idTipoDoc);
		
		return flujo.orElse(null);
	} 
	
	@Override
	public List<FlujoDocumentoGC> getEstadosFlujosByIdTipoDocumento(Long idTipoDocumento){
		return flujoRepository.findAllByTipoIdAndBorrado(idTipoDocumento, 0);
	}
	
	private List<EstadoDocumentoGC> mapearEstados( List<ListadoEstadoTipoDocumentoGC> projection){
		List<EstadoDocumentoGC> estados = new ArrayList<>();		
		projection.forEach(estProj -> {
			EstadoDocumentoGC estado = new EstadoDocumentoGC();
			estado.setId(estProj.getId());
			estado.setDsNombre(estProj.getNombre());
			estados.add(estado);
		});
		
		return estados;
	}

	@Override
	public List<EstadoDocumentoGC> getEstadosOrigen() {
		List<ListadoEstadoTipoDocumentoGC> estadosProjection = estadoDocumentoGCRepository.findEstadosOrigen().stream()
				.map(entity -> modelMapper.map(entity, ListadoEstadoTipoDocumentoGC.class)).collect(Collectors.toList());
		
		return mapearEstados(estadosProjection);
	}

	@Override
	public List<EstadoDocumentoGC> getEstadosDestino(Long idEstadoOrigen) {
		List<ListadoEstadoTipoDocumentoGC> estadosProjection = estadoDocumentoGCRepository.findEstadosDestino(idEstadoOrigen).stream()
				.map(entity -> modelMapper.map(entity, ListadoEstadoTipoDocumentoGC.class)).collect(Collectors.toList());
		
		return mapearEstados(estadosProjection);
	}

	@Override
	public List<PerfilGC> getPerfilesEstadosFlujo() {
		List<PerfilGCProjection> perfilProjection = estadoDocumentoGCRepository.findAllPerfilesEstadosFlujo();
		
		return perfilProjection.stream().map(entity -> modelMapper.map(entity, PerfilGC.class)).collect(Collectors.toList());
	}

	@Override
	public List<FlujoDocumentoGC> createEstadoFlujoDocumento(List<FlujoDocumentoGC> flujoDocumentoGCListIn, Long idTipoDocumento) {			
		return (List<FlujoDocumentoGC>) flujoRepository.saveAll(flujoDocumentoGCListIn);
	}

	@Override
	public void deleteEstadoFlujoDocumento(List<FlujoDocumentoGC> flujoDocumentoGCListIn) {
		flujoDocumentoGCListIn.forEach(flujo ->{
			try {
				flujoRepository.deleteById(flujo.getId());
			}catch (Exception e) {
				deleleEstadoFlujoDocumentoLogico(flujo);
			}
		});
	}
	
	private void deleleEstadoFlujoDocumentoLogico(FlujoDocumentoGC flujo) {
		
		Optional<FlujoDocumentoGC> flujoOptional = flujoRepository.findById(flujo.getId());
		if(flujoOptional.isPresent()) {
			flujoOptional.get().setFechaBorrado(flujo.getFechaBorrado());
			flujoOptional.get().setBorrado(flujo.getBorrado());
			flujoOptional.get().setUsuBorrado(flujo.getUsuBorrado());
			
			flujoRepository.save(flujoOptional.get());
		}
	}

	@Override
	public Integer countDocumentosSinAdjuntosByIdFlujo(Long idFlujo) {
		return flujoRepository.countDocumentosSinAdjuntosByIdFlujo(idFlujo);
	}

	@Override
	public Integer countDocumentosAdjuntosByIdFlujo(Long idFlujo) {
		return flujoRepository.countDocumentosAdjuntosByIdFlujo(idFlujo);
	}

	@Override
	public FlujoDocumentoGC findById(Long id) {
		Optional<FlujoDocumentoGC> flujoOptional = flujoRepository.findById(id);
		return flujoOptional.isPresent()?flujoOptional.get():null;
	}

}
