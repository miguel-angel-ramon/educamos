package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QCursoProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;

public interface CursoProyectoRepository extends AbstractRepository<CursoProyecto, Long, QCursoProyecto> {
	
	@Query(value = "select distinct omg.n_orden || 'º Curso' curso, omg.x_ofertamatrig AS idOfertamatrig, omg.n_orden AS orden "
				 + "from tlofematrgen omg, tlprogramas pro, tlofegenprog ogp "
				 + "where omg.x_ofertamatrig = ogp.x_ofertamatrig "
				 + "and pro.x_programa = ogp.x_programa "
				 + "and ogp.x_centro = ?3 "
				 + "and pro.x_tipoprograma in (124) "
				 + "and ?2 between ogp.c_anno and nvl(ogp.c_annohasta,2099) "
				 + "and ((?4 = 'B' and pro.l_materia = 'N') OR pro.l_materia = 'S') "
				 + "and ?2 between omg.c_anno and nvl(omg.c_annotermina,2099) "
				 + "and omg.x_modalidad = ?1 "
				 + "order by omg.n_orden ", nativeQuery = true)
	List<CursoModalidadProjection> findCursosByModalidad(Long idModalidad, Integer cAnno, Integer idCentro, String idTipoMod);

	List<CursoProyecto> findAllByProyectoId(Long idProyecto);

	CursoProyecto findByIdOfertamatrigIdAndProyectoId(Long idOfertamatrig, Long idProyecto);

	Optional<Object> findByProyectoIdAndIdOfertamatrigId(Long idProyecto, Long idOfertamatrig);
}
