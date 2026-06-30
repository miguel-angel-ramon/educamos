package es.jccm.edu.documentosGC.application.services.actasevaluacioneso;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacioneso.ActasEvaluacionESORepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseso.RegSelDocESORepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.entities.DirectoresActaPrivadoESO;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.entities.ProfesorActaEvaluacionESO;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.projection.ProfesorActaEvaluacionESOProjection;
import es.jccm.edu.documentosGC.application.domain.resgistrosdocumentoseso.RegSelDocESO;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneso.IActasEvaluacionESOService;

@Service
public class ActasEvaluacionESOService implements IActasEvaluacionESOService {
	
	@Autowired
	private ActasEvaluacionESORepository actasEvaluacionESORepository;
	
	@Autowired
	private RegSelDocESORepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionESO> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		
		List<ProfesorActaEvaluacionESOProjection> profesorProjection = actasEvaluacionESORepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionESO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionESO> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
				
		List<ProfesorActaEvaluacionESOProjection> profesorProjection = actasEvaluacionESORepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionESO.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocESO> createRegistrosRegSolDoc(List<RegSelDocESO> regSelDocListIn, Long idUnidad) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		if (idUnidad != -1) {
			RegSelDocESO regUnidad = new RegSelDocESO();
			regUnidad.setIdIdentificador(idIdentificador);
			regUnidad.setIdClave1(idUnidad);
			regSelDocListIn.add(0,regUnidad);			
		}
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocESO>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoESO> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionESORepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoESO.class)).collect(Collectors.toList());
	}	
	
}
