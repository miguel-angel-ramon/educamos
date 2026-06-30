package es.jccm.edu.documentosGC.application.services.actasevaluaciondeportiva;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluaciondeportiva.ActasEvaluacionDeportivaRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosdeportiva.RegSelDocDeportivaRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.DirectoresActaPrivadoDeportiva;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.ProfesorActaEvaluacionDeportiva;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.projection.ProfesorActaEvaluacionDeportivaProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva.RegSelDocDeportiva;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluaciondeportiva.IActasEvaluacionDeportivaService;

@Service
public class ActasEvaluacionDeportivaService implements IActasEvaluacionDeportivaService {
	
	@Autowired
	private ActasEvaluacionDeportivaRepository actasEvaluacionDeportivaRepository;
	
	@Autowired
	private RegSelDocDeportivaRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionDeportiva> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionDeportivaProjection> profesorProjection = actasEvaluacionDeportivaRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDeportiva.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionDeportiva> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionDeportivaProjection> profesorProjection = actasEvaluacionDeportivaRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDeportiva.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocDeportiva> createRegistrosRegSolDoc(List<RegSelDocDeportiva> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocDeportiva>) regSelDocRepository.saveAll(regSelDocListIn);
	}

	@Override
	public List<DirectoresActaPrivadoDeportiva> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionDeportivaRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoDeportiva.class)).collect(Collectors.toList());
	}

}
