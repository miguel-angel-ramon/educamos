package es.jccm.edu.gestionidentidades.application.services;

import org.apache.commons.text.RandomStringGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;
import es.jccm.edu.gestionidentidades.adapter.out.CifradorMD5ISO88591;
import es.jccm.edu.gestionidentidades.adapter.out.ValidadorLogin;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AsignacionNuevasCredenciales;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;
import es.jccm.edu.gestionidentidades.application.ports.in.FindUsuarioGlobalUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.GenerarNuevasCredencialesUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.CredencialesManagerException;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class CredencialesService implements GenerarNuevasCredencialesUseCase, FindUsuarioGlobalUseCase {

	@Autowired
	UsuariosRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	UsuariotService usuariotService;
	
	@Autowired
	GeneradorLoginService generadorLoginService;
	
	@Override
	public UsuarioDto findFirst(String identificacion, String tipide) {
				
			Usuario usr = usuarioRepository.findUsuarioByIdentificacion(identificacion, tipide);
			if(usr!=null) {
				return modelMapper.map(usr, UsuarioDto.class);
			}
		
		return null;
	}
	
	@Override
	public AsignacionNuevasCredenciales generarNuevasCredenciales(Usuario user,AltaUsuarioGlobalRequest request) {

		String nuevoLogin;
		String nuevaClaveSinCifrar;
		String nuevaClaveCifrada;
		
		if (user == null) {
			return new AsignacionNuevasCredenciales(false, false, null, null);
		}
		
		nuevoLogin = user.getLogin();
		if (usuariotService.findUsuarioByIdentificacion(request.getIdentificacion()) == null && !loginCumplePolitica(user.getLogin())) {
			nuevoLogin = generaLoginPersonaS(request.getPersona());
		}
		
		nuevaClaveSinCifrar = generaCredencialesAleatoria(request.getPersona());
		
		nuevaClaveCifrada = new CifradorMD5ISO88591().cifra(nuevaClaveSinCifrar);
		
		return new AsignacionNuevasCredenciales(false, false , nuevoLogin, nuevaClaveCifrada);
	}
	
	@Override
	public boolean esUsuarioEnUsuariost(String documentacion) {
		Usuariot usuarioTAlumno = usuariotService.findUsuarioByIdentificacion(documentacion);
		if(usuarioTAlumno != null) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean loginCumplePolitica(String login) {
		boolean correcto = true;
		
		if(login != null) {
	
			ValidadorLogin validaorLogin = new ValidadorLogin();
			correcto = validaorLogin.isLoginCumplePolitica(login);
		}else {
			correcto = false;
		}
		
		return correcto;
	}
	
	@Override
	public String generaLoginPersonaS(Persona persona) throws CredencialesManagerException {		
		String login;
		try {
			login = generaLoginPersona(persona);
			return login;
		} catch (CredencialesManagerException e) {
			log.error("no se pudo generar login para persona "+persona.getId());
			throw new CredencialesManagerException(e.getMessage(),e);
		}
		
	}
	
	@Override
	public String generaLoginPersona(Persona persona) throws CredencialesManagerException {

		String login = null;

		try {
			login = generadorLoginService.genera(persona);
		} catch (CredencialesManagerException error) {
			error.getStackTrace();
		}

		return login;
	}
	
	@Override
	public String generaLoginALeatorio(Persona persona) throws CredencialesManagerException {
		
		 RandomStringGenerator generator = new RandomStringGenerator.Builder()
				 .withinRange('0', 'z')
				 .filteredBy(c -> Character.isLetterOrDigit(c))
			     .build();
			 String randomLogin = generator.generate(8 , 18);
				
		return randomLogin;
    }
	
	@Override
	public String generaLoginALeatorioNombre(Persona persona) throws CredencialesManagerException {
		
			 String randomLogin = generadorLoginService.genera(persona);
				
		return randomLogin;
    }
	
	@Override
	public String generaCredencialesAleatoria(Persona persona) throws CredencialesManagerException {
		
		 RandomStringGenerator generator = new RandomStringGenerator.Builder()
			     .withinRange('0', 'z')
				 .filteredBy(c -> Character.isLetterOrDigit(c))
			     .build();
			 String randomClave = generator.generate(8 , 18);
				
		return randomClave;
    }
	
}
