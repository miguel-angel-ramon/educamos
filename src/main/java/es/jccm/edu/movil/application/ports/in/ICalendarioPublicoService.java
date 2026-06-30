package es.jccm.edu.movil.application.ports.in;


import es.jccm.edu.movil.application.domain.CalendarioPublico;
import es.jccm.edu.movil.application.domain.projection.CalendarioPublicoProjection;

import java.util.List;

public interface ICalendarioPublicoService {

    List<CalendarioPublicoProjection> getFestivos();

    List<CalendarioPublicoProjection> getFestivosByIdentificador(Long id);

}
