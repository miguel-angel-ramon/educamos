package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaTipoCentroRepository;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaTipoCentroService;

@Service
public class EvaTipoCentroService implements IEvaTipoCentroService {

    private static final Logger LOG = LogManager.getLogger(EvaTipoCentroService.class);

    @Autowired
    private EvaTipoCentroRepository tipoCentroRepository;

    @Autowired
    private ModelMapper modelMapper;

}