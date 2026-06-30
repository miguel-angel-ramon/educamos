package es.jccm.edu.documentosGC.application.services.actasevaluacionpreparatoria;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionpreparatoria.ActasEvaluacionPreparatoriaRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentospreparatoria.RegSelDocPreparatoriaRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.DirectoresActaPrivadoPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorActaEvaluacionPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorFirmante;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.projection.ProfesorActaEvaluacionPreparatoriaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.projection.ProfesorFirmanteProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentospreparatoria.RegSelDocPreparatoria;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionpreparatoria.IActasEvaluacionPreparatoriaService;

@Service
public class ActasEvaluacionPreparatoriaService implements IActasEvaluacionPreparatoriaService {
	
	@Autowired
	private ActasEvaluacionPreparatoriaRepository actasEvaluacionPreparatoriaRepository;
	
	@Autowired
	private RegSelDocPreparatoriaRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionPreparatoria> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		
		List<ProfesorActaEvaluacionPreparatoriaProjection> profesorProjection = actasEvaluacionPreparatoriaRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionPreparatoria.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionPreparatoria> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
				
		List<ProfesorActaEvaluacionPreparatoriaProjection> profesorProjection = actasEvaluacionPreparatoriaRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, idConvUnidad);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionPreparatoria.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocPreparatoria> createRegistrosRegSolDoc(List<RegSelDocPreparatoria> regSelDocListIn, Long idUnidad) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		if (idUnidad != -1) {
			RegSelDocPreparatoria regUnidad = new RegSelDocPreparatoria();
			regUnidad.setIdIdentificador(idIdentificador);
			regUnidad.setIdClave1(idUnidad);
			regSelDocListIn.add(0,regUnidad);			
		}
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocPreparatoria>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	

	@Override
	public List<DirectoresActaPrivadoPreparatoria> getListadoDirectoresPrivado(Long idConvCentro) {		
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionPreparatoriaRepository.getListadoDirectoresPrivado(idConvCentro);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoPreparatoria.class)).collect(Collectors.toList());
	}

	@Override
	public Integer getOfertaMatriculaCurso(Long idOfertamatrig, Long idCentro) {
		return actasEvaluacionPreparatoriaRepository.getOfertaMatriculaCurso(idOfertamatrig, idCentro);
	}

	@Override
	public List<ProfesorFirmante> getFirmantesProfesoradoPreparatorios(Long cAnno, 
																	   Long xCentro, 
																	   String idUnidad,
																	   Long idCurso, 
																	   String sesion) {
		String fSesion = sesion.equals("null")?null:sesion;
		
		List<ProfesorFirmanteProjection> profesorProjection = actasEvaluacionPreparatoriaRepository.getFirmantesProfesoradoPreparatorios(cAnno,
																																	     xCentro,
																																	     idUnidad,
																																	     idCurso, 
																																	     fSesion);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorFirmante.class)).collect(Collectors.toList());
	}	
	
}
