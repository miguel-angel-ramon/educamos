package es.jccm.edu.movil.application.ports.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.jccm.edu.movil.adapter.in.rest.novedades.model.NovedadesPublicoDTO;

import java.util.List;

public interface INovedadesPlugin {

    public List<NovedadesPublicoDTO> obtenerNovedades() throws JsonProcessingException;

    public List<NovedadesPublicoDTO> obtenerNovedadesFiltradas() throws JsonProcessingException;

}
