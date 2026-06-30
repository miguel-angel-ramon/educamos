package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;

public interface IModulosCursosService {

	List<ModulosCurso> getAllModulosCurso();

	List<ModulosCurso> findAllModuloCursoByProyectoId(Long idProyecto);

	ModulosCurso getById(Long id);
}
