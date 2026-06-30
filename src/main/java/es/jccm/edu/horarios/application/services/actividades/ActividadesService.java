package es.jccm.edu.horarios.application.services.actividades;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.horarios.adapter.out.repositories.actividades.ActividadRepository;
import es.jccm.edu.horarios.application.domain.actividades.ActividadList;
import es.jccm.edu.horarios.application.domain.actividades.projection.ActividadProjection;
import es.jccm.edu.horarios.application.ports.in.actividades.IActividadesService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActividadesService implements IActividadesService {
	
	private static final Logger LOG = LogManager.getLogger(ActividadesService.class);
	
	@Autowired
	private ActividadRepository actividadRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<ActividadList> getActividades(String idUsuario, Integer anno) {
		
		LOG.info("Obteniendo actividades del usuario = {}", idUsuario);
		
		List<ActividadProjection> actividades = actividadRepository.findAllActividades(idUsuario, anno);
		
		return actividades.stream().map(actividad -> modelMapper.map(actividad, ActividadList.class)).collect(Collectors.toList());
	}

}
