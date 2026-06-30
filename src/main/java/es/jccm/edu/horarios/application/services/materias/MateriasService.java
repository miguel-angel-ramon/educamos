package es.jccm.edu.horarios.application.services.materias;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.horarios.adapter.out.repositories.materias.MateriaRepository;
import es.jccm.edu.horarios.application.domain.materias.MateriaList;
import es.jccm.edu.horarios.application.domain.materias.projection.MateriaProjection;
import es.jccm.edu.horarios.application.ports.in.materias.IMateriasService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MateriasService implements IMateriasService {
	
	private static final Logger LOG = LogManager.getLogger(MateriasService.class);
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<MateriaList> getMaterias(String idUsuario, Integer anno) {
		
		LOG.info("Obteniendo materias del usuario = {}", idUsuario);
		
		List<MateriaProjection> materias = materiaRepository.findAllMaterias(idUsuario, anno);
		
		return materias.stream().map(materia -> modelMapper.map(materia, MateriaList.class)).collect(Collectors.toList());
	}

}
