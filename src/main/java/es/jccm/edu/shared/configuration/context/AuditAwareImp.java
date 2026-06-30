package es.jccm.edu.shared.configuration.context;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;


import es.jccm.edu.shared.annotations.GetTokenJwt;

public class AuditAwareImp implements AuditorAware<Long> {
	
	@Autowired
    private GetTokenJwt getTokenJwt;

    @Override
    public Optional<Long> getCurrentAuditor() {

        Long usuario = getTokenJwt.getOidDeUsuarioDesdeTokenJwt();
        
        return Optional.ofNullable(usuario);

    }

	
}