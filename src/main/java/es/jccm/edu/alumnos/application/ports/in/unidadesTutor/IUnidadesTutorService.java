package es.jccm.edu.alumnos.application.ports.in.unidadesTutor;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.unidadesTutor.UnidadesTutor;

public interface IUnidadesTutorService {
	
	List<UnidadesTutor> getTutoresByUnidades(List<Long> idEmpleados, Long anno);
}
