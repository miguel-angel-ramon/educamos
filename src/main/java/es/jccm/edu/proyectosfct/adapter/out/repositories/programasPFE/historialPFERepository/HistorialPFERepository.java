package es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.historialPFERepository;

import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.QHistorialProgramaPFE;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;

@Repository
public interface HistorialPFERepository extends AbstractRepository<HistorialProgramaPFE, Long, QHistorialProgramaPFE> {

	List<HistorialProgramaPFE> findByIdProgramaFPEAndIdAneRodalIsNotNull(Long id);

	List<HistorialProgramaPFE> findByIdProgramaFPE(Long id);

}
