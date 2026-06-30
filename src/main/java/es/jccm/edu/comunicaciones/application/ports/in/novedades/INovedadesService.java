package es.jccm.edu.comunicaciones.application.ports.in.novedades;

import es.jccm.edu.comunicaciones.application.domain.novedades.Novedad;

import java.util.List;

public interface INovedadesService {

    List<Novedad> getNovedades(String idUsuario);

    Novedad getNovedad(Long idNovedad);

}
