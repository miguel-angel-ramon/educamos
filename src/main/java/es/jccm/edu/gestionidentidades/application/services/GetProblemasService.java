package es.jccm.edu.gestionidentidades.application.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IGetProblemasService;
import es.jccm.edu.gestionidentidades.application.ports.in.login.ProblemaLogin;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetProblemasService implements IGetProblemasService {
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Override
	public List<ProblemaLogin> getProblemasAccesoUsuarioByNif(String nif) {
		
		List<Usuario> usuario = usuariosRepository.findUsuariosByIdentificacion(nif);
		
		return this.getProblemasLoginUsuarioRecuperado(usuario);
	}
	
	private List<ProblemaLogin> getProblemasLoginUsuarioRecuperado(List<Usuario> usuarios) {
		try {
			List<ProblemaLogin> problemas = getProblemasCuenta(usuarios);
			
			return problemas;
		} catch (Exception e) {
			log.error("error no controlado", e);
			return Arrays.asList(ProblemaLogin.ERROR_INTERNO);
		}
	}
	
	private List<ProblemaLogin> getProblemasCuenta(List<Usuario> usuarios) {
		if (usuarios.isEmpty()) {
			return Arrays.asList(ProblemaLogin.USUARIO_NO_REGISTRADO);
		}
		if (usuarios.size() > 1) {
			throw new IllegalArgumentException("mas de un usuario para obtener problemas de su cuenta");
		}

		Usuario usuario = usuarios.get(0);

		if (!usuario.getActivo().equals("S")) {
			return Arrays.asList(ProblemaLogin.USUARIO_DESACTIVADO);
		}

		if (usuario.getBloqueado().equals("S")) {
			return Arrays.asList(ProblemaLogin.USUARIO_BLOQUEADO);
		}

		return Collections.emptyList();
	}

}
