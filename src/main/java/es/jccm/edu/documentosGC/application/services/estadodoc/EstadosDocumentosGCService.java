package es.jccm.edu.documentosGC.application.services.estadodoc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.estadodoc.EstadoDocumentoGCRepository;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoFlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.ListadoEstadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.EstadoFlujoDocumentoGCProjection;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.LineaEstadoProjection;
import es.jccm.edu.documentosGC.application.ports.in.estadodoc.IEstadosDocumentosGCService;

@Service
public class EstadosDocumentosGCService implements IEstadosDocumentosGCService {
	
	@Autowired
	private EstadoDocumentoGCRepository  estadoDocumentoGCRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<EstadoDocumentoGC> estadosDocumentosGC(Long idPerfil, Long cAnno, String dsAbrevPadre) {
		return (List<EstadoDocumentoGC>) estadoDocumentoGCRepository.findAllEstadosDocGC(idPerfil,
																						 cAnno,
																						 dsAbrevPadre);
	}
	

	@Override
	public List<EstadoFlujoDocumentoGC> estadoInicialDocumentosGC(Long idPerfil, Long idTipodoc, Long cAnno) {
		
		List<EstadoFlujoDocumentoGCProjection> estadoP = estadoDocumentoGCRepository.findEstadoInicialDocGC(idPerfil,idTipodoc,cAnno); 

		return estadoP.stream().map(entity -> modelMapper.map(entity, EstadoFlujoDocumentoGC.class)).collect(Collectors.toList());
				
		
	}

	@Override
	public EstadoDocumentoGC findById(Long idEstado) {
		Optional<EstadoDocumentoGC> estado =  estadoDocumentoGCRepository.findById(idEstado) ;
		
		return estado.orElse(null);
	}	
	
	@Override
	public Long getEstadoId(String abrevEstado ) {
		return estadoDocumentoGCRepository.getEstadoId(abrevEstado);
	}
	@Override
	public EstadoDocumentoGC estadoActualDocumentosGC(Long idDocumento) {
		return estadoDocumentoGCRepository.getEstadoActual(idDocumento); 
	}

	@Override
	public List<LineaEstadoProjection> lineaEstadosDocumentosGC(Long idDocumento, Long idTipo) {
		
		List<LineaEstadoProjection> lineasHistotico = estadoDocumentoGCRepository.getLineaHistoricos(idDocumento);	
		
		EstadoDocumentoGC estado = estadoDocumentoGCRepository.getEstadoActual(idDocumento);
		
		// Si se encuentra en un estado final, no es necesario añadirle posibles estados futuros.
		if (!estado.getLgFinal()) {
			List<LineaEstadoProjection> lineasDirecta = estadoDocumentoGCRepository.getLineaDirecta(estado.getId(),idTipo); 
			
			for(LineaEstadoProjection linea : lineasDirecta) {
				
				lineasHistotico.add(linea);			
				if (linea.getLgfinal() == 1) break;					
			    
			}		
		}
		
		return lineasHistotico;
	}

	@Override
	public List<EstadoFlujoDocumentoGC> estadosPosiblesDocumentosGC(Long idPerfil, Long idTipodoc, Long idDocumento) {
		
		List<EstadoFlujoDocumentoGCProjection> estadoP = estadoDocumentoGCRepository.estadosPosiblesDocumentosGC(idPerfil,idTipodoc,idDocumento,"N"); 

		return estadoP.stream().map(entity -> modelMapper.map(entity, EstadoFlujoDocumentoGC.class)).collect(Collectors.toList());		
		
	}

	@Override
	public List<ListadoEstadoTipoDocumentoGC> listadoEstadosDocumentosGC(Long cAnno) {
		return estadoDocumentoGCRepository.listadoEstadosDocumentosGC(cAnno).stream()
				.map(entity -> modelMapper.map(entity, ListadoEstadoTipoDocumentoGC.class)).collect(Collectors.toList());	
	}

	@Override
	public void deleteEstadoDocumentoGC(Long idEstado) {
		estadoDocumentoGCRepository.deleteById(idEstado);		
	}	
	
	// Create
	
		public EstadoDocumentoGC createEstadoDocumento(EstadoDocumentoGC estadoDocumento) {	
			return estadoDocumentoGCRepository.save(estadoDocumento);
			
		}



		

}
