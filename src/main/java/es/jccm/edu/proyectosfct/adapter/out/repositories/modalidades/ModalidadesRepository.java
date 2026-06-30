package es.jccm.edu.proyectosfct.adapter.out.repositories.modalidades;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.domain.modalidades.QModalidad;
import es.jccm.edu.proyectosfct.application.domain.modalidades.projection.ModalidadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ModalidadesRepository extends AbstractRepository<Modalidad, Long, QModalidad> {
	
	@Query(value = "select distinct moda.x_modalidad AS ID , moda.s_modalidad AS DESCRIPCIONCORTA , moda.d_modalidad AS DESCRIPCIONLARGA "
			+ "from tlofematrgen omg, tlprogramas pro, tlofegenprog ogp, tlmodalidades moda, "
			+ "(select ds_abrev from FCT_TIPOS_PROYECTOS where id_tipo_proyecto = ?4 ) tipo "
			+ "where omg.x_ofertamatrig = ogp.x_ofertamatrig "
			+ "and pro.x_programa = ogp.x_programa "
			+ "and omg.x_modalidad = moda.x_modalidad "
			+ "and ogp.x_centro = ?1 "
			+ "and pro.x_tipoprograma in (124) "
			+ "and ?2 between ogp.c_anno and nvl(ogp.c_annohasta,2099) "
			+ "and ?2 between omg.c_anno and nvl(omg.c_annotermina,2099) "
			+ "and moda.x_familia = ?3 "			
			+ "and ((tipo.ds_abrev <> 'B' and pro.l_materia = 'S')  "
			+ "or (tipo.ds_abrev = 'B' and pro.l_materia = 'N')) "			
			+ "order  by moda.d_modalidad ", nativeQuery = true)
	List<ModalidadProjection> getAllModalidadesFamiliaCentro(Long idCentro, int cAnno, Long idFamilia, Long idTipo);

}
