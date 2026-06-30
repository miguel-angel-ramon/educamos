package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;


import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QModulosCurso;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloModalidadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ModulosCursoRepository extends AbstractRepository<ModulosCurso, Long, QModulosCurso> {
	
	@Query(value = "select momg.c_codigomec codigo, "
				 + "momg.x_materiaomg idMateriaomg,  "
				 + "mcur.d_materiac modulo, "
				 + "momg.n_horasanuales as horasAnuales, "
				 + "momg.n_horas/60 as horasSemanales "
				 + "from tlofematrgen omg, tlmatofematrg momg, tlmateriascurso mcur, tlmatofematrcen momc, tlprogramas pro, tlofegenprog ogp, tlmatomcprog mop "
				 + "where omg.x_ofertamatrig = momg.x_ofertamatrig "
				 + "and momg.x_materiac = mcur.x_materiac "
				 + "and omg.x_ofertamatrig = ogp.x_ofertamatrig "
				 + "and pro.x_programa = ogp.x_programa "
				 + "and pro.x_programa = mop.x_programa "
				 + "and ogp.x_centro = momc.x_centro "
				 + "and momc.x_materiaomc = mop.x_materiaomc "
				 + "and momg.x_materiaomg = momc.x_materiaomg "
				 + "and ogp.x_centro = ?4 "
				 + "and pro.x_tipoprograma in (124) "
				 + "and ?3 between ogp.c_anno and nvl(ogp.c_annohasta,2099) "
				 + "and ?3 between mop.c_anno and nvl(mop.c_annohasta,2099) "
				 + "and ?3 between omg.c_anno and nvl(omg.c_annotermina,2099) "
				 + "and omg.x_modalidad = ?2 "
				 + "and omg.x_ofertamatrig = ?1 "
				 + "order by omg.n_orden, mcur.d_acta ", nativeQuery = true)
	List<ModuloModalidadProjection> findModuloByModalidad(Long idOfertamatrig, Long idModalidad, Integer cAnno, Integer idCentro);

	List<ModulosCurso> findAllByCursoProyectoId(Long id);
	
	@Query(value = "select MOD.* from FCT_CURSOS_PROYECTOS CUR, FCT_MODULOS_CURSOS MOD " + 
			"WHERE CUR.ID_PROYECTO = ?1 " + 
			"AND MOD.ID_CURSO_PROYECTO =  CUR.ID_CURSO_PROYECTO ", nativeQuery = true)
	List<ModulosCurso> findAllModuloCursoByProyectoId(Long idProyecto);

	@Query(value = "select MOD.* from FCT_CURSOS_PROYECTOS CUR, FCT_MODULOS_CURSOS MOD " +
			"WHERE CUR.ID_PROYECTO = ?1 " +
			"AND MOD.ID_CURSO_PROYECTO =  CUR.ID_CURSO_PROYECTO ", nativeQuery = true)
	List<ModulosCurso> findAllByIdModulo(Long idModulo);

	Integer countByCursoProyectoProyectoId(Long idProyecto);

	boolean existsByCursoProyectoId(Long idCurso);
}
