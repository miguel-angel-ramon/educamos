package es.jccm.edu.horarios.application.services.calendario;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.horarios.adapter.out.repositories.calendario.CalendarioRepository;
import es.jccm.edu.horarios.application.domain.calendario.Calendario;
import es.jccm.edu.horarios.application.ports.in.calendario.ICalendarioService;

@Service
public class CalendarioService implements ICalendarioService{
	
	@Autowired
	private CalendarioRepository calendarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
		public List<Calendario> getFestivos() {
			
			List<Calendario> festivos =  (List<Calendario>) calendarioRepository.findAll();
			
			return festivos.stream().map(entity -> modelMapper.map(entity, Calendario.class)).collect(Collectors.toList());
		}

		@Override
		public List<Calendario> getFestivosByIdentificador(Long id) {
			
			return calendarioRepository.getFestivosByIdentificador(id).stream().map(entity -> modelMapper.map(entity, Calendario.class)).collect(Collectors.toList());

		}

		public List<Calendario> getDiasFestivos(Long anyo, Long idCentro) {
			return calendarioRepository.getDiasFestivos(anyo, idCentro).stream().map(entity -> modelMapper.map(entity, Calendario.class)).collect(Collectors.toList());
		}

}
