package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ImpuestoTipoServicio;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QImpuestoTipoServicio;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TipoServicioCentroAnno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ImpuestoTipoServicioRepository extends AbstractRepository<ImpuestoTipoServicio, Long, QImpuestoTipoServicio> {
	
	@Query(value = "select* from FCT_TIPSERCEN tip, FCT_TIPSERCENANNO tpa, FCT_IMPTIPSER imp "
			+ "where tip.cd_tipsercen = :tipServicio "
			+ "and tpa.id_TIPSERCEN = tip.id_TIPSERCEN "
			+ "and tpa.c_anno = :cAnno "
			+ "and imp.ID_TIPSERCENANNO = tpa.ID_TIPSERCENANNO " ,nativeQuery = true)
	List<ImpuestoTipoServicio> getTipoImpuesto(Long cAnno, String tipServicio);

	ImpuestoTipoServicio findByTipoServicioCentroAnnoAnnoAndTipoServicioCentroAnnoTipoServicioCentroCodigo(Integer cAnno, String tipServicio);

}
