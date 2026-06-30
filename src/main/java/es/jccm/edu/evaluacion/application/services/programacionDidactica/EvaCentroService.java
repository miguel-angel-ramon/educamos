package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaCentroRepository;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaCentroService;

@Service
public class EvaCentroService implements IEvaCentroService {

    private static final Logger LOG = LogManager.getLogger(EvaCentroService.class);

    @Autowired
    private EvaCentroRepository centroRepository;

    @Autowired
    private ModelMapper modelMapper;

}