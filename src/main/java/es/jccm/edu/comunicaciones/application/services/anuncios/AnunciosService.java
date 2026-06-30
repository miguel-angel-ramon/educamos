package es.jccm.edu.comunicaciones.application.services.anuncios;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.comunicaciones.adapter.out.repositories.anuncios.AnuncioRepository;
import es.jccm.edu.comunicaciones.application.domain.anuncios.AnuncioList;
import es.jccm.edu.comunicaciones.application.domain.anuncios.projection.AnuncioProjection;
import es.jccm.edu.comunicaciones.application.ports.in.anuncios.IAnunciosService;

@Service
public class AnunciosService implements IAnunciosService {
	
	private static final Logger LOG = LogManager.getLogger(AnunciosService.class);

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AnuncioList> getAnuncios(Long idCentro){
    	
    	LOG.info("Obteniendo los anuncios del centro con id = {}", idCentro);

        List<AnuncioProjection> anuncios = anuncioRepository.findAllAnuncios(idCentro);

        return anuncios.stream().map(anuncio -> modelMapper.map(anuncio, AnuncioList.class)).collect(Collectors.toList());

    }

}
