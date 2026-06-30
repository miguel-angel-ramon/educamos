package es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoActividadesParteDiaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanActmodDto;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoActividadesParteDiaProjection;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IPardiaAluplanService {

    PardiaAluplanDto crearPardiaAluplan(PardiaAluplanDto pardiaAluplanDto, List<PardiaAluplanActmodDto> actividadesDto);

    PardiaAluplanDto actualizarPardiaAluplan(Long id, PardiaAluplanDto pardiaAluplanDto, List<PardiaAluplanActmodDto> actividadesDto);

    @Transactional
    boolean borrarPardiaAluplan(Long idPardiaAluplan);

    Optional<PardiaAluplanDto> buscarPorId(Long id);

    List<InfoActividadesParteDiaDto> getInfoActividadesPorConvProyYFecha(Long idConvProyAlu, String fecha);
}
