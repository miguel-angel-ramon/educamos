package es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.QMunicipio;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MunicipioRepository extends AbstractRepository<Municipio, Long, QMunicipio> {
	
	Optional<Municipio> findByIdProvinciaAndIdMunicipio(Long idProvincia, Long idMunicipio);

}
