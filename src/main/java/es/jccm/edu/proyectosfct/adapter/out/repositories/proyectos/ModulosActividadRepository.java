package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import java.util.List;
import java.util.Optional;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosActividad;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QModulosActividad;

public interface ModulosActividadRepository extends AbstractRepository<ModulosActividad, Long, QModulosActividad> {

	List<ModulosActividad> findAllByModuloCursoId(Long idModuloCurso);

	Optional<ModulosActividad> findByModuloCursoIdAndDatoProyectoId(Long idModCurso, Long idDatProy);

}
