package es.jccm.edu.proyectosfct.application.services.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoActividadesParteDiaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanActmodDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ActividadesModulosRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanActmodRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.ParsemAluplanRepository;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplanActmod;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoActividadesParteDiaProjection;
import es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan.IPardiaAluplanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PardiaAluplanService implements IPardiaAluplanService {

    @Autowired
    private PardiaAluplanRepository pardiaAluplanRepository;

    @Autowired
    private PardiaAluplanActmodRepository pardiaAluplanActmodRepository;

    @Autowired
    private ParsemAluplanRepository parsemAluplanRepository;

    @Autowired
    private ActividadesModulosRepository actividadesModulosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PardiaAluplanDto crearPardiaAluplan(PardiaAluplanDto pardiaAluplanDto, List<PardiaAluplanActmodDto> actividadesDto) {
        // Mapear el DTO a la entidad PardiaAluplan
        PardiaAluplan pardiaAluplan = modelMapper.map(pardiaAluplanDto, PardiaAluplan.class);

        // Guardar el parte de día en la base de datos
        PardiaAluplan pardiaAluplanGuardado = pardiaAluplanRepository.save(pardiaAluplan);

        // Procesar las actividades relacionadas
        actividadesDto.forEach(dto -> {
            PardiaAluplanActmod relacion = new PardiaAluplanActmod();
            relacion.setPardiaAluplan(pardiaAluplanGuardado);

            // Recuperar la actividad módulo correspondiente
            Optional<ActividadesModulos> actividadModuloOpt = actividadesModulosRepository.findById(dto.getIdActividadModulo());
            if (actividadModuloOpt.isPresent()) {
                // Asignar la actividad recuperada a la relación
                relacion.setActividadModulo(actividadModuloOpt.get());
                // Guardar la relación
                pardiaAluplanActmodRepository.save(relacion);
            }
            // Si no se encuentra la actividad, simplemente no se crea la relación
        });

        // Devolver el parte de día creado como DTO
        return modelMapper.map(pardiaAluplanGuardado, PardiaAluplanDto.class);
    }

    @Override
    @Transactional
    public PardiaAluplanDto actualizarPardiaAluplan(Long id, PardiaAluplanDto pardiaAluplanDto, List<PardiaAluplanActmodDto> actividadesDto) {
        // Buscar el parte de día existente en la base de datos
        Optional<PardiaAluplan> pardiaAluplanOpt = pardiaAluplanRepository.findById(id);
        if (!pardiaAluplanOpt.isPresent()) {
            // Si no se encuentra el parte, no hacer nada y devolver null
            return null;
        }

        // Obtener la entidad existente
        PardiaAluplan pardiaAluplanExistente = pardiaAluplanOpt.get();

        // Actualizar los campos del parte de día existente
        modelMapper.map(pardiaAluplanDto, pardiaAluplanExistente);

        // Guardar los cambios en la base de datos
        PardiaAluplan pardiaAluplanActualizado = pardiaAluplanRepository.save(pardiaAluplanExistente);

        // Eliminar las relaciones existentes entre el parte de día y actividades
        List<PardiaAluplanActmod> relacionesExistentes = pardiaAluplanActmodRepository.findByPardiaAluplanId(pardiaAluplanActualizado.getIdPardiaAluplan());
        if (!relacionesExistentes.isEmpty()) {
            for (PardiaAluplanActmod relacion : relacionesExistentes) {
                pardiaAluplanActmodRepository.delete(relacion);
            }
        }

        // Crear nuevas relaciones basadas en la lista proporcionada
        actividadesDto.forEach(dto -> {
            PardiaAluplanActmod nuevaRelacion = new PardiaAluplanActmod();
            nuevaRelacion.setPardiaAluplan(pardiaAluplanActualizado);

            // Recuperar la actividad módulo correspondiente
            Optional<ActividadesModulos> actividadModuloOpt = actividadesModulosRepository.findById(dto.getIdActividadModulo());
            if (actividadModuloOpt.isPresent()) {
                // Asignar la actividad recuperada a la nueva relación
                nuevaRelacion.setActividadModulo(actividadModuloOpt.get());
                // Guardar la nueva relación
                pardiaAluplanActmodRepository.save(nuevaRelacion);
            }
            // Si no se encuentra la actividad, simplemente no se crea la relación
        });

        // Devolver el parte de día actualizado como DTO
        return modelMapper.map(pardiaAluplanActualizado, PardiaAluplanDto.class);
    }

    @Override
    @Transactional
    public boolean borrarPardiaAluplan(Long idPardiaAluplan) {
        if (idPardiaAluplan == null) {
            return false; // Si el ID es nulo, no hacer nada
        }

        // Buscar el parte de día por ID
        Optional<PardiaAluplan> pardiaAluplanOpt = pardiaAluplanRepository.findById(idPardiaAluplan);
        if (!pardiaAluplanOpt.isPresent()) {
            return false; // Si no se encuentra, no hacer nada
        }

        // Obtener la entidad correspondiente
        PardiaAluplan pardiaAluplan = pardiaAluplanOpt.get();

        // Eliminar relaciones entre parte de día y actividades
        List<PardiaAluplanActmod> relaciones = pardiaAluplanActmodRepository.findByPardiaAluplanId(idPardiaAluplan);
        if (!relaciones.isEmpty()) {
            relaciones.forEach(pardiaAluplanActmodRepository::delete);
        }

        // Eliminar parte semanal asociado, si existe
        if (pardiaAluplan.getParsemAluplan() != null) {
            parsemAluplanRepository.deleteById(pardiaAluplan.getParsemAluplan().getIdParsemAluplan());
        }

        // Eliminar el parte de día
        pardiaAluplanRepository.delete(pardiaAluplan);

        return true;
}

    @Override
    public Optional<PardiaAluplanDto> buscarPorId(Long id) {
        return pardiaAluplanRepository.findById(id)
                .map(entity -> modelMapper.map(entity, PardiaAluplanDto.class));
    }

    @Override
    public List<InfoActividadesParteDiaDto> getInfoActividadesPorConvProyYFecha(Long idConvProyAlu, String fecha) {

        List<InfoActividadesParteDiaProjection> proyecciones = pardiaAluplanRepository.findActividadesPorConvProyYFecha(idConvProyAlu, fecha);

        // Si no hay resultados, devolver un objeto con valores predeterminados
        if (proyecciones.isEmpty()) {
            InfoActividadesParteDiaDto defaultDto = new InfoActividadesParteDiaDto();
            defaultDto.setActividadesRelacionadasCheck(0);

            return List.of(defaultDto);
        }

        return proyecciones.stream()
                .map(projection -> modelMapper.map(projection, InfoActividadesParteDiaDto.class))
                .collect(Collectors.toList());
    }
}
