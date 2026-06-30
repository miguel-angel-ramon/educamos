package es.jccm.edu.documentosGC.application.services.actasevaluacionelementales;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionelementales.ActasEvaluacionElementalesRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoselementales.RegSelDocElementalesRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.DirectoresActaPrivadoElementales;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.ProfesorActaEvaluacionElementales;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.projection.ProfesorActaEvaluacionElementalesProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoselementales.RegSelDocElementales;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionelementales.IActasEvaluacionElementalesService;



@Service
public class ActasEvaluacionElementalesService implements IActasEvaluacionElementalesService {
	
	@Autowired
	private ActasEvaluacionElementalesRepository actasEvaluacionRepository;
	
	@Autowired
	private RegSelDocElementalesRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionElementales> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																						 Integer cAnno,
																						 String fSesion, 
																						 Long idCursoEtapa, 
																						 Long idUnidad, 
																						 String f_fecfinconomc,
																						 String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionElementalesProjection> profesorProjection = null;
		
		if (idUnidad == -1L) {
			profesorProjection = actasEvaluacionRepository.getProfesoresCandidatosActaEvaluacionMusica(idCentro, 
																									   cAnno, 
																									   fechaSesion, 
																									   idCursoEtapa, 
																									   fechaFecfinconomc,
																									   fechaFecfincon);

			
		} else {
			profesorProjection = actasEvaluacionRepository.getProfesoresCandidatosActaEvaluacion(idCentro, 
																								 cAnno, 
																								 fechaSesion, 
																								 idCursoEtapa, 
																								 idUnidad,
																								 fechaFecfinconomc,
																								 fechaFecfincon);				
		}
		
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionElementales.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionElementales> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																							Integer cAnno,
																							String fSesion, 
																							Long idCursoEtapa, 
																							Long idUnidad,														
																							String f_fecfinconomc,
																							String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionElementalesProjection> profesorProjection = null;
		
		if (idUnidad == -1L) { 
			
			profesorProjection = actasEvaluacionRepository.getProfesoresSeleccionadosActaEvaluacionMusica(idCentro, 
																										  cAnno, 
																										  fechaSesion, 
																										  idCursoEtapa,				
																										  fechaFecfinconomc,
																										  fechaFecfincon);
			
			
		} else {
			profesorProjection = actasEvaluacionRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																									cAnno, 
																									fechaSesion, 
																									idCursoEtapa, 
																									idUnidad,
																									fechaFecfinconomc,
																									fechaFecfincon);			
		}
	
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionElementales.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocElementales> createRegistrosRegSolDoc(List<RegSelDocElementales> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocElementales>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoElementales> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoElementales.class)).collect(Collectors.toList());
	}	
	
}
