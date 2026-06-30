package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.TipoOperacionDTO;

public interface IEvaTipoOperacionService {
	
	List<TipoOperacionDTO> getTiposOperacion();

}
