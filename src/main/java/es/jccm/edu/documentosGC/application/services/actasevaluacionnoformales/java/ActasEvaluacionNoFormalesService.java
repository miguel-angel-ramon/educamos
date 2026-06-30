package es.jccm.edu.documentosGC.application.services.actasevaluacionnoformales.java;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionnoformales.ActasEvaluacionNoFormalesRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosnoformales.RegSelDocNoFormalesRepository;
import es.jccm.edu.documentosGC.application.domain.actaevaluacionesnoformales.entities.DirectorTutor;
import es.jccm.edu.documentosGC.application.domain.actaevaluacionesnoformales.projection.DirectorTutorProjection;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosnoformales.RegSelDocNoFormales;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionnoformales.IActasEvaluacionNoFormalesService;

@Service
public class ActasEvaluacionNoFormalesService implements IActasEvaluacionNoFormalesService {

	@Autowired
	private ActasEvaluacionNoFormalesRepository actasEvaluacionRepository;
	
	@Autowired
	private RegSelDocNoFormalesRepository regSelDocRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<RegSelDocNoFormales> createRegistrosRegSolDoc(Long idUnidad, 
															  String localidad, 
															  Long idConvOmc,
															  Long idDirector, 
															  Long idTutor) {
		
		Long idIdentificador = regSelDocRepository.getNextvalTlregseldoc();
		
		List<RegSelDocNoFormales> regSelDocListIn = new ArrayList<RegSelDocNoFormales>();
		RegSelDocNoFormales regSelDoctIn = new RegSelDocNoFormales();
		regSelDoctIn.setIdIdentificador(idIdentificador);
		regSelDoctIn.setIdClave1(idUnidad);
		regSelDoctIn.setCodigoClave1(localidad);
		regSelDoctIn.setCodigoDatoAdicional1(idDirector+"");
		regSelDoctIn.setCodigoDatoAdicional2(idTutor+"");
		regSelDoctIn.setCodigoDatoAdicional3(idConvOmc+"");
		regSelDocListIn.add(regSelDoctIn);	
		regSelDocRepository.save(regSelDoctIn);
		regSelDocListIn.add(regSelDoctIn);
		
		return regSelDocListIn;		

	}
	
	
	@Override
	public DirectorTutor getDirectorTutor(Long idConvOmc, Long idUnidad) {
		
		DirectorTutorProjection director = actasEvaluacionRepository.getDirectorTutor(idConvOmc,idUnidad);
		
		return modelMapper.map(director, DirectorTutor.class);		
	}


	@Override
	public Long getMateriasCurso(Long idMateriac, Long idOfertamatrig) {
		
		return actasEvaluacionRepository.getMateriasCurso(idMateriac,idOfertamatrig);
		
	}



}
