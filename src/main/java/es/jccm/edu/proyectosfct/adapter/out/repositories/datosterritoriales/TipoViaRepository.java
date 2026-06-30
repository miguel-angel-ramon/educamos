package es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.QTipoVia;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TipoViaRepository extends AbstractRepository<TipoVia, Long, QTipoVia>{

}
