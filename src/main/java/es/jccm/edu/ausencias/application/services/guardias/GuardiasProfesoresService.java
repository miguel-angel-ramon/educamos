package es.jccm.edu.ausencias.application.services.guardias;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.ausencias.adapter.out.repository.guardias.GuardiasProfesoresRepository;
import es.jccm.edu.ausencias.application.domain.guardias.DatosProfesoresGuardias;
import es.jccm.edu.ausencias.application.domain.guardias.GuardiasProfesores;
import es.jccm.edu.ausencias.application.ports.in.guardias.IGuardiasProfesores;

@Service
public class GuardiasProfesoresService implements IGuardiasProfesores {

	@Autowired
	private GuardiasProfesoresRepository guardiasProfesoresRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<GuardiasProfesores> getGuardiasProfesores(Long codCentro, Integer anno) {

		return guardiasProfesoresRepository.getGuardiasProfesores(codCentro, anno).stream()
				.map(perfil -> modelMapper.map(perfil, GuardiasProfesores.class)).collect(Collectors.toList());
	}

	public List<DatosProfesoresGuardias> getDatosProfesoresGuardias(Long codCentro, Long idTramo, Integer diaSemana) {

		return guardiasProfesoresRepository.getDatosProfesoresGuardias(codCentro, idTramo, diaSemana).stream()
				.map(perfil -> modelMapper.map(perfil, DatosProfesoresGuardias.class)).collect(Collectors.toList());
	}

}
