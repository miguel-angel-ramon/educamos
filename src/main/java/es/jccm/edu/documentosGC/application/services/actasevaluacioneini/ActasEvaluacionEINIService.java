package es.jccm.edu.documentosGC.application.services.actasevaluacioneini;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevualucioneini.ActasEvaluacionEINIRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseini.RegSelDocEINIRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.DirectoresActaPrivadoEINI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.ProfesorActaEvaluacionEINI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.projection.ProfesorActaEvaluacionEINIProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseini.RegSelDocEINI;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneini.IActasEvaluacionEINIService;


@Service
public class ActasEvaluacionEINIService implements IActasEvaluacionEINIService {
	
	@Autowired
	private ActasEvaluacionEINIRepository actasEvaluacionEINIRepository;
	
	@Autowired
	private RegSelDocEINIRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionEINI> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionEINIProjection> profesorProjection = actasEvaluacionEINIRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEINI.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionEINI> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionEINIProjection> profesorProjection = actasEvaluacionEINIRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEINI.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ProfesorActaEvaluacionEINI> getProfesoresSeleccionadosActaEvaluacionEINII(Long idCentro, Long idUnidad, Integer dia, String mes, Integer cAnno) throws ParseException {
				
		List<ProfesorActaEvaluacionEINIProjection> profesorProjection = actasEvaluacionEINIRepository.getProfesoresSeleccionadosActaEvaluacionEINII(idCentro, idUnidad, dia, mes, cAnno);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEINI.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocEINI> createRegistrosRegSolDoc(List<RegSelDocEINI> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocEINI>) regSelDocRepository.saveAll(regSelDocListIn);
	}

	@Override
	public List<DirectoresActaPrivadoEINI> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionEINIRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoEINI.class)).collect(Collectors.toList());
	}
	
	@Override
	public Long getIdConvocatoriaOmcByConvocatoriaCentro(Long idConvocatoria, Long idCentro, Long idOfertamatrig) {		
		return actasEvaluacionEINIRepository.getIdConvocatoriaOmcByConvocatoriaCentro(idConvocatoria, idCentro, idOfertamatrig);
	}

	@Override
	public List<ProfesorActaEvaluacionEINI> getCandidatosActaEINIPendientes(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionEINIProjection> profesorProjection = actasEvaluacionEINIRepository.getCandidatosActaEINIPendientes(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEINI.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ProfesorActaEvaluacionEINI> getSeleccionadosActaEINIPendientes(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionEINIProjection> profesorProjection = actasEvaluacionEINIRepository.getSeleccionadosActaEINIPendientes(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionEINI.class)).collect(Collectors.toList());
	}


}
