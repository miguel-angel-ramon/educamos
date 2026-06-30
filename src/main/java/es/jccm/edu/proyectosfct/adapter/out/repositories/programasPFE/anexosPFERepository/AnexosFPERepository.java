package es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.anexosPFERepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.AnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.QAnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.InfoAnexosProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AnexosFPERepository extends AbstractRepository<AnexosProgPFE, Long, QAnexosProgPFE> {

	@Query(value="select NVL(id_aneautprog,-1) as id, "
			+ "              ane.id_anexo_rodal as idRodal,  "
			+ "              ane.tx_anexo_fichero as fichero, "
			+ "              tip.nu_orden as tipo "
			+ "   from FCT_TIPAUTPROG tip, FCT_ANEAUTPROG ane "
			+ "   where ane.id_progperfor(+) = :idProgramaFPE  "
			+ "   AND ane.id_tipautprog(+) = tip.id_tipautprog "
			+ "   order by tip.nu_orden", nativeQuery = true)
	List<InfoAnexosProjection> getInfoAnexosAut(Long idProgramaFPE);

	List<AnexosProgPFE> findByIdProgramaFPEAndIdTipAutPro(Long idProgramaFPE, Integer tipo);
	

}
