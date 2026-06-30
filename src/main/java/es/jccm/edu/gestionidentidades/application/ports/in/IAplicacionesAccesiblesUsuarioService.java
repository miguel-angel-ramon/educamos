package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.AplicacionesAccesiblesUsuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;

import java.util.Date;

public interface IAplicacionesAccesiblesUsuarioService {

    AplicacionesAccesiblesUsuario save(AplicacionesAccesiblesUsuario aplicacionesAccesiblesUsuario);

    void deleteByIdUsuario(Long idUsuario);

	AplicacionesAccesiblesUsuario construirAplicacionesAccesiblesUsuario(Aplicacion app, Usuario user);

}
