package es.jccm.edu.proyectosfct.application.services.eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.eventos.EventosRepository;
import es.jccm.edu.proyectosfct.application.domain.eventos.Eventos;
import es.jccm.edu.proyectosfct.application.ports.in.eventos.IEventosService;

@Service
public class EventosService implements IEventosService {
	
	@Autowired
	private EventosRepository eventosRepository;


	@Override
	public void generarEvento(Eventos evento) {
		eventosRepository.save(evento);
		
	}


	@Override
	public Integer generaOrden() {
		return eventosRepository.getMaxOrder();		
	}

}
