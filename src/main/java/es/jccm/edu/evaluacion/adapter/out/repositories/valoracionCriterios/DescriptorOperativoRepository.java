package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import es.jccm.edu.evaluacion.application.domain.evaluacion.CompetenciaClave;
import es.jccm.edu.evaluacion.application.domain.evaluacion.DescriptorOperativo;
import es.jccm.edu.evaluacion.application.domain.evaluacion.QDescriptorOperativo;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface DescriptorOperativoRepository
		extends AbstractRepository<DescriptorOperativo, Long, QDescriptorOperativo> {

	Iterable<DescriptorOperativo> findAllByCompetenciaClaveAndIdEtapa(CompetenciaClave competenciaClave, Long idEtapa);

}
