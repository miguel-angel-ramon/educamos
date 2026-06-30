package es.jccm.edu.alumnos.application.services.unidadesTutor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.unidadesTutor.UnidadesTutorRepository;
import es.jccm.edu.alumnos.application.domain.unidadesTutor.UnidadesTutor;
import es.jccm.edu.alumnos.application.domain.unidadesTutor.projection.UnidadesTutorProjection;
import es.jccm.edu.alumnos.application.ports.in.unidadesTutor.IUnidadesTutorService;

@Service
public class UnidadesTutorService implements IUnidadesTutorService {

	@Autowired
	private UnidadesTutorRepository unidadesTutorRepository;

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public List<UnidadesTutor> getTutoresByUnidades(List<Long> idEmpleados, Long anno) {
		
		try {
			List<UnidadesTutorProjection> uniTutor = unidadesTutorRepository.tutoresByUnidad(idEmpleados, anno);	
			
			return uniTutor.stream().map(x -> modelMapper.map(x, UnidadesTutor.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
