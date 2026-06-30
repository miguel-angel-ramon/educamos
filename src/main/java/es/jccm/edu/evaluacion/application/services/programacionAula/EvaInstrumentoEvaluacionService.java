package es.jccm.edu.evaluacion.application.services.programacionAula;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.InstrumentoEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaInstrumentoEvaluacionRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaInstrumentoEvaluacion;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaInstrumentoEvaluacionService;

@Service
public class EvaInstrumentoEvaluacionService implements IEvaInstrumentoEvaluacionService {

    @Autowired
    private EvaInstrumentoEvaluacionRepository instrumentoEvaluacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<InstrumentoEvaluacionDTO> findAll(){
    	
    	List<EvaInstrumentoEvaluacion> instrumentosEvaluacion = 
    			  StreamSupport.stream(instrumentoEvaluacionRepository.findAll().spliterator(), false)
    			    .collect(Collectors.toList());
    	
		return instrumentosEvaluacion.stream().map(x -> modelMapper.map(x, InstrumentoEvaluacionDTO.class)).collect(Collectors.toList());
	}
}