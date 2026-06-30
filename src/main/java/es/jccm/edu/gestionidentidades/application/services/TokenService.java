package es.jccm.edu.gestionidentidades.application.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.cstic.marte.util.randomcodes.logic.RandomCodeService;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.ClockProvider;
import es.jccm.edu.gestionidentidades.application.ports.in.GenerarSolicitudRecuperacionClaveUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.ISolicitudRecuperacionClaveService;
import es.jccm.edu.gestionidentidades.application.ports.in.MandarMailRecuperacionClaveUseCase;
import es.jccm.edu.gestionidentidades.application.ports.out.EnviadorCorreoRecuperacionClave;

@Transactional
@Service
public class TokenService implements 
GenerarSolicitudRecuperacionClaveUseCase, MandarMailRecuperacionClaveUseCase {
	//private static Logger log = Logger.getLogger(TokenService.class);

	@Autowired
	private ISolicitudRecuperacionClaveService solicitudRecuperacionClaveService;
	
	@Autowired
	private RandomCodeService randomCodeService;
	
	
	@Autowired
	private EnviadorCorreoRecuperacionClave enviadorCorreoRecuperacionClave;

	@Autowired
	private ClockProvider clockProvider;
	
	@Autowired
    private UsuariosRepository usuarioRepository;

	/* (non-Javadoc)
	 * @see com.jccm.edu.papas.accesopapas.service.GenerarSolicitudRecuperacionClaveUseCase#newSolicitudRecuperacionClave(java.lang.String)
	 */
	@Override
	public SolicitudRecuperacionClave newSolicitudRecuperacionClave(Long id) {
		SolicitudRecuperacionClave solicitud = new SolicitudRecuperacionClave();
		solicitud.setFecha(clockProvider.getNow());
		solicitud.setToken(randomCodeService.getRandomCode());
		solicitud.setId(id);

		solicitudRecuperacionClaveService.guardarSolicitud(solicitud);
		return solicitud;
	}
	

	@Override
	public String mandarEmailRecuperacionClave(Long id, String email) {
		

		Optional<Usuario> usuario = usuarioRepository.findById(id);
		SolicitudRecuperacionClave solicitud = (solicitudRecuperacionClaveService.findByOidUsuario(id)).get(0);		
		if(solicitud==null){
			return "solicitud de recuperacion de clave no encontrada";
		}
		else{
			return enviadorCorreoRecuperacionClave.enviarCorreoDeRecuperacionDeClave(solicitud , usuario.get());
		}
	}

}
