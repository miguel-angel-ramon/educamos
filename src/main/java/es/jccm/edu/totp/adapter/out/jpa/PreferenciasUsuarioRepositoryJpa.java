package es.jccm.edu.totp.adapter.out.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.jccm.edu.totp.adapter.out.springdata.TotpUsuarioRepository;
import es.jccm.edu.totp.application.domain.PreferenciasUsuario;
import es.jccm.edu.totp.application.ports.out.Cuenta2faGoogleRepository;
import es.jccm.edu.totp.application.ports.out.PreferenciasUsuarioRepository;

@Repository
public class PreferenciasUsuarioRepositoryJpa implements PreferenciasUsuarioRepository {

    private TotpUsuarioRepository usuariosRepository;
    private Cuenta2faGoogleRepository cuenta2faGoogleRepository;
	
	public PreferenciasUsuarioRepositoryJpa(TotpUsuarioRepository usuariosRepository, Cuenta2faGoogleRepository cuenta2faGoogleRepository) {
		super();
		this.usuariosRepository = usuariosRepository;
		this.cuenta2faGoogleRepository=cuenta2faGoogleRepository;
	}

	@Override
	public Optional<PreferenciasUsuario> load(long oid) {
		return usuariosRepository.findById(oid)
        		.map(u->PreferenciasUsuario.builder()
        				.oid(u.getOid())
        				.cuentaGoogle(cuenta2faGoogleRepository.findByOid(oid))
        				.mail(u.getTCorreoE()==null?Optional.empty():Optional.of(u.getTCorreoE()))
        				.movil(Optional.empty())  //TODO: de momento no hay preferencias para enviar mediante movil asi que va vacio
        				.build());
	}
}
