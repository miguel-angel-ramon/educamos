package es.jccm.edu.ausencias.application.services.profesores;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.ausencias.adapter.out.repository.profesores.AusenciasProfesoresRepository;
import es.jccm.edu.ausencias.application.domain.profesores.AusenciasProfesores;
import es.jccm.edu.ausencias.application.ports.in.profesores.IAusenciasProfesores;

@Service
public class AusenciasProfesoresService implements IAusenciasProfesores {
	@Autowired
	private AusenciasProfesoresRepository ausenciasProfesoresRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AusenciasProfesores> getAusenciasProfesores(Long codCentro, Integer anno, String fecha) {

		List<AusenciasProfesores> listOut;

		try {
			listOut = ausenciasProfesoresRepository.getAusenciasProfesores(codCentro, anno, fecha).stream()
					.map(perfil -> modelMapper.map(perfil, AusenciasProfesores.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return listOut;
	}

}
