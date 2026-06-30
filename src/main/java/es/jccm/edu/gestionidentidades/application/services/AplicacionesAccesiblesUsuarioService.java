package es.jccm.edu.gestionidentidades.application.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.AplicacionesAccesiblesUsuarioRepository;
import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.AplicacionesAccesiblesUsuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IAplicacionesAccesiblesUsuarioService;

@Transactional
@Service
public class AplicacionesAccesiblesUsuarioService implements IAplicacionesAccesiblesUsuarioService{
	
	@Autowired
	AplicacionesAccesiblesUsuarioRepository aplicacionesAccesiblesUsuarioRepository;
	
	public AplicacionesAccesiblesUsuario save(AplicacionesAccesiblesUsuario aplicacionesAccesiblesUsuario) {
		return aplicacionesAccesiblesUsuarioRepository.save(aplicacionesAccesiblesUsuario);
	}
	
	public void deleteByIdUsuario(Long idUsuario) {
		aplicacionesAccesiblesUsuarioRepository.deleteByOidUsuario(idUsuario);
	}

	@Override
	public AplicacionesAccesiblesUsuario construirAplicacionesAccesiblesUsuario(Aplicacion app, Usuario user) {

		AplicacionesAccesiblesUsuario aplicacionesAccesiblesUsuario = new AplicacionesAccesiblesUsuario();
		
		aplicacionesAccesiblesUsuario.setIdAplicacion(app.getId());
		aplicacionesAccesiblesUsuario.setIdUsuario(user.getId());
		aplicacionesAccesiblesUsuario.setBloqueado("N");
		
		
		return aplicacionesAccesiblesUsuario;
	}

}
