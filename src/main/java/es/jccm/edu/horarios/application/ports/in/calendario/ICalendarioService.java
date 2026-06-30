package es.jccm.edu.horarios.application.ports.in.calendario;

import java.util.List;

import es.jccm.edu.horarios.application.domain.calendario.Calendario;

public interface ICalendarioService {

	List<Calendario> getFestivos();

	List<Calendario> getFestivosByIdentificador(Long id);
	
	List<Calendario> getDiasFestivos(Long anyo, Long idCentro);

}
