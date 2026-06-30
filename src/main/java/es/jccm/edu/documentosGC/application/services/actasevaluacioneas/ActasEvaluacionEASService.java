package es.jccm.edu.documentosGC.application.services.actasevaluacioneas;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.actasevalucioneas.ActasEvaluacionEASRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseas.RegSelDocEASRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.entities.DirectoresActaPrivadoEAS;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.entities.ProfesorActaEvaluacionEAS;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.projection.ProfesorActaEvaluacionEASProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseas.RegSelDocEAS;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneas.IActasEvaluacionEASService;

@Service
public class ActasEvaluacionEASService implements IActasEvaluacionEASService {
	
	@Autowired
	private ActasEvaluacionEASRepository actasEvaluacionRepository;
	
	@Autowired
	private RegSelDocEASRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionEAS> getProfesoresCandidatosActaEvaluacion(Long idCentro, 	
																			     Integer cAnno,
																			     String fSesion, 
																			     Long idOfertamatrig, 
																			     String f_fecfinconomc,
																			     String f_fecfincon,
																			     Long idMateriac) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionEASProjection> profesorProjection = null;
		
		if (idMateriac != -1L) {
			
			profesorProjection = actasEvaluacionRepository.getProfesoresCandidatosActaEvaluacionMateria(idCentro, 
																										cAnno, 
																										fechaSesion, 
																										idOfertamatrig, 
																										fechaFecfinconomc, 
																										fechaFecfincon,
																										idMateriac);
			
		} else {
			
			profesorProjection = actasEvaluacionRepository.getProfesoresCandidatosActaEvaluacion(idCentro, 
																								 cAnno, 
																								 fechaSesion, 
																								 idOfertamatrig, 
																								 fechaFecfinconomc, 
																								 fechaFecfincon);
			
		}
																																	   
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEAS.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionEAS> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																					Integer cAnno,
																					String fSesion, 
																					Long idOfertamatrig, 
																					String f_fecfinconomc, 
																					String f_fecfincon,
																					Long idMateriac) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionEASProjection> profesorProjection = null;
		
		if (idMateriac != -1L) { 
			
			profesorProjection = actasEvaluacionRepository.getProfesoresSeleccionadosActaEvaluacionMateria(idCentro, 
																										   cAnno, 
																										   fechaSesion, 
																										   idOfertamatrig, 
																										   fechaFecfinconomc, 
																										   fechaFecfincon,
																										   idMateriac);
			
		} else {
			
			profesorProjection = actasEvaluacionRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																								    cAnno, 
																								    fechaSesion, 
																								    idOfertamatrig, 
																								    fechaFecfinconomc, 
																								    fechaFecfincon);
			
		}
			
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEAS.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocEAS> createRegistrosRegSolDoc(List<RegSelDocEAS> regSelDocListIn,
													   Long idCurso) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
			if (idCurso != -1L) registro.setCodigoDatoAdicional1("FIRMANTES");
		});
		
		if (idCurso != -1L) {
		  RegSelDocEAS registroCurso = new RegSelDocEAS();
		  registroCurso.setIdIdentificador(idIdentificador);
		  registroCurso.setIdClave2(idCurso);
		  registroCurso.setCodigoDatoAdicional1("OFERTAMATRIG");
		  regSelDocListIn.add(registroCurso);
		}		
		
		return (List<RegSelDocEAS>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoEAS> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoEAS.class)).collect(Collectors.toList());
	}

	@Override
	public Integer getEtapaModalidad(Long idCurso) {
		
		return actasEvaluacionRepository.getEtapaModalidad(idCurso);
	}	

}
