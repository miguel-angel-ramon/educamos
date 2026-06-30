package es.jccm.edu.comunicaciones.application.ports.in.anuncios;

import java.util.List;

import es.jccm.edu.comunicaciones.application.domain.anuncios.AnuncioList;

public interface IAnunciosService {

    List<AnuncioList> getAnuncios(Long idCentro);

}
