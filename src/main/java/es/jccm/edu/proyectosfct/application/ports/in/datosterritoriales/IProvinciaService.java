package es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Provincia;

public interface IProvinciaService {

	// Read
	Provincia findProvinciaById(Long id);
	
	// Read
	List<Provincia> getListadoProvincias();

	List<Provincia> getListadoProvinciasManchegas();

}
