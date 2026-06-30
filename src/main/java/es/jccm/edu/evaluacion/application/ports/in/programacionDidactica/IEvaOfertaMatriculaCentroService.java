package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaCentro;

public interface IEvaOfertaMatriculaCentroService {

	EvaOfertaMatriculaCentro getOfertaMatriculaCentroByCentroAndOfertaMatriculaGenerico(Long idCentro, Long idOfertaMatrig);
	
	EvaOfertaMatriculaCentro getCursoByCentroOfertamatrigAndAnyo(Long codigoCentro, Long idOfertaMatrig, Integer anyo);

}
