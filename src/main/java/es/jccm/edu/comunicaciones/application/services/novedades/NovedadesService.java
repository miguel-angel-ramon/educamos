package es.jccm.edu.comunicaciones.application.services.novedades;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.comunicaciones.adapter.out.repositories.novedades.NovedadRepository;
import es.jccm.edu.comunicaciones.application.domain.novedades.Novedad;
import es.jccm.edu.comunicaciones.application.ports.in.novedades.INovedadesService;

@Service
public class NovedadesService implements INovedadesService {

	private static final Logger LOG = LogManager.getLogger(NovedadesService.class);

	@Autowired
	private NovedadRepository novedadRepository;

	@Override
	public List<Novedad> getNovedades(String idUsuario) {
		LOG.info("Obteniendo las novedades para el usuario = {}", idUsuario);

		return novedadRepository.findAllNovedades(idUsuario);

	}

	@Override
	public Novedad getNovedad(Long idNovedad) {

		Optional<Novedad> novedad = novedadRepository.findById(idNovedad);

		return novedad.orElse(null);

	}

}
