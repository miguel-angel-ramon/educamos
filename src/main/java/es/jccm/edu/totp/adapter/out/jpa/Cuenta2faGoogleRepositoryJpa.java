package es.jccm.edu.totp.adapter.out.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.jccm.edu.totp.adapter.out.jpamodel.TotpUsuario;
import es.jccm.edu.totp.adapter.out.springdata.TotpUsuarioRepository;
import es.jccm.edu.totp.application.domain.Cuenta2faGoogle;
import es.jccm.edu.totp.application.ports.out.Cuenta2faGoogleRepository;

@Repository
public class Cuenta2faGoogleRepositoryJpa implements Cuenta2faGoogleRepository {

    private final TotpUsuarioRepository usuariosRepository;
   
	@Value("${totp.google-issuer}") private String issuer;

	public Cuenta2faGoogleRepositoryJpa(TotpUsuarioRepository usuariosRepository) {
		super();
		this.usuariosRepository = usuariosRepository;
	}
	

	@Override
	public Optional<Cuenta2faGoogle> findByOid(long oid){
        return usuariosRepository.findById(oid)
        		.map(u->Cuenta2faGoogle.builder()
        				.oidUsuario(u.getOid())
        				.login(u.getTLogin())
        				.issuer(issuer)
        				.secretKey2FA(u.getSecretKey2fa())
        				.build());
	}
	
	@Override
	public void update(Cuenta2faGoogle cuenta2fa){
		 Optional<TotpUsuario> ou=usuariosRepository.findById(cuenta2fa.getOidUsuario());
		if(ou.isEmpty()) {
			return;
		}
		
		TotpUsuario u=ou.get();
		
		u.setSecretKey2fa(cuenta2fa.getSecretKey2FA());
		
		usuariosRepository.save(u);
	}
}
