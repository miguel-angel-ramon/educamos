package es.jccm.edu.documentosGC.application.services.actasevaluacionarte;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.ActasEvaluacionArteRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrodocumentosarte.RegSelDocArteRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.DirectoresActaPrivadoArte;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.ProfesorActaEvaluacionArte;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.projection.DirectoresActaPrivadoProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.projection.ProfesorActaEvaluacionArteProjection;
import es.jccm.edu.documentosGC.application.domain.registrodocumentosarte.RegSelDocArte;
import es.jccm.edu.documentosGC.application.ports.in.actaevaluacionarte.IActasEvaluacionArteService;

@Service
public class ActasEvaluacionArteService implements IActasEvaluacionArteService {

	@Autowired
	private ActasEvaluacionArteRepository actasEvaluacionArteRepository;
	@Autowired
	private RegSelDocArteRepository regSelDocRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProfesorActaEvaluacionArte> getCandidatosCombosObraFinal(Long idCentro, String fSesion) {
		String fechaSesion = fSesion.equals("null") ? "" : fSesion;

		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getCandidatosCombosObraFinal(idCentro, fechaSesion);
		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionArte> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno,
			String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon)
			throws ParseException {

		String fechaSesion = fSesion.equals("null") ? "" : fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null") ? "" : f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null") ? "" : f_fecfincon;

		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad,
						fechaFecfinconomc, fechaFecfincon);

		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());

	}

	@Override
	public List<ProfesorActaEvaluacionArte> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno,
			String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon)
			throws ParseException {
		String fechaSesion = fSesion.equals("null") ? "" : fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null") ? "" : f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null") ? "" : f_fecfincon;

		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad,
						fechaFecfinconomc, fechaFecfincon);

		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<RegSelDocArte> createRegistrosRegSolDoc(List<RegSelDocArte> regSelDocListIn) {
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();

		regSelDocListIn.forEach(registro -> {
			registro.setIdIdentificador(idIdentificador);
		});

		return (List<RegSelDocArte>) regSelDocRepository.saveAll(regSelDocListIn);
	}

	@Override
	public List<DirectoresActaPrivadoArte> getListadoDirectoresPrivado(Long idConvCentro) {
		List<DirectoresActaPrivadoProjection> directoresP = actasEvaluacionArteRepository
				.getListadoDirectoresPrivado(idConvCentro);

		return directoresP.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoArte.class))
				.collect(Collectors.toList());

	}

//	@Override
//	public List<ConvocatoriasExtraordinariaArte> getListadoConvocatoriasExtraordinaria(Long idCentro, Long cAnno) {
//		List<ConvocatoriasExtraordinariaProjection> convocatoriasP = actasEvaluacionArteRepository.getListadoConvocatoriasExtraordinaria(idCentro,cAnno);
//		
//		return convocatoriasP.stream().map(entity -> modelMapper.map(entity, ConvocatoriasExtraordinariaArte.class)).collect(Collectors.toList());
//	}

//	@Override
//	public List<ConvocatoriasCorrespondenciaArte> getConvocatoriasCorrespondencia(Long idCentro, Long cAnno,
//			Long idOfertamatrig, Long idConvunidad) {
//		
//		List<ConvocatoriasCorrespondenciaProjection> convocatoriasP = actasEvaluacionArteRepository.getConvocatoriasCorrespondencia(idCentro,cAnno,idOfertamatrig, idConvunidad);
//		
//		return convocatoriasP.stream().map(entity -> modelMapper.map(entity, ConvocatoriasCorrespondenciaArte.class)).collect(Collectors.toList());
//	}

	@Override
	public List<ProfesorActaEvaluacionArte> getCandidatosActaArtePendientes(Long idCentro, Integer cAnno,
			String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon)
			throws ParseException {

		String fechaSesion = fSesion.equals("null") ? "" : fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null") ? "" : f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null") ? "" : f_fecfincon;

		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getCandidatosActaBachilleratoPendientes(idCentro, cAnno, fechaSesion, idOfertamatrig, idUnidad,
						fechaFecfinconomc, fechaFecfincon);

		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionArte> getVocalesCandidatosActaEvaluacion(Long idCentro,Integer cAnno,String fSesion) {
		String fechaSesion = fSesion.equals("null") ? "" : fSesion;
		
		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getVocalesCandidatos(idCentro, fSesion, cAnno);

		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProfesorActaEvaluacionArte> getSeleccionadosActaArtePendientes(Long idCentro, Integer cAnno,
			String fSesion, Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon)
			throws ParseException {

		String fechaSesion = fSesion.equals("null") ? "" : fSesion;
		String fechaFecfinconomc = f_fecfinconomc.equals("null") ? "" : f_fecfinconomc;
		String fechaFecfincon = f_fecfincon.equals("null") ? "" : f_fecfincon;

		List<ProfesorActaEvaluacionArteProjection> profesorProjection = actasEvaluacionArteRepository
				.getSeleccionadosActaArtePendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, f_fecfinconomc,
						f_fecfincon);

		return profesorProjection.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionArte.class))
				.collect(Collectors.toList());
	}

}
