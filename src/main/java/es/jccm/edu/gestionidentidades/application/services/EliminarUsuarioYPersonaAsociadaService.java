package es.jccm.edu.gestionidentidades.application.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import es.jccm.edu.gestionidentidades.adapter.out.repository.AplicacionesAccesiblesUsuarioRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.ConfiguracionUsuarioRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.DocumentacionesRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.HistoricoCredencialesAccesoRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.LogAccesoUsuarioRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.LogAccesoUsuarioSisAutGesIdiRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.PersonaRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosReasignaClaveRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IEliminarUsuarioYPersonaAsociadaUseCase;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EliminarUsuarioYPersonaAsociadaService implements IEliminarUsuarioYPersonaAsociadaUseCase{

	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private UsuariosReasignaClaveRepository usuariosReasignaClaveRepository;
	
	@Autowired
	private AplicacionesAccesiblesUsuarioRepository aplicacionesAccesiblesUsuarioRepository;
	
	@Autowired
	private DocumentacionesRepository documentacionesRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LogAccesoUsuarioRepository logAccesoUsuarioRepository;
	
	@Autowired
	private HistoricoCredencialesAccesoRepository historicoCredencialesAccesoRepository;
	
	@Autowired
	private ConfiguracionUsuarioRepository configuracionUsuarioRepository;
	
	@Autowired
	private LogAccesoUsuarioSisAutGesIdiRepository logAccesoUsuarioSisAutRepository;
	
	@Transactional
	@Override
	public void eliminarUsuarioYPersonaAsociada(String oid) {
		
		long oidlong=Long.parseLong(oid);
		Usuario user = usuariosRepository.findById(oidlong).get();
		
		
		if(user == null) {
			log.warn("El usuario con id: " + oidlong +  " no existe.");
		}else {

			usuariosReasignaClaveRepository.deleteUsuarioReasignaClaveByOidUsuario(oidlong);
			historicoCredencialesAccesoRepository.deleteHistoricoCredencialesAccesoByOidUsuario(oidlong);
			logAccesoUsuarioRepository.deleteLogAccesoUsuarioByOidUsuario(oidlong);
			logAccesoUsuarioSisAutRepository.deleteLogAccesoUsuarioByIdUsuario(oidlong);			
			aplicacionesAccesiblesUsuarioRepository.deleteByOidUsuario(oidlong);
			configuracionUsuarioRepository.deleteUsuarioByOidUsuario(oidlong);
			
			long oidPersona = user.getPersona().getId();
			documentacionesRepository.deleteByOidPersona(oidPersona);
			usuariosRepository.deleteUsuarioByOid(oidlong);
			personaRepository.deleteByOidPersona(oidPersona);

		}

	}
}
