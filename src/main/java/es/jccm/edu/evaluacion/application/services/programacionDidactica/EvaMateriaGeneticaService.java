package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaMateriaGenericaRepository;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaMateriaGeneticaService;

@Service
public class EvaMateriaGeneticaService implements IEvaMateriaGeneticaService {

    private static final Logger LOG = LogManager.getLogger(EvaMateriaGeneticaService.class);

    @Autowired
    private EvaMateriaGenericaRepository materiaGeneticaRepository;

    @Autowired
    private ModelMapper modelMapper;

}