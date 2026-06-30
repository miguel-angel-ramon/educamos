package es.jccm.edu.comunicaciones.application.services.avisos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.comunicaciones.adapter.out.repositories.avisos.AvisoRepository;
import es.jccm.edu.comunicaciones.application.domain.avisos.Aviso;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.IAvisosDelphosService;

@Service
public class AvisosDelphosService implements IAvisosDelphosService {
	
	private static final Logger LOG = LogManager.getLogger(AvisosDelphosService.class);
	
	@Autowired
	private AvisoRepository avisoRepository;
	
	public List<Aviso> getAvisosDelphos(String perfil, Integer nivEducativo) {
		
		LOG.debug("Obteniendo avisos para el perfil = {} y el nivel educativo = {}", perfil, nivEducativo);
		
		return avisoRepository.findByAvisoActivo("S").subList(0, 5);
	}

	@Override
	public List<Aviso> getAvisosByCentro(Long idCentro) {

		String nivEdu = avisoRepository.findNivEduByCentro(idCentro);

		String regex = "PERFILES=.*\\W(P|PRO)\\W([^=]*$|.*NIVEDU=.*\\W(" + nivEdu + ")\\W.*)";

		return avisoRepository.findByRegex(regex);

	}

}
