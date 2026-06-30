package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.InstrumentoEvaluacionDTO;

public interface IEvaInstrumentoEvaluacionService {
	
	List<InstrumentoEvaluacionDTO> findAll();
}