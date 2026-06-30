package es.jccm.edu.totp.adapter.out.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.jccm.edu.totp.adapter.out.jpamodel.TotpUsuario;
import es.jccm.edu.totp.adapter.out.springdata.TotpUsuarioRepository;
import es.jccm.edu.totp.application.domain.TotpCaduco;
import es.jccm.edu.totp.application.ports.out.TotpCaducoRepository;

@Repository
public class TotpCaducoRepositoryJpa implements TotpCaducoRepository {

    private final TotpUsuarioRepository usuariosRepository;
	
	public TotpCaducoRepositoryJpa(TotpUsuarioRepository usuariosRepository) {
		super();
		this.usuariosRepository = usuariosRepository;
	}
	

	@Override
	public Optional<TotpCaduco> findByOid(long oid){
        return usuariosRepository.findById(oid)
        		.map(u->TotpCaduco.builder()
        				.oidUsuario(u.getOid())
        				.codigo2FA(u.getCodigo2fa())
        				.fechaGeneracionCodigo(u.getFechaGeneracionCodigo())
        				.build());
	}
	
	@Override
	public void update(TotpCaduco clave2fa){
		 Optional<TotpUsuario> ou=usuariosRepository.findById(clave2fa.getOidUsuario());
		if(ou.isEmpty()) {
			return;
		}
		
		TotpUsuario u=ou.get();
		
		u.setCodigo2fa(clave2fa.getCodigo2FA());
		u.setFechaGeneracionCodigo(clave2fa.getFechaGeneracionCodigo());
		
		usuariosRepository.save(u);
	}
}
