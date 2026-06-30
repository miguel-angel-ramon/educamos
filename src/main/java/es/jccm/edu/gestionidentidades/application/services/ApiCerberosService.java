package es.jccm.edu.gestionidentidades.application.services;

import java.time.Clock;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.CifradorMD5ISO88591;
import es.jccm.edu.gestionidentidades.adapter.out.repository.AplicacionesAccesiblesUsuarioRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.MotivoBloqueoRegistroPersonaRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.PersonaRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.TlpersonaRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariostRepository;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioRequest;
import es.jccm.edu.gestionidentidades.application.domain.AplicacionesAccesiblesUsuario;
import es.jccm.edu.gestionidentidades.application.domain.DatosUsuarioLdap;
import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.domain.MotivoBloqueoRegistroPersona;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Sexo;
import es.jccm.edu.gestionidentidades.application.domain.Tlpersona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;
import es.jccm.edu.gestionidentidades.application.domain.enums.AplicacionesPorDefecto;
import es.jccm.edu.gestionidentidades.application.ports.in.GenerarNuevasCredencialesUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.IApiCerberosService;
import es.jccm.edu.gestionidentidades.application.ports.in.IDocumentacionesService;
import es.jccm.edu.gestionidentidades.application.ports.in.IGetProblemasService;
import es.jccm.edu.gestionidentidades.application.ports.in.login.ProblemaLogin;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.AddAccesoAplicacionException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.AltaUsuarioException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ModuloAccesoException;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiCerberosService implements IApiCerberosService {
	
	private static final String modoAlta="MA";
	
	@Autowired
	MotivoBloqueoRegistroPersonaRepository motivoBloqueoRegistroPersonaRepository;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired
	private IGetProblemasService getProblemas;
    
    @Autowired
	private PersonaRepository personaRepository;
    
    @Autowired
    private UsuariostRepository usuariostRepository;
    
    @Autowired
    private GenerarNuevasCredencialesUseCase generarCredenciales;
    
    @Autowired
    private AplicacionesAccesiblesUsuarioRepository aplicacionesAccesiblesUsuarioRepository;
    
    @Autowired
    private GetUserLdapService getUserLdapService;
    
	@Autowired
	TlpersonaRepository tlpersonaRepository;
	
	@Autowired
	IDocumentacionesService documentacionesService;
    
	@Transactional
    @Override
	public Usuario altaUsuarioP(AltaUsuarioRequest usuarioRequest)
			throws AltaUsuarioException {

		try {
			Usuario usuarioM;
			List<ProblemaLogin> problemas = getProblemas.getProblemasAccesoUsuarioByNif(usuarioRequest.getNif());
			if (problemas.contains(ProblemaLogin.USUARIO_DESACTIVADO)) {
				throw new AltaUsuarioException("Usuario Desactivado");
			}
			if (problemas.contains(ProblemaLogin.USUARIO_NO_REGISTRADO)) {			
				usuarioM = altaUsuario(usuarioRequest.newLimpio(),null);
			}else {
				usuarioM = getUsuarioFromDniOPasaporte(usuarioRequest.getNif());
			}

			if (problemas.contains(ProblemaLogin.USUARIO_BLOQUEADO)) {
				desbloqueaUsuario(usuarioM.getLogin());
			}

			if (usuarioM != null) {
				darAltaEnAplicacionesPorDefecto(usuarioM.getId());
			}

			return usuarioM;
		} catch (AltaUsuarioException e) {
			throw new AltaUsuarioException("no se pudo dar de alta un nuevo usuario");
		} catch (ModuloAccesoException e) {
			throw new AltaUsuarioException("no se pudo recuperar usuario");
		} catch (AddAccesoAplicacionException e) {
			throw new AltaUsuarioException("no se pudo dar de alta en las aplicaciones al usuario");
		}

	}

    @Override
    public Usuario altaUsuarioLdap(AltaUsuarioRequest usuarioRequest, String uidLdapUsuario) throws AltaUsuarioException {
    	    	
    	DatosUsuarioLdap datosUsuarioLdap = getUserLdapService.getDatosLdapByUid(uidLdapUsuario);
    	
    	List<Usuario> usuarios = usuariosRepository.findUsuariosByIdentificacion(usuarioRequest.getNif());
    	Usuario usuario = new Usuario();
    	    	
    	if(usuarios.isEmpty()) {
    		try {
				usuario = altaUsuario(usuarioRequest.newLimpio(), datosUsuarioLdap);
				Documentaciones existeNif = documentacionesService.findByNif(usuarioRequest.getNif());
				if(existeNif == null) {
					documentacionesService.save(crearDocumentacion(usuarioRequest, usuario.getPersona()));
				}

			} catch (AltaUsuarioException e) {
				throw new AltaUsuarioException("no se pudo dar de alta un nuevo usuario");
			}
    	}else {
    		usuarios.get(0).setMailLdap(parseLdapFields(datosUsuarioLdap.getCorreo()));
    		usuarios.get(0).setUidLdap(parseLdapFields(uidLdapUsuario));
    		usuario = usuariosRepository.save(usuarios.get(0));   		
    	}
    	
    	Usuariot usuariot = usuariostRepository.findByIdentificacion(usuarioRequest.getNif());
    	
    	if(usuariot !=null && "".equals(usuariot)) {
    		usuariot.setUidLdap(parseLdapFields(datosUsuarioLdap.getAtributoUid()));
    		usuariot.setMailLdap(parseLdapFields(datosUsuarioLdap.getCorreo()));
    		usuariostRepository.save(usuariot);
    	}
    	
        return usuario;

    }
    
    @Override
	@Transactional
	public Usuario altaUsuario(AltaUsuarioRequest usuario, DatosUsuarioLdap datosUsuarioLdap) throws AltaUsuarioException {
		try {
						
			Persona persona = creaORecuperaPersonaByNif(usuario);
			
			String nuevoLogin = generarCredenciales.generaLoginALeatorioNombre(persona);
			String nuevaClaveSinCifrar = generarCredenciales.generaCredencialesAleatoria(persona);
			String nuevaClaveCifrada = new CifradorMD5ISO88591().cifra(nuevaClaveSinCifrar);
			
			Usuario usuarioEnBd = new Usuario();
			usuarioEnBd.setPersona(persona);
			usuarioEnBd.setLogin(nuevoLogin);
			usuarioEnBd.setClave(nuevaClaveCifrada);
			usuarioEnBd.setCredencialesUnSoloUso("S");
			usuarioEnBd.setBloqueado("N");
			usuarioEnBd.setIntentosFallidosAcceso(0L);
			usuarioEnBd.setOidAplicacionFavorita(null);
			usuarioEnBd.setCorreoE(null);
			usuarioEnBd.setModoAlta(modoAlta);
			usuarioEnBd.setIdUsuarioCreacion(null);
			usuarioEnBd.setActivo("S");
			if(datosUsuarioLdap != null) {
				usuarioEnBd.setMailLdap(parseLdapFields(datosUsuarioLdap.getCorreo()));
				usuarioEnBd.setUidLdap(parseLdapFields(datosUsuarioLdap.getAtributoUid()));
			}
			log.debug("El usuario no existe en el MA. Lo creamos");
			usuarioEnBd = usuariosRepository.save(usuarioEnBd);

			return usuarioEnBd;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al dar de alta usuario ", e);
			throw new AltaUsuarioException(e);
		}

	}
    
    private Persona creaORecuperaPersonaByNif(AltaUsuarioRequest usuario) {
    	List<Persona> personas = personaRepository.findPersonasByIdentificacion(usuario.getNif());
    	 Tlpersona tlpersonaByIdentificacion = null ;
    	try {
    	     tlpersonaByIdentificacion = tlpersonaRepository.findPersonaByIdentificacion(usuario.getNif());
    	} catch (Exception e) {
    	    log.error(e.getMessage());
    	    System.out.println("Error ejecutando la consulta: " + e.getMessage());
    	}
		
		if (personas.isEmpty()) {
			log.debug("La persona no existe en el MA. La creamos");

			Persona persona = new Persona();
			
			if(tlpersonaByIdentificacion != null && !"".equals(tlpersonaByIdentificacion)) {
				persona.setXPersona(tlpersonaByIdentificacion.getXPersona());
				persona.setNombre(usuario.getNombre());
				persona.setApellido1(usuario.getApellido1());
				persona.setApellido2(usuario.getApellido2());
				persona.setFechaNacimiento(usuario.getFechaNacimiento());
				persona.setSexo(Sexo.I);
				persona = personaRepository.save(persona); 
				documentacionesService.save(crearDocumentacion(usuario, persona));
				return persona;
			}else {
				Tlpersona tlpersona = new Tlpersona();
				tlpersona.setNombre(usuario.getNombre());	
				tlpersona.setApellido1(usuario.getApellido1());		
				tlpersona.setApellido2(usuario.getApellido2());
				tlpersona.setFechaNacimiento(usuario.getFechaNacimiento());
				tlpersona.setTipIde("D");	
				tlpersona.setGenero(Sexo.I);
				tlpersona.setNumIde(usuario.getNif());	
				tlpersona.setActprodatper('N');			
				Tlpersona tlpersonaCreada = tlpersonaRepository.save(tlpersona);
				
				List<MotivoBloqueoRegistroPersona> bloqueo = motivoBloqueoRegistroPersonaRepository.findMotivoBloqueoRegistroPersonaByXPersona(tlpersonaCreada.getXPersona());
				
				if(bloqueo != null && !bloqueo.isEmpty()){
					throw new RuntimeException("El registro de la persona se ha parado debido a un bloqueo en la misma.");
				}else {
					persona.setXPersona(tlpersonaCreada.getXPersona());
					persona.setNombre(usuario.getNombre());
					persona.setApellido1(usuario.getApellido1());
					persona.setApellido2(usuario.getApellido2());
					persona.setFechaNacimiento(usuario.getFechaNacimiento());
					persona.setSexo(Sexo.I);
					persona = personaRepository.save(persona);
					documentacionesService.save(crearDocumentacion(usuario, persona));
					return persona;
				}
			}				
		} else {
			return personas.get(0);
		}		
	}
    
    private Documentaciones crearDocumentacion(AltaUsuarioRequest usuarioRequest, Persona persona) {
    	Documentaciones documentacion = new Documentaciones();
		documentacion.setIdentificacion(usuarioRequest.getNif());
		documentacion.setPersona(persona);
		documentacion.setTipoDocumentacion(1L);
		
		return documentacion;
    }
    
    @Override
	public Usuario getUsuarioFromDniOPasaporte(String docIdentificativo) throws ModuloAccesoException {

		List<Usuario> usuarios = usuariosRepository.findUsuariosByIdentificacion(docIdentificativo);
		
		if(usuarios.size()>1) {
			throw new ModuloAccesoException("mas de un usuario encontrado para una misma documentación");
		}
		if(usuarios.size()==0) {
			return null;
		}
		
		return usuarios.get(0);
	}
    
    @Override
	public void desbloqueaUsuario(String loginUsuario) throws ModuloAccesoException {
		List<Usuario> usuarios = usuariosRepository.findByLogin(loginUsuario);
		if (usuarios.isEmpty()) {
			throw new ModuloAccesoException("el usuario no existe");
		}

		Usuario modificacion = usuarios.get(0);
		modificacion.setBloqueado("N");
		modificacion.setIntentosFallidosAcceso(0L);

		usuariosRepository.save(modificacion);
	}
    
    public void darAltaEnAplicacionesPorDefecto(Long idUsuario) {
		log.debug("Añadiendo aplicaciones disponibles para el usuario con ID: {}",idUsuario);
		
		List<AplicacionesAccesiblesUsuario> appusuario = aplicacionesAccesiblesUsuarioRepository.findByIdUsuario(idUsuario);
		
		Date date = new Date();
		AplicacionesAccesiblesUsuario moduloAcceso = new AplicacionesAccesiblesUsuario();
		AplicacionesAccesiblesUsuario secretariaVirtual = new AplicacionesAccesiblesUsuario();
		moduloAcceso.setIdUsuario(idUsuario);
		moduloAcceso.setIdAplicacion(AplicacionesPorDefecto.APP_MODULO_ACCESO.id);
		moduloAcceso.setBloqueado("N");

		secretariaVirtual.setIdUsuario(idUsuario);
		secretariaVirtual.setIdAplicacion(AplicacionesPorDefecto.APP_SECRETARIA_VIRTUAL.id);
		secretariaVirtual.setBloqueado("N");

		if(!appusuario.stream().anyMatch(aplAccUsu -> aplAccUsu.getIdAplicacion().equals(AplicacionesPorDefecto.APP_MODULO_ACCESO.id) )) {
			aplicacionesAccesiblesUsuarioRepository.save(moduloAcceso);
		}
		if(!appusuario.stream().anyMatch(aplAccUsu -> aplAccUsu.getIdAplicacion().equals(AplicacionesPorDefecto.APP_SECRETARIA_VIRTUAL.id))) {
			aplicacionesAccesiblesUsuarioRepository.save(secretariaVirtual);
		}
	}
	public  String parseLdapFields(String input) {
		String[] parts = input.split(":", 2);
		if (parts.length > 1) {
			return parts[1].trim();
		} else {
			return input;
		}
	}

    
}
