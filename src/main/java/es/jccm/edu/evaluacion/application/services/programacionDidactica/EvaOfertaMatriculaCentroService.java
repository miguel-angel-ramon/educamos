package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaOfertaMatriculaCentroRepository;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaCentro;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaOfertaMatriculaCentroService;

@Service
public class EvaOfertaMatriculaCentroService implements IEvaOfertaMatriculaCentroService {

    private static final Logger LOG = LogManager.getLogger(EvaOfertaMatriculaCentroService.class);

    @Autowired
    private EvaOfertaMatriculaCentroRepository ofertaMatriculaCentroRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public EvaOfertaMatriculaCentro getOfertaMatriculaCentroByCentroAndOfertaMatriculaGenerico(Long idCentro, Long idOfertaMatrig) {
		Optional<EvaOfertaMatriculaCentro> ofertaMatriculaCentro = ofertaMatriculaCentroRepository.findByCentroIdAndOfertaMatriculaGenericoId(idCentro, idOfertaMatrig);
		if (ofertaMatriculaCentro.isPresent()) {
			return ofertaMatriculaCentro.get();
		} else {
			return null;
		}
	}
	
	public EvaOfertaMatriculaCentro getCursoByCentroOfertamatrigAndAnyo(Long codigoCentro, Long idOfertaMatrig, Integer anyo) {
		return ofertaMatriculaCentroRepository.getCursoByCentroOfertamatrigAndAnyo(codigoCentro, idOfertaMatrig, anyo);
	}

}