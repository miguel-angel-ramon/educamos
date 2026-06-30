package es.jccm.edu.proyectosfct.application.ports.in.descarga;

import java.util.Map;

public interface IFormFielsFillerService {
	
	<T> Map<String, String> execute(T entity, String propiedad);

}
