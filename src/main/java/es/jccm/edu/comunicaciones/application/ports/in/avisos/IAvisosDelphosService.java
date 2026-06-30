package es.jccm.edu.comunicaciones.application.ports.in.avisos;

import java.util.List;

import es.jccm.edu.comunicaciones.application.domain.avisos.Aviso;


public interface IAvisosDelphosService {
	
	List<Aviso> getAvisosDelphos(String perfil, Integer nivEducativo);

	List<Aviso> getAvisosByCentro(Long idCentro);

}
