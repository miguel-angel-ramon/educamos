package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AsignacionNuevasCredenciales;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

	Optional<Usuario> findById(Long id);
	
	Usuario findUsuarioByIdentificacion(String identificacion, String tipide);

	Usuario save(Usuario user);

	void limpiaBorrar(Long idUsuario);

	Usuario borrarUsuarioByIdPersona(Long idPersona);

	Usuario construirUsuario(AltaUsuarioGlobalRequest request,
			AsignacionNuevasCredenciales asignacionNuevasCredenciales);

    Optional<Usuario> modificarLoginClaveUsuario(Long id, String login, String clave);

	UsuarioDto modificarUsuario(UsuarioDto usuarioDto);

	Boolean aceptarLdap(Long idUsuario);

	Boolean completarDatosPersonales(Long idUsuario, String sexo, Date fechaNacimiento);
	
	List<Usuario> findByLogin(String login);

}
