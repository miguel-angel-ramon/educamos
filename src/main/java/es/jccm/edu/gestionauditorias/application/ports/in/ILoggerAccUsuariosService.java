package es.jccm.edu.gestionauditorias.application.ports.in;

import es.jccm.edu.gestionauditorias.adapter.in.rest.model.LoggAccUsuSisAutDto;

public interface ILoggerAccUsuariosService {

	void savelogaccususisaut (LoggAccUsuSisAutDto loggAccUsuSisAut);
}
