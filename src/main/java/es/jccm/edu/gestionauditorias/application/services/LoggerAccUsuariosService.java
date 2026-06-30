package es.jccm.edu.gestionauditorias.application.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionauditorias.adapter.in.rest.model.LoggAccUsuSisAutDto;
import es.jccm.edu.gestionauditorias.adapter.out.repositories.LogAccesoUsuarioSisAutRepository;
import es.jccm.edu.gestionauditorias.application.domain.logger.LogAccesoUsuarioSisAut;
import es.jccm.edu.gestionauditorias.application.ports.in.ILoggerAccUsuariosService;

@Service
public class LoggerAccUsuariosService implements ILoggerAccUsuariosService {

	@Autowired
	LogAccesoUsuarioSisAutRepository logAccesoUsuarioSisAutRepository;

	@Override
	public void savelogaccususisaut(LoggAccUsuSisAutDto loggAccUsuSisAut) {

		Optional<LogAccesoUsuarioSisAut> logAccSisAutActual = logAccesoUsuarioSisAutRepository
				.findByIdUsuarioAndLoginAndSistemaAutenticacion(Long.parseLong(loggAccUsuSisAut.getIdUsuario()),
						loggAccUsuSisAut.getLogin(), loggAccUsuSisAut.getCodSistemaAut());

		if (logAccSisAutActual.isPresent()) {

			LogAccesoUsuarioSisAut logAccesoUsuarioSisAutActualizar = logAccSisAutActual.get();
			logAccesoUsuarioSisAutActualizar.setFechaUltimoAcceso(new Date());
			logAccesoUsuarioSisAutActualizar.setNumAccesos(logAccesoUsuarioSisAutActualizar.getNumAccesos() + 1);

			logAccesoUsuarioSisAutRepository.save(logAccesoUsuarioSisAutActualizar);

		} else {

			LogAccesoUsuarioSisAut logAccesoUsuarioSisAutNuevo = new LogAccesoUsuarioSisAut();
			logAccesoUsuarioSisAutNuevo.setIdUsuario(Long.parseLong(loggAccUsuSisAut.getIdUsuario()));
			logAccesoUsuarioSisAutNuevo.setLogin(loggAccUsuSisAut.getLogin());
			logAccesoUsuarioSisAutNuevo.setFechaUltimoAcceso(new Date());
			logAccesoUsuarioSisAutNuevo.setNumAccesos(1);
			logAccesoUsuarioSisAutNuevo.setSistemaAutenticacion(loggAccUsuSisAut.getCodSistemaAut());

			logAccesoUsuarioSisAutRepository.save(logAccesoUsuarioSisAutNuevo);

		}

	}

}
