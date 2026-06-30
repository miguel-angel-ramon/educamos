package es.jccm.edu.evaluacion.application.ports.in.calificacionActividades;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.MateriaCursoGenericaDTO;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;

public interface IEvaMateriaCursoOfertaMatriculaGenericaService {

	MateriaCursoGenericaDTO getMateria(Long idMateriaOmg) throws EvaluacionException;
}
