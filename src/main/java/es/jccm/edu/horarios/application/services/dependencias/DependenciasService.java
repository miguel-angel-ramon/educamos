package es.jccm.edu.horarios.application.services.dependencias;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.horarios.application.domain.dependencias.DependenciaLibreList;
import es.jccm.edu.horarios.application.domain.dependencias.TipoDependencia;
import es.jccm.edu.horarios.application.domain.dependencias.projection.DependenciaLibreProjection;
import es.jccm.edu.horarios.application.domain.dependencias.projection.TipoDependenciaProjection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.jccm.edu.horarios.adapter.out.repositories.dependencias.DependenciaRepository;
import es.jccm.edu.horarios.application.domain.dependencias.DependenciaList;
import es.jccm.edu.horarios.application.domain.dependencias.projection.DependenciaProjection;
import es.jccm.edu.horarios.application.ports.in.dependencias.IDependenciasService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DependenciasService implements IDependenciasService {
	
	private static final Logger LOG = LogManager.getLogger(DependenciasService.class);
	
	@Autowired
	private DependenciaRepository dependenciaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<DependenciaList> getDependencias(String idUsuario, Integer anno) {
		
		LOG.info("Obteniendo dependencias del usuario = {}", idUsuario);
		
		List<DependenciaProjection> dependencias = dependenciaRepository.findAllDependencias(idUsuario, anno);
		
		return dependencias.stream().map(dependencia -> modelMapper.map(dependencia, DependenciaList.class)).collect(Collectors.toList());
	}

	public Page<DependenciaLibreList> getDependenciasLibres(Long idCentro, Integer anno, Integer diaSemana, int page, int numItems) {

		Pageable paging = PageRequest.of(page, numItems);

		Page<DependenciaLibreProjection> dependenciasLibres = dependenciaRepository.findDependenciasLibresByCentro(idCentro, anno, diaSemana, paging);

		return dependenciasLibres.map(dependenciaLibre -> modelMapper.map(dependenciaLibre, DependenciaLibreList.class));

	}

	public List<TipoDependencia> getTiposDependenciaByCentro(Long codCentro) {

		LOG.info("Obteniendo todos los tipos de dependencias por centro");

		List<TipoDependenciaProjection> tipos = dependenciaRepository.findTiposDependenciaByCentro(codCentro);
		return tipos.stream().map(tipo -> modelMapper.map(tipo, TipoDependencia.class)).collect(Collectors.toList());
	}

}
