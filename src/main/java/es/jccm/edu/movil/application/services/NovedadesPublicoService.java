package es.jccm.edu.movil.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.jccm.edu.movil.adapter.in.rest.novedades.model.NovedadesPublicoDTO;
import es.jccm.edu.movil.application.ports.in.INovedadesPublicoService;
import es.jccm.edu.movil.application.ports.out.INovedadesPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovedadesPublicoService implements INovedadesPublicoService {

    @Autowired
    public INovedadesPlugin novedadesPlugin;
    
    public List<NovedadesPublicoDTO> obtenerNovedades() throws JsonProcessingException {
        return novedadesPlugin.obtenerNovedades();
    }
    
    public List<NovedadesPublicoDTO> obtenerNovedadesFiltradas() throws JsonProcessingException {
        return novedadesPlugin.obtenerNovedadesFiltradas();
    }
}
