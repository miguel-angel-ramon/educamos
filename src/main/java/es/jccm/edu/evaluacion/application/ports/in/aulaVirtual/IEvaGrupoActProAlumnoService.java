package es.jccm.edu.evaluacion.application.ports.in.aulaVirtual;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.GrupoActProAlumnoDTO;

public interface IEvaGrupoActProAlumnoService {
	
	GrupoActProAlumnoDTO getGrupoActProAlumnoById(Long idGrupoActProAlumno);
}
