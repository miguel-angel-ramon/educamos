package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.OfertaMatriculaGenericoDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaOfertaMatriculaGenericoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaGenerico;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaOfertaMatriculaGenericoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaOfertaMatriculaGenericoService implements IEvaOfertaMatriculaGenericoService {

    @Autowired
    private EvaOfertaMatriculaGenericoRepository ofertaMatriculaGenericoRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public List<OfertaMatriculaGenericoDTO> getCursosCentro(Long codigoCentro, Integer anyo) {
		log.debug("Obteniendo cursos del centro con código ", codigoCentro);
		List<EvaOfertaMatriculaGenerico> ofertas = ofertaMatriculaGenericoRepository.findAllByAnyoAndCodigoCentro(anyo, codigoCentro);
		return ofertas.stream().map(entity -> modelMapper.map(entity, OfertaMatriculaGenericoDTO.class)).collect(Collectors.toList());
	}

	@Override
	public OfertaMatriculaGenericoDTO getCursoByOfertaMatrig(Long idOfermatrig) throws Exception {
		Optional<EvaOfertaMatriculaGenerico> curso = ofertaMatriculaGenericoRepository.findById(idOfermatrig);
		if(curso.isPresent()) {
			return modelMapper.map(curso.get(), OfertaMatriculaGenericoDTO.class);
		} else {
			log.error("No se ha encontrado curso con id: " + idOfermatrig);
			throw new Exception("No se ha encontrado curso con id: " + idOfermatrig);
		}
	}
	
	public List<OfertaMatriculaGenericoDTO> getCursosACNEAECentro(Long codigoCentro, Integer anyo) {
		log.debug("Obteniendo cursos ACNEAE del centro con código ", codigoCentro);
		List<EvaOfertaMatriculaGenerico> ofertas = ofertaMatriculaGenericoRepository.findAllACNEAEByAnyoAndCodigoCentro(anyo, codigoCentro);
		return ofertas.stream().map(entity -> modelMapper.map(entity, OfertaMatriculaGenericoDTO.class)).collect(Collectors.toList());
	}
	
	public List<OfertaMatriculaGenericoDTO> getNivelesCurricularesACNEAEByCentroAnyoCursoAndMateria(Long codigoCentro, Integer anyo, Long idOfertaMatrig, Long idMateriaOmg) {
		log.debug("Obteniendo cursos ACNEAE del centro con código ", codigoCentro);
		List<EvaOfertaMatriculaGenerico> ofertas = ofertaMatriculaGenericoRepository.findAllNivelesCurricularesACNEAEByAnyoCodigoCentroCursoAndMateria(anyo, codigoCentro, idOfertaMatrig, idMateriaOmg);
		return ofertas.stream().map(entity -> modelMapper.map(entity, OfertaMatriculaGenericoDTO.class)).collect(Collectors.toList());
	}

}