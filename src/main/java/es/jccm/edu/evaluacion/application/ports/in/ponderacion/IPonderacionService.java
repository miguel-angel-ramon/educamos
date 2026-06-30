package es.jccm.edu.evaluacion.application.ports.in.ponderacion;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto;
import es.jccm.edu.evaluacion.application.domain.ponderacion.DocentePonderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.MateriasUnidad;
import es.jccm.edu.evaluacion.application.domain.ponderacion.Ponderacion;

public interface IPonderacionService {

	List<MateriasUnidad> getMateriasUnidad(Long idEmpleado, Integer anno, Long codigoCentro);

	Ponderacion getPonderaciones(Long idMateria, Long idEmpleado);

	void savePonderacion(PonderacionDto ponderacion, Integer guardado);

	List<DocentePonderacion> getDocentesPonderacion(Long codCentro, Long idMateria, Long idEmpleado);
	
	Boolean compruebaPonderacionEditable(Long idPonderacion);

}
