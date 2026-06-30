package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ResultadosAprendizajeActividades;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.QResultadosAprendizajeActividades;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadosAprendizajeActividadesRepository extends AbstractRepository<ResultadosAprendizajeActividades, Long, QResultadosAprendizajeActividades> {

    void deleteByIdActividadModulo(Long idActividad);

    List<ResultadosAprendizajeActividades> findByIdActividadModulo(Long idActividadModulo);
}
