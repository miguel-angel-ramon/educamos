package es.jccm.edu.documentosGC.application.services.actasevaluacionbachillerato;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionbachillerato.ActasEvaluacionBachilleratoRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentos.bachillerato.RegSelDocBachilleratoRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ConvocatoriasExtraordinaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.DirectoresActaPrivadoBachillerato;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ProfesorActaEvaluacionBachillerato;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.projection.ConvocatoriasExtraordinariaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.projection.ProfesorActaEvaluacionBachilleratoProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosbachillerato.RegSelDocBachillerato;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionbachillerato.IActasEvaluacionBachilleratoService;


@Service
public class ActasEvaluacionBachilleratoService implements IActasEvaluacionBachilleratoService {
	
	@Autowired
	private ActasEvaluacionBachilleratoRepository actasEvaluacionBachilleratoRepository;
	
	@Autowired
	private RegSelDocBachilleratoRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<ProfesorActaEvaluacionBachillerato> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionBachilleratoProjection> profesorProjection = actasEvaluacionBachilleratoRepository.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionBachillerato.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionBachillerato> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionBachilleratoProjection> profesorProjection = actasEvaluacionBachilleratoRepository.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
		
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionBachillerato.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<RegSelDocBachillerato> createRegistrosRegSolDoc(List<RegSelDocBachillerato> regSelDocListIn) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});
		
		return (List<RegSelDocBachillerato>) regSelDocRepository.saveAll(regSelDocListIn);
	}
	
	/*@Override
	public List<TlDocumento> getDocumentosByIdCentroAndCAnnoAndIdConvunidad(Long idCentro, Integer cAnno, Long idConvunidad){
		List<TldocumentoListProjection> documentoProjection = actasEvaluacionBachilleratoRepository.getDocumentosByIdCentroAndCAnnoAndIdConvunidad(idCentro, cAnno, idConvunidad);
		
		return documentoProjection.stream().map(entity -> modelMapper.map(entity, TlDocumento.class)).collect(Collectors.toList());
	} */

	@Override
	public List<DirectoresActaPrivadoBachillerato> getListadoDirectoresPrivado(Long idConvCentro,
																		       String fSesion,
																		       String f_fecfinconomc,
																		       String f_fecfincon) {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionBachilleratoRepository.getListadoDirectoresPrivado(idConvCentro,
																															  fechaSesion,
																															  fechaFecfinconomc,
																															  fechaFecfincon);
		
		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoBachillerato.class)).collect(Collectors.toList());
	}

	@Override
	public List<ConvocatoriasExtraordinaria> getListadoConvocatoriasExtraordinaria(Long idCentro, Long cAnno) {
		List<ConvocatoriasExtraordinariaProjection> convocatoriasP = actasEvaluacionBachilleratoRepository.getListadoConvocatoriasExtraordinaria(idCentro,cAnno);
		
		return convocatoriasP.stream().map(entity -> modelMapper.map(entity, ConvocatoriasExtraordinaria.class)).collect(Collectors.toList());
	}	

	@Override
	public List<ProfesorActaEvaluacionBachillerato> getCandidatosActaBachilleratoPendientes(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
		
		List<ProfesorActaEvaluacionBachilleratoProjection> profesorProjection = actasEvaluacionBachilleratoRepository.getCandidatosActaBachilleratoPendientes(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionBachillerato.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ProfesorActaEvaluacionBachillerato> getSeleccionadosActaBachilleratoPendientes(Long idCentro, Integer cAnno,
		String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc,String f_fecfincon) throws ParseException {
		
		String fechaSesion = fSesion.equals("null")?"":fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null")?"":f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null")?"":f_fecfincon;
				
		List<ProfesorActaEvaluacionBachilleratoProjection> profesorProjection = actasEvaluacionBachilleratoRepository.getSeleccionadosActaBachilleratoPendientes(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad, fechaFecfinconomc,fechaFecfincon);
				
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionBachillerato.class)).collect(Collectors.toList());
	}


}
