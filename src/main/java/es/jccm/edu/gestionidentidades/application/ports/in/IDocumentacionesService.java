package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.List;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.DocumentacionesDto;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.domain.Persona;

public interface IDocumentacionesService {

    Documentaciones save(Documentaciones documentaciones);

	Documentaciones construirDocumentacion(AltaUsuarioGlobalRequest request);

	boolean validaDocumentacion(Documentaciones documentacion);

	DocumentacionesDto modificarDocumentacion(DocumentacionesDto documentacionDto);

	List<Documentaciones> findByOidDocumentacion(Long idDocumentacion);

	Documentaciones findByNif(String nif);
}
