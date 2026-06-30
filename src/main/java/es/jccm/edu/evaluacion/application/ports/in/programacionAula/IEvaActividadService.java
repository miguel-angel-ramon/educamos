package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;

public interface IEvaActividadService {

	EvaActividad getActividadById(Long idActividad);

	List<ActividadDTO> findActividadesByUnidadProgramacionAndConvocatoria(Long idUnidadProgramacion, Long idConvOmc);
	
	List<ActividadDTO> findActividadesByUnidadProgramacionAndConvocatoriaAndProgramacionAula(Long idUnidadProgramacion, Long idConvCentroOmc, Long idProgramacionAula);
	
	void deleteActividadById(Long idActividad);

}
