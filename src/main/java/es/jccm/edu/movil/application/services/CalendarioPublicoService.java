package es.jccm.edu.movil.application.services;


import es.jccm.edu.movil.adapter.out.repository.CalendarioPublicoRepository;
import es.jccm.edu.movil.application.domain.CalendarioPublico;
import es.jccm.edu.movil.application.domain.projection.CalendarioPublicoProjection;
import es.jccm.edu.movil.application.ports.in.ICalendarioPublicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarioPublicoService implements ICalendarioPublicoService {
	
	@Autowired
	private CalendarioPublicoRepository calendarioPublicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	/**

	 Obtiene una lista de los días festivos del último año.
	 @return una lista de objetos de proyección de calendario público que representan los días festivos.
	 */
		@Override
		public List<CalendarioPublicoProjection> getFestivos() {
            Long añoActual = Long.valueOf(LocalDate.now().getYear());
			List<CalendarioPublicoProjection> festivos =  calendarioPublicoRepository.findCalendarioPublicoDelUltimoAnio(añoActual);  //calendarioPublicoRepository.findAll();
			return festivos;
		}
	/**Obtiene una lista de los días festivos identificados por el id especificado.
	@param id el id que identifica los días festivos que se van a obtener.
	@return una lista de objetos de proyección de calendario público que representan los días festivos identificados por el id especificado.
	*/
		@Override
		public List<CalendarioPublicoProjection> getFestivosByIdentificador(Long id) {
			List<CalendarioPublicoProjection> festivos =  calendarioPublicoRepository.getFestivosByIdentificador(id);
			return festivos;
		}

}
