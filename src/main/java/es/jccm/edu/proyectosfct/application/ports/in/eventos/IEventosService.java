package es.jccm.edu.proyectosfct.application.ports.in.eventos;

import es.jccm.edu.proyectosfct.application.domain.eventos.Eventos;



public interface IEventosService {
	
	  void generarEvento(Eventos evento);
	  
	  Integer generaOrden();

}
