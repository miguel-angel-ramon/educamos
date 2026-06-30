package es.jccm.edu.proyectosfct.adapter.out.repositories.programas;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.programas.entities.ParteSemanalAnexosPrograma;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.QParteSemanalAnexosPrograma;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ListadoAnnoCentroProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ParteSemanalAnexosProgramaRepository extends AbstractRepository<ParteSemanalAnexosPrograma, Long, QParteSemanalAnexosPrograma>{

	@Query(value = "SELECT tma.X_CENTRO AS centro, tma.C_ANNO AS anno "
			+ " FROM FCT_CONVPROG_ALU fca, TLMATALU tma "
			+ " WHERE fca.X_MATRICULA = tma.X_MATRICULA "
			+ " AND fca.ID_CONVPROG_ALU = :idConvProgAlu", nativeQuery = true)
	ListadoAnnoCentroProjection getAnnoCentroByIdConvProgAlu(Long idConvProgAlu);

	List<ParteSemanalAnexosPrograma> findAllByIdConvProgAlu(Long idConvProgAlu);
}
