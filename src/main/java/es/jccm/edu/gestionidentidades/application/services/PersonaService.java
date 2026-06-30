package es.jccm.edu.gestionidentidades.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.PersonaRepository;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.ports.in.IPersonaService;

@Transactional
@Service
public class PersonaService implements IPersonaService{
	
	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public Persona createPersona(Persona persona) {
		return personaRepository.save(persona);
	}
	
	@Override
	public void deletePersona(Long idPersona) {
		personaRepository.deleteById(idPersona);
	}

}
