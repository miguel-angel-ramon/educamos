package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ParteSemanalAnexosProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QParteSemanalAnexosProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ListadoAnioCentroProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParteSemanalAnexosProyectoRepository extends AbstractRepository<ParteSemanalAnexosProyecto, Long, QParteSemanalAnexosProyecto> {

    List<ParteSemanalAnexosProyecto> findAllByIdConvProyAlu(Long idConvProyAlu);

    @Query(value=" select mua.c_anno as anio, " +
            "                   cen.x_centro as idCentro  " +
            "     from fct_convproy_alu conv, " +
            "          tlmatalu mua, " +
            "          tlcentros cen " +
            "     where conv.id_convproy_alu = :idConvProyAlu " +
            "           and mua.x_matricula = conv.x_matricula " +
            "           and cen.x_centro = mua.x_centro ", nativeQuery = true)
    ListadoAnioCentroProjection getAnioAndIdCentro(Long idConvProyAlu);

}
