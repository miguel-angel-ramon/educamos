package es.jccm.edu.movil.application.ports.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.jccm.edu.movil.adapter.in.rest.novedades.model.NovedadesPublicoDTO;

import java.util.List;

public interface INovedadesPublicoService {
    List<NovedadesPublicoDTO> obtenerNovedades() throws JsonProcessingException;

	List<NovedadesPublicoDTO> obtenerNovedadesFiltradas() throws JsonProcessingException;
}
