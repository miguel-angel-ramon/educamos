package es.jccm.edu.proyectosfct.application.services.modalidades;

import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.ports.in.modalidades.IModalidadesService;
import es.jccm.edu.proyectosfct.application.domain.modalidades.projection.ModalidadProjection;
import es.jccm.edu.proyectosfct.adapter.out.repositories.modalidades.ModalidadesRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModalidadesService implements IModalidadesService  {

	@Autowired
	private ModalidadesRepository modalidadesRepository;	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<Modalidad> getAllModalidadesFamiliaCentro(Long idCentro, int cAnno, Long idFamilia, Long idTipo) {		
		
		List<ModalidadProjection> modalidadProjection = modalidadesRepository.getAllModalidadesFamiliaCentro(idCentro,cAnno,idFamilia,idTipo);
		
		return modalidadProjection.stream()
				.map(entity -> modelMapper.map(entity, Modalidad.class)).collect(Collectors.toList());
	}
	

}
