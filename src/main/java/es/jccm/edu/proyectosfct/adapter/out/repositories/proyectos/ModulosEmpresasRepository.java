package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosEmpresas;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QModulosEmpresas;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ModulosEmpresasRepository extends AbstractRepository<ModulosEmpresas, Long, QModulosEmpresas> {

	List<ModulosEmpresas> findAllByModuloCursoId(Long idModuloCurso);

	Optional<ModulosEmpresas> findByModuloCursoIdAndConvenioProyectoId(Long idModCurso, Long idConvProy);

	List<ModulosEmpresas> findAllByConvenioProyectoId(Long idConvProy);

	@Query(value = "delete fct_modulos_empresas where id_conv_proy = :idConvProy", nativeQuery = true)
	void deleteAllByIdConvProy(Long idConvProy);

}
