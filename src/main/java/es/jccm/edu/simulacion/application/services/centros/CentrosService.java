package es.jccm.edu.simulacion.application.services.centros;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.simulacion.application.domain.centros.entities.DepartamentoCentro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.simulacion.adapter.out.repositories.centros.CentrosRepository;
import es.jccm.edu.simulacion.application.domain.centros.entities.Centros;
import es.jccm.edu.simulacion.application.domain.centros.projection.CentroProjection;
import es.jccm.edu.simulacion.application.ports.in.centros.ICentrosService;

@Service
public class CentrosService implements ICentrosService {

	private static final Logger LOG = LogManager.getLogger(CentrosService.class);

	@Autowired
	private CentrosRepository centroRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public CentroProjection getCentro(Long codCentro) {

		LOG.info("Obteniendo datos del centro con código = {}", codCentro);

		return centroRepository.findByCodCentro(codCentro);
	}
	
	public CentroProjection getCentroById(Long idCentro) {

		LOG.info("Obteniendo datos del centro con Id = {}", idCentro);

		return centroRepository.findByIdCentro(idCentro);
	}

	@Override
	public List<Centros> getListadoCentros(Long xUsuarioComunica, String codCuestionario) {
		String mensaje = String.format("Rescatando los centros donde el usuario con id = %s puede rellenar formularios con código = %s", xUsuarioComunica, codCuestionario);
		
		LOG.info(mensaje);
		
		try {
			
			List<Centros> centrosOut = centroRepository.getListadoCentros(xUsuarioComunica, codCuestionario).stream().map(x -> modelMapper.map(x, Centros.class)).collect(Collectors.toList());
			
			return centrosOut;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		
		}
	}

	public List<Centros> getCentrosInspector(Long idEmpleado){
		return centroRepository.getInspectorCentros(idEmpleado).stream().map(x -> modelMapper.map(x, Centros.class)).collect(Collectors.toList());
	}

	public List<DepartamentoCentro> getDepartamentosCentro(Long idCentro, Long anyo){
		return centroRepository.getDepartamentosCentro(idCentro, anyo).stream().map(x -> modelMapper.map(x, DepartamentoCentro.class)).collect(Collectors.toList());
	}

}
