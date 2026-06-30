package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;

import java.util.Date;

public interface IUsuariotService {

    Usuariot findUsuarioByIdentificacion(String identificacion);

	Usuariot crearUsuariot(Usuariot usuariot);

	Usuariot construirUsuariot(Persona person, Usuario user);

	Usuariot construirUsuariotDesdeDelphos(Persona person, Usuario user, EmpleadoDelphos es, Integer xCentro);

}
