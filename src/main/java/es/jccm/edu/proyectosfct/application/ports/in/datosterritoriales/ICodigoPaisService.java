package es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;

import java.util.List;

public interface ICodigoPaisService {
	
	CodigoPais findCodigoPaisById(String id);

	List<CodigoPais> findAll();

}
