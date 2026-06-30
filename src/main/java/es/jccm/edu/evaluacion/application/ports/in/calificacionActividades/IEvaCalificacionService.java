package es.jccm.edu.evaluacion.application.ports.in.calificacionActividades;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;

public interface IEvaCalificacionService {

	EvaCalificacion getCalificacionById(Long idCalifica);
}
