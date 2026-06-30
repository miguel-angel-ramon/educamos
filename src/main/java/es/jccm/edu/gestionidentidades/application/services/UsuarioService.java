package es.jccm.edu.gestionidentidades.application.services;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;
import es.jccm.edu.gestionidentidades.adapter.out.CifradorMD5ISO88591;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.ValidadorCadenas;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AsignacionNuevasCredenciales;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Sexo;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
	@Qualifier("validadorCadenasLogin")
	private ValidadorCadenas validadorLogins;

	@Autowired
	@Qualifier("validadorCadenasClaves")
	private ValidadorCadenas validadorClaves;

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario findUsuarioByIdentificacion(String identificacion, String tipide) {
        return usuarioRepository.findUsuarioByIdentificacion(identificacion, tipide);
    }
    
    @Override
    public List<Usuario> findByLogin(String login){
    	return usuarioRepository.findByLogin(login);
    }

    public Usuario save(Usuario user) {
        return usuarioRepository.save(user);
    }

    public void limpiaBorrar(Long idUsuario) {
        usuarioRepository.limpiarBorrar(idUsuario);
    }

    public Usuario borrarUsuarioByIdPersona(Long idPersona) {
        return usuarioRepository.borrarUsuarioByIdPersona(idPersona);
    }

    @Override
    public Usuario construirUsuario(AltaUsuarioGlobalRequest request, AsignacionNuevasCredenciales asignacionNuevasCredenciales) {

        Usuario user = new Usuario();
        user.setPersona(request.getPersona());
        user.setLogin(asignacionNuevasCredenciales.getLogin());
        user.setClave(asignacionNuevasCredenciales.getClave());
        user.setCredencialesUnSoloUso("S");
        user.setActivo("S");
        user.setBloqueado("N");
        user.setIntentosFallidosAcceso(0L);

        return user;
    }

    @Override
    @Transactional
    public Optional<Usuario> modificarLoginClaveUsuario(Long id, String login, String clave)throws RuntimeException {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new RuntimeException("error al actualizar clave: usuario no existente");
        }

        Usuario user = usuario.get();
        
        List<String> errores = new ArrayList<>();
        
        if(!user.getLogin().equals(login)) {
			errores.addAll(validadorLogins.getErrores(login));
		}
        
        errores.addAll(validadorClaves.getErrores(clave));
        
        List <Usuario> usuariosConLogin = usuarioRepository.findByLogin(login);
        if (!usuariosConLogin.isEmpty() && !usuariosConLogin.get(0).getId().equals(user.getId())) {
			errores.add("Login repetido");
		}

        String claveEnBd = new CifradorMD5ISO88591().cifra(clave);
        
        if (user.getClave().equals(claveEnBd)) {
			errores.add("Contraseña igual a la anterior");
		}
        
        if (errores.isEmpty()) {
			
        	user.setLogin(login);
        	user.setClave(claveEnBd);

			if(user.getCredencialesUnSoloUso().equals("S")) {
				user.setCredencialesUnSoloUso("N");
			}
			
			usuarioRepository.save(user);
			
		} else {
			// TODO: solo se manda como error el primero que se de
			throw new RuntimeException(errores.get(0));
		}

        return usuario;

    }

    @Override
    public UsuarioDto modificarUsuario(UsuarioDto usuarioDto) {
    	
    	Usuario userMapped = toUsuarioEntity(usuarioDto);

    	Optional<Usuario> usuario = usuarioRepository.findById(userMapped.getId());

        Usuario user = usuario.orElseThrow(() -> new RuntimeException("Error al actualizar usuario: usuario no existente"));
        
        List<String> errores = new ArrayList<>();
        
        if(!user.getLogin().equals(userMapped.getLogin())) {
			errores.addAll(validadorLogins.getErrores(userMapped.getLogin()));
		}
        
        errores.addAll(validadorClaves.getErrores(userMapped.getClave()));
        
        List <Usuario> usuariosConLogin = usuarioRepository.findByLogin(userMapped.getLogin());
        if (!usuariosConLogin.isEmpty() && !Objects.equals(usuariosConLogin.get(0).getId(), user.getId())) {
			errores.add("Login repetido");
		}

        String claveEnBd = new CifradorMD5ISO88591().cifra(userMapped.getClave());
        
        if (user.getClave().equals(claveEnBd)) {
			errores.add("Contraseña igual a la anterior");
		}

        if (!errores.isEmpty()) {
            throw new RuntimeException(errores.get(0));
        }
			
        user.setLogin(userMapped.getLogin());
        user.setClave(claveEnBd);

        if(user.getCredencialesUnSoloUso().equals("S")) {
            user.setCredencialesUnSoloUso("N");
        }

        usuarioRepository.save(user);

        return toUsuarioDto(user);

    }
    
    @Override
    public Boolean aceptarLdap(Long idUsuario) {

        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (usuario.isEmpty()) {
            return false;
        }

        usuario.get().setFLopd(new Date());
        usuarioRepository.save(usuario.get());

        return true;

    }

    @Override
    public Boolean completarDatosPersonales(Long idUsuario, String sexo, Date fechaNacimiento) {

        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (usuario.isEmpty()) {
            return false;
        }

        Persona persona = usuario.get().getPersona();

        if (persona == null) {
            return false;
        }

        persona.setSexo(Sexo.getByCodigo(sexo));
        persona.setFechaNacimiento(fechaNacimiento);

        usuarioRepository.save(usuario.get());

        return true;

    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    private Usuario toUsuarioEntity(UsuarioDto usuarioDto) {
        return modelMapper.map(usuarioDto, Usuario.class);
    }

}
