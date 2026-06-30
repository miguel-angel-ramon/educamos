package es.jccm.edu.alumnos.application.services.familiasAlumnado;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.familiasAlumnado.FamiliaAlumnadoRepository;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FamiliaAlumnado;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.projection.FamiliaAlumnoProjection;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IFamiliasCentroService;

@Service
public class FamiliasCentroService implements IFamiliasCentroService{
		
	@Autowired
	private FamiliaAlumnadoRepository familiasRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<FamiliaAlumnado> getFamiliasAlumnadoCentro(Long idCentro, int annio) {
		List <FamiliaAlumnoProjection> familiasAlumnos=familiasRepository.findFamiliasAlumnadoCentro(idCentro, annio);
		return familiasAlumnos.stream().map(x -> modelMapper.map(x, FamiliaAlumnado.class)).collect(Collectors.toList());
	
	}




	
}
