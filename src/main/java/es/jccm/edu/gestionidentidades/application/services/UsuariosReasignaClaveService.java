package es.jccm.edu.gestionidentidades.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosReasignaClaveRepository;
import es.jccm.edu.gestionidentidades.application.domain.UsuariosReasignaClave;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuariosReasignaClaveService;

@Transactional
@Service
public class UsuariosReasignaClaveService implements IUsuariosReasignaClaveService{

	@Autowired
	private UsuariosReasignaClaveRepository usuariosReasignaClaveRepository;
	
	public UsuariosReasignaClave save(UsuariosReasignaClave usuariosReasignaClave) {
		return usuariosReasignaClaveRepository.save(usuariosReasignaClave);
	}
}
