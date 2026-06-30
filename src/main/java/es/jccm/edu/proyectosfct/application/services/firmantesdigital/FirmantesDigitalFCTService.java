package es.jccm.edu.proyectosfct.application.services.firmantesdigital;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.firmantesdigital.AdjuntosFirmantesFCTRepository;
import es.jccm.edu.proyectosfct.application.ports.in.firmantesdigital.IFirmantesFCTDigitalService;



@Service
public class FirmantesDigitalFCTService implements IFirmantesFCTDigitalService {
	
	@Autowired
	private AdjuntosFirmantesFCTRepository adjuntosFirmantesRepository;	

	@Override
	public List<String> getEntornoFirma() {
		List<String> entornoFirma = adjuntosFirmantesRepository.getEntornoFirma();
		return entornoFirma;
	}


}
