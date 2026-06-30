package es.jccm.edu.gestionidentidades.adapter.out.repository;

import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.QAplicacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AplicacionRepository extends AbstractRepository<Aplicacion, Long, QAplicacion> {

    Aplicacion findByCodigo(String codigo);

}
