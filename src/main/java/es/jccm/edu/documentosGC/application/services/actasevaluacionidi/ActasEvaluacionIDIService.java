package es.jccm.edu.documentosGC.application.services.actasevaluacionidi;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionidi.ActasEvaluacionIDIRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosidi.RegSelDocIDIRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.entities.DirectoresActaPrivadoIDI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.entities.ProfesorActaEvaluacionIDI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.projection.ProfesorActaEvaluacionIDIProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosidi.RegSelDocIDI;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionidi.IActasEvaluacionIDIService;

@Service
public class ActasEvaluacionIDIService implements IActasEvaluacionIDIService {
	
	@Autowired
	private ActasEvaluacionIDIRepository actasEvaluacionRepository;
	
	@Autowired
	private RegSelDocIDIRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionIDI> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		
		List<ProfesorActaEvaluacionIDIProjection> profesorProjection = actasEvaluacionRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionIDI.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionIDI> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
				
		List<ProfesorActaEvaluacionIDIProjection> profesorProjection = actasEvaluacionRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionIDI.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocIDI> createRegistrosRegSolDoc(List<RegSelDocIDI> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocIDI>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoIDI> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoIDI.class)).collect(Collectors.toList());
	}	
	
}
