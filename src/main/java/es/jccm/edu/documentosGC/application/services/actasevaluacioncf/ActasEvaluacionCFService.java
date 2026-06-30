package es.jccm.edu.documentosGC.application.services.actasevaluacioncf;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacioncf.ActasEvaluacionCFRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoscf.RegSelDocCFRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.entities.DirectoresActaPrivadoCF;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.entities.ProfesorActaEvaluacionCF;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.projection.ProfesorActaEvaluacionCFProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoscf.RegSelDocCF;
import es.jccm.edu.documentosGC.application.ports.in.actaevaluacioncf.IActasEvaluacionCFService;

@Service
public class ActasEvaluacionCFService implements IActasEvaluacionCFService {
	
	@Autowired
	private ActasEvaluacionCFRepository actasEvaluacionCFRepository;
	
	@Autowired
	private RegSelDocCFRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionCF> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																				Integer cAnno,
																				String fSesion, 
																				Long idOfertamatrig, 
																				Long idUnidad, 
																				Long idConvUnidad, 
																				Long idDocumento) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		
		List<ProfesorActaEvaluacionCFProjection> profesorProjection = null;
		
		if (idDocumento == 2586L) {
			
			profesorProjection = actasEvaluacionCFRepository.getProfesoresCandidatosActaEvaluacionModulares(idCentro, 
																											cAnno, 
																											fechaSesion, 
																											idOfertamatrig, 
																											idUnidad, 
																											idConvUnidad);
		} else {
			profesorProjection = actasEvaluacionCFRepository.getProfesoresCandidatosActaEvaluacion(idCentro, 
																								   cAnno, 
																								   fechaSesion, 
																								   idOfertamatrig, 
																								   idUnidad, 
																								   idConvUnidad);
		}
			
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionCF.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionCF> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																				   Integer cAnno,
																				   String fSesion, 
																				   Long idOfertamatrig, 
																				   Long idUnidad, 
																				   Long idConvUnidad, 
																				   Long idDocumento) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
				
		List<ProfesorActaEvaluacionCFProjection> profesorProjection = null;
		
		if (idDocumento == 2586L) {
			profesorProjection = actasEvaluacionCFRepository.getProfesoresSeleccionadosActaEvaluacionModulares(idCentro, 
																											   cAnno, 
																											   fechaSesion, 
																											   idOfertamatrig, 
																											   idUnidad, 
																											   idConvUnidad);
			
		} else {
			profesorProjection = actasEvaluacionCFRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																									  cAnno, 
																									  fechaSesion, 
																									  idOfertamatrig, 
																									  idUnidad, 
																									  idConvUnidad);	
		}		
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionCF.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocCF> createRegistrosRegSolDoc(List<RegSelDocCF> regSelDocListIn, 
													  Long idUnidad) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		if (idUnidad != -1) {
			RegSelDocCF regUnidad = new RegSelDocCF();
			regUnidad.setIdIdentificador(idIdentificador);
			regUnidad.setIdClave1(idUnidad);
			regSelDocListIn.add(0,regUnidad);			
		}
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocCF>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoCF> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionCFRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoCF.class)).collect(Collectors.toList());
	}	
	
}
