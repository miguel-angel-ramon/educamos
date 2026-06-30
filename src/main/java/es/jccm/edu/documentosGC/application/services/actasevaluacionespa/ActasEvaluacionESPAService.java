package es.jccm.edu.documentosGC.application.services.actasevaluacionespa;

import java.text.ParseException;


import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionespa.ActasEvaluacionESPARepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosespa.RegSelDocESPARepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.entities.DirectoresActaPrivadoESPA;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.entities.ProfesorActaEvaluacionESPA;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.projection.ProfesorActaEvaluacionESPAProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosespa.RegSelDocESPA;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionespa.IActasEvaluacionESPAService;


@Service
public class ActasEvaluacionESPAService implements IActasEvaluacionESPAService {
	
	@Autowired
	private ActasEvaluacionESPARepository actasEvaluacionESPARepository;
	
	@Autowired
	private RegSelDocESPARepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionESPA> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																				  Integer cAnno,
																				  String fSesion, 
																				  Long idOfertamatrig, 
																				  Long idUnidad, 
																				  Long idConvUnidad, 
																				  Long idDocumento,
																				  String fFirma) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;		
		
		List<ProfesorActaEvaluacionESPAProjection> profesorProjection = null;
		
		if ((idDocumento == 7372L) || (idDocumento == 7373L)) {
			
			String fechaFirma = fFirma.equals("null")?"":fFirma;
			
			profesorProjection = actasEvaluacionESPARepository.getProfesoresCandidatosActaEvaluacionDistancia(idCentro, 
																											  cAnno,						
																											  idOfertamatrig,
																											  idUnidad,
																											  fechaFirma);
		} else if (idDocumento == 7312L)  {
			
			String fechaFirma = fFirma.equals("null")?"":fFirma;
			
			profesorProjection = actasEvaluacionESPARepository.getProfesoresCandidatosActaEvaluacionOtros(idCentro, 
																										  cAnno,						
																										  idOfertamatrig,
																										  idUnidad,
																										  fechaFirma);			
		} else {			
		
			profesorProjection = actasEvaluacionESPARepository.getProfesoresCandidatosActaEvaluacion(idCentro, 
																									 cAnno, 
			  																					     fechaSesion, 
																									 idOfertamatrig, 
																								     idUnidad, 
																									 idConvUnidad);
		}		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionESPA.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionESPA> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																					 Integer cAnno,
																					 String fSesion, 
																					 Long idOfertamatrig, 
																					 Long idUnidad, 
																					 Long idConvUnidad, 
																					 Long idDocumento,
																					 String fFirma) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		
		List<ProfesorActaEvaluacionESPAProjection> profesorProjection=null;
				
		if ((idDocumento == 7372L) || (idDocumento == 7373L)) {
			
			profesorProjection = actasEvaluacionESPARepository.getProfesoresSeleccionadosActaEvaluacionDistancia(idCentro, 
																											     cAnno, 
																											     idOfertamatrig, 
																											     idUnidad);		
			
		} else if (idDocumento == 7312L)  {
			
			String fechaFirma = fFirma.equals("null")?"":fFirma;
			
			profesorProjection = actasEvaluacionESPARepository.getProfesoresSeleccionadosActaEvaluacionOtros(idCentro, 
																										     cAnno, 
																										     idOfertamatrig, 
																										     idUnidad,
																										     fechaFirma);					
			
			
		} else {
			profesorProjection = actasEvaluacionESPARepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																									    cAnno, 
																									    fechaSesion, 
																									    idOfertamatrig, 
																									    idUnidad, 
																									    idConvUnidad);
			
		}

		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionESPA.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocESPA> createRegistrosRegSolDoc(List<RegSelDocESPA> regSelDocListIn, Long idUnidad) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		if (idUnidad != -1) {
			RegSelDocESPA regUnidad = new RegSelDocESPA();
			regUnidad.setIdIdentificador(idIdentificador);
			regUnidad.setIdClave1(idUnidad);
			regSelDocListIn.add(0,regUnidad);			
		}
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocESPA>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoESPA> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionESPARepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoESPA.class)).collect(Collectors.toList());
	}	
	
}
