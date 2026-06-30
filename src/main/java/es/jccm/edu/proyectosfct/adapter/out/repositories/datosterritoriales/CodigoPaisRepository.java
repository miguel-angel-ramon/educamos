package es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.QCodigoPais;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface CodigoPaisRepository extends AbstractRepository<CodigoPais, Long, QCodigoPais> {
	
	Optional<CodigoPais> findOneById(String id);

    @Query(value="select * from tlpais where l_activo = 'S' order by d_pais",nativeQuery = true)
	List<CodigoPais> findByPaisActivoOrderByDescripcionLarga();

}
