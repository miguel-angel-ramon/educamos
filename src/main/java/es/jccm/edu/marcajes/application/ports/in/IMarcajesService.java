package es.jccm.edu.marcajes.application.ports.in;

import java.util.List;

import es.jccm.edu.marcajes.application.domain.Marcaje;

public interface IMarcajesService {
	
	String crearMarcaje(String idEvento, String urlMarcaje);
	
	String existeMarcaje(String idEvento, String urlMarcaje);
	
	String borrarMarcaje(String idEvento, String urlMarcaje);
	
	List<Marcaje> obtenerMarcajes(String idEvento);

}
