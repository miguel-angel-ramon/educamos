package es.jccm.edu.documentosGC.application.services.actasevaluacionprimaria;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionprimaria.ActasEvaluacionPrimariaRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosprimaria.RegSelDocPrimariaRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.entities.DirectoresActaPrivadoPrimaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.entities.ProfesorActaEvaluacionPrimaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.projection.ProfesorActaEvaluacionPrimariaProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosprimaria.RegSelDocPrimaria;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionprimaria.IActasEvaluacionPrimariaService;


@Service
public class ActasEvaluacionPrimariaService implements IActasEvaluacionPrimariaService {
	
	@Autowired
	private ActasEvaluacionPrimariaRepository actasEvaluacionPrimariaRepository;
	
	@Autowired
	private RegSelDocPrimariaRepository regSelDocRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionPrimaria> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionPrimariaProjection> profesorProjection = actasEvaluacionPrimariaRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionPrimaria.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionPrimaria> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionPrimariaProjection> profesorProjection = actasEvaluacionPrimariaRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionPrimaria.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocPrimaria> createRegistrosRegSolDoc(List<RegSelDocPrimaria> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocPrimaria>) regSelDocRepository.saveAll(regSelDocListIn);
	}

	@Override
	public List<DirectoresActaPrivadoPrimaria> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionPrimariaRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoPrimaria.class)).collect(Collectors.toList());
	}

}
