package es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;

public interface IMunicipioService {
	
	Municipio findMunicipioByProvinciaAndMunicipio(Long idProvincia, Long idMunicipio);

}
