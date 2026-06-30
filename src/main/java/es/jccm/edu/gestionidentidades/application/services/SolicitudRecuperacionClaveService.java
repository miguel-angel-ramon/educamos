package es.jccm.edu.gestionidentidades.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.SolicitudRecuperacionClaveRepository;
import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;
import es.jccm.edu.gestionidentidades.application.ports.in.ISolicitudRecuperacionClaveService;

@Transactional
@Service
public class SolicitudRecuperacionClaveService implements ISolicitudRecuperacionClaveService{
	
	@Autowired
	private SolicitudRecuperacionClaveRepository solicitudRecuperacionClaveRepository;
	
	@Override
	public void guardarSolicitud(SolicitudRecuperacionClave solicitud) {
		solicitudRecuperacionClaveRepository.save(solicitud);
	}
	
	@Override
	public List<SolicitudRecuperacionClave> findByOidUsuario(Long id){
		return solicitudRecuperacionClaveRepository.findByOidUsuario(id);
	}

}
