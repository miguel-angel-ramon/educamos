package es.jccm.edu.evaluacion.application.services.programacionAula;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.UnidadPorMateriaDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaUnidadCentroRepository;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaUnidadCentroService;

@Service
public class EvaUnidadCentroService implements IEvaUnidadCentroService {

    @Autowired
    private EvaUnidadCentroRepository unidadCentroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UnidadPorMateriaDTO> findUnidadesByProgramacionDidactica(Long idEmpleado, Long idDidac, Long codigoCentro, Long anno) {
		return unidadCentroRepository.findUnidadesByProgramacionDidactica(idEmpleado, idDidac, codigoCentro, anno).stream().map(x -> modelMapper.map(x, UnidadPorMateriaDTO.class)).collect(Collectors.toList());
	}
}