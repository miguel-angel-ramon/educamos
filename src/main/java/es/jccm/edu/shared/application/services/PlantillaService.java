package es.jccm.edu.shared.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.jccm.edu.shared.application.domain.plantilla.entities.Plantilla;
import es.jccm.edu.shared.application.ports.in.IPlantillaService;

@Service
@Validated
public class PlantillaService implements IPlantillaService {

    private static final Logger LOG = LogManager.getLogger(PlantillaService.class);
	
	@Override
	public void createPlantilla(Plantilla plantilla) throws NullPointerException {
        
        try {
            LOG.info(plantilla);
        } 
        catch (NullPointerException ex) 
        {
            LOG.info("EL OBJETO NO PUEDE SER NULO");
        }

    }
	
}
