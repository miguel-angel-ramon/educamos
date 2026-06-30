package es.jccm.edu.shared.application.services.segsocial;

import org.springframework.beans.factory.annotation.Autowired;

import es.jccm.edu.shared.adapter.out.resttemplate.segsocial.ClientSegSocial;
import es.jccm.edu.shared.application.ports.in.segsocial.ISegSocialService;

public class SegSocialService implements ISegSocialService{

	@Autowired
	ClientSegSocial cliente;
	
	@Override
	public String getTokenAcceso() {
		return cliente.getToken();
	}

}
