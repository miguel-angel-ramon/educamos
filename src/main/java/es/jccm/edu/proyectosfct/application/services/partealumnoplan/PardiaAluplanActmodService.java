package es.jccm.edu.proyectosfct.application.services.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanActmodDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanActmodRepository;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplanActmod;
import es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan.IPardiaAluplanActmodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PardiaAluplanActmodService implements IPardiaAluplanActmodService {

    @Autowired
    private PardiaAluplanActmodRepository pardiaAluplanActmodRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PardiaAluplanActmodDto crearPardiaAluplanActmod(PardiaAluplanActmodDto pardiaAluplanActmodDto) {
        // Convertir el DTO a la entidad
        PardiaAluplanActmod pardiaAluplanActmod = modelMapper.map(pardiaAluplanActmodDto, PardiaAluplanActmod.class);

        // Guardar la entidad en la base de datos
        PardiaAluplanActmod entidadGuardada = pardiaAluplanActmodRepository.save(pardiaAluplanActmod);

        // Convertir la entidad guardada de vuelta al DTO y retornarla
        return modelMapper.map(entidadGuardada, PardiaAluplanActmodDto.class);
    }

    @Override
    @Transactional
    public boolean borrarPardiaAluplanActmod(PardiaAluplanActmodDto pardiaAluplanActmodDto) {
        // Extraer los IDs directamente del DTO
        Long idPardiaAluplan = pardiaAluplanActmodDto.getIdPardiaAluplan();
        Long idActividadModulo = pardiaAluplanActmodDto.getIdActividadModulo();

        // Buscar la relación específica en la base de datos
        Optional<PardiaAluplanActmod> relacion = pardiaAluplanActmodRepository.findByPardiaAluplanIdAndActividadModuloId(
                idPardiaAluplan,
                idActividadModulo
        );

        if (relacion.isPresent()) {
            // Si la relación existe, eliminarla
            pardiaAluplanActmodRepository.delete(relacion.get());
            return true;
        }
        return false;
    }
}
