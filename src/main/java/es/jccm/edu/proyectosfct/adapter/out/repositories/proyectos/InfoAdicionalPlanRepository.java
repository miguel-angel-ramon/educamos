package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.InfoAdicionalPlan;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QInfoAdicionalPlan;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoAdicionalPlanRepository extends AbstractRepository<InfoAdicionalPlan, Long, QInfoAdicionalPlan> {
    InfoAdicionalPlan findByIdProyecto(Long idProyecto);
}
