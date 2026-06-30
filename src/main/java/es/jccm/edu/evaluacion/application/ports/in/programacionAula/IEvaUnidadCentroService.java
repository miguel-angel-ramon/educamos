package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.UnidadPorMateriaDTO;

public interface IEvaUnidadCentroService {
	
	List<UnidadPorMateriaDTO> findUnidadesByProgramacionDidactica(Long idEmpleado, Long idDidac, Long codigoCentro, Long anno);
}