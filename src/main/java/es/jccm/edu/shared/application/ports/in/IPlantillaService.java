package es.jccm.edu.shared.application.ports.in;

import es.jccm.edu.shared.application.domain.plantilla.entities.Plantilla;

public interface IPlantillaService {
	// Create
	void createPlantilla(Plantilla plantilla) throws NullPointerException;
	
}
