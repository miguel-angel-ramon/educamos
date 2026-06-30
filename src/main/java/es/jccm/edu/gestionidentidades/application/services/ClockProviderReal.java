package es.jccm.edu.gestionidentidades.application.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.application.ports.in.ClockProvider;

@Service
public class ClockProviderReal implements ClockProvider{

	@Override
	public Date getNow() {
		return new Date();
	}

}
